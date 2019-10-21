//package com.track.security.http.servlet.input.stream;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author cheng
// * @create 2019-10-21 16:48
// *
// * 没生效--待完善
// */
//@WebFilter("/**")
//public class RequestFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
////        ServletContext context = filterConfig.getServletContext();
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        System.out.println("=====+" + req.hashCode());
//        HttpServletResponse response = (HttpServletResponse) res;
//        if ("POST".equalsIgnoreCase(request.getMethod())) {
//
//            ServletRequest requestWrapper = new RequestWrapper(request);
//            System.out.println("===filter==+" + requestWrapper.hashCode());
//
//            String body = HttpUtil.getBodyString(requestWrapper);
//            System.out.println("AccessFilter="+body);
//            chain.doFilter(requestWrapper, response);
//            return ;
//        }
//
//        chain.doFilter(req, res);
//    }
//}
