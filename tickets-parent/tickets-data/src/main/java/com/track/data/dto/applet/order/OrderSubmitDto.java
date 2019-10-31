package com.track.data.dto.applet.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/31 11:28
 * <p>
 * 订单下单提交参数 场次，档次，取票人，手机尾号，购票数目
 */
@Data
@ApiModel(description = "订单下单提交参数")
public class OrderSubmitDto   implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场次id")
    @NotNull(message = "场次id参数不能为空")
    private Long sceneId;

    @ApiModelProperty(value = "档次id")
    @NotNull(message = "档次id参数不能为空")
    private Long gradeId;

    @ApiModelProperty(value = "购买票数")
    @NotNull(message = "购买票数参数不能为空")
    private Integer orderNum;

    @ApiModelProperty(value = "取票人")
    @NotNull(message = "取票人参数不能为空")
    private String shipName;

    @ApiModelProperty(value = "手机尾号")
    @NotNull(message = "手机尾号参数不能为空")
    private Integer shipPhone;

}