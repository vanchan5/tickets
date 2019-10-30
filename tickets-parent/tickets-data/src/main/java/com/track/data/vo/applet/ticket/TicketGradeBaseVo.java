package com.track.data.vo.applet.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/10/30 13:28
 * <p>
 * 小程序根据门票id获取对应的档次信息
 */
@Data
@ApiModel(value = "小程序根据门票id获取对应的档次信息")
public class TicketGradeBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票档次id")
    private Long gradeId;

    @ApiModelProperty(value = "档次名称")
    private String gradeName;

    @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;

}
