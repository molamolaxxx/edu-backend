package com.njupt.hpc.edu.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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


}
