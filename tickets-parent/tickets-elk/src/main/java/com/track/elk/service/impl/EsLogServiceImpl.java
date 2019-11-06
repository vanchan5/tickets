package com.track.elk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.track.core.base.util.PageUtil;
import com.track.data.dto.base.BaseSearchDto;
import com.track.data.dto.elk.SearchLogDto;
import com.track.elk.po.EsLogDo;
import com.track.elk.repository.EsLogRepository;
import com.track.elk.service.EsLogService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;

/**
 * @author admin
 * @since 2019-11-06
 *
 */
@Service
@Transactional
@Slf4j
public class EsLogServiceImpl implements EsLogService {

    @Autowired
    private EsLogRepository logDao;

    /**
     * @Author chauncy
     * @Date 2019-11-01 11:16
     * @Description //添加elasticSearch日志
     *
     * @Update chauncy
     *
     * @param  esLog
     * @return com.track.elk.po.EsLogDo
     **/
    @Override
    public EsLogDo saveLog(EsLogDo esLog) {

        return logDao.save(esLog);
    }

    /**
     * @Author chauncy
     * @Date 2019-11-01 10:46
     * @Description //批量删除日志
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    @Override
    public void deleteLog(List<Long> ids) {

        ids.forEach(id->{
            logDao.deleteById(String.valueOf(id));
        });
    }

    /**
     * @Author chauncy
     * @Date 2019-11-01 11:16
     * @Description //删除elasticSearch全部日志
     *
     * @Update chauncy
     *
     * @param
     * @return void
     **/
    @Override
    public void deleteAll() {

        logDao.deleteAll();
    }


    @Override
    public Page<EsLogDo> searchEsLog(SearchLogDto searchLogDto) {
        Integer type = searchLogDto.getLogType();
        String key = searchLogDto.getKey();

        BaseSearchDto page = new BaseSearchDto();
        page.setPageNo(searchLogDto.getPageNo()).setPageSize(searchLogDto.getPageSize())
                .setOrder(searchLogDto.getOrder()).setSort(searchLogDto.getSort());
        Pageable pageable = PageUtil.initPage(page);

        if(type==null&&StrUtil.isBlank(key)&& searchLogDto.getStartTime() == null){
            // 无过滤条件获取全部
            return logDao.findAll(pageable);
        }else if(type!=null&&StrUtil.isBlank(key)&&searchLogDto.getStartTime() == null){
            // 仅有type
            return logDao.findByLogType(type, pageable);
        }

        QueryBuilder qb;

        QueryBuilder qb0 = QueryBuilders.termQuery("logType", type);
        QueryBuilder qb1 = QueryBuilders.multiMatchQuery(key, "name", "requestUrl", "requestType","requestParam","username","ip","ipInfo");
        // 在有type条件下
        if(StrUtil.isNotBlank(key)&&searchLogDto.getStartTime() == null &&searchLogDto.getEndTime() == null){
            // 仅有key
            qb = QueryBuilders.boolQuery().must(qb0).must(qb1);
        }else if(StrUtil.isBlank(key)&&searchLogDto.getStartTime() != null &&searchLogDto.getEndTime() != null){
            // 仅有时间范围
            Long start = searchLogDto.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            Long end = searchLogDto.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            QueryBuilder qb2 = QueryBuilders.rangeQuery("timeMillis").gte(start).lte(end);
            qb = QueryBuilders.boolQuery().must(qb0).must(qb2);
        }else{
            // 两者都有
            Long start = searchLogDto.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            Long end = searchLogDto.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            QueryBuilder qb2 = QueryBuilders.rangeQuery("timeMillis").gte(start).lte(end);
            qb = QueryBuilders.boolQuery().must(qb0).must(qb1).must(qb2);
        }

        //多字段搜索
        return logDao.search(qb, pageable);
    }

}
