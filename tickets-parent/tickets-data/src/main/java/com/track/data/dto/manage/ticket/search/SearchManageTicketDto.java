package com.track.data.dto.manage.ticket.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author yeJH
 * @description: 查询门票列表条件
 * @since 2019/10/25 11:15
 */
@Data
@ApiModel(description = "查询门票列表条件")
public class SearchManageTicketDto extends BaseSearchDto {

    @ApiModelProperty(value = "演唱会门票名称")
    private String ticketName;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Boolean publishState;

    @ApiModelProperty(value = "查询开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "查询结束日期")
    private LocalDate endDate;

}

