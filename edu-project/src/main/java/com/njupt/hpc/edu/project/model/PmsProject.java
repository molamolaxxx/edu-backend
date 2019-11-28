package com.njupt.hpc.edu.project.model;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 质量评价核心工程
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     */
    private String id;

    /**
     * 创建者用户id
     */
    private String uid;

    /**
     * 工程名
     */
    private String name;

    /**
     * 工程描述
     */
    private String description;

    /**
     * 工程状态 0:未执行 1:执行中 2:已完成
     */
    private String state;

    /**
     * 工程创建时间
     */
    private LocalDateTime createTime;

    /**
     * 工程修改时间
     */
    private LocalDateTime updateTime;


}
