package com.track.common.constant.wetch.applet;
/**
 * @description: 小程序常量
 * @author yeJH
 * @since 2019/10/16 21:21
 */
public class WxAppletConstant {

    // 登录凭证校验 根据登录凭证 code 获取小程序用户openId
    public final static String CODE_2_SESSION =
            "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";


}
