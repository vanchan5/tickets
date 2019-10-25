package com.track.data.domain.po.order;

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
    * 门票座位信息表
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_ticket_seat")
    @ApiModel(value="OmTicketSeatPo对象", description="门票座位信息表")
    public class OmTicketSeatPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "门票座位id")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "门票档次id")
    private Long gradeId;

            @ApiModelProperty(value = "座位区（第几排）")
    private Integer seatRow;

            @ApiModelProperty(value = "当前座位区座位数量")
    private Integer seatSum;

            @ApiModelProperty(value = "剩余座位数")
    private Integer remainingSum;


}
