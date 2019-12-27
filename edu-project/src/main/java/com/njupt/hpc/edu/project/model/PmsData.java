package com.njupt.hpc.edu.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 算法数据表
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsData implements Serializable {

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
     * 工程创建者id
     */
    private String uid;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 数据创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数据更改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0:csv数据 1:json数据 2:xml数据
     */
    private String dataType;

    /**
     * 数据大小，单位为b
     */
    private String dataSize;

    /**
     * 数据绝对路径
     */
    private String dataPath;


}
