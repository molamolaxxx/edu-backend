package com.njupt.hpc.edu.project.model;

import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 算法实例表
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实例id
     */
    private String id;

    /**
     * 实例创建者用户id
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
     * 0:未执行 1:执行中 2:已完成 -1 执行错误
     */
    private String state = InstanceStateEnum.READY.getCode();

    /**
     * 0、资源生成质量评价 1、资源聚合质量评价 2、资源用户情感分析
     */
    private String type;

    /**
     * 实例创建时间
     */
    private LocalDateTime createTime;

    /**
     * 实例更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 实例开始时间
     */
    private LocalDateTime startTime;

    /**
     * 实例结束时间
     */
    private LocalDateTime finishTime;

    /**
     * 实例描述
     */
    private String description;


}
