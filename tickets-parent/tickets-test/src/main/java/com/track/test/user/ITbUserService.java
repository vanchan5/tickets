package com.track.test.user;

import com.github.pagehelper.PageInfo;
import com.track.core.base.service.Service;
import com.track.data.domain.po.test.TbUserPo;
import com.track.data.dto.test.save.SaveTestUsersDto;
import com.track.data.dto.test.select.SearchUsersDto;
import com.track.data.vo.test.SearchUsersVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-09-01
 */
public interface ITbUserService extends Service<TbUserPo> {

    //测试用户模块
    void test(String username);

    /**
     * 测试分页查询、数据校验
     *
     * @param searchUsersDto
     * @return
     */
    PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto);

    /**
     * 保存用户--测试事务和异常处理
     *
     * @param saveTestUsersDto
     * @return
     */
    void saveUsers(SaveTestUsersDto saveTestUsersDto);
}
