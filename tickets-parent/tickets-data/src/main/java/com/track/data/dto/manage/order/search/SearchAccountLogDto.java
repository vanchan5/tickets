package com.track.data.dto.manage.order.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author yeJH
 * @Description 查询系统流水条件
 * @since 2019/11/13 14:01
 */
@Data
@ApiModel(description = "查询系统流水条件")
public class SearchAccountLogDto extends BaseSearchDto {

    @ApiModelProperty(value = "流水类型  1.订单支付  2.售后退款")
    private Integer logType;

    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "购买人手机号码")
    private String phone;

    @ApiModelProperty(value = "流水发生开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "流水发生结束日期")
    private LocalDate endDate;

}


