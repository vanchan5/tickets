package com.track.security.filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.LoggerUtil;
import com.track.core.interaction.JsonViewData;
import com.track.data.bo.security.TokenUserBo;
import com.track.security.util.ResponseUtil;
import com.track.security.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2019-10-17 13:53
 * <p>
 * 重写BasicAuthenticationFilter的doFilterInternal()方法用户的每次请求都会被拦截,token有效并用户没选择保持登录状态则更新失效时间
 * <p>
 * token过滤器来验证token有效性，主要处理根据请求中携带的header即token来获取用户信息并进行判断以及生成security的token
 *
 * 校验token有效性和生成Authentication(new UsernamePasswordAuthenticationToken())有两种方式
 *
 * 1：redis 2、jwt
 *
 */
@Slf4j
public class MyAuthenticationFilter extends BasicAuthenticationFilter {

    private Boolean tokenRedis;

    private Integer tokenExpireTime;

    private Boolean storePerms;

    private StringRedisTemplate redisTemplate;

    private SecurityUtil securityUtil;


    public MyAuthenticationFilter(AuthenticationManager authenticationManager, Boolean tokenRedis, Integer tokenExpireTime,
                                  Boolean storePerms, StringRedisTemplate redisTemplate, SecurityUtil securityUtil) {
        super(authenticationManager);
        this.tokenRedis = tokenRedis;
        this.tokenExpireTime = tokenExpireTime;
        this.storePerms = storePerms;
        this.redisTemplate = redisTemplate;
        this.securityUtil = securityUtil;
    }

    public MyAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    /**
     * @Author chauncy
     * @Date 2019-10-17 17:37
     * @Description //重写请求过滤,每次请求都设置认证信息并检查token是否有效getAuthentication(header, response)
     *
     * @Update chauncy
     *
     * @param  request
     * @param  response
     * @param  chain
     * @return void
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstant.HEADER);
        if (StringUtils.isBlank(header)) {
            header = request.getParameter(SecurityConstant.HEADER);
        }
        Boolean notValid = StringUtils.isBlank(header) || (!tokenRedis && !header.startsWith(SecurityConstant.TOKEN_SPLIT));
        if (notValid) {
            chain.doFilter(request, response);
            return;
        }
        try {
            //如果authentication为空，则跳转到还未登录接口
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            //将用户个体信息、权限等set进Authentication中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            LoggerUtil.error(e);
        }

        chain.doFilter(request, response);
    }
    
    /**
     * @Author chauncy
     * @Date 2019-10-17 17:17
     * @Description //设置认证信息并检查token是否有效
     *
     * @Update chauncy
     *       
     * @param  header
     * @param  response
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     **/
    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        //用户名
        String username = null;
        String password = null;
        //权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        /**接下来需要判断使用的是redis和token交互还是jwt和token交互**/
        //redis交互
        if (tokenRedis){
            String v = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE+header);
            //该token不存在
            if (StringUtils.isBlank(v)){
                log.error("redis不存在该token,原因：token已被清除或已经过期");
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.NO_LOGIN,"未登陆或登陆已超时！"));
                return null;
            }
            //该token存在
            else {
                //根据token获取对应的用户信息(key-value)
                TokenUserBo tokenUserBo = new Gson().fromJson(v,TokenUserBo.class);
                username = tokenUserBo.getUsername();
                password = tokenUserBo.getPassword();
                //缓存了权限
                if (storePerms){
                    for (String ga : tokenUserBo.getPermissions()){
                        authorities.add(new SimpleGrantedAuthority(ga));
                    }
                }
                //不缓存权限,需要读取权限(需要从数据库获取)
                else {
                    authorities = securityUtil.getSysCurrUserPerms(username);
                }
                //判断用户是否选择的是保持登录状态,若不是则需要重新更新失效时间，否则不更新时间
                if (!tokenUserBo.getSaveLogin()){
                    redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN+username,header,tokenExpireTime, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE+header,v,tokenExpireTime,TimeUnit.MINUTES);
                }
            }
        }
        //jwt交互，解析token,根据登录成功后jwt加密密钥SecurityConstant.JWT_SIGN_KEY
        // 以及自定义权限属性SecurityConstant.AUTHORITIES获取信息
        else {

            try{
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();
                //获取用户名
                username = claims.getSubject();
                password = claims.get("password").toString();
                //获取权限，缓存了权限
                if (storePerms){
                    String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
                    if (StringUtils.isNotBlank(authority)){
                        List<String> list = new Gson().fromJson(authority,new TypeToken<List<String>>(){}.getType());
                        for (String ga : list){
                            authorities.add(new SimpleGrantedAuthority(ga));
                        }
                    }
                }
                //不缓存权限
                authorities = securityUtil.getSysCurrUserPerms(username);

            }catch (ExpiredJwtException e){
                log.error(String.valueOf(e));
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.NO_LOGIN,"jwt过期，未登陆或登陆已超时！"));
            }catch (Exception e){
                log.error(e.toString());
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL,"解析token错误"));
            }
        }

        //根据token获取的信息生成AuthenticationToken
        if (StringUtils.isNotBlank(username)){
            User principal = new User(username,"",authorities);
            return new UsernamePasswordAuthenticationToken(principal, password, authorities);
        }

        return null;
    }
}
