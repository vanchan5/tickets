package com.track.data.domain.po.order;

    import java.math.BigDecimal;
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
    * 门票档次信息表
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_ticket_grade")
    @ApiModel(value="OmTicketGradePo对象", description="门票档次信息表")
    public class OmTicketGradePo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "门票档次id")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "门票场次id")
    private Long sceneId;

            @ApiModelProperty(value = "档次名称")
    private String name;

            @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;

            @ApiModelProperty(value = "座位总数量")
    private Integer seatSum;

            @ApiModelProperty(value = "剩余总座位数")
    private Integer remainingSum;


}
