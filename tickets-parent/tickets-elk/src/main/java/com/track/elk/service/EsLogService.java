package com.track.elk.service;

import com.track.data.dto.elk.SearchLogDto;
import com.track.elk.po.EsLogDo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-11-06 22:02
 *
 * elasticSearch业务层
 *
 */
public interface EsLogService {

    /**
     * @Author chauncy
     * @Date 2019-11-01 11:16
     * @Description //添加elasticSearch日志
     *
     * @Update chauncy
     *
     * @param  esLogDo
     * @return com.track.elk.po.EsLogDo
     **/
    EsLogDo saveLog(EsLogDo esLogDo);

    /**
     * @Author chauncy
     * @Date 2019-11-01 11:15
     * @Description //通过id删除elasticSearch日志
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    void deleteLog(List<Long> ids);

    /**
     * @Author chauncy
     * @Date 2019-11-01 11:15
     * @Description //删除elasticSearch全部日志
     *
     * @Update chauncy
     *
     * @param
     * @return void
     **/
    void deleteAll();

    /**
     * @Author chauncy
     * @Date 2019-11-01 19:54
     * @Description //分页搜索获取elasticSearch日志
     *
     * @Update chauncy
     *
     * @param  searchLogDto
     * @return org.springframework.data.domain.Page<com.track.elk.po.EsLogDo>
     **/
    Page<EsLogDo> searchEsLog(SearchLogDto searchLogDto);

}
