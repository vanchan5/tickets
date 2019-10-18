package com.track.core.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.track.data.domain.po.test.TbUserPo;

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


}
