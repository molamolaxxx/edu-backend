package com.njupt.hpc.edu.project.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 数据使用的dto
 * @date : 2020-01-13 15:10
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工程创建者id
     */
    private String uid;

    /**
     * 数据名
     */
    private String name;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 数据绝对路径
     */
    private String dataPath;

    /**
     * 数据的实例类型
     */
    private String instanceType;
}
