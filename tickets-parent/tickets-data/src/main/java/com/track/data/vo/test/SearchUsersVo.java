package com.track.data.vo.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.track.common.enums.test.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-09-02 09:20
 *
 * 测试分页获取用户列表数据
 */
@ApiModel(description = "测试分页获取用户列表数据")
@Accessors(chain = true)
@Data
public class SearchUsersVo {

    @ApiModelProperty(value = "用户ID")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "姓名")
    @JSONField(ordinal = 1)
    private String name;

    @ApiModelProperty(value = "年龄")
    @JSONField(ordinal = 2)
    private Integer age;

    @ApiModelProperty(value = "薪资")
    @JSONField(ordinal = 3)
    private Double salary;

    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 4)
    private LocalDateTime createTime;

    @ApiModelProperty("用户类型 1-app 2-商家 3-后台")
    @JSONField(ordinal = 5)
    private UserTypeEnum type;

    @ApiModelProperty("入职时间")
    @JSONField(ordinal = 6)
    private LocalDateTime entryTime;
}
