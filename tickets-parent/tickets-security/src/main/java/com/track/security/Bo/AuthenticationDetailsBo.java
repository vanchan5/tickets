package com.track.security.Bo;

import com.track.common.enums.manage.sys.LoginTypeEnum;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-20 23:27
 *
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码、账号、密码等
 *
 * 额外数据实体
 */
@Data
public class AuthenticationDetailsBo {

    //账号
    private String username;

    //密码
    private String password;

    //手机号码
    private String phone;

    //验证码
    private String verifyCode;

    //登录方式
    private LoginTypeEnum loginType;

    //通过第三方登录获得的用户唯一idf
    private String openId;
}
