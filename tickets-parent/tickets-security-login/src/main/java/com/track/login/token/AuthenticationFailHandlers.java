package com.track.login.token;

import com.track.common.enums.system.ResultCode;
import com.track.core.exception.LoginFailLimitException;
import com.track.core.interaction.JsonViewData;
import com.track.login.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
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
public class AuthenticationFailHandlers extends SimpleUrlAuthenticationFailureHandler {

    @Value("${tickets.loginTimeLimit}")
    private Integer loginTimeLimit; //限制用户登陆错误次

    @Value("${tickets.loginAfterTime}")
    private Integer loginAfterTime; //错误超过次数后多少分钟后才能继续登录

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

        String username;
        //json方式登录
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            username = "";
        } else {
            username = request.getParameter("username");
        }
        //用户名或密码错误异常
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {

            ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL, "用户名或密码错误"));

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
}
