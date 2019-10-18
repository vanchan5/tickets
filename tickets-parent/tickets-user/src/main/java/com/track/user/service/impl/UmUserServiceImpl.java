package com.track.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.enums.manage.user.UserTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.ListUtil;
import com.track.core.base.service.AbstractService;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.user.SaveUserDto;
import com.track.data.mapper.user.UmUserMapper;
import com.track.user.service.IUmUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UmUserServiceImpl extends AbstractService<UmUserMapper, UmUserPo> implements IUmUserService {

    @Autowired
    private UmUserMapper mapper;

    /**
     * @Author chauncy
     * @Date 2019-10-17 09:15
     * @Description //保存用户信息
     *
     * @Update chauncy
     *
     * @param  userDto
     * @return void
     **/
    @Override
    public void saveUser(SaveUserDto userDto) {

        //保存系统用户信息
        if (userDto.getUserType() != UserTypeEnum.CONSUMER.getId()){
            //新增操作
            if (userDto.getId() == 0){
                //不能为空
                if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())){
                    throw new ServiceException(ResultCode.FAIL,"缺少必需表单字段");
                }
                //用户名不能重复
                UmUserPo userPo = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().and(obj->obj
                        .eq(UmUserPo::getUsername,userDto.getUsername())));
                if (userPo != null){
                    throw new ServiceException(ResultCode.DUPLICATION,String.format("该用户名:【%s】已经存在!",userPo.getUsername()));
                }

                //加密密码
                String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
                userDto.setPassword(encryptPass);

                UmUserPo umUserPo = new UmUserPo();
                BeanUtils.copyProperties(userDto,umUserPo);
                umUserPo.setId(null);
                umUserPo.setCreateBy(null);
                mapper.insert(umUserPo);

            }
            //修改操作
            else {
                //不能为空
                if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())){
                    throw new ServiceException(ResultCode.FAIL,"缺少必需表单字段");
                }
                //用户名不能重复
                UmUserPo umUserPo = mapper.selectById(userDto.getId());
                List<String> userNameList = mapper.selectList(null).stream()
                        .filter(a->!a.getUsername().equals(umUserPo.getUsername()))
                        .map(b->b.getUsername()).collect(Collectors.toList());
                if (!ListUtil.isListNullAndEmpty(userNameList) && userNameList.contains(userDto.getUsername())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("用户名称【%s】已经存在,请检查！", userDto.getUsername()));
                }

                //更新用户信息操作
                BeanUtils.copyProperties(userDto,umUserPo);
                umUserPo.setUpdateBy(null);
                mapper.updateById(umUserPo);

            }
        }
        //保存微信用户操作
        else {
            //微信openId不能为空
            if (userDto.getOpenId().isEmpty()){
                throw new ServiceException(ResultCode.FAIL,"用户微信openId不能为空");
            }

            UmUserPo wxUserPo = mapper.selectOne(new QueryWrapper<UmUserPo>().lambda().eq(UmUserPo::getOpenId,userDto.getOpenId()));
            //新增操作
            if (wxUserPo == null){
                UmUserPo userPo = new UmUserPo();
                BeanUtils.copyProperties(userDto,userPo);
                userPo.setId(null);
                userPo.setCreateBy(null);
                mapper.insert(userPo);
            }
            //编辑操作
            else {

                BeanUtils.copyProperties(userDto,wxUserPo);
                wxUserPo.setUpdateBy(null);
                mapper.updateById(wxUserPo);
            }

        }
    }
}
