package com.track.login.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.sys.LoginTypeEnum;
import com.track.common.enums.system.SystemLogTypeEnum;
import com.track.common.utils.ListUtil;
import com.track.core.annotation.elk.SystemLog;
import com.track.core.interaction.JsonViewData;
import com.track.data.bo.security.TokenUserBo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.sys.security.UserInfoVo;
import com.track.login.Bo.AuthenticationDetailsBo;
import com.track.login.details.authentication.MyWebAuthenticationDetails;
import com.track.login.util.ResponseUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2019-10-17 20:22
 * <p>
 * 认证成功(登陆成功)处理器,继承了SavedRequestAwareAuthenticationSuccessHandler
 * 生成token、将权限缓存等操作,生成的方法有两种
 * <p>
 * 1：将信息存储在Redis
 * 1）使用UUID生成token,以username作为key,token为value
 * 2）以token为key，tokenUser为value
 * <p>
 * 2：使用JWT交互，Jwts.builder()生成包含了用户名、失效时间、权限等信息
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlers extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${tickets.token.redis}")
    private Boolean tokenRedis; //设置为true后，token将存入redis，并具有单点登录功能 设为false将使用JWT交互

    @Value("${tickets.tokenExpireTime}")
    private Integer tokenExpireTime; //token过期时间（分钟）

    @Value("${tickets.saveLoginTime}")
    private Integer saveLoginTime; //用户选择保存登录状态对应token过期时间

    @Value("${tickets.token.storePerms}")
    private Boolean storePerms;  //token中存储用户权限数据 设为true开启后可避免每次请求再获取用户权限，但有可能导致编辑权限菜单后无法读取到最新权限数据（需用户重新登录）

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IBaseMapper<UmUserPo> umUserPoIBaseMapper;

    @Override
    @SystemLog(description = "登录系统", type = SystemLogTypeEnum.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();

        //jwt时间到毫秒
        tokenExpireTime = saveLoginTime * 60 * 24;

        //根据security模块存储的认证信息Authentication获取当前登陆的用户的信息
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal()拿到的是用户主题，userDetails
        String password = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
        String loginType = ((MyWebAuthenticationDetails) authentication.getDetails()).getAuthenticationDetailsBo().getLoginType().name();
        AuthenticationDetailsBo authenticationDetailsBo = ((MyWebAuthenticationDetails) authentication.getDetails()).getAuthenticationDetailsBo();

        //使用JWT进行token交互
        String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                //主题 放入用户名
                .setSubject(username)
                //自定义属性 放入密码
                .claim("password", password)
                .claim("loginType", loginType)
                .claim("user", authenticationDetailsBo)
                //设置失效时间，根据用户选择保存登录状态对应token过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();

        ResponseUtil.out(response, new JsonViewData<String>(token));

    }
}
