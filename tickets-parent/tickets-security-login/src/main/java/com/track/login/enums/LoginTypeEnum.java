package com.track.login.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * 系统登录方式
 */
public enum LoginTypeEnum {


    MANAGE_PASSWORD("后台账号密码登录"),

    MANAGE_EMAIL("后台账号密码登录"),

    MANAGE_CODE("后台手机验证码登录"),

    APP_PASSWORD("App密码登录"),

    APP_CODE("App手机验证码登录"),

    THIRD_WECHAT("第三方微信登录");

    private String description;

    LoginTypeEnum(String description) {
        this.description = description;
    }

    //遍历枚举值判断是否存在
    public static boolean iteratorIsExit(String enumName) {
        boolean flag = false;
        List<String> enumNames = Arrays.asList(LoginTypeEnum.values()).stream().map(a -> a.name()).collect(Collectors.toList());
        if (enumNames.contains(enumName)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
