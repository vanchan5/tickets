package com.track.elk.service;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.elk.SysLogPo;
import com.track.core.base.service.Service;
import com.track.data.dto.elk.SearchLogDto;
import com.track.elk.vo.SearchLogVo;

import java.util.List;

/**
 * <p>
 * 日志管理 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-06
 */
public interface ISysLogService extends Service<SysLogPo> {

    /***
     * @Author chauncy
     * @Date 2019-11-06 10:41
     * @Description //删除全部本地日志
     *
     * @Update chauncy
     *
     * @param
     * @return void
     **/
    void deleteAll();

    /**
     * @Author chauncy
     * @Date 2019-11-06 11:12
     * @Description //批量删除本地日志
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    void delByIds(List<Long> ids);

    /**
     * @Author chauncy
     * @Date 2019-11-06 11:39
     * @Description //分页条件查询本地日志
     *
     * @Update chauncy
     *
     * @param  searchLogDto
     * @return com.github.pagehelper.PageInfo<com.track.elk.vo.SearchLogVo>
     **/
    PageInfo<SearchLogVo> searchLog(SearchLogDto searchLogDto);
}
