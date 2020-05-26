package com.njupt.hpc.edu.project.data.parser;

import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;

import java.util.function.Function;

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
<<<<<<< HEAD
    default CSVContentVO parseCSV(String path, Integer offset, Integer limit, Function<String,CSVLine> function){
=======
    default CSVContentVO parseCSV(String path, Integer offset, Integer limit, Function<String, CSVLine> function){
>>>>>>> cd110bc58dccd8d2ac7c7d32cb28c9af2e4c89ca
        throw new RuntimeException("need to override");
    }

    /**
     * 转化data到csv格式
     * @param path
     * @return
     */
    default CSVContentVO parseCSV(String path, Integer offset, Integer limit){
        throw new RuntimeException("need to override");
    }

    /**
     * 转化data到图谱数据格式
     * @param path
     * @return
     */
    default GraphContentVO parseGraph(String path, Integer offset, Integer limit){
        throw new RuntimeException("need to override");
    }

    /**
     * 清除缓存
     * @param path
     */
    void clear(String path);

    /**
     * 将csv数据转化成vo对象，序列化到前端展示
     * @param path
     * @param offset
     * @param limit
     * @return
     */
    default CSVContentVO parseDataCSV(String path, int offset, int limit){return null;}
    default CSVContentVO parseResultDetail(String path, int offset, int limit){return null;}
<<<<<<< HEAD
}
=======
}
>>>>>>> cd110bc58dccd8d2ac7c7d32cb28c9af2e4c89ca
