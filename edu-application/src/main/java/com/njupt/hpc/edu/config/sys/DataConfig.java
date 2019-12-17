package com.njupt.hpc.edu.config.sys;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 数据系统配置
 * @date : 2019-12-16 15:54
 **/

@ConfigurationProperties(prefix = "edu.data")
@Data
public class DataConfig {

    /**
     * 数据在服务器上存储的路径
     */
    private String dataPath = "/tmp/edu";

    /**
     * 数据最大限制,默认为1m
     */
    private Long maxDataSize = 1000000L;
}
