package com.njupt.hpc.edu.project.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 数据的viewobject
 * @date : 2020-02-13 17:23
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataVO {

    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
    private String id;

    /**
     * 数据名
     */
    private String name;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 0:csv数据 1:json数据 2:xml数据
     */
    private String dataType;

    /**
     * 数据大小，单位为b
     */
    private String dataSize;

    /**
     * 数据对应什么类型的实例
     */
    private String instanceType;
}
