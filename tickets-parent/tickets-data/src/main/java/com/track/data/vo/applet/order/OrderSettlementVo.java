package com.track.data.vo.applet.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/30 17:08
 * <p>
 * 点击订单结算的页面响应参数
 */
@Data
@ApiModel(description = "订单结算参数")
public class OrderSettlementVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "场次档次关联id")
    private Long relId;

    @ApiModelProperty(value = "场次id")
    private Long sceneId;

    @ApiModelProperty(value = "档次id")
    private Long gradeId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "票档名称")
    private String gradeName;

    @ApiModelProperty(value = "门票图片")
    private String picture;

    @ApiModelProperty(value = "单价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "总计")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

    @ApiModelProperty(value = "剩余票数/座位数")
    private Integer remainingSum;

    @ApiModelProperty(value = "场次开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "场次名称")
    private String sceneName;

}
