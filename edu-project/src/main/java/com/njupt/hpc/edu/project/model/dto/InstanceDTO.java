package com.njupt.hpc.edu.project.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 用于实例的dto
 * @date : 2020-01-13 14:20
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工程创建者id
     */
    private String uid;

    /**
     * 实例名
     */
    private String name;

    /**
     * 实例数据id
     */
    private String dataId;

    /**
     * 0、资源生成质量评价 1、资源聚合质量评价 2、资源用户情感分析
     */
    private String type;

    /**
     * 实例描述
     */
    private String description;

    /**
     * 以json形式保存的配置
     */
    private String config;
}
