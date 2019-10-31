package com.track.data.vo.manage.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/10/31 21:04
 * <p>
 * 下单快照信息
 */
@Data
@ApiModel(description = "下单快照信息")
public class OrderTempVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "票号")
    private Long ticketNo;

    @ApiModelProperty(value = "地址详情")
    private String addrDetail;

    @ApiModelProperty(value = "演唱会名称")
    private String ticketName;

    @ApiModelProperty(value = "档次")
    private String gradeName;

    @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

    @ApiModelProperty(value = "单价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "合计")
    private BigDecimal payAmount;

}
