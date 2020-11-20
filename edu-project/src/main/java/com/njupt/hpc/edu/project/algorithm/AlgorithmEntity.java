package com.njupt.hpc.edu.project.algorithm;

import lombok.Data;

import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法服务：一个算法模块可以包括多个算法
 * @date : 2020-11-11 15:58
 **/
@Data
public class AlgorithmService {

    /**
     * 算法唯一标示
     */
    private String id;

    /**
     * 算法服务名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 主机地址
     */
    private String address;

    /**
     * 上线时间
     */
    private long startTime;

    /**
     * 最后续约时间
     */
    private long lastHeartBeat;

    /**
     * 包含实例种类
     */
    private List<String> instanceCodeList;
}