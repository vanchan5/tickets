package com.track.data.vo.applet.order;

import com.alibaba.fastjson.annotation.JSONField;
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

    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    @JSONField(ordinal = 1)
    private Integer orderState;

    @ApiModelProperty(value = "演唱会门票名称")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty(value = "场次开始时间")
    @JSONField(ordinal = 3)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "场次名称")
    @JSONField(ordinal = 4)
    private String sceneName;

    @ApiModelProperty(value = "地址详情")
    @JSONField(ordinal = 5)
    private String addrDetail;

    @ApiModelProperty(value = "票档名称")
    @JSONField(ordinal = 6)
    private String gradeName;

    @ApiModelProperty(value = "购买票数")
    @JSONField(ordinal = 7)
    private Integer orderNum;

    @ApiModelProperty(value = "总计/支付金额")
    @JSONField(ordinal = 8)
    private BigDecimal payAmount;

    @ApiModelProperty(value = "订单id")
    @JSONField(ordinal = 9)
    private Long orderId;

    @ApiModelProperty(value = "演出图片")
    @JSONField(ordinal = 10)
    private String picture;

}
