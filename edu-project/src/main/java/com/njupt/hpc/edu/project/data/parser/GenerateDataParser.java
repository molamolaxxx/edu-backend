package com.njupt.hpc.edu.project.data.parser;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.CSVUtils;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.content.graph.Entity;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.content.graph.Relation;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块数据转换器
 * @date : 2020-02-27 10:17
 **/
@Component
public class GenerateDataParser implements DataParser {

    private Map<String, List<CSVLine>> csvContentMap = new ConcurrentHashMap<>();

    // private Map<String, GraphContentVO> graphContentMap = new ConcurrentHashMap<>();

    @Override
    public CSVContentVO parseCSV(String path, Integer offset, Integer limit) {
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
            lines = csvLines.stream().map(line -> {
                String[] split = line.split(",");
                if (split.length != 3) {
                    split = line.split(" ");
                }
                if (split.length != 3) {
                    throw new EduProjectException("数据解析失败！请阅读三元组csv格式规范，上传正确格式的csv");
                }
                CSVLine csvLine = new CSVLine();
                csvLine.put("first", split[0]);
                csvLine.put("relation", split[1]);
                csvLine.put("end", split[2]);
                return csvLine;
            }).collect(Collectors.toList());
            // 存入缓存
            csvContentMap.put(path, lines);
        }
        // 对lines进行分页读取
        Integer total = lines.size() % limit == 0 ? lines.size() / limit : lines.size() / limit + 1;
        Integer start = validIndex((offset - 1)* limit, lines.size());
        Integer stop =  validIndex(offset * limit, lines.size());
        List<CSVLine> result = lines.subList(start, stop);
        CSVContentVO csvContentVO = new CSVContentVO(offset, limit, total);
        csvContentVO.setResultList(result);
        return csvContentVO;
    }

    @Override
    public GraphContentVO parseGraph(String path, Integer offset, Integer limit) {
        // 先进行csv的转换
        CSVContentVO first = this.parseCSV(path, offset, limit);
        List<CSVLine> lines = first.getResultList();
        // "实体1" -> "1"
        Map<String, String> keyWordMap = new HashMap<>();
        // "1" -> "entity"
        Map<String, Entity> entityMap = new HashMap<>();
        int point = 0;
        // 循环csvlist
        Iterator<CSVLine> iterator = lines.iterator();
        while (iterator.hasNext()) {
            CSVLine line = iterator.next();
            String firstEntityName = line.getFirst();
            String endEntityName = line.getEnd();
            // 如果map中不包含实体名，则放入
            if (!keyWordMap.keySet().contains(firstEntityName)){
                keyWordMap.put(firstEntityName, String.valueOf(point));
                entityMap.put(String.valueOf(point++), new Entity(firstEntityName, "first"));
            }
            if (!keyWordMap.keySet().contains(endEntityName)) {
                keyWordMap.put(endEntityName, String.valueOf(point));
                entityMap.put(String.valueOf(point++), new Entity(endEntityName, "end"));
            }
        }

        // 再一次循环csvlist，构建返回参数
        List<Relation> relationList = new ArrayList<>();
        iterator = lines.iterator();
        while (iterator.hasNext()) {
            CSVLine line = iterator.next();
            Relation r = new Relation(keyWordMap.get(line.getFirst()),
                                      keyWordMap.get(line.getEnd()),
                                      line.getRelation());
            relationList.add(r);
        }

        return new GraphContentVO(entityMap, relationList);
    }

    @Override
    public CSVContentVO parseResultCSV(String path) {
        return null;
    }

    @Override
    public GraphContentVO parseResultGraph(String path) {
        return null;
    }

    /**
     * 检查分页起始页码的合法性
     * @param index
     * @param size
     * @return
     */
    private Integer validIndex(Integer index, Integer size) {
        if (index < 0) {
            return  0;
        }
        else if (index > size) {
            return size;
        }
        return index;
    }

    @Override
    public void clear(String path) {
        csvContentMap.remove(path);
    }

}
