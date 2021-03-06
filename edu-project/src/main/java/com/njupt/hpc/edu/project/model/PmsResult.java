package com.njupt.hpc.edu.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结果id
     */
    private String id;

    /**
     * 实例的id
     */
    private String instanceId;

    /**
     * 结果内容，json格式
     */
    private String content;

    /**
     * 结果明细文件地址
     */
    private String path;

    /**
     * 结果创建时间
     */
    private LocalDateTime createTime;

    /**
     * 结果更新时间
     */
    private LocalDateTime updateTime;
}
