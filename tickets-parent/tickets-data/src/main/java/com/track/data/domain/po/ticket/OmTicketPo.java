package com.track.data.domain.po.ticket;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 门票信息表
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_ticket")
@ApiModel(value = "OmTicketPo对象", description = "门票信息表")
public class OmTicketPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "演唱会门票名称")
    private String name;

    @ApiModelProperty(value = "地区ID")
    private Long addrId;

    @ApiModelProperty(value = "地区名称（省/市/区）")
    private String addrName;

    @ApiModelProperty(value = "地址详情")
    private String addrDetail;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "演唱会门票排序")
    private BigDecimal sort;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Boolean publishState;

    @ApiModelProperty(value = "门票图片")
    private String picture;

    @ApiModelProperty(value = "销量")
    private Integer salesVolume;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
