package com.track.data.dto.applet.order;

import com.track.common.enums.manage.order.SearchOrderStateEnum;
import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/30 21:52
 * <p>
 * 查询我的订单参数
 */
@Data
@ApiModel(description = "查询我的订单参数")
public class SearchMyOrderDto extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单查询状态（默认全部）   \n1.全部   \n2.待支付   \n3.待消费   \n4.已完成   \n")
    private SearchOrderStateEnum orderState;

    @ApiModelProperty(value = "小程序用户id")
    private Long userId;


}
