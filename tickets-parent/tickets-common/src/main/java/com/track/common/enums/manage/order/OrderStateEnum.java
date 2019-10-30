package com.track.common.enums.manage.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/29 12:28
 * <p>
 * 订单状态枚举
 */
public enum OrderStateEnum  implements BaseEnum {


    /**
     * 订单状态枚举
     * 1.待付款
     * 2.已取消
     * 3.待消费
     * 4.已完成
     * 5.退款中
     * 6.已退款
     */
    WAIT_PAY(1, "待付款"),
    ALREADY_CANCEL(2, "已取消"),
    WAIT_CONSUME(3, "待消费"),
    FINISH(4, "已完成"),
    REFUNDING(5, "退款中"),
    REFUNDED(6, "已退款"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    OrderStateEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static OrderStateEnum getById(Integer id) {
        for (OrderStateEnum type : OrderStateEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static OrderStateEnum fromName(String name) {
        for (OrderStateEnum type : OrderStateEnum.values()) {
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
