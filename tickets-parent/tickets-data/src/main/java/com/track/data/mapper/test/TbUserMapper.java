package com.track.data.mapper.test;

import com.track.data.domain.po.test.TbUserPo;
import com.track.data.dto.test.select.SearchUsersDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.test.SearchUsersVo;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-09-01
 */
public interface TbUserMapper extends IBaseMapper<TbUserPo> {

    /**
     * 测试分页查询
     *
     * @param SearchUsersDto
     * @return
     */
    List<SearchUsersVo> searchUsers(SearchUsersDto SearchUsersDto);


}
