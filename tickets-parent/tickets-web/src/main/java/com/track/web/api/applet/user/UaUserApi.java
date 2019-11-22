package com.track.web.api.applet.user;

import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.core.interaction.JsonViewData;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.user.ImproveUserInfoDto;
import com.track.security.util.SecurityUtil;
import com.track.ticket.service.IOmTicketService;
import com.track.user.service.IUmUserService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @Description
 * @since 2019/11/22 16:52
 */
@Api(tags = "小程序_用户接口")
@RestController
@RequestMapping("/applet/user")
@Slf4j
public class UaUserApi extends BaseWeb {

    @Autowired
    private IUmUserService umUserService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author yeJH
     * @Date 2019/11/22 17:40
     * @Description 完善用户信息
     *
     * @Update yeJH
     *
     * @param  improveUserInfoDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @ApiOperation(value = "完善用户信息", notes = "完善用户信息，昵称，性别等")
    @PostMapping("/improveUserInfo")
    public JsonViewData improveUserInfo(
            @ApiParam(required = true, name = "improveUserInfoDto", value = "用户信息，昵称，性别等")
            @Validated @RequestBody ImproveUserInfoDto improveUserInfoDto) {
        //获取当前用户
        UmUserPo umUserPo = securityUtil.getSysCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        umUserPo.setSex(improveUserInfoDto.getGender())
                .setNickName(improveUserInfoDto.getNickName());
        umUserService.updateById(umUserPo);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

}
