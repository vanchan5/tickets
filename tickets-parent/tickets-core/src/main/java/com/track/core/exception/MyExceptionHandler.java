package com.track.core.exception;

import com.track.common.enums.system.ResultCode;
import com.track.common.utils.LoggerUtil;
import com.track.core.interaction.JsonViewData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author cheng
 * @create 2019-09-01 13:33
 * <p>
 * 统一异常拦截
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    /**
     * 处理所有自定义异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonViewData handleServiceException(Exception e) {
        LoggerUtil.error(e);
        LoggerUtil.error(e.getLocalizedMessage());
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return new JsonViewData(serviceException.getResultCode(), serviceException.getLocalizedMessage(), serviceException.getData());
        } else if (e instanceof MethodArgumentNotValidException) {
            FieldError fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
            Object errorMessage = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            Object value = fieldError.getRejectedValue();
            return new JsonViewData(ResultCode.PARAM_ERROR, String.format("【%s】为【%s】:%s", field, value, errorMessage));
        } else {
            return new JsonViewData(ResultCode.SYSTEM_ERROR, e.toString());
        }

    }
}