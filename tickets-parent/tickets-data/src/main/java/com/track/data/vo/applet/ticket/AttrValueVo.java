package com.track.data.vo.applet.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @Description 场次跟档次的组合信息
 * @since 2019/11/15 21:59
 */
@Data
@ApiModel(description = "场次跟档次的组合信息")
public class AttrValueVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选择的规格名称  档次或者场次")
    private String attrKey;

    @ApiModelProperty(value = "规格值  场次名称或者档次名称")
    private String attrValue;

    @ApiModelProperty(value = "规格值  场次id或者档次id")
    private Long attrCode;

}
