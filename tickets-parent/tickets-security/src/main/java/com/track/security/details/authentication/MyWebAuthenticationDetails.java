package com.track.security.details.authentication;

import com.track.common.enums.manage.sys.LoginTypeEnum;
import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码
 * @Author zhangrt
 * @Date 2019/7/2 14:38
 **/
@Data
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
        phone=request.getParameter("phone");
        verifyCode=request.getParameter("verifyCode");
        //登录类型
        loginType=LoginTypeEnum.valueOf(request.getParameter("loginType"));
        unionId=request.getParameter("unionId");
    }
}
