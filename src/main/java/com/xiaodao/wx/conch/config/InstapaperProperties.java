package com.xiaodao.wx.conch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiaodaojiang
 * @Classname InstapaperProperties
 * @Version 1.0.0
 * @Date 2024-07-16 22:49
 * @Created by xiaodaojiang
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "instapaper.basic")
public class InstapaperProperties {

    private String username;

    private String password;

}
