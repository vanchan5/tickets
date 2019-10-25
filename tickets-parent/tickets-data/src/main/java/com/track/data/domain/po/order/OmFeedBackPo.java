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
    * 意见反馈表
    * </p>
*
* @author admin
* @since 2019-10-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("om_feed_back")
    @ApiModel(value="OmFeedBackPo对象", description="意见反馈表")
    public class OmFeedBackPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "意见反馈ID")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "创建者")
    private Long createBy;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "创建者")
    private Long updateBy;

            @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

            @ApiModelProperty(value = "反馈内容")
    private String content;

            @ApiModelProperty(value = "0 未处理 1 已处理，默认为0")
    private Integer state;


}
