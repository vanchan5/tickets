package com.track.data.vo.applet.ticket;

import com.track.data.vo.manage.ticket.TicketSceneInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/30 13:30
 * <p>
 * 小程序根据门票id获取对应的场次信息
 */
@Data
@ApiModel(value = "小程序根据门票id获取对应的场次信息")
public class TicketSceneBaseVo extends TicketSceneInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "判断该场次是否可以购买")
    private Boolean canBuy;


}
