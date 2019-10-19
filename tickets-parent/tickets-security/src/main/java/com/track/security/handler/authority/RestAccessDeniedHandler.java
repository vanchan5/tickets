package com.track.security.handler.authority;

import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.security.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.ext.fn.Json;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cheng
 * @create 2019-10-19 23:18
 *
 * 权限不足处理类
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseUtil.out(response,new JsonViewData<>(ResultCode.FAIL,"抱歉，您没有访问权限"));
    }
}
