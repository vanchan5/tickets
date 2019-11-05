package com.track.data.vo.applet.order;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/30 22:55
 * <p>
 * 我的订单详情-座位信息
 */
@Data
@ApiModel(description = "我的订单详情-座位信息")
public class MyOrderDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    @JSONField(ordinal = 0)
    private Integer orderState;

    @ApiModelProperty(value = "订单状态说明")
    @JSONField(ordinal = 1)
    private String stateExplain;

    @ApiModelProperty(value = "用户昵称")
    @JSONField(ordinal = 2)
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    @JSONField(ordinal = 3)
    private String phone;

    @ApiModelProperty(value = "票号")
    @JSONField(ordinal = 4)
    private Long ticketNo;

    @ApiModelProperty(value = "购买票数")
    @JSONField(ordinal = 5)
    private Integer orderNum;

    /*@ApiModelProperty(value = "座位号")
    @JSONField(serialize = false)
    private String seatStr;*/

    @ApiModelProperty(value = "座位号")
    @JSONField(ordinal = 6)
    private List<String> seatStrList;

    @ApiModelProperty(value = "门票图片")
    @JSONField(ordinal = 7)
    private String picture;

    @ApiModelProperty(value = "演唱会门票名称")
    @JSONField(ordinal = 8)
    private String name;

    @ApiModelProperty(value = "场次开始时间")
    @JSONField(ordinal = 9)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "场次名称")
    @JSONField(ordinal = 10)
    private String sceneName;

    @ApiModelProperty(value = "地址详情")
    @JSONField(ordinal = 11)
    private String addrDetail;

    @ApiModelProperty(value = "票档名称")
    @JSONField(ordinal = 12)
    private String gradeName;

    @ApiModelProperty(value = "总计/支付金额")
    @JSONField(ordinal = 13)
    private BigDecimal payAmount;

    @ApiModelProperty(value = "订单id")
    @JSONField(ordinal = 14)
    private Long orderId;

    @ApiModelProperty(value = "下单时间")
    @JSONField(ordinal = 15)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付时间")
    @JSONField(ordinal = 16)
    private LocalDateTime payTime;

    @ApiModelProperty(value = "退款时间")
    @JSONField(ordinal = 17)
    private LocalDateTime refundTime;

    @ApiModelProperty(value = "未支付订单支付截止时间")
    @JSONField(ordinal = 18)
    private LocalDateTime expireTime;

}
