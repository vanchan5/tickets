package com.track.common.exception;

import com.track.common.enums.system.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author cheng
 * @create 2019-09-01 13:30
 *
 * 工具类处理抛出异常
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UtilException extends RuntimeException {

    private ResultCode resultCode = ResultCode.SYSTEM_ERROR;

    private Object data;

    public UtilException(ResultCode resultCode, String errorMessage) {
        this(errorMessage);
        this.resultCode = resultCode;
    }

    public UtilException(ResultCode resultCode, String errorMessage, Object data) {
        this(errorMessage);
        this.resultCode = resultCode;
        this.data=data;
    }


    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable e) {
        super(message, e);
    }


}

