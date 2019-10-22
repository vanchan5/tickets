package com.track.security.details.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.sys.LoginTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.JSONUtils;
import com.track.common.utils.wetch.applet.WxAppletUtil;
import com.track.core.exception.ServiceException;
import com.track.data.bo.applet.CodeToSessionBo;
import com.track.security.Bo.AuthenticationDetailsBo;
import com.track.security.util.ResponseUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码
 * @Author zhangrt
 * @Date 2019/7/2 14:38
 **/
@Data
@Slf4j
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    //额外数据实体
    private final AuthenticationDetailsBo authenticationDetailsBo = new AuthenticationDetailsBo();






    public MyWebAuthenticationDetails(HttpServletRequest request) {

        super(request);

        //json方式登录
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream jsonData = request.getInputStream()) {

                Map<String, String> jsonAuthenticationBean = objectMapper.readValue(jsonData, Map.class);
                log.info(new Gson().toJson(jsonAuthenticationBean));
                if (!LoginTypeEnum.iteratorIsExit(jsonAuthenticationBean.get(SecurityConstant.LOGIN_TYPE))){
                    throw new ServiceException(ResultCode.PARAM_ERROR,String.format("所传的loginType:【%s】枚举不存在,请检查",jsonAuthenticationBean.get(SecurityConstant.LOGIN_TYPE)));
                }
                authenticationDetailsBo.setPhone(jsonAuthenticationBean.get(SecurityConstant.PHONE));
                authenticationDetailsBo.setVerifyCode(jsonAuthenticationBean.get(SecurityConstant.VERIFY_CODE));
                authenticationDetailsBo.setOpenId(jsonAuthenticationBean.get(SecurityConstant.OPEN_ID));
                authenticationDetailsBo.setLoginType(LoginTypeEnum.valueOf(jsonAuthenticationBean.get(SecurityConstant.LOGIN_TYPE)));
                authenticationDetailsBo.setUsername(jsonAuthenticationBean.get(SecurityConstant.LOGIN_NAME_PARAM));
                authenticationDetailsBo.setPassword(jsonAuthenticationBean.get(SecurityConstant.LOGIN_PASSWOED_PARAM));
                authenticationDetailsBo.setCode(jsonAuthenticationBean.get(SecurityConstant.CODE));
                //解析code获取openId
                CodeToSessionBo codeToSessionBo = JSONUtils.toBean(
                        WxAppletUtil.codeToSession(jsonAuthenticationBean.get(SecurityConstant.CODE)),
                        CodeToSessionBo.class);
                String openId = codeToSessionBo.getOpenId();
                authenticationDetailsBo.setOpenId(openId);

            } catch (Exception e) {
                //异常处理
                log.error(e.getMessage());
                if (e instanceof ServiceException) {
                    throw new ServiceException(ResultCode.PARAM_ERROR,String.format("所传的loginType枚举不存在,请检查"));
                }
                else if (e instanceof IOException){
                    throw new ServiceException(ResultCode.FAIL, "根据输入流获取json来获取额外信息异常!");
                }
            }
        }

        //表单处理方式
        else {
            authenticationDetailsBo.setPhone(request.getParameter(SecurityConstant.PHONE));
            authenticationDetailsBo.setVerifyCode(request.getParameter(SecurityConstant.VERIFY_CODE));
            //登录类型
            authenticationDetailsBo.setLoginType(LoginTypeEnum.valueOf(request.getParameter(SecurityConstant.LOGIN_TYPE)));
            authenticationDetailsBo.setOpenId(request.getParameter(SecurityConstant.OPEN_ID));
            authenticationDetailsBo.setUsername(request.getParameter(SecurityConstant.LOGIN_NAME_PARAM));
            authenticationDetailsBo.setPassword(request.getParameter(SecurityConstant.LOGIN_PASSWOED_PARAM));
            authenticationDetailsBo.setCode(request.getParameter(SecurityConstant.CODE));
            //解析code获取openId
            CodeToSessionBo codeToSessionBo = JSONUtils.toBean(
                    WxAppletUtil.codeToSession(request.getParameter(SecurityConstant.CODE)),
                    CodeToSessionBo.class);
            String openId = codeToSessionBo.getOpenId();
            authenticationDetailsBo.setOpenId(openId);

        }
    }
}
