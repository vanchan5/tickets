package com.track.elk.repository;

import com.track.elk.po.EsLogDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author cheng
 * @create 2019-11-06 22:02
 *
 * 日志操作：根据方法名或类的信息进行CRUD操作
 */
public interface EsLogRepository extends ElasticsearchRepository<EsLogDo, String> {

    Page<EsLogDo> findByLogType(Integer type, Pageable pageable);
}
