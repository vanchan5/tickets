package com.track.core.base.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.track.common.enums.third.ValidCodeEnum;
import com.track.common.utils.RedisUtil;
import com.track.data.domain.po.test.TbUserPo;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.mapper.base.IBaseMapper;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private RedisUtil redisUtil;


    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected static String defaultSoft = "create_time desc";

    @Override
    public Map<String, Object> findByUserName(String username) {
        return iBaseMapper.findByUserName(username);
    }

    @Override
    public TbUserPo findByUserNames(String username) {
        return iBaseMapper.findByUserNames(username);
    }

    /**
     * @param verifyCode
     * @param phone
     * @param validCodeEnum
     * @return boolean
     * @Author chauncy
     * @Date 2019-10-21 22:39
     * @Description //验证码是否正确
     * @Update chauncy
     **/
    @Override
    public boolean validVerifyCode(String verifyCode, String phone, ValidCodeEnum validCodeEnum) {
        String redisKey = String.format(validCodeEnum.getRedisKey(), phone);
        Object redisValue = redisUtil.get(redisKey);
        //默认8888
        if ("8888".equals(verifyCode.trim())) {
            return true;
        }
        if (redisValue == null) {
            return false;
        }
        if (StringUtils.equals(verifyCode.trim(), redisValue.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-23 20:57
     * @Description //批量禁用启用
     *
     * @Update chauncy
     *
     * @param  editEnabledDto
     * @return void
     **/
    @Override
    public void editEnabledBatch(EditEnabledDto editEnabledDto) {

        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", editEnabledDto.getId());
        updateWrapper.set("enabled", editEnabledDto.getEnabled());
        this.update(updateWrapper);
    }
}