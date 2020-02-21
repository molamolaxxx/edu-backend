package com.njupt.hpc.edu.project.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 用于前端展示的实例列表vo
 * @date : 2020-02-11 10:57
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstanceItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实例id
     */
    private String id;

    /**
     * 实例名
     */
    private String name;

    /**
     * 实例数据id
     */
    private String dataId;

    /**
     * 0:未执行 1:执行中 2:已完成 -1 执行错误
     */
    private String state;

    /**
     * 0、资源生成质量评价 1、资源聚合质量评价 2、资源用户情感分析
     */
    private String type;

}

