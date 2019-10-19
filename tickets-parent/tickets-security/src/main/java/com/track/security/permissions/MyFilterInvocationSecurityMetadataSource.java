package com.track.security.permissions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.constant.SecurityConstant;
import com.track.data.domain.po.permission.SysPermissionPo;
import com.track.data.mapper.base.IBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @Author cheng
 * @create 2019-10-19 21:20
 *
 * 权限资源管理器,为权限决断器提供支持
 * 首先加载权限表中所有的操作权限，若请求路径不在表中则放行，否则将该请求url传给决断器MyAccessDecisionManager判断
 *
 * 获取的configAttributes参数返回给权限决断器的decide方法
 *
 * 参考默认实现类DefaultFilterInvocationSecurityMetadataSource
 *
 */
@Component
@Slf4j
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //key--请求的路径，value--具体操作名称
    private Map<String, Collection<ConfigAttribute>> map = null;

    @Autowired
    private IBaseMapper<SysPermissionPo> permissionMapper;

    /**
     * @Author chauncy
     * @Date 2019-10-19 21:54
     * @Description //加载权限表中所有操作请求权限
     *
     * @Update chauncy
     *
     * @param
     * @return
     **/
    public void loadResourceDefine(){

        //获取所有为删除的已经启用的具体操作请求--url
        List<SysPermissionPo> permissionPos = permissionMapper.selectList(new QueryWrapper<SysPermissionPo>()
                .lambda().and(obj->obj.eq(SysPermissionPo::getType, SecurityConstant.PERMISSION_OPERATION)
                        .eq(SysPermissionPo::getStatus,SecurityConstant.STATUS_NORMAL)));
        log.info(String.valueOf(permissionPos.size()));
        map = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        for(SysPermissionPo permission : permissionPos) {
            if(StringUtils.isNotBlank(permission.getTitle())&& StringUtils.isNotBlank(permission.getPath())){
                configAttributes = new ArrayList<>();
                cfg = new SecurityConfig(permission.getTitle());
                //作为MyAccessDecisionManager类的decide()的第三个参数
                configAttributes.add(cfg);
                //用权限的path作为map的key，用ConfigAttribute的集合作为value
                map.put(permission.getPath(), configAttributes);
            }
        }

    }

    /**
     * @Author chauncy
     * @Date 2019-10-19 21:22
     * @Description
     *
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限,实现类AbstractSecurityInterceptor的
     * decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes)
     * 如果不在权限表中则放行
     *
     * @Update chauncy
     *
     * @param  object
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     **/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if (map == null || map.size() == 0){
            loadResourceDefine();
        }
        //获取用户HttpServletRequest请求的url
        String url = ((FilterInvocation) object).getRequestUrl();
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = map.keySet().iterator();
        //遍历权限表中所有操作请求权限
        while (iterator.hasNext()){
            String resURL = iterator.next();
            //不在权限表中则放行,在权限表中，则返回给decide方法
            if (StringUtils.isNotBlank(resURL) && pathMatcher.match(resURL,url)){
                return map.get(resURL);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
