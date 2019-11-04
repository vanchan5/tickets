package com.track.data.dto.applet.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/30 16:59
 * <p>
 * 订单结算参数
 */
@Data
@ApiModel(description = "订单结算参数")
public class OrderSettlementDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@ApiModelProperty(value = "场次id")
    @NotNull(message = "场次id参数不能为空")
    private Long sceneId;

    @ApiModelProperty(value = "档次id")
    @NotNull(message = "档次id参数不能为空")
    private Long gradeId;*/

    @ApiModelProperty(value = "场次跟档次关联id")
    @NotNull(message = "relId参数不能为空")
    private Long relId;

    @ApiModelProperty(value = "购买票数")
    @NotNull(message = "购买票数参数不能为空")
    @Min(value = 1, message = "至少购买一张")
    private Integer orderNum;

}