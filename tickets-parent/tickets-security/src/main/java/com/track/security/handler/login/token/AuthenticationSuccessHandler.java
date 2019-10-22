package com.track.security.handler.login.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.sys.LoginTypeEnum;
import com.track.common.utils.ListUtil;
import com.track.core.interaction.JsonViewData;
import com.track.data.bo.security.TokenUserBo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.sys.security.UserInfoVo;
import com.track.security.details.authentication.MyWebAuthenticationDetails;
import com.track.security.util.ResponseUtil;
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
 *
 * 认证成功(登陆成功)处理器,继承了SavedRequestAwareAuthenticationSuccessHandler
 * 生成token、将权限缓存等操作,生成的方法有两种
 *
 * 1：将信息存储在Redis
 *     1）使用UUID生成token,以username作为key,token为value
 *     2）以token为key，tokenUser为value
 *
 *  2：使用JWT交互，Jwts.builder()生成包含了用户名、失效时间、权限等信息
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

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
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();

        //获取用户请求是否保持登陆状态:saveLogin
        String saveLogin = "";
        //json方式登录
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            saveLogin = ((MyWebAuthenticationDetails) authentication.getDetails()).getAuthenticationDetailsBo().getSaveLogin();
        }
        //表单方式
        else {
            saveLogin = request.getParameter(SecurityConstant.SAVE_LOGIN);
        }
        Boolean isSave = false;

        if (StringUtils.isNotBlank(saveLogin) && Boolean.valueOf(saveLogin)){
            isSave = true;
            //不使用redis交互，使用jwt,jwt时间到毫秒
            if (!tokenRedis){
                tokenExpireTime = saveLoginTime * 60 * 24;
            }
        }

        //根据security模块存储的认证信息Authentication获取当前登陆的用户的信息
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal()拿到的是用户主题，userDetails
        String password = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
        String loginType = ((MyWebAuthenticationDetails) authentication.getDetails()).getAuthenticationDetailsBo().getLoginType().name();
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();

        //这里的权限指的是操作类型权限，
        // 需要在实现了UserDetails接口的SecurityUserDetails实现类中重写getAuthorities()方法
        List<String> permissions = new ArrayList<>();
        if (!ListUtil.isListNullAndEmpty(authorityList)) {
            permissions = authorityList.stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        }

        //使用redis进行token交互
        String token;
        if (tokenRedis){
            //生成token并保存到redis
            token = UUID.randomUUID().toString().replace("-","");
            TokenUserBo userInfo = new TokenUserBo(username,password,permissions,isSave,loginType);

            //不缓存权限
            if (!storePerms){
                userInfo.setPermissions(null);
            }
            //单点登陆,之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
            //不用删除key为SecurityConstant.USER_TOKEN+username的记录，回覆盖掉，但是SecurityConstant.TOKEN_PRE + oldToken必须删除，因为每次token都会不一样
            if (StringUtils.isNotBlank(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            //判断用户是否选择保持登陆状态,时间单位是天数
            if (isSave){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN+username,token,saveLoginTime, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(userInfo), saveLoginTime, TimeUnit.DAYS);
            }
            else {
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE+token,new Gson().toJson(userInfo),tokenExpireTime,TimeUnit.MINUTES);
            }

        }
        //使用JWT进行token交互
        else {
            //不缓存权限
            if (!storePerms){
                permissions = null;
            }
            token = SecurityConstant.TOKEN_SPLIT+ Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES,new Gson().toJson(permissions))
                    //自定义属性 放入密码
                    .claim("password",password)
                    //设置失效时间，根据用户选择保存登录状态对应token过期时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime*60*1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512,SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        if (details.getAuthenticationDetailsBo().getLoginType().equals(LoginTypeEnum.MANAGE_PASSWORD) ||
            details.getAuthenticationDetailsBo().getLoginType().equals(LoginTypeEnum.MANAGE_CODE)) {
            ResponseUtil.out(response, new JsonViewData<String>(token));
        }else if (details.getAuthenticationDetailsBo().getLoginType().equals(LoginTypeEnum.THIRD_WECHAT)){
            UmUserPo userPo =umUserPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                    .eq(UmUserPo::getOpenId,details.getAuthenticationDetailsBo().getOpenId()));
            userInfoVo.setToken(token);
            userInfoVo.setIM(String.valueOf(userPo.getId()));
            userInfoVo.setJPush(String.valueOf(userPo.getId()));
            userInfoVo.setNickName(userPo.getNickName() == null ? userPo.getNickName() : SecurityConstant.USER_DEFAULT_NICKNAME);
            ResponseUtil.out(response, new JsonViewData<UserInfoVo>(userInfoVo));

        }else {
            UmUserPo userPo =umUserPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                    .eq(UmUserPo::getPhone,details.getAuthenticationDetailsBo().getPhone()));
            userInfoVo.setToken(token);
            userInfoVo.setIM(String.valueOf(userPo.getId()));
            userInfoVo.setJPush(String.valueOf(userPo.getId()));
            userInfoVo.setNickName(userPo.getNickName() == null ? userPo.getNickName() : SecurityConstant.USER_DEFAULT_NICKNAME);
            ResponseUtil.out(response, new JsonViewData<UserInfoVo>(userInfoVo));

        }
    }
}
