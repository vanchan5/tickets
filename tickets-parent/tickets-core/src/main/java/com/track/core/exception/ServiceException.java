package com.track.core.exception;

import com.track.common.enums.system.ResultCode;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-09-01 13:30
 *
 * 业务层抛出异常
 */
@Data
public class ServiceException extends RuntimeException {

    private ResultCode resultCode = ResultCode.SYSTEM_ERROR;

    private Object data;

    public ServiceException(ResultCode resultCode, String errorMessage) {
        this(errorMessage);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String errorMessage,Object data) {
        this(errorMessage);
        this.resultCode = resultCode;
        this.data=data;
    }


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }


}

