package com.track.user.service;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.data.domain.po.user.UmUserPo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.user.save.SaveUserDto;
import com.track.data.dto.manage.user.search.SearchUsersDto;
import com.track.data.vo.user.SearchUsersVo;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
public interface IUmUserService extends Service<UmUserPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-17 09:14
     * @Description //保存用户信息
     *
     * @Update chauncy
     *
     * @param  userDto
     * @return void
     **/
    void saveUser(SaveUserDto userDto);

    /**
     * @Author chauncy
     * @Date 2019-10-23 15:39
     * @Description //条件分页查询用户信息
     *
     * @Update chauncy
     *
     * @param  searchUsersDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.user.SearchUsersVo>
     **/
    PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto);

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
    void delUsersByIds(List<Long> ids);
}
