package com.njupt.hpc.edu.project.data.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.CSVUtils;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.parser.BasicParser;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块数据转换器
 * @date : 2020-02-27 10:17
 **/
@Component
public class GenerateDataParser extends BasicParser {

    public CSVContentVO parseGenerateCSV(String path, Integer offset, Integer limit,
                                 Function<String, CSVLine> function) {
        // 从缓存中读取csv
        List<CSVLine> lines = csvContentMap.get(path);
        if (null == lines) {
            // 如果缓存中读不到csv
            List<String> csvLines = null;
            try {
                csvLines = CSVUtils.readCSV(new File(path));
            } catch (IOException e) {
                throw new EduProjectException("读取csv文件时发生IO错误");
            }
            // csv检查
            lines = csvLines.stream().map(function).collect(Collectors.toList());
            // 检查第一行是否为null，function默认为将标题行置为Null
            if (null == lines.get(0)) lines.remove(0);
            // 存入缓存
            csvContentMap.put(path, lines);
        }
        // 对lines进行分页读取
        Integer pages = lines.size() % limit == 0 ? lines.size() / limit : lines.size() / limit + 1;
        Integer start = validIndex((offset - 1)* limit, lines.size());
        Integer stop =  validIndex(offset * limit, lines.size());
        List<CSVLine> result = lines.subList(start, stop);
        CSVContentVO csvContentVO = new CSVContentVO(offset, limit, lines.size(), pages);
        csvContentVO.setResultList(result);
        return csvContentVO;
    }


    public CSVContentVO parseDataCSV(String path, int offset, int limit) {
        return this.parseGenerateCSV(path, offset, limit, parseTupleDataFunc());
    }

    @Override
    public CSVContentVO parseResultDetail(String path, int offset, int limit) {
        return this.parseGenerateCSV(path, offset, limit, parseResultDetailFunc());
    }

    /**
     * 转化三元组数据的function
     * @return
     */

    protected Function<String, CSVLine> parseTupleDataFunc(){
        return line -> {
            String[] split = line.split(",");
            if (split.length != 5) {
                split = line.split(" ");
            }
            if (split.length != 5) {
                throw new EduProjectException("数据解析失败！请阅读三元组csv格式规范，上传正确格式的csv");
            }
            CSVLine csvLine = new CSVLine();
            csvLine.put("id", split[0]);
            csvLine.put("first", split[1]);
            csvLine.put("relation", split[2]);
            csvLine.put("end", split[3]);
            csvLine.put("condition", split[4]);
            return csvLine;
        };
    }

    /**
     * 转化结果数据的function
     * @return
     */
    protected Function<String, CSVLine> parseResultDetailFunc() {
        return line -> {
            String[] split = line.split(",");
            if (split.length != 12) {
                split = line.split(" ");
            }
            if (split.length != 12) {
                throw new EduProjectException("评价结果解析失败!");
            }
            CSVLine csvLine = new CSVLine();
            if (split[0].equals("实体id")) {
                // 删去列标题
                return null;
            }
            csvLine.put("id", split[0]);
            csvLine.put("first", split[1]);
            csvLine.put("firstScore", split[2]);
            csvLine.put("relation", split[3]);
            csvLine.put("relationScore", cutLength(split[4]));
            csvLine.put("end", split[5]);
            csvLine.put("endScore", cutLength(split[6]));
            csvLine.put("propScore", cutLength(split[7]));
            csvLine.put("totalScore", cutLength(split[8]));
            csvLine.put("need", split[9]);
            csvLine.put("refer", split[10].length() == 1 ? split[10] : getRefer(split[10]));
            csvLine.put("condition", split[11]);
            return csvLine;
        };
    }

    // 将参考三元组文本转化成json

    protected  JSONObject getRefer(String referText) {
        referText = referText.substring(1,referText.length()-1);
        String[] split = referText.split("-");
        JSONObject jo = new JSONObject();
        jo.put("first", split[0]);
        jo.put("relation", split[1]);
        jo.put("end", split[2]);
        return jo;
    }
    // 准确度保留五位

    protected String cutLength(String raw) {
        if (raw.length() >5) {
            return raw.substring(0,6);
        }
        return raw;
    }
}

