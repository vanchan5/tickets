package com.track.core.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author cheng
 * @create 2019-11-19 17:58
 *
 * 为logback日志增加traceId
 *
 * 这里被security模块自定义的MyAuthenticationFilter拦截了 这里不生效
 *
 */
@WebFilter(urlPatterns = "/*", filterName = "logMdcFilter")
public class LogMdcFilter implements Filter{

    private static final String UNIQUE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            chain.doFilter(request, response);
        } finally {
            if (bInsertMDC) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }
}
