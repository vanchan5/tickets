package com.track.security.handler.login.token;

import com.track.common.enums.system.ResultCode;
import com.track.core.exception.LoginFailLimitException;
import com.track.core.interaction.JsonViewData;
import com.track.security.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2019-10-18 13:19
 * <p>
 * 继承security默认处理认证异常的实现了AuthenticationFailureHandler接口的
 * 处理器SimpleUrlAuthenticationFailureHandler
 */
@Slf4j
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${tickets.loginTimeLimit}")
    private Integer loginTimeLimit; //限制用户登陆错误次

    @Value("${tickets.loginAfterTime}")
    private Integer loginAfterTime; //错误超过次数后多少分钟后才能继续登录

    @Autowired
    private StringRedisTemplate redisTemplate;

    //通过重写该方法，处理认证异常
    //BadCredentialsException(密码错误)、UsernameNotFoundException(用户名错误)、AccountStatusException(用户不存在或者被禁用)、InternalAuthenticationServiceException
    // 继承AuthenticationException
    //LockedException(被禁用)、DisabledException(用户不存在)继承AccountStatusException
    //LoginFailLimitException继承InternalAuthenticationServiceException

    /**
     * @param request
     * @param response
     * @param e
     * @return void
     * @Author chauncy
     * @Date 2019-10-18 13:33
     * @Description //这些异常，假如没有重写AuthenticationProvider里面的authenticate()方法，则会走默认的对应的异常处理
     * 比如AuthenticationProvider默认实现类AbstractUserDetailsAuthenticationProvider里面的DefaultPreAuthenticationChecks()
     * 方法处理用户是否被禁用，禁用则抛出DisabledException异常，账户是否过期，过期则抛出AccountExpiredException，
     * 密码是否过期，过期则抛出CredentialsExpiredException异常
     * @Update chauncy
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        //用户名或密码错误异常
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            recordLoginTime(username);
            //获取用户登录失败的次数
            String key = "loginTimeLimit:" + username;
            String value = redisTemplate.opsForValue().get(key);
            //没有失败
            if (StringUtils.isBlank(value)) {
                value = "0";
            }
            //登录失败并获取次数
            else {
                int loginFailTime = Integer.parseInt(value);
                int remainLoginTime = loginTimeLimit - loginFailTime;
                log.error(String.format("用户：【%s】登录失败，还有【%s】次机会", username, remainLoginTime));
                if (remainLoginTime <= 3 && remainLoginTime > 0) {
                    ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, String.format("用户名或密码错误,还有【%s】次尝试机会", remainLoginTime)));
                } else if (remainLoginTime <= 0) {
                    ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, String.format("登录错误次数超过限制，请【%s】分钟后再试", loginAfterTime)));
                } else {
                    ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, "用户名或密码错误"));
                }
            }
        }
        //账号被禁用
        else if (e instanceof LockedException) {
            ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, String.format("账户【%s】被禁用，请联系管理员", username)));
        }
        //用户不存在
        else if (e instanceof DisabledException) {
            ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, String.format("用户【%s】不存在，请联系管理员", username)));
        }
        //登录限制异常，这个异常在用户登录时判断(比如实现了UserDetailsService接口，用来做登陆次数限制的UserDetailsServiceImpl)
        //处理超过限制次数的异常，并显示剩余时间
        //假设不拦截处理超过登录限制异常，用户在超过此数之后在指定的多少分钟后才能登录这个时间内登录永远是10分钟之后
        //因为这个错误异常ProviderManager会抛出InternalAuthenticationServiceException
        else if (e instanceof LoginFailLimitException) {
            ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, ((LoginFailLimitException) e).getMsg()));
        } else {
            ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, "登录失败，其他内部错误"));
        }

    }

    /**
     * @param username
     * @return boolean
     * @Author chauncy
     * @Date 2019-10-18 13:46
     * @Description //根据用户名判断用户登录的次数
     * @Update chauncy
     **/
    public boolean recordLoginTime(String username) {

        String key = "loginTimeLimit:" + username;
        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(key);
        //该用户没有继续登录失败
        if (StringUtils.isBlank(value)) {
            value = "0";
        }
        //继续登录失败
        //获取登录失败次数
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
        if (loginFailTime >= loginTimeLimit) {
            redisTemplate.opsForValue().set(flagKey, "fail", loginAfterTime, TimeUnit.MINUTES);
            return false;
        }

        return true;
    }

}
