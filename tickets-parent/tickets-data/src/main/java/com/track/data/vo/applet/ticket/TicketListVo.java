package com.track.data.vo.applet.ticket;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/29 17:32
 * <p>
 * 小程序门票列表数据
 */
@Data
@ApiModel(value = "小程序门票列表数据")
public class TicketListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "门票图片")
    private String picture;

    @ApiModelProperty(value = "最低销售价")
    private BigDecimal minSellPrice;

    @ApiModelProperty(value = "最快开始时间")
    private LocalDateTime minStartTime;

    /*@ApiModelProperty(value = "最晚开始时间")
    private LocalDateTime maxStartTime;*/

    @ApiModelProperty(value = "地址详情")
    private String addrDetail;

}