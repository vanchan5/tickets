package com.track.quartz.service;

import com.track.data.domain.po.quartz.QuartzJobPo;
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
public interface IQuartzJobService extends Service<QuartzJobPo> {

    /**
     * 获取quartz调度器的计划任务
     *
     * @param job
     * @return
     */
    List<QuartzJobPo> selectJobList(QuartzJobPo job);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId
     * @return
     */
    QuartzJobPo selectJobById(Long jobId);

    /**
     * 暂停任务
     *
     * @param job
     * @return
     */
    int pauseJob(QuartzJobPo job);

    /**
     * 恢复任务
     *
     * @param job
     * @return
     */
    int resumeJob(QuartzJobPo job);

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job
     * @return
     */
    int deleteJob(QuartzJobPo job);

    /**
     * 批量删除调度信息
     *
     * @param ids
     */
    void deleteJobByIds(String ids);

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    int changeStatus(QuartzJobPo job);

    /**
     * 立即运行任务
     *
     * @param job
     * @return
     */
    int run(QuartzJobPo job);

    /**
     * 新增任务表达式
     *
     * @param job
     * @return
     */
    int insertJobCron(QuartzJobPo job);

    /**
     * 更新任务的时间表达式
     *
     * @param job
     * @return
     */
    int updateJobCron(QuartzJobPo job);

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression
     * @return
     */
    boolean checkCronExpressionIsValid(String cronExpression);
}
