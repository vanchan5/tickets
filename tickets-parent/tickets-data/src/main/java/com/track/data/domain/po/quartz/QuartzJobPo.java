package com.track.data.domain.po.quartz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.track.common.constant.QuartzConstants;
import com.track.common.utils.CronUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("quartz_job")
@ApiModel(value = "QuartzJobPo对象", description = "")
public class QuartzJobPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务Id")
    @TableId(value = "job_id", type = IdType.ID_WORKER)
    private Long jobId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务所属组")
    private String jobGroup;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "错过的，指本来应该被执行但实际没有被执行的任务调度")
    private String misfirePolicy = QuartzConstants.MISFIRE_DEFAULT;;

    @ApiModelProperty(value = "NORMAL-正常，PAUSE-暂停")
    private String status;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @ApiModelProperty(value = "修改者")
    private Long updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDate updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }
}
