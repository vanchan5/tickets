package com.track.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.ListUtil;
import com.track.common.utils.TreeUtil;
import com.track.core.base.service.AbstractService;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.permission.SysPermissionPo;
import com.track.data.domain.po.permission.SysRolePermissionPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.permission.save.SavePermissionDto;
import com.track.data.mapper.permission.SysPermissionMapper;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import com.track.data.vo.permission.permission.GetAllPermissionsVo;
import com.track.data.vo.permission.permission.GetUserMenuListVo;
import com.track.permission.ISysPermissionService;
import com.track.security.permissions.MyFilterInvocationSecurityMetadataSource;
import com.track.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysPermissionServiceImpl extends AbstractService<SysPermissionMapper, SysPermissionPo> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper mapper;

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MyFilterInvocationSecurityMetadataSource mySecurityMetadataSource;

    /**
     * @Author chauncy
     * @Date 2019-10-28 23:37
     * @Description //保存菜单权限
     *
     * @Update chauncy
     *
     * @param  savePermissionDto
     * @return void
     **/
    @Override
    public void savePermission(SavePermissionDto savePermissionDto) {

        UmUserPo userPo = securityUtil.getSysCurrUser();

        //新增
        if (savePermissionDto.getId() == 0){
           //title标题不能重复
            List<String> titleList = mapper.selectList(null).stream().map(a->a.getTitle()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(titleList) && titleList.contains(savePermissionDto.getTitle())){
                throw new ServiceException(ResultCode.DUPLICATION,String.format("标题【%s】已存在，请重新输入!",savePermissionDto.getTitle()));
            }

            SysPermissionPo sysPermissionPo = new SysPermissionPo();
            BeanUtils.copyProperties(savePermissionDto,sysPermissionPo);
            sysPermissionPo.setCreateBy(userPo.getId());
            sysPermissionPo.setId(null);
            mapper.insert(sysPermissionPo);
            //重新加载权限
            mySecurityMetadataSource.loadResourceDefine();
        }
        //编辑
        else {
            SysPermissionPo sysPermissionPo = mapper.selectById(savePermissionDto.getId());
            //判断权限是否存在
            if (sysPermissionPo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,"该菜单权限ID不存在，请检查");
            }
            //tilte不能重复
            String title = sysPermissionPo.getTitle();
            List<String> titleList = mapper.selectList(null).stream().map(a->a.getTitle())
                    .filter(b->!b.equals(title)).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(titleList) && titleList.contains(savePermissionDto.getTitle())){
                throw new ServiceException(ResultCode.DUPLICATION,String.format("title【%s】已存在，请重新输入!",savePermissionDto.getTitle()));
            }
            BeanUtils.copyProperties(savePermissionDto,sysPermissionPo);
            sysPermissionPo.setUpdateBy(userPo.getId());
            mapper.updateById(sysPermissionPo);
            //重新加载权限
            mySecurityMetadataSource.loadResourceDefine();
            //修改了权限需要把所有缓存用户的菜单删除
            Set<String> keyUserMenu = redisTemplate.keys("permission::userMenuList:*");
            redisTemplate.delete(keyUserMenu);
        }
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 10:54
     * @Description //获取全部权限列表
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.permission.permission.GetAllPermissionsVo>
     **/
    @Override
    public List<GetAllPermissionsVo> getAllPermissions() {

        List<GetAllPermissionsVo> permissionsVos = mapper.getAllPermissions();
        List<GetAllPermissionsVo> permissions = Lists.newArrayList();
        try {
            permissions = TreeUtil.getTree(permissionsVos,"id","parentId","children");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return permissions;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:16
     * @Description //获取用户页面菜单数据，注意去重，可以加入缓存
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.permission.permission.GetUserMenuListVo>
     **/
    @Override
    public List<GetUserMenuListVo> getUserMenuList() {

        UmUserPo userPo = securityUtil.getSysCurrUser();
        List<GetUserMenuListVo> menuList = new ArrayList<>();
        //读取缓存
        String key = SecurityConstant.USER_MENU_LIST+ userPo.getId();
        String menus = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(menus)){
            menuList = new Gson().fromJson(menus,new TypeToken<List<GetUserMenuListVo>>(){}.getType());
            return menuList;
        }
        menuList = mapper.selectByUserId(userPo.getId());
        List<GetUserMenuListVo> userMenuListVoList = new ArrayList<>();
        try {
            userMenuListVoList = TreeUtil.getTree(menuList,"id","parentId","children");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(ResultCode.FAIL,"将用户菜单权限转为树结构出错");
        }
        //放入缓存
        redisTemplate.opsForValue().set(key,new Gson().toJson(userMenuListVoList));

        return userMenuListVoList;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 21:29
     * @Description //批量删除菜单权限,被角色关联的权限不能删除
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    @Override
    public void delByIds(List<Long> ids) {
        ids.forEach(a->{
            SysPermissionPo sysPermissionPo = mapper.selectById(a);
            if (sysPermissionPo != null){
                throw new ServiceException(ResultCode.FAIL,String.format("不存在ID为【%s】的权限",a));
            }
            List<SysRolePermissionPo> list = rolePermissionMapper.selectList(new QueryWrapper<SysRolePermissionPo>()
                    .lambda().eq(SysRolePermissionPo::getPermissionId,a));
            if (!ListUtil.isListNullAndEmpty(list)){
                throw new ServiceException(ResultCode.FAIL,String.format("删除失败,包含正被角色使用关联的菜单或操作权限【%s】",sysPermissionPo.getName()));
            }
        });

        //批量删除
        mapper.deleteBatchIds(ids);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
    }
}
