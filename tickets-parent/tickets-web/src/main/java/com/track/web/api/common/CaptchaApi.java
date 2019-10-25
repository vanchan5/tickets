package com.track.web.api.common;

import com.track.common.utils.CreateVerifyCodeUtil;
import com.track.common.utils.RedisUtil;
import com.track.core.interaction.JsonViewData;
import com.track.data.vo.common.CaptchaVo;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author cheng
 * @create 2019-10-25 21:55
 *
 * 验证码接口
 *
 * http://localhost:8001/tickets/common/captcha/draw/43bbe346146a46e5b82ebd9e61736abd
 */
@Slf4j
@RestController
@Api(tags = "通用_验证码接口")
@RequestMapping("/common/captcha")
public class CaptchaApi extends BaseWeb {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/initCaptcha")
    @ApiOperation(value = "初始化验证码")
    public JsonViewData<CaptchaVo> initCaptcha(){

        String captchaId = UUID.randomUUID().toString().replace("-","");
        String code = new CreateVerifyCodeUtil().randomStr(4);
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setCaptchaId(captchaId);
        captchaVo.setCode(code);
        //缓存到redis
        redisUtil.set(captchaId,code,180);
        return setJsonViewData(captchaVo);
    }

    @GetMapping("/draw/{captchaId}")
    @ApiOperation("根据验证码ID获取图片")
    public void drawCaptcha(@PathVariable String captchaId, HttpServletResponse response) throws IOException {

        //得到验证码 生成指定验证码
        String code= (String) redisUtil.get(captchaId);
        CreateVerifyCodeUtil vCode = new CreateVerifyCodeUtil(116,36,4,10,code);
        vCode.write(response.getOutputStream());
    }
}
