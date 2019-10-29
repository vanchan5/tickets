package com.track.common.enums.manage.area;

import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/28 20:38
 *
 *
 * @description: 省市区  级别
 *
 */
public enum AreaRegionLevelEnum {


    /**
     * 省市区  级别
     * 0.国家
     * 1.省(直辖市)
     * 2.市
     * 3.区(县)
     * 4.街道
     */
    COUNTRY (0, "国家"),
    PROVINCE(1, "省(直辖市)"),
    CITY(2, "市"),
    DISTRICT(3, "区(县)"),
    STREET(4, "街道"),
    ;

    private Integer id;
    private String name;
    AreaRegionLevelEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

