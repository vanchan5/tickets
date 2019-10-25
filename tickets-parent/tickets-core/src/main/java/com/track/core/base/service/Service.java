package com.track.core.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.track.common.enums.third.ValidCodeEnum;
import com.track.data.domain.po.test.TbUserPo;
import com.track.data.dto.base.EditEnabledDto;

import java.util.Map;

/**
 * @Author cheng
 * @create 2019-09-01 21:37
 *
 * Service 层 基础接口，其他Service接口 都继承该接口
 */
public interface Service<T> extends IService<T> {

    //测试
    Map<String, Object> findByUserName(String username);

    //测试
    TbUserPo findByUserNames(String username);

    /**
     * @Author chauncy
     * @Date 2019-10-21 22:38
     * @Description //验证码是否正确
     *
     * @Update chauncy
     *
     * @param  verifyCode
     * @param  phone
     * @param  validCodeEnum
     * @return boolean
     **/
    boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum);

    /**
     * @Author chauncy
     * @Date 2019-10-23 20:56
     * @Description //批量禁用启用
     *
     * @Update chauncy
     *
     * @param  editEnabledDto
     * @return void
     **/
    void editEnabledBatch(EditEnabledDto editEnabledDto);
}
