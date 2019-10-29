package com.track.user.service.impl;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.manage.user.UserTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.GuavaUtil;
import com.track.common.utils.ListUtil;
import com.track.common.utils.RedisUtil;
import com.track.core.base.service.AbstractService;
import com.track.core.exception.ServiceException;
import com.track.data.bo.user.permission.UmUserBo;
import com.track.data.domain.po.permission.SysRolePo;
import com.track.data.domain.po.permission.SysRoleUserPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.dto.manage.user.edit.EditPasswordDto;
import com.track.data.dto.manage.user.save.SaveUserDto;
import com.track.data.dto.manage.user.search.SearchUsersDto;
import com.track.data.mapper.permission.SysRoleMapper;
import com.track.data.mapper.permission.SysRoleUserMapper;
import com.track.data.mapper.user.UmUserMapper;
import com.track.data.vo.base.BaseVo;
import com.track.data.vo.user.SearchUsersVo;
import com.track.security.util.SecurityUtil;
import com.track.user.service.IUmUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UmUserServiceImpl extends AbstractService<UmUserMapper, UmUserPo> implements IUmUserService {

    @Autowired
    private UmUserMapper mapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author chauncy
     * @Date 2019-10-17 09:15
     * @Description //保存用户信息
     *
     * @Update chauncy
     *
     * @param  userDto
     * @return void
     **/
    @Override
    public void saveUser(SaveUserDto userDto) {

        UmUserPo urrUser =securityUtil.getSysCurrUser();

        //判断角色是否存在
        if (!ListUtil.isListNullAndEmpty(userDto.getRoleIds())){
            userDto.getRoleIds().forEach(a->{
                SysRolePo sysRolePo = roleMapper.selectById(a);
                if (sysRolePo == null){
                    throw new ServiceException(ResultCode.FAIL,String.format("ID为【%s】的角色不存在!",a));
                }
            });
        }

        //保存系统用户信息
        if (userDto.getUserType() != UserTypeEnum.WECHAT_USER.getId()){
            //新增操作
            if (userDto.getId() == 0){
                //不能为空
                if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())){
                    throw new ServiceException(ResultCode.FAIL,"缺少必需表单字段");
                }
                //用户名不能重复
                UmUserPo userPo = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                        .eq(UmUserPo::getUsername,userDto.getUsername())));
                if (userPo != null){
                    throw new ServiceException(ResultCode.DUPLICATION,String.format("该用户名:【%s】已经存在!",userPo.getUsername()));
                }
                //手机号不能重复
                if (StringUtils.isNotBlank(userDto.getPhone()) && mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().eq(UmUserPo::getPhone,userDto.getPhone())
                        .ne(UmUserPo::getUserType,UserTypeEnum.WECHAT_USER.getId())) != null){
                    throw new ServiceException(ResultCode.DUPLICATION, "该手机号已绑定其他账户");
                }
                //加密密码
                String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
                userDto.setPassword(encryptPass);

                UmUserPo umUserPo = new UmUserPo();
                BeanUtils.copyProperties(userDto,umUserPo);
                umUserPo.setId(null);
                umUserPo.setCreateBy(urrUser.getId());

                mapper.insert(umUserPo);
                //绑定角色
                if (!ListUtil.isListNullAndEmpty(userDto.getRoleIds())){
                    SysRoleUserPo roleUserPo = new SysRoleUserPo();
                    userDto.getRoleIds().forEach(d->{
                        roleUserPo.setId(null).setRoleId(d).setUserId(umUserPo.getId())
                                .setCreateBy(urrUser.getId());

                        roleUserMapper.insert(roleUserPo);
                    });
                }
                //若无，则若有默认角色绑定默认角色
                else {
                    SysRolePo rolePo = roleMapper.selectOne(new QueryWrapper<SysRolePo>().lambda()
                            .eq(SysRolePo::getDefaultRole,true));
                    if (rolePo != null){
                        SysRoleUserPo roleUserPo = new SysRoleUserPo();
                        roleUserPo.setId(null).setRoleId(rolePo.getId()).setUserId(umUserPo.getId())
                                .setCreateBy(urrUser.getId());
                        roleUserMapper.insert(roleUserPo);
                    }
                }

            }
            //修改操作
            else {
                //不能为空
                if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())){
                    throw new ServiceException(ResultCode.FAIL,"缺少必需表单字段");
                }

                UmUserPo umUserPo = mapper.selectById(userDto.getId());
                if (umUserPo == null){
                    throw new ServiceException(ResultCode.FAIL,"用户不存在,请检查");
                }
                //用户名不能重复
                List<String> userNameList = mapper.selectList(null).stream()
                        .filter(a->!a.getUsername().equals(umUserPo.getUsername()))
                        .map(b->b.getUsername()).collect(Collectors.toList());
                if (!ListUtil.isListNullAndEmpty(userNameList) && userNameList.contains(userDto.getUsername())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("用户名称【%s】已经存在,请检查！", userDto.getUsername()));
                }
                //若修改了手机号码，则判断是否已存在，用户类型:1-超级管理员，2-系统普通用户,3-消费者
                if (!userDto.getPhone().equals(umUserPo.getPhone()) && mapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                        .eq(UmUserPo::getPhone,userDto.getPhone()).ne(UmUserPo::getUserType,UserTypeEnum.WECHAT_USER.getId())) != null){
                    throw new ServiceException(ResultCode.DUPLICATION, "该手机号已绑定其他账户");
                }
                //密码不能和原来一样
//                if ()

                //更新用户与权限信息表
                roleUserMapper.delete(new QueryWrapper<SysRoleUserPo>().lambda()
                        .eq(SysRoleUserPo::getUserId,userDto.getId()));

                if (!ListUtil.isListNullAndEmpty(userDto.getRoleIds())){
                    SysRoleUserPo roleUserPo = new SysRoleUserPo();
                    userDto.getRoleIds().forEach(d->{
                        roleUserPo.setId(null).setRoleId(d).setUserId(umUserPo.getId())
                                .setCreateBy(urrUser.getId());

                        roleUserMapper.insert(roleUserPo);
                    });
                }

                //更新用户信息操作
                BeanUtils.copyProperties(userDto,umUserPo);
                umUserPo.setUpdateBy(urrUser.getId());
                mapper.updateById(umUserPo);

            }
        }

        //保存微信用户操作
        else {
            //微信openId不能为空
            if (userDto.getOpenId().isEmpty()){
                throw new ServiceException(ResultCode.FAIL,"用户微信openId不能为空");
            }

            UmUserPo wxUserPo = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().eq(UmUserPo::getOpenId,userDto.getOpenId()));
            //新增操作
            if (wxUserPo == null){
                UmUserPo userPo = new UmUserPo();
                BeanUtils.copyProperties(userDto,userPo);
                userPo.setId(null);
                userPo.setCreateBy(urrUser.getId());
                mapper.insert(userPo);
            }
            //编辑操作
            else {

                BeanUtils.copyProperties(userDto,wxUserPo);
                wxUserPo.setUpdateBy(urrUser.getId());
                mapper.updateById(wxUserPo);
            }

        }
    }

    /**
     * @Author chauncy
     * @Date 2019-10-23 15:40
     * @Description //条件分页查询用户信息
     *
     * @Update chauncy
     *
     * @param  searchUsersDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.user.SearchUsersVo>
     **/
    @Override
    public PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto) {

        Integer pageNo = searchUsersDto.getPageNo() == null ? defaultPageNo : searchUsersDto.getPageNo();
        Integer pageSize = searchUsersDto.getPageSize() == null ? defaultPageSize : searchUsersDto.getPageSize();

        PageInfo<SearchUsersVo> usersVoPageInfo = PageHelper.startPage(pageNo,pageSize,defaultSoft)
                .doSelectPageInfo(()->mapper.searchUsers(searchUsersDto));

        usersVoPageInfo.getList().forEach(a->{
            //用户关联的角色
            List<BaseVo> roles = roleMapper.findRoleByUserId(a.getId());
            if (!ListUtil.isListNullAndEmpty(roles)){
                a.setRoleList(roles);
                String roleName = GuavaUtil.ListToString(roles.stream().map(b->b.getName()).collect(Collectors.toList()),",");
                a.setRoleName(roleName);
            }
        });

        return usersVoPageInfo;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-23 23:48
     * @Description //批量删除用户信息
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    @Override
    public void delUsersByIds(List<Long> ids) {

        ids.forEach(a->{
            UmUserPo userPo = mapper.selectById(a);
            if (userPo == null){
                throw new ServiceException(ResultCode.FAIL,String.format("ID为【%s】的用户不存在！",a));
            }
        });
        mapper.deleteBatchIds(ids);
    }

    /**
     * @Author chauncy
     * @Date 2019-10-25 17:36
     * @Description //修改密码
     *
     * @Update chauncy
     *
     * @param  editPasswordDto
     * @return void
     **/
    @Override
    public void editPassword(EditPasswordDto editPasswordDto) {
        //获取用户信息
        UmUserPo userPo = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                .eq(UmUserPo::getId,editPasswordDto.getUserId()));

        if (userPo == null){
            throw new ServiceException(ResultCode.FAIL,"该用户不存在");
        }

        if (new BCryptPasswordEncoder().matches(editPasswordDto.getPassword(),userPo.getPassword())){
            throw new ServiceException(ResultCode.FAIL,"不能和原来密码相同!");
        }else {
            userPo.setPassword(new BCryptPasswordEncoder().encode(editPasswordDto.getPassword()));
        }
        mapper.updateById(userPo);
    }
}
