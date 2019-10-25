package com.track.data.domain.po.order;

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
    * 门票场次信息表
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_ticket_scene")
    @ApiModel(value="OmTicketScenePo对象", description="门票场次信息表")
    public class OmTicketScenePo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "门票场次id")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "门票id")
    private Long ticketId;

            @ApiModelProperty(value = "场次名称")
    private String name;

            @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;


}
