package com.track.data.vo.manage.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/29 12:10
 *
 *
 * @description: 平台订单列表
 *
 */
@Data
@ApiModel(value = "平台订单列表数据")
public class ManageOrderListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "票号")
    private Long ticketNo;

    @ApiModelProperty(value = "购买场次id")
    private Long sceneId;

    @ApiModelProperty(value = "购买场次")
    private String sceneName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

    @ApiModelProperty(value = "订单金额（元）")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "购买人手机号码")
    private String phone;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "退款时间")
    private LocalDateTime refundTime;

    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    private Integer orderState;

}
