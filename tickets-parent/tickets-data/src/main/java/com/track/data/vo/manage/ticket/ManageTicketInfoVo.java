package com.track.data.vo.manage.ticket;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @description: 门票详细信息
 * @since 2019/10/25 14:15
 */
@Data
@ApiModel(value = "平台门票列表数据")
public class ManageTicketInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    @JSONField(ordinal = 1)
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty(value = "场次列表")
    @JSONField(ordinal = 3)
    private List<TicketSceneInfoVo> ticketSceneInfoList;

    @ApiModelProperty(value = "地区ID")
    @JSONField(ordinal = 4)
    private Long addrId;

    @ApiModelProperty(value = "地区名称（省/市/区）")
    @JSONField(ordinal = 5)
    private String addrName;

    @ApiModelProperty(value = "地址详情")
    @JSONField(ordinal = 6)
    private String addrDetail;

    @ApiModelProperty(value = "经度")
    @JSONField(ordinal = 7)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JSONField(ordinal = 8)
    private BigDecimal latitude;

    @ApiModelProperty(value = "演唱会门票排序")
    @JSONField(ordinal = 9)
    private BigDecimal sort;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    @JSONField(ordinal = 10)
    private Boolean publishState;

    @ApiModelProperty(value = "门票图片")
    @JSONField(ordinal = 10)
    private String picture;

    @ApiModelProperty(value = "购买须知带格式文本")
    @JSONField(ordinal = 11)
    private String purchaseHtml;

    @ApiModelProperty(value = "门票详情信息带格式文本")
    @JSONField(ordinal = 12)
    private String detailHtml;

    @ApiModelProperty(value = "档次列表")
    @JSONField(ordinal = 13)
    private List<TicketGradeInfoVo> ticketGradeInfoList;

    @ApiModelProperty(value = "座位区列表")
    @JSONField(ordinal = 14)
    private List<TicketSeatInfoVo> ticketSeatInfoList;


}
