package com.njupt.hpc.edu.project.data.parser;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.CSVUtils;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.content.graph.Entity;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.content.graph.Relation;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @program: edu-backend
 * @description: 数据转换器的抽象类
 * @author: Su
 * @create: 2020-05-26 10:43
 **/
public abstract class BasicParser {

    protected Map<String, List<CSVLine>> csvContentMap = new ConcurrentHashMap<>();

    public abstract CSVContentVO parseDataCSV(String path, int offset, int limit);

    public abstract CSVContentVO parseResultDetail(String path, int offset, int limit);
    /**
    * @Autor:Su
    * @Description 解析三元组
    * @Param
    */
    protected CSVContentVO parseCSV(String path, Integer offset, Integer limit,
                                         Function<String, CSVLine> function) {
        // 从缓存中读取csv
        List<CSVLine> lines = csvContentMap.get(path);
        if (null == lines) {
            // 如果缓存中读不到csv
            List<String> csvLines = null;
            try {
                // todo 适配oss
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

    public GraphContentVO parseGraph(String path, Integer offset, Integer limit) {
        // 先进行csv的转换
        CSVContentVO first = this.parseDataCSV(path, offset, limit);
        List<CSVLine> lines = first.getResultList();
        // "实体1" -> "对应的实体id"
        Map<String, String> keyWordMap = new HashMap<>();
        // "实体id" -> "entity"
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

    public void clear(String path) {
        csvContentMap.remove(path);
    }

    /**
     * 检查分页起始页码的合法性
     * @param index
     * @param size
     * @return
     */
    protected Integer validIndex(Integer index, Integer size) {
        if (index < 0) {
            return  0;
        }
        else if (index > size) {
            return size;
        }
        return index;
    }

}
