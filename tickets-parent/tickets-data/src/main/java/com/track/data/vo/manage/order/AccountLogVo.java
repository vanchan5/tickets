package com.track.data.vo.manage.order;

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
 * @Description 系统流水
 * @since 2019/11/13 14:08
 */
@Data
@ApiModel(description = "平台订单列表数据")
public class AccountLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水")
    private Long logId;

    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "购买人手机号码")
    private String phone;

    @ApiModelProperty(value = "购买人昵称")
    private String nickName;

    @ApiModelProperty(value = "流水发生额")
    private BigDecimal amount;

    @ApiModelProperty(value = "发生时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "流水类型  1.订单支付  2.售后退款")
    private Integer logType;


}
