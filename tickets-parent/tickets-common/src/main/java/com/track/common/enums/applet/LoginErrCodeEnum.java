package com.track.common.enums.applet;

import com.track.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @description: 小程序登录凭证校验错误码枚举
 * @author yeJH
 * @since 2019/10/16 22:48
 */
@Getter
public enum LoginErrCodeEnum implements BaseEnum {


    SYSTEM_BUSY(-1,"系统繁忙，请稍候再试"),
    REQUEST_SUCCESS(0,"请求成功"),
    CODE_INVALID(40029,"CODE 无效"),
    FREQUENCY_LIMIT(45011,"频率限制，每个用户每分钟100次"),
    ;

    private Integer id;

    private String name;

    LoginErrCodeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    //通过name获取结果
    public static LoginErrCodeEnum getOrderStatusEnum(Integer id) {
        for (LoginErrCodeEnum LoginErrCodeEnum : LoginErrCodeEnum.values()) {
            if (LoginErrCodeEnum.getId().equals(id))
                return LoginErrCodeEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getOrderStatusEnum(Integer.valueOf(field.toString())));
    }
}
