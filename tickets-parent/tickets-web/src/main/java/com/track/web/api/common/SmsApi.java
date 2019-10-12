package com.track.web.api.common;

import com.track.common.enums.system.ResultCode;
import com.track.common.utils.CreateVerifyCodeUtil;
import com.track.common.utils.RedisUtil;
import com.track.common.utils.third.SendSms;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.base.VerifyCodeDto;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangrt
 * @Date 2019/7/1 16:41
 **/
@RestController
@RequestMapping(value = "/common/sms")
//@PropertySource("classpath:config/redis.properties")
@Api(tags = "发送短信验证码")
public class SmsApi extends BaseWeb {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/send")
    @ApiOperation(value = "发送验证码")
    public JsonViewData send(@Validated @RequestBody VerifyCodeDto verifyCodeDto) {
        //生成4位随机验证码
        String verifyCode = CreateVerifyCodeUtil.randomNumber(4);
        String redisKey=String.format(verifyCodeDto.getValidCodeEnum().getRedisKey(),verifyCodeDto.getPhone());
        //5分钟内有效
        redisUtil.set(redisKey,verifyCode,3000);
        SendSms.send(verifyCodeDto.getPhone(), verifyCode,
                verifyCodeDto.getValidCodeEnum().getTemplateCode());
        return setJsonViewData(ResultCode.SUCCESS);
    }


}
