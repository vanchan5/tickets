package com.track.data.vo.applet.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/30 9:28
 * <p>
 * 小程序演出详情VO
 */
@Data
@ApiModel(description = "小程序演出详情")
public class TicketDetailVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "地址详情")
    private String addrDetail;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "门票图片")
    private String picture;

    @ApiModelProperty(value = "购买须知带格式文本")
    private String purchaseHtml;

    @ApiModelProperty(value = "门票详情信息带格式文本")
    private String detailHtml;

    @ApiModelProperty(value = "最低销售价")
    private BigDecimal minSellPrice;

    @ApiModelProperty(value = "最高销售价")
    private BigDecimal maxSellPrice;

    @ApiModelProperty(value = "最快开始时间")
    private LocalDateTime minStartTime;

    @ApiModelProperty(value = "最晚开始时间")
    private LocalDateTime maxStartTime;

    @ApiModelProperty(value = "客服电话")
    private String phone;

    @ApiModelProperty(value = "场次信息")
    private List<CommodityAttrVo> commodityAttr;
    /*@ApiModelProperty(value = "场次信息")
    private List<TicketSceneBaseVo> ticketSceneBaseList;

    @ApiModelProperty(value = "档次信息")
    private List<TicketGradeBaseVo> ticketGradeBaseList;

    @ApiModelProperty("选择场次跟档次对应的信息")
    private Map<String, SceneRelGradeInfoVo> sceneRelGradeInfo;*/

}
