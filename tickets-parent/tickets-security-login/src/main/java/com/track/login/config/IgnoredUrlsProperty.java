package com.track.login.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * 从配置文件中读取忽略的url
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsProperty {

    private List<String> urls;
}
