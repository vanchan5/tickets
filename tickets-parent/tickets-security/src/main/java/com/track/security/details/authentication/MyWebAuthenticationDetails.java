package com.track.security.details.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.sys.LoginTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
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

    //手机号码
    private final String phone;
    //验证码
    private final String verifyCode;
    //登录方式
    private final LoginTypeEnum loginType;
    //通过第三方登录获得的用户唯一idf
    private final String unionId;






    public MyWebAuthenticationDetails(HttpServletRequest request) {

        super(request);

        //json方式登录
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream jsonData = request.getInputStream()) {
                log.info(request.getInputStream().toString());
                Map<String, String> jsonAuthenticationBean = objectMapper.readValue(jsonData, Map.class);

                phone = jsonAuthenticationBean.get(SecurityConstant.PHONE);
                verifyCode = jsonAuthenticationBean.get(SecurityConstant.VERIFY_CODE);
                unionId = jsonAuthenticationBean.get(SecurityConstant.UNION_ID);
                loginType = LoginTypeEnum.valueOf(jsonAuthenticationBean.get(SecurityConstant.LOGIN_TYPE));


            } catch (IOException e) {
                //异常处理
                log.error(e.getMessage());
                throw new ServiceException(ResultCode.FAIL, "根据输入流获取json来获取额外信息异常!");

            }
        }

        //表单处理方式
        else {
            phone = request.getParameter("phone");
            verifyCode = request.getParameter("verifyCode");
            //登录类型
            loginType = LoginTypeEnum.valueOf(request.getParameter("loginType"));
            unionId = request.getParameter("unionId");
        }
    }
}
