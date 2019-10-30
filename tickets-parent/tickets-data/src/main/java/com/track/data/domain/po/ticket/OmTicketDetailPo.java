package com.track.data.domain.po.ticket;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 门票详情信息
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_ticket_detail")
@ApiModel(value = "OmTicketDetailPo对象", description = "门票详情信息")
public class OmTicketDetailPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "购买须知纯文本")
    private String purchaseText;

    @ApiModelProperty(value = "购买须知带格式文本")
    private String purchaseHtml;

    @ApiModelProperty(value = "门票详情信息纯文本")
    private String detailText;

    @ApiModelProperty(value = "门票详情信息带格式文本")
    private String detailHtml;


}
