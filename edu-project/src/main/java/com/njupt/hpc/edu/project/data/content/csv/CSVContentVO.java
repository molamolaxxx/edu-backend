package com.njupt.hpc.edu.project.data.content.csv;

import lombok.Data;

import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description: csv格式下的vo
 * @date : 2020-02-26 12:42
 **/
@Data
public class CSVContentVO {

    // 页面偏移量
    private int offset;

    // 每页多少信息
    private int limit;

    /**
     * 保存三元组的列表
     */
    private List<CSVLine> resultList;
}
