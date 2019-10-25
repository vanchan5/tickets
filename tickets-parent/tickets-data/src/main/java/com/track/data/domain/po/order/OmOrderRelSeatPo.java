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
    * 订单关联座位表
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_order_rel_seat")
    @ApiModel(value="OmOrderRelSeatPo对象", description="订单关联座位表")
    public class OmOrderRelSeatPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "id")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "订单id")
    private Long orderId;

            @ApiModelProperty(value = "几排几座")
    private String seatStr;

            @ApiModelProperty(value = "座位区id")
    private Long seatId;

            @ApiModelProperty(value = "第几排")
    private Integer seatRow;

            @ApiModelProperty(value = "第几位")
    private Integer seatNum;


}
