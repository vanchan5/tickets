package com.track.data.mapper.elk;

import com.track.data.domain.po.elk.SysLogPo;
import com.track.data.dto.elk.SearchLogDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.elk.SearchLogVo;

import java.util.List;

/**
 * <p>
 * 日志管理 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-06
 */
public interface SysLogMapper extends IBaseMapper<SysLogPo> {

    /**
     * @Author chauncy
     * @Date 2019-11-01 16:41
     * @Description //分页条件查询本地日志
     *
     * @Update chauncy
     *
     * @param  searchLogDto
     * @return List<SearchDto>
     **/
    List<SearchLogVo> searchLog(SearchLogDto searchLogDto);
}
