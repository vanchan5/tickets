package com.track.data.dto.applet.ticket;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/29 18:11
 * <p>
 * 小程序查询门票列表条件
 */
@Data
@ApiModel(description = "查询门票列表条件")
public class SearchTicketDto extends BaseSearchDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "演唱会门票名称")
    private String ticketName;

    @ApiModelProperty(value = "城市编码/区编码")
    @NotNull(message = "城市编码/区编码不能为空")
    private List<String> cityCodeList;

    @ApiModelProperty(value = "城市编码/区编码")
    @JsonIgnore
    private String cityCode;

    @ApiModelProperty(value = "级别 0.国家，1.省(直辖市) 2.市 3.区(县),4.街道")
    @JsonIgnore
    private Integer cityCodeType;

    @ApiModelProperty(value = "排序类型   \n1.综合排序（默认）   \n2.最新优选   \n3.低价优选   \n")
    private Integer sortFile;

    @ApiModelProperty(value = "时间筛选   \n1.全部时间（默认）   \n2.一周内   \n3.一月内   \n")
    private Integer timeScreening;

}