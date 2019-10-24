package com.track.common.enums.manage.permission;

import com.track.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-10-23 14:20
 *
 * 角色级别 1-超级管理员 2-普通用户 3-财务 4-运营 5-客服 等等
 *
 */
@Getter
public enum RoleLevelEnum implements BaseEnum {

    SUPER_ADMIN(1,"超级管理员"),
    SYS_GENERAL_USER(2,"系统普通用户"),
    CONSUMER(3,"消费者");

    private Integer id;

    private String name;

    RoleLevelEnum(Integer id , String name){
        this.id = id;
        this.name = name;
    }

    //重写toString方法，返回值为显示的值
    @Override
    public String toString(){
        return this.getName();
    }

    //通过ID获取结果
    public static RoleLevelEnum fromId(Integer id){
        for (RoleLevelEnum type : RoleLevelEnum.values()){
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static RoleLevelEnum fromName(String name){
        for (RoleLevelEnum type : RoleLevelEnum.values()){
            if (type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }

    //通过enumName获取结果
    public static RoleLevelEnum fromEnumName(String name){
        for (RoleLevelEnum type : RoleLevelEnum.values()){
            if (type.name().equals(name))
                return type;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        //通过ID判断
        return Objects.nonNull(fromId(Integer.parseInt(field.toString())));

        //通过enumName判断
//        return Objects.nonNull(fromEnumName(field.toString()));
    }
}

