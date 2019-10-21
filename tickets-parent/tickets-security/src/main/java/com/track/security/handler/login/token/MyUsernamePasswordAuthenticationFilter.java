package com.track.security.handler.login.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.security.provider.MyAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-10-20 21:58
 *
 * Json登录
 *
 * 继承默认的登录方案，用户密码的提取在UsernamePasswordAuthenticationFilter
 * 过滤器中的attemptAuthentication（）,需要重写这个方法改变登录方式并且必须生成authentication证书
 *
 * 这一步完成会报Error creating bean with name 'myUsernamePasswordAuthenticationFilter'
 * IllegalArgumentException: authenticationManager must be specified
 *
 * AuthenticationManager需要被指定：思路是
 *
 * 1:自定义实现AuthenticationManager
 * 2:重用WebSecurityConfigurerAdapter配置的AuthenticationManager
 *
 */
@Slf4j
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //判断是否是post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported只支持Post请求: " + request.getMethod());
        }

        //判断是否是json方式登录，使用security的HTTP规范中定义的参数常量
        //使用输入流获取json数据并使用fastJson的ObjectMapper(快)
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authentication = null;
            try(InputStream jsonData = request.getInputStream()){

                log.info(request.getInputStream().toString());

                Map<String,String> jsonAuthenticationBean = objectMapper.readValue(jsonData,Map.class);
                //系统设置默认的登录账号密码字段登录系统
                //生成证书
                authentication = new UsernamePasswordAuthenticationToken(
                        jsonAuthenticationBean.get(SecurityConstant.LOGIN_NAME_PARAM),
                        jsonAuthenticationBean.get(SecurityConstant.LOGIN_PASSWOED_PARAM));


            }catch (IOException e){
                //异常处理
                log.error(e.getMessage());
                //必须要生成证书，否则会抛出登录认证的错误
                authentication = new UsernamePasswordAuthenticationToken("","");

                throw new ServiceException(ResultCode.FAIL,"根据输入流获取json异常!");
            }finally {
                //设置详情,用户所有的请求
                setDetails(request,authentication);
                //调用AuthenticationManager的.authenticate(Authentication authentication),
                //默认的实现类是ProviderManager的authenticate(Authentication authentication)方法
                //该方法遍历AuthenticationProvider,获取额外字段需实现AuthenticationProvider接口
                return authenticationProvider.authenticate(authentication);
            }
        }
        //get请求，直接使用默认接口实现类
        else {
            return this.attemptAuthentication(request, response);
        }
    }

}
