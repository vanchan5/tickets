package com.track.quartz.service.impl;

import cn.hutool.core.convert.Convert;
import com.track.data.domain.po.quartz.QuartzLogJobPo;
import com.track.data.mapper.quartz.QuartzLogJobMapper;
import com.track.quartz.service.IQuartzLogJobService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class QuartzLogJobServiceImpl extends AbstractService<QuartzLogJobMapper, QuartzLogJobPo> implements IQuartzLogJobService {

    @Autowired
    private QuartzLogJobMapper jobLogMapper;
    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog
     * @return
     */
    @Override
    public List<QuartzLogJobPo> selectJobLogList(QuartzLogJobPo jobLog) {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId
     * @return
     */
    @Override
    public QuartzLogJobPo selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     *
     * @param jobLog
     */
    @Override
    public void addJobLog(QuartzLogJobPo jobLog) {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteJobLogByIds(String ids) {
        return jobLogMapper.deleteJobLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务日志
     *
     * @param jobId
     * @return
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }
}
