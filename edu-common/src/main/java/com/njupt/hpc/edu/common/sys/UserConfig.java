package com.njupt.hpc.edu.common.sys;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 用户设置
 * @date : 2020-02-14 13:44
 **/
@Configuration
@EnableConfigurationProperties({UserConfig.class})
@ConfigurationProperties(prefix = "edu.user")
@Data
public class UserConfig {

    /**
     * 用户头像在服务器上存储的位置
     */
    private String headerIconPath = "/tmp/edu/header-icon";
}
