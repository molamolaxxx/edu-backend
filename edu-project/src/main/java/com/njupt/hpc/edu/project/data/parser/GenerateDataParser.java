package com.njupt.hpc.edu.project.data.parser;

import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import org.springframework.stereotype.Component;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块数据转换器
 * @date : 2020-02-27 10:17
 **/
@Component
public class GenerateDataParser implements DataParser {

    @Override
    public CSVContentVO parseCSV(String path) {
        return null;
    }

    @Override
    public GraphContentVO parseGraph(String path) {
        return null;
    }

    @Override
    public CSVContentVO parseResultCSV(String path) {
        return null;
    }

    @Override
    public GraphContentVO parseResultGraph(String path) {
        return null;
    }
}
