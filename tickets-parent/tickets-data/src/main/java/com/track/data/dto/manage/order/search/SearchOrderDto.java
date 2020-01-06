package com.track.data.dto.manage.order.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/10/29 13:59
 * <p>
 * 查询订单列表条件
 */
@Data
@ApiModel(description = "查询订单列表条件")
public class SearchOrderDto extends BaseSearchDto {

    @ApiModelProperty(value = "订单状态   \n1.待付款   \n2.已取消   \n3.待消费   \n" +
            "4.已完成   \n5.退款中   \n6.已退款   \n")
    private Integer orderState;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "购买人手机号码")
    private String phone;

    @ApiModelProperty(value = "场次")
    private String sceneName;

    @ApiModelProperty(value = "场次id")
    private Long sceneId;

    @ApiModelProperty(value = "查询下单开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "查询下单结束日期")
    private LocalDate endDate;

}

