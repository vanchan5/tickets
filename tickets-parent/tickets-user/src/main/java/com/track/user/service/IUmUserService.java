package com.track.user.service;

import com.track.data.domain.po.user.UmUserPo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.user.SaveUserDto;

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
}
