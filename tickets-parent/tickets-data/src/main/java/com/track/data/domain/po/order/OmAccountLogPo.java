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
 * 账目流水表
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_account_log")
@ApiModel(value = "OmAccountLogPo对象", description = "账目流水表")
public class OmAccountLogPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "发生额")
    private BigDecimal amount;

    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "流水类型  1.订单支付  2.售后退款")
    private Integer logType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
