package com.track.data.vo.applet.ticket;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @Description 选择场次跟档次的所有组合信息
 * @since 2019/11/15 21:53
 */
@Data
@ApiModel(description = "场次跟档次的所有组合信息")
public class CommodityAttrVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档次场次关联id")
    private Long priceId;

    @ApiModelProperty(value = "销售价")
    private BigDecimal price;

    @ApiModelProperty(value = "剩余座位数")
    private Integer stock;

    @ApiModelProperty(value = "组合规格信息")
    private List<AttrValueVo> attrValueList;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "档次id")
    private Long gradeId;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "档次名称")
    private String gradeName;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "场次id")
    private Long sceneId;

    @JsonIgnore
    @ApiModelProperty(value = "场次名称")
    private String sceneName;

}
