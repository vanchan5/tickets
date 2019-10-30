package com.track.quartz.service;

import com.track.data.domain.po.quartz.QuartzLogJobPo;
import com.track.core.base.service.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-29
 */
public interface IQuartzLogJobService extends Service<QuartzLogJobPo> {

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog
     * @return
     */
    List<QuartzLogJobPo> selectJobLogList(QuartzLogJobPo jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId
     * @return
     */
    QuartzLogJobPo selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog
     */
    void addJobLog(QuartzLogJobPo jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param ids
     * @return
     */
    int deleteJobLogByIds(String ids);

    /**
     * 删除任务日志
     *
     * @param jobId
     * @return
     */
    int deleteJobLogById(Long jobId);

    /**
     * 清空任务日志
     */
    void cleanJobLog();

}
