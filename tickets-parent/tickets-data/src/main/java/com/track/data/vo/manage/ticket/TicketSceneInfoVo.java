package com.track.data.vo.manage.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @description: 平台门票场次数据
 * @since 2019/10/25 14:21
 */
@Data
@ApiModel(description = "平台门票场次数据")
public class TicketSceneInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票场次id")
    private Long sceneId;

    @ApiModelProperty(value = "场次名称")
    private String sceneName;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

}
