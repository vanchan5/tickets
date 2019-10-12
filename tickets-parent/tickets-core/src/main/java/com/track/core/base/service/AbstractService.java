package com.track.core.base.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.track.data.mapper.base.IBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author cheng
 * @create 2019-09-01 21:39
 *
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements Service<T> {

    @Autowired
    private IBaseMapper<T> iBaseMapper;


    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected static String defaultSoft="id desc";

    @Override
    public Map<String, Object> findByUserName(String username) {
        return iBaseMapper.findByUserName(username);
    }
}
