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
 * @since 2019/10/28 17:19
 *
 *
 * @description: 保存演唱会档次参数
 *
 */
@Data
@ApiModel(value = "保存演唱会档次参数")
public class SaveTicketGradeDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@ApiModelProperty(value = "门票档次id")
    @Min(value = 0, message = "gradeId参数错误，不能为0")
    private Long gradeId;*/

    @ApiModelProperty(value = "档次名称")
    @NotNull(message = "档次名称参数不能为空")
    private String gradeName;

    @ApiModelProperty(value = "销售价")
    @NotNull(message = "销售价参数不能为空")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "该档位有多少排座位")
    @NotNull(message = "座位排数不能为空")
    private Integer rowSum;

    @ApiModelProperty(value = "座位区列表")
    @NotEmpty(message = "座位区列表不能为空")
    private List<SaveTicketSeatDto> ticketSeatList;

}
