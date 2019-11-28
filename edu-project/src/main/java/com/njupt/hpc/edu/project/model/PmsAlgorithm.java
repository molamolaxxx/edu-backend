package com.njupt.hpc.edu.project.model;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 算法表
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsAlgorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 算法id
     */
    private String id;

    /**
     * 父工程id
     */
    private String pid;

    /**
     * 0:未执行 1:执行中 2:已完成 -1 执行错误
     */
    private String state;

    /**
     * 0:实体准确度 1:sourceRank 2:teansE 3:对齐 4:冗余度 5:资源情感分析
     */
    private String type;

    /**
     * 前一个算法的id，空则为-1
     */
    private String headId;

    /**
     * 后一个算法的id，空则为-1
     */
    private String tailId;

    /**
     * 算法数据id
     */
    private String dataId;

    /**
     * 算法创建时间
     */
    private LocalDateTime createTime;

    /**
     * 算法更新时间
     */
    private LocalDateTime updateTime;


}
