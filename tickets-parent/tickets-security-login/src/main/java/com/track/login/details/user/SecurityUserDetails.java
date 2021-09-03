package com.track.login.details.user;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-18 09:37
 *
 * UserDetails提供用户基本信息，但一般不会用它来存储用户信息，如getUserName(),getPassword(),List<? extends GrantedAuthority>  getAuthorities()等；
 * 而是包装后放到Authentication中的Principle和authorities，使用UsernamePasswordAuthenticationToken()封装
 * 用户一般需要自定义一个继承user的并实现UserDetails的SecurityUserDetails类，来满足额外的需求，比如账号过期、禁用等等

 * 账号过期、是否启用、禁用、密码是否过期等都是通过接口AuthenticationProvider默认的实现类AbstractUserDetailsAuthenticationProvider的DefaultPreAuthenticationChecks
 * 和DefaultPostAuthenticationChecks方法来判断；
 *
 * 加入自定义AuthenticationProvider接口的实现类，则不走默认的实现类的判断，需要自己判断
 *
 *
 * 代表了Spring Security的用户实体类，带有用户名、密码、权限特性等性质
 * 实现该接口来定义自己认证用户的获取方式
 *
 * 认证成功后会将 UserDetails 赋给 Authentication 的 principal 属性，然后再把 Authentication
 * 保存到 SecurityContext 中，之后需要实用用户信息时通过 SecurityContextHolder 获取存放在
 * SecurityContext 中的 Authentication 的 principal
 *
 * 使用构造函数赋值,使用注解构造一个无参构造函数
 */
@Slf4j
@NoArgsConstructor
public class SecurityUserDetails extends UserInfoBo implements UserDetails {

    public SecurityUserDetails(UserInfoBo userInfoBo){

        if (userInfoBo != null){
            //必须对username和password赋值
            this.setUsername(userInfoBo.getUsername());
            this.setPassword(userInfoBo.getPassword());
            this.setEnabled(userInfoBo.getEnabled());
            this.setEmail(userInfoBo.getEmail());
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        return authorityList;
    }

    /**
     * 账户是否过期
     *
     * true 未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     *
     * false 禁用
     * true 启用
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {

        return this.getEnabled();
    }

    /**
     * 密码是否过期
     *
     * @return true 没过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }
}
