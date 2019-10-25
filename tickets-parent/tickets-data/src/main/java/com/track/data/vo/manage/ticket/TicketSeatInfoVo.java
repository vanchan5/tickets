package com.track.data.vo.manage.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @description: 平台门票座位数据
 * @since 2019/10/25 15:58
 */
@Data
@ApiModel(value = "平台门票座位数据")
public class TicketSeatInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档次id")
    private Long gradeId;

    @ApiModelProperty(value = "档次名称")
    private String gradeName;

    @ApiModelProperty(value = "座位区（第几排）")
    private Integer seatRow;

    @ApiModelProperty(value = "当前座位区座位数量")
    private Integer seatSum;

    @ApiModelProperty(value = "最小编号")
    private Integer minRange;

    @ApiModelProperty(value = "最大编号")
    private Integer maxRange;

}
