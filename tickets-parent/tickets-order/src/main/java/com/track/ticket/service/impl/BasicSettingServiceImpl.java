package com.track.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.data.domain.po.ticket.BasicSettingPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.ticket.BasicSettingMapper;
import com.track.security.util.SecurityUtil;
import com.track.ticket.service.IBasicSettingService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 平台基本设置 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BasicSettingServiceImpl extends AbstractService<BasicSettingMapper, BasicSettingPo> implements IBasicSettingService {

    @Autowired
    private BasicSettingMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

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
    @Override
    public void saveCustomerService(String phone) {

        UmUserPo userPo = securityUtil.getSysCurrUser();

        BasicSettingPo kefu = mapper.selectOne(new QueryWrapper<BasicSettingPo>());
        if (kefu == null){
            kefu = new BasicSettingPo();
            kefu.setId(null).setCreateBy(userPo.getId()).setPhone(phone);
            mapper.insert(kefu);
        }else {
            kefu.setPhone(phone).setUpdateBy(userPo.getId());
            mapper.updateById(kefu);
        }
    }

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
    @Override
    public String getCustomerService() {
        BasicSettingPo kefu = mapper.selectOne(new QueryWrapper<BasicSettingPo>());
        return kefu.getPhone();
    }
}
