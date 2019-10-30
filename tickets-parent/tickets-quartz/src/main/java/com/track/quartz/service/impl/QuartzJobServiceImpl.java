package com.track.quartz.service.impl;

import cn.hutool.core.convert.Convert;
import com.track.quartz.proccessor.ScheduleUtils;
import com.track.common.utils.CronUtils;
import com.track.common.constant.QuartzConstants;
import com.track.data.domain.po.quartz.QuartzJobPo;
import com.track.data.mapper.quartz.QuartzJobMapper;
import com.track.quartz.service.IQuartzJobService;
import com.track.core.base.service.AbstractService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
public class QuartzJobServiceImpl extends AbstractService<QuartzJobMapper, QuartzJobPo> implements IQuartzJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper jobMapper;

    /**
     * 项目启动时初始化定时器
     */
    @PostConstruct
    public void init() {
        log.info("初始化Begin ： QuartzInit");
        //查询所有的任务job
        List<QuartzJobPo> jobList = jobMapper.selectJobAll();
        for (QuartzJobPo job : jobList) {
            //获取触发器Trigger表达式
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
        log.info("初始化End ： QuartzInit");
    }

    /**
     * 获取quartz调度器的计划任务列表
     *
     * @param job
     * @return
     */
    @Override
    public List<QuartzJobPo> selectJobList(QuartzJobPo job) {
        return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId
     * @return
     */
    @Override
    public QuartzJobPo selectJobById(Long jobId) {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     *
     * @param job
     * @return
     */

    @Override
    public int pauseJob(QuartzJobPo job) {
        job.setStatus(QuartzConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0) {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job
     * @return
     */
    @Override
    public int resumeJob(QuartzJobPo job) {
        job.setStatus(QuartzConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0) {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job
     * @return
     */
    @Override
    public int deleteJob(QuartzJobPo job) {
        int rows = jobMapper.deleteJobById(job.getJobId());
        if (rows > 0) {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     *
     * @param ids
     */
    @Override
    public void deleteJobByIds(String ids) {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds) {
            QuartzJobPo job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    @Override
    public int changeStatus(QuartzJobPo job) {
        int rows = 0;
        String status = job.getStatus();
        if (QuartzConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (QuartzConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     *
     * @param job
     * @return
     */
    @Override
    public int run(QuartzJobPo job) {
        return ScheduleUtils.run(scheduler,/*job*/selectJobById(job.getJobId()));
    }

    /**
     * 新增任务
     *
     * @param job
     * @return
     */
    @Override
    public int insertJobCron(QuartzJobPo job) {
        job.setStatus(QuartzConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if(rows>0){
            ScheduleUtils.createScheduleJob(scheduler,job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job
     * @return
     */
    @Override
    public int updateJobCron(QuartzJobPo job) {
        int rows = jobMapper.updateJob(job);
        if (rows>0){
            ScheduleUtils.updateScheduleJob(scheduler,job);
        }
        return rows;
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression
     * @return
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
