package com.njupt.hpc.edu.project.data.parser.impl;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.content.graph.Entity;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.content.graph.Relation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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

    public GraphContentVO parseGraph(String path, Integer offset, Integer limit) {
        // 先进行csv的转换
        CSVContentVO first = this.parseDataCSV(path, offset, limit);
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
