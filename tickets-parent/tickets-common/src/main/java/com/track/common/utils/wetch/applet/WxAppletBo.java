package com.track.common.utils.wetch.applet;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 小程序参数配置
 * @author yeJH
 * @since 2019/10/16 21:32
 */
@Slf4j
@Component
@Data
public class WxAppletBo {

    @Value("${wetch.applet.APP_ID}")
    public String APP_ID;

    @Value("${wetch.applet.SECRET}")
    public String SECRET;

    @Bean
    public int instanceWxApplet(){
        WxAppletConfig.setAppId(APP_ID);
        WxAppletConfig.setSECRET(SECRET);
        return 0;
    }

}
