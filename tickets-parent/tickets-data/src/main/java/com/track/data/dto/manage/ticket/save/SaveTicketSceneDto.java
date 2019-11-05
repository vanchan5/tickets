package com.track.data.dto.manage.ticket.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/28 16:42
 *
 *
 * @description: 保存演唱会场次参数
 *
 */
@Data
@ApiModel(description = "保存演唱会场次参数")
public class SaveTicketSceneDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@ApiModelProperty(value = "门票场次id")
    @Min(value = 0, message = "sceneId参数错误，不能为0")
    private Long sceneId;*/

    @ApiModelProperty(value = "场次名称")
    @NotNull(message = "场次名称参数不能为空")
    private String sceneName;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "场次开始时间参数不能为空")
    private LocalDateTime startTime;

}
