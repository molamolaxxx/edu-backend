package com.njupt.hpc.edu.project.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-04-15 15:41
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResultDTO implements Serializable {

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
}
