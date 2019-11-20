package com.track.common.constant.wetch.applet;

/**
 * @author yeJH
 * @Description 微信小程序支付常量
 * @since 2019/11/19 17:48
 */
public class WxPayConstant {

    //交易类型 JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    //币种  微信支付订单支付时使用的币种
    public static final String FEE_TYPE = "CNY";

    //签名类型，默认为MD5
    public static final String SIGN_TYPE_MD5 = "MD5";
}
