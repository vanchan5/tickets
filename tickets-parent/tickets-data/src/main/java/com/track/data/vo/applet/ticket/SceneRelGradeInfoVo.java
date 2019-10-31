package com.track.data.vo.applet.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/30 13:36
 * <p>
 * 场次关联档次信息（剩余库存）
 */
@Data
@ApiModel(description = "场次关联档次信息（剩余库存）")
public class SceneRelGradeInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票场次id")
    private Long sceneId;

    @ApiModelProperty(value = "门票档次id")
    private Long gradeId;

    @ApiModelProperty(value = "剩余座位数")
    private Integer remainingSum;

}
