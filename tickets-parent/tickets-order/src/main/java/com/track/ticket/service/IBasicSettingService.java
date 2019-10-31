package com.track.ticket.service;

import com.track.common.enums.system.ResultCode;
import com.track.data.domain.po.ticket.BasicSettingPo;
import com.track.core.base.service.Service;

/**
 * <p>
 * 平台基本设置 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface IBasicSettingService extends Service<BasicSettingPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-31 10:52
     * @Description //保存客服电话
     *
     * @Update chauncy
     *
     * @param  phone
     * @return void
     **/
    void saveCustomerService(String phone);

    /**
     * @Author chauncy
     * @Date 2019-10-31 10:59
     * @Description //获取客服电话
     *
     * @Update chauncy
     *
     * @param
     * @return java.lang.String
     **/
    String getCustomerService();
}
