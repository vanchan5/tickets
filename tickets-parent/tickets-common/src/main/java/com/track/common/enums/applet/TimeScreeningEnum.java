package com.track.common.enums.applet;

import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/29 20:13
 * <p>
 * //TODO
 */
public enum TimeScreeningEnum implements BaseEnum {

    /**
     *
     * 筛选门票时间枚举
     *
     **/
    ALL_TIME(1, "全部时间"),
    WITHIN_A_WEEK(2, "一周内"),
    WITHIN_A_MONTH(3, "一月内"),
    ;
    private Integer id;

    private String name;

    TimeScreeningEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name()+ "_"+this.name  ;
    }

    //通过name获取结果
    public static TimeScreeningEnum getTimeScreeningEnum(String name) {
        for (TimeScreeningEnum TimeScreeningEnum : TimeScreeningEnum.values()) {
            if (TimeScreeningEnum.name().equals(name))
                return TimeScreeningEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        if (field == null){
            return true;
        }
        return Objects.nonNull(getTimeScreeningEnum(field.toString()));
    }
}


