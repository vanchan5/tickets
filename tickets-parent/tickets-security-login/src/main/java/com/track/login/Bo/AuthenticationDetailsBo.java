package com.track.login.Bo;

import com.track.login.enums.LoginTypeEnum;
import lombok.Data;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码、账号、密码等
 *
 * 额外数据实体
 */
@Data
public class AuthenticationDetailsBo {

    //账号
    private String username;

    //邮件
    private String email;

    //密码
    private String password;

    //手机号码
    private String phone;

    //验证码
    private String verifyCode;

    //登录方式
    private LoginTypeEnum loginType;

    //是否保持登录
    private String saveLogin;
}
