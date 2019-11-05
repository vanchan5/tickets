package com.track.data.vo.manage.ticket;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @description: 平台门票列表
 * @since 2019/10/25 10:57
 */
@Data
@ApiModel(description = "平台门票列表数据")
public class ManageTicketListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "销量")
    private Integer salesVolume;

    @ApiModelProperty(value = "最低销售价")
    private BigDecimal minSellPrice;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Boolean publishState;

    @ApiModelProperty(value = "最快开始时间")
    private LocalDateTime minStartTime;

    @ApiModelProperty(value = "最晚开始时间")
    private LocalDateTime maxStartTime;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "场次场数")
    private Integer sceneCount;

    @ApiModelProperty(value = "演唱会门票排序")
    private BigDecimal sort;

}
