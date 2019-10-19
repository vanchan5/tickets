package com.track.security.permissions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author cheng
 * @create 2019-10-19 20:18
 *
 * FilterSecurityInterceptor是访问接口的最后的一个过滤器，根据自定义配置来判断请求
 * 是否允许被访问
 *
 * 1、需要自定义接口FilterInvocationSecurityMetadataSource的实现类（权限资源管理器，为权限决断器提供支持），
 *    重写getAttributes(Object 0)方法
 *    在权限表中的url作为第三个参数返回给AccessDecisionManager的decide(a,b,c)方法
 *
 *  2、自定义接口AccessDecisionManager的实现类(权限管理决断器,判断用户拥有的权限或角色是否有资源访问权限)，
 *     重写decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes)方法
 *
 */
@Component
@Slf4j
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        //将自定义的权限决断器放入到AbstractSecurityInterceptor的setAccessDecisionManager()方法中
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    //这个必须重写实现接口方法
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    /**以下是默认的方法**/

    /**
     * @Author chauncy
     * @Date 2019-10-18 23:27
     * @Description //安全元数据资源--Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
     * 				.getAttributes(object);
     * 			如果url在权限表中，则返回给decide方法，用来判定用户是否有此权限
     *
     * @Update chauncy
     *
     * @param
     * @return org.springframework.security.access.SecurityMetadataSource
     **/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    /**
     * @Author chauncy
     * @Date 2019-10-19 21:50
     * @Description
     * //默认实现类AbstractSecurityInterceptor的beforeInvocation()方法，通过
     * SecurityMetadataSource obtainSecurityMetadataSource()
     * 1、Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource().getAttributes(object);
     *    获取到属性url的值
     *    private AccessDecisionManager accessDecisionManager;
     * 2、将该值作为decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes)
     *    accessDecisionManager.decide(authenticated, object, attributes)的第三个参数传给AccessDecisionManager
     *
     * @Update chauncy
     *
     * @param  fi
     * @return void
     **/
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //doFilter()方法就是调用我们的接口处理响应求
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }


}
