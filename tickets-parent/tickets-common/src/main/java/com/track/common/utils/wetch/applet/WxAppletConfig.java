package com.track.common.utils.wetch.applet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 小程序参数配置
 * @author yeJH
 * @since 2019/10/16 21:32
 */
@Component
@Slf4j
public class WxAppletConfig {

    public static String APP_ID;

    public static String SECRET;

    public static String getAppId() {
        return APP_ID;
    }

    public static void setAppId(String appId) {
        APP_ID = appId;
    }

    public static String getSECRET() {
        return SECRET;
    }

    public static void setSECRET(String SECRET) {
        WxAppletConfig.SECRET = SECRET;
    }

    /*@Bean
    public void a(){
        log.error("测试---==="+getAppId());
        try {
            log.error(WxAppletUtil.codeToSession("033pKJB42gQj0P0CBeB42CfPB42pKJBb"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
