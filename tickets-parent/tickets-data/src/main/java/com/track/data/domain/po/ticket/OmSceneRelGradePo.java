package com.track.data.domain.po.ticket;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 档次跟场次关联表
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_scene_rel_grade")
@ApiModel(value = "OmSceneRelGradePo对象", description = "场次跟座位区的关联表")
public class OmSceneRelGradePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "门票场次id")
    private Long sceneId;

    @ApiModelProperty(value = "门票档次id")
    private Long gradeId;

    @ApiModelProperty(value = "门票座位区id")
    private Long seatId;

    @ApiModelProperty(value = "剩余总座位数")
    private Integer remainingSum;


}
