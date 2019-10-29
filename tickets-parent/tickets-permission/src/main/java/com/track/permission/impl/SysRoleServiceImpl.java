package com.track.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.ListUtil;
import com.track.common.utils.TreeUtil;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.permission.SysRolePermissionPo;
import com.track.data.domain.po.permission.SysRolePo;
import com.track.data.domain.po.permission.SysRoleUserPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.permission.save.EditDefaultRoleDto;
import com.track.data.dto.manage.permission.save.SaveRoleDto;
import com.track.data.dto.manage.permission.save.SaveRolePermissionsDto;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import com.track.data.mapper.permission.SysRoleUserMapper;
import com.track.data.vo.permission.role.GetRolePermissionVo;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.mapper.permission.SysRoleMapper;
import com.track.data.vo.permission.role.SearchRoleVo;
import com.track.permission.ISysRoleService;
import com.track.core.base.service.AbstractService;
import com.track.security.util.SecurityUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleServiceImpl extends AbstractService<SysRoleMapper, SysRolePo> implements ISysRoleService {

    @Autowired
    private SysRoleMapper mapper;

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author chauncy
     * @Date 2019-10-22 21:50
     * @Description //保存角色信息
     *
     * @Update chauncy
     *
     * @param  saveRoleDto
     * @return void
     **/
    @Override
    public void saveRole(SaveRoleDto saveRoleDto) {

        //获取当前用户
        UmUserPo userPo = securityUtil.getSysCurrUser();
        SysRolePo sysRolePo = new SysRolePo();

        //新增
        if (saveRoleDto.getId() == 0){
            //角色名不能重复
            SysRolePo roleName = mapper.selectOne(new QueryWrapper<SysRolePo>().lambda().eq(SysRolePo::getName,saveRoleDto.getName()));
            if (roleName != null){
                throw new ServiceException(ResultCode.FAIL,String.format("角色名【%s】已存在,请检查！",saveRoleDto.getName()));
            }
            //默认角色配置
            setDefaultRole(saveRoleDto, sysRolePo);
            sysRolePo.setId(null).setCreateBy(userPo.getId());
            mapper.insert(sysRolePo);
        }
        //编辑
        else {
            //判断角色是否存在
            sysRolePo = mapper.selectById(saveRoleDto.getId());
            if (sysRolePo == null){
                throw new ServiceException(ResultCode.FAIL,String.format("角色名【%s】已存在,请检查！",saveRoleDto.getName()));
            }
            //角色名不能重复
            String oldName = sysRolePo.getName();
            List<String> names = mapper.selectList(null).stream().filter(a->!a.getName().equals(oldName))
                    .map(b->b.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(names) && names.contains(saveRoleDto.getName())){
                throw new ServiceException(ResultCode.FAIL,String.format("角色名【%s】已存在,请检查！",saveRoleDto.getName()));
            }
            //默认角色配置
            setDefaultRole(saveRoleDto, sysRolePo);
            sysRolePo.setUpdateBy(userPo.getId());
            mapper.updateById(sysRolePo);
        }
    }

    /**
     * @Author chauncy
     * @Date 2019-10-23 14:03
     * @Description //分页查询角色信息
     *
     * @Update chauncy
     *
     * @param  searchRoleDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.permission.role.SearchRoleVo>
     **/
    @Override
    public PageInfo<SearchRoleVo> searchRole(SearchRoleDto searchRoleDto) {

        Integer pageNo = searchRoleDto.getPageNo() == null ? defaultPageNo : searchRoleDto.getPageNo();
        Integer pageSize = searchRoleDto.getPageSize() == null ? defaultPageSize : searchRoleDto.getPageSize();

        PageInfo<SearchRoleVo> searchRoleVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchRole(searchRoleDto));

        return searchRoleVoPageInfo;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-28 21:49
     * @Description //设置默认角色
     *
     * @Update chauncy
     *
     * @param  editDefaultRoleDto
     * @return void
     **/
    @Override
    public void setDefaultRole(EditDefaultRoleDto editDefaultRoleDto) {

        if (editDefaultRoleDto.getIsDefault()){
            //判断当前是否有默认角色，若有则置为false
            SysRolePo rolePo = mapper.selectOne(new QueryWrapper<SysRolePo>().lambda()
                    .eq(SysRolePo::getDefaultRole, true));
            if (rolePo != null) {
                rolePo.setDefaultRole(false);
                mapper.updateById(rolePo);
            }
        }

        SysRolePo rolePo = mapper.selectById(editDefaultRoleDto.getRoleId());
        if (rolePo == null){
            throw new ServiceException(ResultCode.FAIL,"角色不存在");
        }
        rolePo.setDefaultRole(editDefaultRoleDto.getIsDefault());
        mapper.updateById(rolePo);
    }

    /**
     * @Author chauncy
     * @Date 2019-10-28 22:00
     * @Description //获取菜单权限
     *
     * @Update chauncy
     *
     * @param  roleId
     * @return java.util.List<com.track.data.vo.permission.role.GetRolePermissionVo>
     **/
    @Override
    public List<GetRolePermissionVo> getPermission(String roleId) {
        if (mapper.selectById(roleId) == null){
            throw new ServiceException(ResultCode.NO_EXISTS,"该角色不存在,请检查!");
        }
        //获取角色下的权限
        List<GetRolePermissionVo> rolePermissionVoList = mapper.getPermission(roleId);
        List<Long> trueIds = rolePermissionVoList.stream().map(a->a.getId()).collect(Collectors.toList());
        List<GetRolePermissionVo> allPermissions = mapper.selectAll();
        allPermissions.stream().filter(a->trueIds.contains(a.getId())).forEach(b->{
                b.setChecked(true);
        });
        List<GetRolePermissionVo> rolePermissionsTree = Lists.newArrayList();
        try {
            rolePermissionsTree = TreeUtil.getTree(allPermissions,"id","parentId","children");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("将对象转为树形结构出错!");
        }

        return rolePermissionsTree;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:27
     * @Description //添加/编辑角色分配菜单权限
     *
     * @Update chauncy
     *
     * @param  saveRolePermissionsDto
     * @return void
     **/
    @Override
    public void saveRolePermissions(SaveRolePermissionsDto saveRolePermissionsDto) {

        UmUserPo userPo = securityUtil.getSysCurrUser();

        rolePermissionMapper.delete(new QueryWrapper<SysRolePermissionPo>().lambda()
                .eq(SysRolePermissionPo::getRoleId,saveRolePermissionsDto.getRoleId()));

        //分配权限
        saveRolePermissionsDto.getPermissionIds().forEach(a->{
            SysRolePermissionPo rolePermissionPo = new SysRolePermissionPo();
            rolePermissionPo.setId(null);
            rolePermissionPo.setRoleId(saveRolePermissionsDto.getRoleId());
            rolePermissionPo.setPermissionId(a);
            rolePermissionPo.setCreateBy(userPo.getId());
            rolePermissionMapper.insert(rolePermissionPo);
        });

    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 21:56
     * @Description //批量删除角色信息
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    @Override
    public void delByIds(List<Long> ids) {

        ids.forEach(a->{
            SysRolePo sysRolePo = mapper.selectById(a);
            if (sysRolePo == null){
                throw new ServiceException(ResultCode.FAIL,String.format("ID为【%s】的角色不存在!"));
            }
            List<SysRoleUserPo> list = roleUserMapper.selectList(new QueryWrapper<SysRoleUserPo>().lambda()
                    .eq(SysRoleUserPo::getRoleId,a));
            if (!ListUtil.isListNullAndEmpty(list)){
                throw new ServiceException(ResultCode.FAIL,String.format("删除失败,角色【%s】正被用户使用!",sysRolePo.getName()));
            }

            //删除角色与权限的关联表
            rolePermissionMapper.delete(new QueryWrapper<SysRolePermissionPo>().lambda()
                    .eq(SysRolePermissionPo::getRoleId,a));
        });

        mapper.deleteBatchIds(ids);
    }


    /**
     * @Author chauncy
     * @Date 2019-10-22 22:51
     * @Description //默认角色配置
     *
     * @Update chauncy
     *
     * @param  saveRoleDto
     * @param  sysRolePo
     * @return void
     **/
    private void setDefaultRole(SaveRoleDto saveRoleDto, SysRolePo sysRolePo) {
        if (saveRoleDto.getDefaultRole()) {
            //判断当前是否有默认角色，若有则置为false
            SysRolePo rolePo = mapper.selectOne(new QueryWrapper<SysRolePo>().lambda()
                    .eq(SysRolePo::getDefaultRole, true));
            if (rolePo != null) {
                rolePo.setDefaultRole(false);
                mapper.updateById(rolePo);
            }
        }
        BeanUtils.copyProperties(saveRoleDto, sysRolePo);
    }

}
