package com.track.data.mapper.quartz;

import com.track.data.domain.po.quartz.QuartzJobPo;
import com.track.data.mapper.base.IBaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-29
 */
public interface QuartzJobMapper extends IBaseMapper<QuartzJobPo> {

    /**
     * 查询调度任务集合
     *
     * @param job
     * @return
     */
    List<QuartzJobPo> selectJobList(QuartzJobPo job);

    /**
     * 查询所有调度任务
     *
     * @return
     */
    List<QuartzJobPo> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param jobId
     * @return
     */
    QuartzJobPo selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param jobId
     * @return
     */
    int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     *
     * @param ids
     * @return
     */
//    int deleteJobLogByIds(Long[] ids);

    /**
     * 修改调度任务信息
     *
     * @param job
     * @return
     */
    int updateJob(QuartzJobPo job);

    /**
     * 新增调度任务信息
     *
     * @param job
     * @return
     */
    int insertJob(QuartzJobPo job);

}
