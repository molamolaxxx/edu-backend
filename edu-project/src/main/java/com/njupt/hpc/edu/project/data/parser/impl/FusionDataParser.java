package com.njupt.hpc.edu.project.data.parser.impl;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.CSVUtils;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.csv.CSVLine;
import com.njupt.hpc.edu.project.data.parser.BasicParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: edu-backend
 * @description: 融合模块的数据转换器
 * @author: Su
 * @create: 2020-05-21 16:57
 **/
@Component
public class FusionDataParser extends BasicParser {

    //单纯写解析融合csv方法
    public CSVContentVO parseFusionCSV(String path, Integer offset, Integer limit,
                                 Function<String, List<CSVLine>> function) {
        // 从缓存中读取csv
        List<CSVLine> tupleLines = csvContentMap.get(path);
        if (null == tupleLines) {
            tupleLines=new ArrayList<>();
            // 如果缓存中读不到csv
            List<String> recodeLines = null;
            try {
                recodeLines = CSVUtils.readCSV(new File(path));
            } catch (IOException e) {
                throw new EduProjectException("读取csv文件时发生IO错误");
            }
            // 将每行记录转换成一个包含多个三元组集合的集合
            List<List<CSVLine>> tupleLists = recodeLines.stream().map(function).collect(Collectors.toList());
            //将包含的多个三元组集合进行合并成一个集合
           for(List<CSVLine> tupleList:tupleLists){
               for(CSVLine csvLine:tupleList){
                   tupleLines.add(csvLine);
               }
           }
            // 检查第一行是否为null，function默认为将标题行置为Null
            if (null == tupleLines.get(0)) tupleLines.remove(0);
            // 存入缓存
            csvContentMap.put(path, tupleLines);
        }
        // 对lines进行分页读取
        Integer pages = tupleLines.size() % limit == 0 ? tupleLines.size() / limit : tupleLines.size() / limit + 1;
        Integer start = validIndex((offset - 1)* limit, tupleLines.size());
        Integer stop =  validIndex(offset * limit, tupleLines.size());
        List<CSVLine> result = tupleLines.subList(start, stop);
        CSVContentVO csvContentVO = new CSVContentVO(offset, limit, tupleLines.size(), pages);
        csvContentVO.setResultList(result);
        return csvContentVO;
    }


    @Override
    public CSVContentVO parseDataCSV(String path, int offset, int limit) {
        return this.parseFusionCSV(path, offset, limit, parseRecord2TupleDataFunc());
    }

    @Override
    public CSVContentVO parseResultDetail(String path, int offset, int limit) {
        return null;
    }

    /**
     * 将记录转化成三元组数据的function
     * @return
     */
    private Function<String, List<CSVLine>> parseRecord2TupleDataFunc() {
        //1,俗称,中文名:俗称;拼音:sú chēng;注音:ㄙㄨˊㄔㄥ;出处:《春在堂随笔》;,字词,俗称，词义：通俗的称呼;非正式的名称；一般大众给予的称呼。
        return line -> {
            //存放三元组对象的集合
            List<CSVLine> csvLines=new ArrayList<>();
            String[] split = line.split(",");
            if (split.length != 5) {
                throw new EduProjectException("数据解析失败！请阅读融合质量评价数据csv格式规范，上传正确格式的csv");
            }
            //对属性进行切割(数组最后一位为空)
            String[] attributes=split[2].split(";");
            for (String attribute:attributes) {
                if(attribute.isEmpty()) continue;
                CSVLine csvLine = new CSVLine();
                String[] attribute_arry=attribute.split(":");
                //属性值不能为空
                if(attribute_arry[1].isEmpty()) continue;
                csvLine.put("id",split[0]);
                csvLine.put("first", split[1]);
                csvLine.put("relation",attribute_arry[0]);
                csvLine.put("end", attribute_arry[1]);
                csvLines.add(csvLine);
            }
            //对类别进行切割(数组最后一位为空)
            String[] labels=split[3].split(";");
            for (String label:labels) {
                if(label.isEmpty()) continue;
                CSVLine csvLine = new CSVLine();
                csvLine.put("id",split[0]);
                csvLine.put("first", split[1]);
                csvLine.put("relation","类别");
                csvLine.put("end",label);
                csvLines.add(csvLine);
            }
            CSVLine csvLine = new CSVLine();
            csvLine.put("id",split[0]);
            csvLine.put("first", split[1]);
            csvLine.put("relation","定义");
            csvLine.put("end", split[4]);
            csvLines.add(csvLine);
            return csvLines;
        };
    }

    /**
     * 转化冗余结果的function
     * @return
     */

    protected Function<String, CSVLine> parseRedundanceResultDetailFunc(){
        return line -> {
            String[] split = line.split(",");
            if (split.length != 5) {
                split = line.split(" ");
            }
            if (split.length != 5) {
                throw new EduProjectException("数据解析失败！请阅读三元组csv格式规范，上传正确格式的csv");
            }
            if (split[0].equals("实体Id")) {
                // 删去列标题
                return null;
            }
            CSVLine csvLine = new CSVLine();
            csvLine.put("entity_id", split[0]);
            csvLine.put("redundance_entity_id", split[1]);
            csvLine.put("attribute_sim", split[2]);
            csvLine.put("description_sim", split[3]);
            csvLine.put("entity_sim", split[4]);
            return csvLine;
        };
    }

    /**
     * 转化信息缺失度结果的function
     * @return
     */

    protected Function<String, CSVLine> parseInfoLackResultDetailFunc(){
        return line -> {
            String[] split = line.split(",");
            if (split.length != 4) {
                split = line.split(" ");
            }
            if (split.length != 4) {
                throw new EduProjectException("数据解析失败！请阅读三元组csv格式规范，上传正确格式的csv");
            }
            if (split[0].equals("实体Id")) {
                // 删去列标题
                return null;
            }
            CSVLine csvLine = new CSVLine();
            csvLine.put("entityId", split[0]);
            csvLine.put("entityName", split[1]);
            csvLine.put("lackAttrList", split[2]);
            csvLine.put("entityInfoLackRate", split[3]);
            return csvLine;
        };
    }
    /**
    * @Autor:Su
    * @Description 冗余结果解析页面
    * @Param
    */
    public CSVContentVO parseRedundanceResultDetail(String path,int offset, int limit) {
        return this.parseCSV(path, offset, limit, parseRedundanceResultDetailFunc());
    }
    /**
     * @Autor:Su
     * @Description 信息缺失度结果解析页面
     * @Param
     */
    public CSVContentVO parseInfoLackResultDetail(String path,int offset, int limit) {
        return this.parseCSV(path, offset, limit, parseInfoLackResultDetailFunc());
    }


}
