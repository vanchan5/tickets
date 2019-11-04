package com.track.common.enums.manage.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/11/2 12:14
 * <p>
 * 订单状态说明枚举
 */
public enum OrderStateExplainEnum  implements BaseEnum {


    /**
     * 订单状态说明枚举
     * 1.待付款
     * 2.已取消
     * 3.待消费
     * 4.已完成
     * 5.退款中
     * 6.已退款
     */
    WAIT_PAY(1, ""),
    ALREADY_CANCEL(2, ""),
    WAIT_CONSUME(3, "预定成功，等待观演"),
    FINISH(4, "消费已完成"),
    REFUNDING(5, ""),
    REFUNDED(6, ""),
            ;

    @EnumValue
    private Integer id;
    private String name;
    OrderStateExplainEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static  OrderStateExplainEnum getById(Integer id) {
        for ( OrderStateExplainEnum type :  OrderStateExplainEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static  OrderStateExplainEnum fromName(String name) {
        for ( OrderStateExplainEnum type :  OrderStateExplainEnum.values()) {
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
