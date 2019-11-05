package com.track.common.enums.manage.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.track.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/10/30 22:27
 * <p>
 * 小程序查询订单状态枚举
 */
public enum SearchOrderStateEnum  implements BaseEnum {


    /**
     * 小程序查询订单状态枚举
     * 1.全部
     * 2.待支付
     * 3.待消费
     * 4.已完成
     */
    ALL_LIST(1, "全部"),
    WAIT_PAY(2, "待支付"),
    WAIT_CONSUME(3, "待消费"),
    FINISH(4, "已完成"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    SearchOrderStateEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static SearchOrderStateEnum getById(Integer id) {
        for (SearchOrderStateEnum type : SearchOrderStateEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static SearchOrderStateEnum fromName(String name) {
        for (SearchOrderStateEnum type : SearchOrderStateEnum.values()) {
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
