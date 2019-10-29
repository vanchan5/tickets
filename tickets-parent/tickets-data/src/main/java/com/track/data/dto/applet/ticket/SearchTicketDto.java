package com.track.data.dto.applet.ticket;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yeJH
 * @since 2019/10/29 18:11
 * <p>
 * 小程序查询门票列表条件
 */
@Data
@ApiModel(description = "查询门票列表条件")
public class SearchTicketDto extends BaseSearchDto {

    @ApiModelProperty(value = "演唱会门票名称")
    private String ticketName;

    @ApiModelProperty(value = "排序类型   \n1.综合排序（默认）   \n2.最新优选   \n3.低价优选   \n")
    private Integer sortFile;

    @ApiModelProperty(value = "时间筛选   \n1.全部时间（默认）   \n2.一周内   \n3.一月内   \n")
    private Integer timeScreening;

}