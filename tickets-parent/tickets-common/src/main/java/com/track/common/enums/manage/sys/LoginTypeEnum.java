package com.track.common.enums.manage.sys;

/**
 * @Author cheng
 * @create 2019-10-17 23:54
 *
 * 系统登录方式
 */
public enum LoginTypeEnum {

    MANAGE("总后台登录"),
    APP_PASSWORD("App密码登录"),
    APP_CODE("App手机验证码登录"),
    THIRD_WECHAT("第三方微信登录");

    private String description;

    LoginTypeEnum(String description){
        this.description=description;
    }
}
