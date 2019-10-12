package com.track.core.interaction;

import com.alibaba.fastjson.JSON;
import com.google.common.net.MediaType;
import com.track.common.enums.system.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Json 封装类
 * 用于返回前端JSON格式串
 *
 * 前后端数据交互
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/09/01 15:24
 * @Version 1.0
 */
@ApiModel(value = "response返回对象")
public class JsonViewData<T>{

    /**
     * 未登陆或超时视图
     */
    public static final JsonViewData NO_LOGIN_VIEW = new JsonViewData(ResultCode.NO_LOGIN);

    /**
     * 操作结果码
     */
    @ApiModelProperty(value = "返回状态码")
    private ResultCode resultCode;

    /**
     * 消息提示
     */
    @ApiModelProperty(value = "消息提示")
    private String message;

    /**
     * 有效数据包
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    public JsonViewData(ResultCode resultCode) {
        this(resultCode, resultCode.getDescription());
    }

    public JsonViewData(ResultCode resultCode, String message) {
        this(resultCode, message, null);
    }

    public JsonViewData(ResultCode resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public JsonViewData(T data) {
        this(ResultCode.SUCCESS, ResultCode.SUCCESS.getDescription(), data);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>(3);
        map.put("resultCode", resultCode);
        map.put("message", message);
        map.put("data", data);
        return map;
    }


    /**
     * 写入响应信息
     *
     * @param response 请求响应
     * @throws IOException I/O异常
     */
    public void write(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.JSON_UTF_8.withoutParameters().toString());
        JSON.writeJSONString(response.getWriter(), this);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
