package com.track.web.base;

import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;

/**
 * @Author cheng
 * @create 2019-09-01 22:10
 *
 *
 */
public abstract class BaseWeb {

    protected JsonViewData setJsonViewData(ResultCode resultCode) {
        return new JsonViewData(resultCode);
    }

    protected JsonViewData setJsonViewData(ResultCode resultCode, String message,Object... args) {
        return new JsonViewData(resultCode, String.format(message,args));
    }

    protected JsonViewData setJsonViewData(Object data) {
        return new JsonViewData(data);
    }

}
