package com.track.core.exception;

import com.track.common.enums.system.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @Author cheng
 * @create 2019-10-18 14:46
 *
 * 自定义登录限制异常
 */
@Data
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private String msg;

    private ResultCode resultCode = ResultCode.SYSTEM_ERROR;

    private Object data;

    public LoginFailLimitException(ResultCode resultCode, String errorMessage) {
        this(errorMessage);
        this.resultCode = resultCode;
    }

    public LoginFailLimitException(ResultCode resultCode, String errorMessage,Object data) {
        this(errorMessage);
        this.resultCode = resultCode;
        this.data=data;
    }

    public LoginFailLimitException(String msg){
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

}
