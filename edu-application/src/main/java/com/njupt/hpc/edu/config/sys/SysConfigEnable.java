package com.njupt.hpc.edu.config.sys;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 声明此包下的所有配置，均为系统可用配置
 * @date : 2019-12-16 16:11
 **/
@Configuration
@EnableConfigurationProperties({DataConfig.class})
public class SysConfigEnable {
}
