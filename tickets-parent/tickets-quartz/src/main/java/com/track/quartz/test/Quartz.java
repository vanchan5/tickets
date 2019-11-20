package com.track.quartz.test;

import com.track.core.annotation.logback.LogBack;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @since 2019-10-29
 *
 */
@Slf4j
@Component
@Data
public class Quartz {

    public String texts = "测试spel";

    @LogBack
    public String quartzTest(String texts){

        String text = "测试定时任务传参数";
        log.info("测试定时任务传参数");
        log.info(texts);
        return text;
    }


    public String quartzTest2(){

        String text = "无参定时任务测试";
        log.info("无参定时任务测试");
        return text;
    }

}
