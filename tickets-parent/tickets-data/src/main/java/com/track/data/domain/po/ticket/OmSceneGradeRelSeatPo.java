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
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_scene_grade_rel_seat")
@ApiModel(value = "OmSceneGradeRelSeatPo对象", description = "档次跟场次关联表")
public class OmSceneGradeRelSeatPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "档次跟场次关联表id")
    private Long relId;

    @ApiModelProperty(value = "门票座位区id")
    private Long seatId;

    @ApiModelProperty(value = "起始总座位数")
    private Integer startSum;

    @ApiModelProperty(value = "剩余座位数")
    private Integer remainingSum;


}
