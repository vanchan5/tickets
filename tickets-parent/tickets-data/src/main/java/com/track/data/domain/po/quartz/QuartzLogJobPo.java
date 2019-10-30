package com.track.data.domain.po.quartz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("quartz_log_job")
@ApiModel(value = "QuartzLogJobPo对象", description = "")
public class QuartzLogJobPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务日志ID")
    @TableId(value = "job_log_id", type = IdType.AUTO)
    private Long jobLogId;

    @ApiModelProperty(value = "任务名")
    private String jobName;

    @ApiModelProperty(value = "任务组")
    private String jobGroup;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    @ApiModelProperty(value = "任务信息")
    private String jobMessage;

    @ApiModelProperty(value = "状态：NORMAL-正常，PAUSE-暂停")
    private String status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改者")
    private Long updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;


}
