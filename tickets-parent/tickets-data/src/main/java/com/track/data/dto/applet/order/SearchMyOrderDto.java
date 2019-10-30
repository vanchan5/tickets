package com.track.data.dto.applet.order;

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


    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    @NotNull(message = "订单状态不能为空")
    private Integer orderState;

}
