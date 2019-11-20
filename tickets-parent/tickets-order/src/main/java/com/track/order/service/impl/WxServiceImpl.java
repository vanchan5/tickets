package com.track.order.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.track.common.constant.wetch.applet.WxPayConstant;
import com.track.common.enums.manage.order.OrderStateEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.BigDecimalUtil;
import com.track.common.utils.LoggerUtil;
import com.track.common.utils.wetch.pay.WXConfigUtil;
import com.track.common.utils.wetch.pay.WxMD5Util;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.vo.applet.order.UnifiedOrderVo;
import com.track.order.service.IOmOrderService;
import com.track.order.service.IWxService;
import com.track.security.util.SecurityUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:23
 */

@Service
public class WxServiceImpl implements IWxService {

    private static final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    //支付结果通知地址
    //@Value("${distribution.wxpay.PAY_NOTIFY_URL}")
    public String PAY_NOTIFY_URL ;



    @Autowired
    private WXConfigUtil wxConfigUtil;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private IOmOrderService omOrderService;

    @Autowired
    private WxMD5Util wxMD5Util;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 调用官方SDK统一下单 获取前端调起支付接口的参数
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnifiedOrderVo unifiedOrder(Long orderId, String ipAddr, UmUserPo umUsrePo) throws Exception {
        //商品支付
        OmOrderPo omOrderPo = omOrderMapper.selectById(orderId);
        if (null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }
        if (!OrderStateEnum.WAIT_PAY.getId().equals(omOrderPo.getState())) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "订单状态不是待付款状态");
        }
        WxMD5Util md5Util = wxMD5Util;
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);
        //app调起支付接口所需参数
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        //小程序参加调起支付的签名字段有且只能是5个，分别为appid、timeStamp、nonceStr、package、signType
        unifiedOrderVo.setAppId(config.getAppID());
        //时间戳 单位为秒
        unifiedOrderVo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        unifiedOrderVo.setNonceStr(WXPayUtil.generateNonceStr());
        unifiedOrderVo.setSignType(WxPayConstant.SIGN_TYPE_MD5);

        if(Strings.isNotBlank(omOrderPo.getPrePayId())) {
            //预支付交易会话标识 说明预支付单已生成
            unifiedOrderVo.setPackageStr("prepay_id=" + omOrderPo.getPrePayId());
            //调起支付参数重新签名  不要使用请求预支付订单时返回的签名
            Map<String, String> returnMap = getWxPayParam(unifiedOrderVo);
            returnMap.remove("class");
            unifiedOrderVo.setPaySign(md5Util.getSign(returnMap));
            return unifiedOrderVo;
        }

        //总金额  单位分
        Integer totalFee = BigDecimalUtil.safeMultiply(omOrderPo.getPayAmount(), new BigDecimal("100")).intValue();
        //统一下单接口微信签名参数
        Map<String, String> data = getSignMap(wxConfigUtil, wxMD5Util, ipAddr, omOrderPo.getId(), totalFee, umUsrePo);

        //订单信息修改
        //IP地址
        omOrderPo.setUserIp(ipAddr);
        //支付过期时间， 默认为半小时
        omOrderPo.setExpireTime(LocalDateTime.now().plusMinutes(30));

        //使用微信统一下单API请求预付订单
        Map<String, String> response = wxpay.unifiedOrder(data);
        //获取返回码
        String returnCode = response.get("return_code");
        //若返回码return_code为SUCCESS，则会返回一个result_code,再对该result_code进行判断
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                unifiedOrderVo.setPackageStr("prepay_id=" + response.get("prepay_id"));
                //调起支付参数重新签名  不要使用请求预支付订单时返回的签名
                Map<String, String> returnMap = getWxPayParam(unifiedOrderVo);
                returnMap.remove("class");
                unifiedOrderVo.setPaySign(md5Util.getSign(returnMap));
                //更新支付订单
                omOrderPo.setPrePayId(response.get("prepay_id"));
                omOrderMapper.updateById(omOrderPo);
                return unifiedOrderVo;
            } else {
                //调用微信统一下单接口返回失败
                String errCodeDes = response.get("err_code_des");
                //更新支付订单
                omOrderPo.setErrorCode(response.get("err_code"));
                omOrderPo.setErrorMsg(errCodeDes);
                omOrderMapper.updateById(omOrderPo);
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信统一下单接口返回失败
            String returnMsg = response.get("return_msg");
            //更新支付订单
            omOrderPo.setErrorCode(response.get("return_code"));
            omOrderPo.setErrorMsg(returnMsg);
            omOrderMapper.updateById(omOrderPo);
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }
    }


    /**
     * @Author yeJH
     * @Date 2019/10/12 9:43
     * @Description 将调用微信支付的参数转为map  完成签名操作
     *
     * @Update yeJH
     *
     * @param  unifiedOrderVo
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    private Map<String, String> getWxPayParam(UnifiedOrderVo unifiedOrderVo) {
        Map<String, String> map = new HashMap<>();
        map.put("appId", unifiedOrderVo.getAppId());
        map.put("timeStamp", unifiedOrderVo.getTimestamp());
        map.put("nonceStr", unifiedOrderVo.getNonceStr());
        map.put("package", unifiedOrderVo.getPackageStr());
        map.put("signType", WxPayConstant.SIGN_TYPE_MD5);
        return map;
    }

    /**
     * @Author yeJH
     * @Date 2019/11/19 22:33
     * @Description 统一下单接口微信签名参数
     *
     * @Update yeJH
     *
     * @param  config
     * @param  md5Util
     * @param  ipAddr   下单IP地址
     * @param  orderId  订单id
     * @param  totalFee 支付金额（分）
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    private Map<String, String> getSignMap(WXConfigUtil config, WxMD5Util md5Util, String ipAddr,
                                           Long orderId, Integer totalFee, UmUserPo umUserPo) throws Exception {
        Map<String, String> data = new HashMap<>();
        //小程序id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述  APP——需传入应用市场上的APP名字-实际商品名称
        data.put("body", config.getBody());
        //商户订单号
        data.put("out_trade_no", String.valueOf(orderId));
        data.put("total_fee", String.valueOf(totalFee));
        //调用微信支付API的机器IP
        data.put("spbill_create_ip", ipAddr);
        //异步通知地址
        data.put("notify_url", PAY_NOTIFY_URL);
        //交易类型
        data.put("trade_type", WxPayConstant.TRADE_TYPE_JSAPI);
        //用户标识  trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
        data.put("openid", umUserPo.getOpenId());
        //统一下单签名
        String orderSign = md5Util.getSign(data);
        data.put("sign", orderSign);
        return data;
    }

    /**
     * 微信支付结果通知
     * @param notifyData 异步通知后的XML数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payBack(String notifyData) throws Exception{
        String failXmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
        String successXmlBack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);
        //异步通知返回微信的参数 xml格式
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            //异步通知微信返回的参数 调用官方SDK转换成map类型数据
            notifyMap = WXPayUtil.xmlToMap(notifyData);
            //验证签名是否有效，有效则进一步处理
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                //状态
                String returnCode = notifyMap.get("return_code");
                if (returnCode.equals("SUCCESS")) {
                    String outTradeNo = notifyMap.get("out_trade_no");
                    //商品支付订单
                    OmOrderPo omOrderPo = omOrderMapper.selectById(outTradeNo);
                    if (null != omOrderPo) {
                        if(omOrderPo.getState().equals(OrderStateEnum.WAIT_PAY.getId())) {
                            //回调支付金额
                            Integer cashFee = Integer.parseInt(notifyMap.get("cash_fee"));
                            //支付订单计算应支付总金额
                            Integer totalMoney = BigDecimalUtil.safeMultiply(omOrderPo.getPayAmount(),
                                    new BigDecimal("100")).intValue();
                            if(cashFee.equals(totalMoney)) {
                                //签名 金额都没有问题  业务数据持久化
                                omOrderService.wxPayNotify(omOrderPo, notifyMap);
                            } else {
                                logger.info("微信手机支付回调成功，订单号:{}，但是金额不对应，回调支付金额{}，支付订单计算应支付总金额", outTradeNo, cashFee, totalMoney);
                            }
                        }
                        logger.info("微信手机支付回调成功订单号:{}", outTradeNo);
                        xmlBack = successXmlBack;
                    } else {
                        logger.info("微信手机支付回调失败订单号:{}", outTradeNo);
                        xmlBack = failXmlBack;
                    }
                }
                return xmlBack;
            } else {
                //签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
                logger.error("手机支付回调通知签名错误,返回参数：" + notifyMap);
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
                return xmlBack;
            }
        } catch (Exception e) {
            logger.error("手机支付回调通知失败", e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
        }
        return xmlBack;
    }



    /**
     *   微信查询订单接口  订单未操作的做业务更新
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderQuery(Long payOrderId) throws Exception {
        OmOrderPo omOrderPo = omOrderMapper.selectById(payOrderId);
        if(null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }

        WxMD5Util md5Util = new WxMD5Util();
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);
        Map<String, String> returnMap = new HashMap<>();

        //查询订单接口微信签名参数
        Map<String, String> data = new HashMap<>();
        //应用id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        //微信订单号(优先选择)  商户订单号
        if(Strings.isNotBlank(omOrderPo.getPayOrderNo())) {
            data.put("transaction_id", omOrderPo.getPayOrderNo());
        } else {
            data.put("out_trade_no", String.valueOf(omOrderPo.getId()));
        }
        //签名
        String sign = md5Util.getSign(data);
        data.put("sign", sign);

        //使用微信查询订单API请求预付订单
        Map<String, String> response = wxpay.orderQuery(data);
        //获取返回码
        String returnCode = response.get("return_code");
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                if("SUCCESS".equals(response.get("trade_state")) && omOrderPo.getState().equals(OrderStateEnum.WAIT_PAY.getId())) {
                    //回调支付金额
                    Integer cashFee = Integer.parseInt(response.get("cash_fee"));
                    //支付订单计算应支付总金额
                    Integer totalMoney = BigDecimalUtil.safeMultiply(omOrderPo.getPayAmount(), new BigDecimal("100")).intValue();
                    if(cashFee.equals(totalMoney)) {
                        //业务数据持久化
                        omOrderService.wxPayNotify(omOrderPo, response);
                    } else {
                        logger.info("微信查询订单成功，订单号:{}，但是金额不对应，回调支付金额{}，支付订单计算应支付总金额", omOrderPo, cashFee, totalMoney);
                    }
                }
            } else {
                //调用微信统一下单接口返回失败
                String errCodeDes = response.get("err_code_des");
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信查询订单接口返回失败
            String returnMsg = response.get("return_msg");
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }

    }



    /**
     * @Author yeJH
     * @Date 2019/11/19 23:51
     * @Description 调用官方SDK申请退款
     *
     * @Update yeJH
     *
     * @param  orderId  订单
     * @return java.lang.String
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(Long orderId)  {

        //订单
        OmOrderPo omOrderPo = omOrderMapper.selectById(orderId);
        if (null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }
        WxMD5Util md5Util = wxMD5Util;
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);

        //申请退款接口微信签名参数
        Map<String, String> data = new HashMap<>();
        //应用id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        if(null != omOrderPo.getPayOrderNo()) {
            //微信订单号
            data.put("transaction_id", omOrderPo.getPayOrderNo());
        } else {
            //商户订单号
            data.put("out_trade_no", String.valueOf(omOrderPo.getId()));
        }
        //商户退款单号 没有退款订单 直接用订单id
        data.put("out_refund_no", String.valueOf(orderId));
        //退款金额 = 支付金额
        String refundFee = Integer.toString(
                BigDecimalUtil.safeMultiply(omOrderPo.getPayAmount(), new BigDecimal("100")).intValue());
        data.put("refund_fee", refundFee);
        data.put("total_fee", refundFee);
        //申请退款签名
        String orderSign = null;
        try {
            orderSign = md5Util.getSign(data);
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL,"微信退款加签出错！");
        }
        data.put("sign", orderSign);

        //使用微信申请退款API请求预付订单
        Map<String, String> response = null;
        try {
            response = wxpay.refund(data);
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL,"微信退款请求出错！");
        }
        //获取返回码
        String returnCode = response.get("return_code");
        //若返回码return_code为SUCCESS，则会返回一个result_code,再对该result_code进行判断
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                return response.get("refund_id");
            } else {
                //调用微信申请退款接口返回失败
                String errCodeDes = response.get("err_code_des");
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信微信申请接口返回失败
            String returnMsg = response.get("return_msg");
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }
    }


}

