package com.track.data.dto.manage.ticket.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/28 16:31
 *
 * @description: 新增/编辑门票参数
 *
 */
@Data
@ApiModel(value = "新增/编辑门票参数")
public class SaveTicketDto  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "门票id")
    @Min(value = 1, message = "ticketId参数错误，不能为0")
    private Long ticketId;

    @ApiModelProperty(value = "演唱会门票名称")
    @NotNull(message = "演唱会门票名称不能为空")
    private String name;

    @ApiModelProperty(value = "场次列表")
    @NotEmpty(message = "至少添加一个演唱会场次")
    private List<SaveTicketSceneDto> ticketSceneList;

    @ApiModelProperty(value = "地区ID")
    @NotNull(message = "addrId参数不能为空")
    private Long addrId;

    @ApiModelProperty(value = "地址详情")
    @NotNull(message = "地址详情不能为空")
    private String addrDetail;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "演唱会门票排序")
    @NotNull(message = "演唱会门票排序不能为空")
    private BigDecimal sort;

    /*@ApiModelProperty(value = "上架状态：0->下架；1->上架")
    @NotNull(message = "门票状态不能为空")
    private Boolean publishState;*/

    @ApiModelProperty(value = "门票图片（一张）")
    @NotNull(message = "门票图片不能为空")
    private String picture;

    @ApiModelProperty(value = "购买须知带格式文本")
    @NotNull(message = "购买须知不能为空")
    private String purchaseHtml;

    @ApiModelProperty(value = "购买须知纯文本")
    @NotNull(message = "购买须知不能为空")
    private String purchaseText;

    @ApiModelProperty(value = "门票详情信息带格式文本")
    @NotNull(message = "门票详情信息不能为空")
    private String detailHtml;

    @ApiModelProperty(value = "门票详情信息纯文本")
    @NotNull(message = "门票详情信息不能为空")
    private String detailText;

    @ApiModelProperty(value = "档次列表")
    @NotEmpty(message = "档次列表不能为空")
    private List<SaveTicketGradeDto> ticketGradeList;

}
