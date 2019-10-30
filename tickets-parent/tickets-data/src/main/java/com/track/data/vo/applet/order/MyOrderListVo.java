package com.track.data.vo.applet.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/30 21:17
 * <p>
 * 我的订单
 */
@Data
@ApiModel(description = "我的订单")
public class MyOrderListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "场次开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "场次名称")
    private String sceneName;

    @ApiModelProperty(value = "地址详情")
    private String addrDetail;

    @ApiModelProperty(value = "票档名称")
    private String gradeName;

    @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

    @ApiModelProperty(value = "总计/支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    private Integer orderState;


}
