package com.njupt.hpc.edu.project.data.parser;

import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-27 14:46
 **/

public interface DataParser {

    /**
     * 转化data到csv格式
     * @param path
     * @return
     */
    CSVContentVO parseCSV(String path, Integer offset, Integer limit);

    /**
     * 转化data到图谱数据格式
     * @param path
     * @return
     */
    GraphContentVO parseGraph(String path, Integer offset, Integer limit);

    /**
     * 转化result到csv格式
     * @return
     */
    CSVContentVO parseResultCSV(String path);

    /**
     * 转化result到图谱格式
     * @return
     */
    GraphContentVO parseResultGraph(String path);

    /**
     * 清除缓存
     * @param path
     */
    void clear(String path);
}
