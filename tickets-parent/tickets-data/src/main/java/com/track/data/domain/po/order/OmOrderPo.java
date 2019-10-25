package com.track.data.domain.po.order;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 支付订单
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_order")
    @ApiModel(value="OmOrderPo对象", description="支付订单")
    public class OmOrderPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "用于微信商户交易流水号,唯一索引。")
    private String payOrderNo;

            @ApiModelProperty(value = "微信返回的给app或者网页的支付凭证，客户端通过此信息调起支付界面。")
    private String prePayId;

    private String userIp;

            @ApiModelProperty(value = "如果创建订单失败，则保存第三方返回的失败错误码")
    private String errorCode;

    private String errorMsg;

            @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

            @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

            @ApiModelProperty(value = "门票价格")
    private BigDecimal sellPrice;

            @ApiModelProperty(value = "支付过期时间， 默认为2小时")
    private LocalDateTime expireTime;

            @ApiModelProperty(value = "1-待付款  2-已取消 3-待消费  4-已完成 5-退款中 6-已退款")
    private Integer state;

            @ApiModelProperty(value = "取票人")
    private String shipName;

            @ApiModelProperty(value = "手机尾号后4位")
    private String shipPhone;

            @ApiModelProperty(value = "备注")
    private String remark;

            @ApiModelProperty(value = "用户id")
    private Long umUserId;

            @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

            @ApiModelProperty(value = "支付申请时间")
    private LocalDateTime startTime;

            @ApiModelProperty(value = "取消时间")
    private LocalDateTime closeTime;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
