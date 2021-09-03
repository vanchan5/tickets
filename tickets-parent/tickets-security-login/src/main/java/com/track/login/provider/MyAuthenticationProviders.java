package com.track.login.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.user.UserTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.enums.third.ValidCodeEnum;
import com.track.core.base.service.Service;
import com.track.core.exception.LoginFailLimitException;
import com.track.data.bo.user.permission.PermissionBo;
import com.track.data.bo.user.permission.RoleBo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import com.track.data.mapper.permission.SysRoleUserMapper;
import com.track.login.details.authentication.MyWebAuthenticationDetails;
import com.track.login.details.user.SecurityUserDetails;
import com.track.login.details.user.UserInfoBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2019-10-17 13:28
 *
 * 实现认证提供者接口，将用户提交的信息加到AuthenticationManagerBuilder中，
 * 由ProviderManager管理获取并生成Authentication
 *
 */
@Component
public class MyAuthenticationProviders implements AuthenticationProvider {

    @Autowired
    private IBaseMapper<UmUserPo> userMapper;

    @Autowired
    private IBaseMapper<SysRoleUserMapper> roleUserMapper;

    @Autowired
    private IBaseMapper<SysRolePermissionMapper> rolePermissionMapper;

    @Autowired
    private Service<UmUserPo> service;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * @Author chauncy
     * @Date 2019-10-17 23:59
     * @Description //不指定具体的loginType，则提示未登录
     *
     * 验证传入的authentication信息
     * 验证成功则返回一个包含authorities，并设置isAuthorized=true的完整Authentication
     * 验证失败则抛出异常
     *
     * @Update chauncy
     *
     * @param  authentication
     * @return org.springframework.security.core.Authentication
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        //加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //获取密码并加密
        String encryptPass = bCryptPasswordEncoder.encode(authentication.getCredentials().toString());
        //获取表单中用户名,这里不能再用authentication.getName(),因为json方式登录输入流只能读一次问题还没解决
        String username = details.getAuthenticationDetailsBo().getUsername();
        //获取密码
        String password = details.getAuthenticationDetailsBo().getPassword();
        //获取邮件
        String email = details.getAuthenticationDetailsBo().getEmail();

        //根据用户名查找用户基本信息、绑定角色、权限等
        UserInfoBo userInfo = new UserInfoBo();
        UmUserPo userPo = new UmUserPo();

        switch (details.getAuthenticationDetailsBo().getLoginType()) {
            //后台账号密码灯登录，涉及权限管理
            case MANAGE_PASSWORD:
                //用户基本信息
                userPo = userMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                        .eq(UmUserPo::getUsername,username));
                getUserInfo(userInfo, userPo);

                //需要处理异常情况，不能使用SecurityUserDetails里面实现的判断方法，因为不走AuthenticationProvider接口的默认实现类AbstractUserDetailsAuthenticationProvider里面的判断了
                //MyAuthenticationProvider抛出异常,需要被SimpleUrlAuthenticationFailureHandler捕捉
                //自定义一个继承了SimpleUrlAuthenticationFailureHandler失败处理器AuthenticationFailHandler
                // 对异常进行处理，默认实现类
                if (userPo == null) {
                    throw new DisabledException("用户名不存在");
                }else if (!bCryptPasswordEncoder.matches(password, userPo.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }else if (!userPo.getEnabled()){
                    throw new LockedException("账户被禁用，请联系管理员");
                }

                //UserDetails提供用户基本信息，但一般不会用它来存储用户信息，如getUserName(),getPassword(),List<? extends GrantedAuthority>   getAuthorities()等；
                // 而是包装后放到Authentication中的Principle和authorities，使用UsernamePasswordAuthenticationToken()封装
                //用户一般需要自定义一个继承user的并实现UserDetails的SecurityUserDetails类，来满足额外的需求，比如账号过期、禁用等等
                SecurityUserDetails userDetails = new SecurityUserDetails(userInfo);
                //获取给予的授权
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                //默认的实现类也会包装,加密的权限会在FilterAuthenticationIntecepter实现类中用到，用于每次请求的添加权限
                return new UsernamePasswordAuthenticationToken(userDetails,encryptPass,authorities);

            //后台手机验证码登录
            case MANAGE_CODE:
                //用户基本信息
                userPo = userMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                        .eq(UmUserPo::getPhone,details.getAuthenticationDetailsBo().getPhone()));
                getUserInfo(userInfo, userPo);
                if (userPo == null) {
                    throw new DisabledException("用户名不存在");
                }else if (!bCryptPasswordEncoder.matches(password, userPo.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }else if (!userPo.getEnabled()){
                    throw new LockedException("账户被禁用，请联系管理员");
                }
                //验证码验证
                boolean checkCode = service.validVerifyCode(details.getAuthenticationDetailsBo().getVerifyCode(),
                        details.getAuthenticationDetailsBo().getPhone(),
                        ValidCodeEnum.LOGIN_CODE);
                if (!checkCode){
                    throw new LoginFailLimitException("验证码错误！");
                }
                SecurityUserDetails manageCodeUserDetail = new SecurityUserDetails(userInfo);
                //获取给予的授权
                Collection<? extends GrantedAuthority> manageCodeUserDetailAuthorities = manageCodeUserDetail.getAuthorities();
                return new UsernamePasswordAuthenticationToken(manageCodeUserDetail,encryptPass,manageCodeUserDetailAuthorities);

            case THIRD_WECHAT:
            case APP_PASSWORD:
                break;
            case APP_CODE:
                break;

        }
        return null;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-22 10:11
     * @Description //获取用户信息
     *
     * @Update chauncy
     *
     * @param  userInfo
     * @param  userPo
     * @return void
     **/
    private void getUserInfo(UserInfoBo userInfo, UmUserPo userPo) {
        if (userPo != null) {
            BeanUtils.copyProperties(userPo, userInfo);
        }
    }

    /**
     * 判断AuthenticationProvider是否支持authentication的补全
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
