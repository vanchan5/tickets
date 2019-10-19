package com.track.security.permissions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Author cheng
 * @create 2019-10-19 21:33
 *
 * 权限管理决断器(抛出异常信息)
 * 判断用户拥有的权限或角色是否有资源访问权限
 *
 */
@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    //decide()方法会在AbstractSecurityInterceptor中的beforeInvocation()和afterInvocation()方法被调用
    //configAttributes代表在权限表中的请求路径
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null){
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()){
            ConfigAttribute c = iterator.next();
            String needPerm = c.getAttribute();
            //遍历已经将用户实体、密码、权限等封装好的证书的Authentication.getAuthorities()[包含了具体操作权限和角色]
            for (GrantedAuthority ga : authentication.getAuthorities()){
                if (needPerm.trim().equals(ga.getAuthority())){
                    return;
                }
            }
        }
        //不满足以上条件则抛出异常,Not granted any authorities
        // 需要自定义实现AccessDeniedHandler接口的类处理这个异常
        throw new AccessDeniedException("抱歉，您没有访问权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
