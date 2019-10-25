package com.track.data.mapper.user;

import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.user.search.SearchUsersDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.user.SearchUsersVo;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
public interface UmUserMapper extends IBaseMapper<UmUserPo> {

    List<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto);
}
