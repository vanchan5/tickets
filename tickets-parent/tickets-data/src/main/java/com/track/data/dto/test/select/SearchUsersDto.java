package com.track.data.dto.test.select;

import com.track.common.enums.test.TestUserTypeEnum;
import com.track.data.dto.base.BaseSearchDto;
import com.track.data.valid.annotation.EnumConstraint;
import com.track.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-09-02 09:10
 *
 * 测试条件分页查询用户信息
 */
@Data
@ApiModel(description ="测试条件分页查询用户信息")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SearchUsersDto extends BaseSearchDto {

    @ApiModelProperty("用户ID")
//    @NeedExistConstraint(tableName = "tb_user",field = "id",concatWhereSql = "and type =2")
    @NeedExistConstraint(tableName = "tb_user")
    private Long userId;

    @ApiModelProperty("用户类型 1-app 2-商家 3-后台")
    @EnumConstraint(target = TestUserTypeEnum.class)
    private Integer type;

    @ApiModelProperty
    private String name;

    @ApiModelProperty("最早创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty("最早创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

}
