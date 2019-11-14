package com.track.common.enums.manage.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @Description 流水类型枚举类
 * @since 2019/11/13 14:05
 */
public enum LogTypeEnum implements BaseEnum {


    /**
     * 流水类型枚举类
     * 1.订单支付
     * 2.售后退款
     */
    ORDER_PAY(1, "订单支付"),
    AFTER_SALE_REFUND(2, "售后退款"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    LogTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static LogTypeEnum getById(Integer id) {
        for (LogTypeEnum type : LogTypeEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static LogTypeEnum fromName(String name) {
        for (LogTypeEnum type : LogTypeEnum.values()) {
            if (type.name().equals(name))
                return type;
        }
        return null;
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

    @Override
    public boolean isExist(Object field) {
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }

}
