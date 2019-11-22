package com.track.web.api.applet.wx;

import com.track.common.enums.system.ResultCode;
import com.track.common.utils.IpInfoUtil;
import com.track.core.exception.ServiceException;
import com.track.core.interaction.JsonViewData;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.user.GetPhoneNumberDto;
import com.track.data.vo.applet.order.UnifiedOrderVo;
import com.track.order.service.IWxService;
import com.track.security.util.SecurityUtil;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author yeJH
 * @Description 访问微信小程序服务端接口
 * @since 2019/11/22 11:27
 */
@Api(tags = "小程序_访问微信服务端接口")
@RestController
@RequestMapping("/applet")
@Slf4j
public class WaWxApi extends BaseWeb {


    @Autowired
    private IWxService wxService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * 统一下单
     * 官方文档:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @return
     * @throws Exception
     */
    @PostMapping("/wxPay/{orderId}")
    @ApiOperation("统一下单")
    public JsonViewData<UnifiedOrderVo> wxPay(HttpServletRequest request, @PathVariable Long orderId) {

        //获取当前用户
        UmUserPo umUserPo = securityUtil.getSysCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        try {
            //请求预支付订单
            UnifiedOrderVo unifiedOrderVo = wxService.unifiedOrder(orderId, ipInfoUtil.getIpAddr(request), umUserPo);
            return new JsonViewData(ResultCode.SUCCESS, "操作成功", unifiedOrderVo);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.SYSTEM_ERROR, "系统错误");
        }

    }


    /**
     *   支付异步结果通知，请求预支付订单时传入的地址
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     */
    @RequestMapping(value = "/wxPay/notify", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("异步通知")
    public String wxPayNotify(HttpServletRequest request) {
        String resXml;
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = wxService.payBack(resXml);
            return result;
        } catch (Exception e) {
            log.error("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
            return result;
        }
    }


    /**
     * @Author yeJH
     * @Date 2019/11/22 12:04
     * @Description 获取微信小程序手机号码
     *
     * @Update yeJH
     *
     * @param  getPhoneNumberDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @ApiOperation(value = "获取微信小程序手机号码", notes = "获取微信小程序手机号码")
    @PostMapping("/getPhoneNumber")
    public JsonViewData getPhoneNumber(
            @ApiParam(required = true, name = "searchMyOrderDto", value = "小程序访问getPhoneNumber拿到的参数")
            @Validated @RequestBody GetPhoneNumberDto getPhoneNumberDto) {

        //获取当前用户
        UmUserPo umUserPo = securityUtil.getSysCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        wxService.getPhoneNumber(getPhoneNumberDto, umUserPo);
        return new JsonViewData(ResultCode.SUCCESS, "绑定手机成功");

    }


}
