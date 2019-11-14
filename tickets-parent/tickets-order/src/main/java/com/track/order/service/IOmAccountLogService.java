package com.track.order.service;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.order.OmAccountLogPo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.order.search.SearchAccountLogDto;
import com.track.data.vo.manage.order.AccountLogVo;

/**
 * <p>
 * 账目流水表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
public interface IOmAccountLogService extends Service<OmAccountLogPo> {

    /**
     * @Author yeJH
     * @Date 2019/11/13 16:11
     * @Description 查询系统流水
     *
     * @Update yeJH
     *
     * @param  searchAccountLogDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.AccountLogVo>
     **/
    PageInfo<AccountLogVo> searchAccountLog(SearchAccountLogDto searchAccountLogDto);
}
