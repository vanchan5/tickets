package com.track.data.domain.po.order;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单快照信息
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_ticket_temp")
@ApiModel(value = "OmTicketTempPo对象", description = "订单快照信息")
public class OmTicketTempPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "门票价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "购买票数")
    private Integer orderNum;

    @ApiModelProperty(value = "门票图片")
    private String picture;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String ticketName;

    @ApiModelProperty(value = "场次跟座位区关联id")
    private Long relId;

    @ApiModelProperty(value = "场次id")
    private Long sceneId;

    @ApiModelProperty(value = "场次名称")
    private String sceneName;

    @ApiModelProperty(value = "档次id")
    private Long gradeId;

    @ApiModelProperty(value = "档次名称")
    private String gradeName;


}
