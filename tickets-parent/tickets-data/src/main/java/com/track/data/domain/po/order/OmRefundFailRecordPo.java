package com.track.data.domain.po.order;

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
 * 订单退款失败记录
 * </p>
 *
 * @author admin
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_refund_fail_record")
@ApiModel(value = "OmRefundFailRecordPo对象", description = "订单退款失败记录")
public class OmRefundFailRecordPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单退款失败记录id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "退款失败订单")
    private Long orderId;

    @ApiModelProperty(value = "分配的座位是否退回")
    private Boolean isReturnStock;

    @ApiModelProperty(value = "是否重新退款 true 重新退款成功  false 退款失败")
    private Boolean state;

    @ApiModelProperty(value = "场次id")
    private Long sceneId;

    @ApiModelProperty(value = "操作退款时间")
    private LocalDateTime operatingTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
