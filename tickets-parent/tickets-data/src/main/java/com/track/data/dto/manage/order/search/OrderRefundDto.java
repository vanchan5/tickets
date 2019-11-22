package com.track.data.dto.manage.order.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.track.data.dto.base.BaseSearchDto;
import com.track.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @Description 订单退款参数
 * @since 2019/11/20 13:42
 */
@Data
@ApiModel(description = "查询系统流水条件")
public class OrderRefundDto  extends BaseSearchDto {

    @ApiModelProperty(value = "true 根据场次查出所有订单全部退款 false 根据勾选的记录退款")
    @NotNull(message = "isAll参数不能为空")
    private Boolean isAll;

    @ApiModelProperty(value = "订单id")
    private List<Long> orderIdList;

    @ApiModelProperty(value = "场次id")
    @NeedExistConstraint(tableName = "om_ticket_scene")
    private Long sceneId;

    @ApiModelProperty(value = "操作时间 申请退款时间")
    @JsonIgnore
    private LocalDateTime operationTime;



}
