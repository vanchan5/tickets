package com.track.data.vo.manage.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @description: 平台门票档次数据
 * @since 2019/10/25 14:36
 */
@Data
@ApiModel(value = "平台门票档次数据")
public class TicketGradeInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票档次id")
    private Long gradeId;

    @ApiModelProperty(value = "档次名称")
    private String gradeName;

    @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "该档位有多少排座位")
    private Integer rowSum;

}
