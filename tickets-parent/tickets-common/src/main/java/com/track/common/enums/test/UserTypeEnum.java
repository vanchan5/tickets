package com.track.common.enums.test;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.track.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-09-02 10:28
 *
 * 用户类型 1-app 2-商家 3-后台
 *
 * 测试枚举类校验、从数据库查询自动映射到具体名称，比如查数据库是type为1，那么显示的就是app
 */
@Getter
public enum UserTypeEnum implements BaseEnum {

    APP(1,"app"),
    MERCHANT(2,"商家"),
    PLATFORM(3,"后台");

    @EnumValue  //这个注解放在数据库存储的字段上
    private Integer id;

    private String name;

    UserTypeEnum(Integer id ,String name){
        this.id = id;
        this.name = name;
    }

    //重写toString方法，返回值为显示的值
    @Override
    public String toString(){
        return this.getName();
    }

    //通过ID获取结果
    public static UserTypeEnum fromId(Integer id){
        for (UserTypeEnum type : UserTypeEnum.values()){
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static UserTypeEnum fromName(String name){
        for (UserTypeEnum type : UserTypeEnum.values()){
            if (type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }

    //通过enumName获取结果
    public static UserTypeEnum fromEnumName(String name){
        for (UserTypeEnum type : UserTypeEnum.values()){
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
