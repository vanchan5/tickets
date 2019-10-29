package com.track.common.enums.applet;

import com.track.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/29 20:08
 * <p>
 * //TODO
 */
@Getter
public enum SortFileEnum  implements BaseEnum {

    /**
     *
     * 前端app排序类型
     *
     **/
    COMPREHENSIVE_SORT(1, "综合排序"),
    NEWEST_SORT(2, "最新优选"),
    PRICE_SORT(3, "低价优选"),
    ;
    private Integer id;

    private String name;

    SortFileEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name()+ "_"+this.name  ;
    }

    //通过name获取结果
    public static SortFileEnum getSortFileEnum(String name) {
        for (SortFileEnum sortFileEnum : SortFileEnum.values()) {
            if (sortFileEnum.name().equals(name))
                return sortFileEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        if (field == null){
            return true;
        }
        return Objects.nonNull(getSortFileEnum(field.toString()));
    }
}

