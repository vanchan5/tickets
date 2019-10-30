package com.track.data.dto.manage.ticket.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/28 17:38
 *
 *
 * @description: 保存演唱会座位区参数
 *
 */
@Data
@ApiModel(value = "保存演唱会座位区参数")
public class SaveTicketSeatDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@ApiModelProperty(value = "座位区id")
    @Min(value = 1, message = "seatId参数错误，不能为0")
    private Long seatId;*/

    @ApiModelProperty(value = "座位区（第几排）")
    @NotNull(message = "座位区排数不能为空")
    private Integer seatRow;

    @ApiModelProperty(value = "当前座位区座位数量")
    @NotNull(message = "座位数量不能为空")
    private Integer seatSum;

}
