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
    private Integer offset;

    // 每页多少信息
    private Integer limit;

    // 总共多少条
    private Integer total;

    // 总共多少页
    private Integer pages;

    public CSVContentVO(Integer offset, Integer limit, Integer total, Integer pages) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.pages = pages;
    }

    /**
     * 保存三元组的列表
     */
    private List<CSVLine> resultList;
}
