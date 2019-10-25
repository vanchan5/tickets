package com.track.data.domain.po.ticket;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("area_region")
@ApiModel(value = "AreaRegionPo对象", description = "")
public class AreaRegionPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一性编号")
    private Long id;

    @ApiModelProperty(value = "行政区划id")
    private String cityCode;

    @ApiModelProperty(value = "父级id")
    private String parentCode;

    @ApiModelProperty(value = "行政区划全称")
    private String name;

    @ApiModelProperty(value = "省市区全称聚合")
    private String mergerName;

    @ApiModelProperty(value = "行政区划简称")
    private String shortName;

    @ApiModelProperty(value = "行政区划简称聚合")
    private String mergerShortName;

    @ApiModelProperty(value = "行政区划级别country:国家,province:省份,city:市,district:区县,street:街道")
    private String level;

    @ApiModelProperty(value = "级别 0.国家，1.省(直辖市) 2.市 3.区(县),4.街道")
    private Integer levelType;

    @ApiModelProperty(value = "电话区划号码")
    private String telephoneCode;

    @ApiModelProperty(value = "邮编")
    private String zipCode;

    @ApiModelProperty(value = "拼音")
    private String namePinyin;

    @ApiModelProperty(value = "简拼")
    private String nameJianpin;

    @ApiModelProperty(value = "城市中心点")
    private String center;

    @ApiModelProperty(value = "首字母")
    private String nameFirstChar;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态 1可修改 2不可修改 3已删除")
    private Integer status;

    @ApiModelProperty(value = "该地区所属市的id")
    private Long cityId;

    @ApiModelProperty(value = "历史版本")
    private String version;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;


}
