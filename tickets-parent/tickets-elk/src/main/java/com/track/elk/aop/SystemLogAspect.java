package com.track.elk.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.track.common.utils.IpInfoUtil;
import com.track.common.utils.JSONUtils;
import com.track.common.utils.ObjectUtil;
import com.track.common.utils.ThreadPoolUtil;
import com.track.core.elk.SystemLog;
import com.track.data.domain.po.elk.SysLogPo;
import com.track.elk.po.EsLogDo;
import com.track.elk.service.EsLogService;
import com.track.elk.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-10-31 21:42
 *
 * Spring AOP实现日志管理
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Value("${tickets.logRecord.es}")
    private Boolean esRecord;

    @Autowired
    private EsLogService esLogService;

    @Autowired
    private ISysLogService logService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * Controller层切点,注解方式
     */
    //@Pointcut("execution(* *..controller..*Controller*.*(..))")
    @Pointcut("@annotation(com.track.core.elk.SystemLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{

        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);
    }


    /**
     * 后置通知(在方法执行之后返回) 用于拦截Controller层操作
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint){
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();

            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//            HttpServletRequest request = sra.getRequest();
            HttpServletResponse response = sra.getResponse();

            if (StrUtil.isNotBlank(username)) {

                if(esRecord){
                    EsLogDo esLog = new EsLogDo();

                    //日志标题
                    esLog.setName(getControllerMethodInfo(joinPoint).get("description").toString());
                    //日志类型
                    esLog.setLogType((int)getControllerMethodInfo(joinPoint).get("type"));
                    //日志请求url
                    esLog.setRequestUrl(request.getRequestURI());
                    //请求方式
                    esLog.setRequestType(request.getMethod());
                    //请求用户
                    esLog.setUsername(username);
                    //请求IP
                    esLog.setIp(ipInfoUtil.getIpAddr(request));
                    //IP地址
                    esLog.setIpInfo(ipInfoUtil.getIpCity(ipInfoUtil.getIpAddr(request)));
                    //请求开始时间
                    Date logStartTime = beginTimeThreadLocal.get();

                    long beginTime = beginTimeThreadLocal.get().getTime();
                    long endTime = System.currentTimeMillis();
                    //请求耗时(单位:毫秒)
                    Long logElapsedTime = endTime - beginTime;
                    esLog.setCostTime(logElapsedTime.intValue());
                    ipInfoUtil.getInfo(request, ObjectUtil.mapToStringAll(request.getParameterMap()));

                    String requestParam = "";

                    //请求参数Json/form
                    //json方式
                    if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                            request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                        //登录处理,由于在provider获取额外数据获取了流，故在这里获取不到请求参数，可以通过details获取，但为了不依赖security,置空先
                        if (joinPoint.getSignature().getName().equals("onAuthenticationSuccess")){
                            esLog.setRequestParam("忽略");
                        }else {
                            //获取json参数
                            Object[] args = joinPoint.getArgs();
                            //param
                            Object object = args[0];
                            Map<String, Object> jsonParam = JSONUtils.toHashMap(object);
                            requestParam = JSON.toJSONStringWithDateFormat(jsonParam, "yyyy-MM-dd HH:mm:ss", SerializerFeature.UseSingleQuotes);

                            esLog.setRequestParam(requestParam);
                        }

                    }
                    //form表单
                    else {
                        Map<String, String[]> logParams = request.getParameterMap();
                        requestParam = request.getQueryString();
                        esLog.setMapToParams(logParams);
                    }

                    //调用线程保存至ES
                    ThreadPoolUtil.getPool().execute(new SaveEsSystemLogThread(esLog, esLogService));
                }else{
                    SysLogPo sysLog = new SysLogPo();

                    //日志标题
                    sysLog.setName(getControllerMethodInfo(joinPoint).get("description").toString());
                    //日志类型
                    sysLog.setLogType((int)getControllerMethodInfo(joinPoint).get("type"));
                    //日志请求url
                    sysLog.setRequestUrl(request.getRequestURI());
                    //请求方式
                    sysLog.setRequestType(request.getMethod());
                    //请求用户
                    sysLog.setUsername(username);
                    //请求IP
                    sysLog.setIp(ipInfoUtil.getIpAddr(request));
                    //IP地址
                    sysLog.setIpInfo(ipInfoUtil.getIpCity(ipInfoUtil.getIpAddr(request)));
                    //请求开始时间
                    Date logStartTime = beginTimeThreadLocal.get();

                    long beginTime = beginTimeThreadLocal.get().getTime();
                    long endTime = System.currentTimeMillis();
                    //请求耗时
                    Long logElapsedTime = endTime - beginTime;
                    sysLog.setCostTime(logElapsedTime.intValue());
                    ipInfoUtil.getInfo(request, ObjectUtil.mapToStringAll(request.getParameterMap()));

                    String requestParam = "";

                    //请求参数Json/form
                    //json方式
                    if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                            request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                        //登录处理,由于在provider获取额外数据获取了流，故在这里获取不到请求参数，可以通过details获取，但为了不依赖security,置空先
                        if (joinPoint.getSignature().getName().equals("onAuthenticationSuccess")){
                            sysLog.setRequestParam("忽略");
                        }else {
                            //获取json参数
                            Object[] args = joinPoint.getArgs();
                            //param
                            Object object = args[0];
                            Map<String, Object> jsonParam = JSONUtils.toHashMap(object);
                            requestParam = JSON.toJSONStringWithDateFormat(jsonParam, "yyyy-MM-dd HH:mm:ss", SerializerFeature.UseSingleQuotes);

                            sysLog.setRequestParam(requestParam);
                        }

                    }
                    //form表单
                    else {
                        Map<String, String[]> logParams = request.getParameterMap();
                        requestParam = request.getQueryString();
                        sysLog.setMapToParams(logParams);
                    }

                    //调用线程保存至ES
                    ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(sysLog, logService));
                }
            }
        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
    }

    /**
     * 保存日志至ES
     */
    private static class SaveEsSystemLogThread implements Runnable {

        private EsLogDo esLog;
        private EsLogService esLogService;

        public SaveEsSystemLogThread(EsLogDo esLog, EsLogService esLogService) {
            this.esLog = esLog;
            this.esLogService = esLogService;
        }

        @Override
        public void run() {

            esLogService.saveLog(esLog);
        }
    }

    /**
     * 保存日志至数据库
     */
    private static class SaveSystemLogThread implements Runnable {

        private SysLogPo log;
        private ISysLogService logService;

        public SaveSystemLogThread(SysLogPo esLog, ISysLogService logService) {
            this.log = esLog;
            this.logService = logService;
        }

        @Override
        public void run() {

            logService.save(log);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Map<String, Object> getControllerMethodInfo(JoinPoint joinPoint) throws Exception{

        Map<String, Object> map = new HashMap<String, Object>(16);
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";
        Integer type = null;

        for(Method method : methods) {
            if(!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if(clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，原因是方法可以重载
                continue;
            }
            description = method.getAnnotation(SystemLog.class).description();
            type = method.getAnnotation(SystemLog.class).type().ordinal();
            map.put("description", description);
            map.put("type", type);
        }
//        map.put("targetName",targetName);
//        map.put("methodName",methodName);
//        map.put("arguments",arguments);
//        map.put("targetClass",targetClass);
        return map;
    }

}
