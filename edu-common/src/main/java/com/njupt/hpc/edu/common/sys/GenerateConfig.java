package com.njupt.hpc.edu.common.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块的配置
 * @date : 2020-01-04 22:59
 **/
@Configuration
@EnableConfigurationProperties({GenerateConfig.class})
@ConfigurationProperties(prefix = "edu.generate")
@Data
public class GenerateConfig {

    /**
     * 实体评价最大线程数(2-8)
     */
    private Integer entityMaxThreadCount = 8;

    /**
     * 三元组评价最大线程数(1-4)
     */
    private Integer tupleMaxThreadCount = 4;

    // 关系置信度的阈值(0-100，越高评价参考系的置信度就越高)
    private Integer tupleConfidenceThreshold = 0;

    // 在候选关系对，查找topk个参考关系（任意，越多参考的数量越大，更加精准，但单次计算量加大）
    private Integer tupleTopK = 10;

    // 进入候选三元组的实体相似度阈值(只有高于此相似度的实体可以进入候选三元组)
    // (0 - 100越大越严格)
    private Double entitySimThreshold = 80.0;

    // 关系评价占总评价的权重（0-1越大关系评价占总评分的比例越大，属性评价则越小)
    private Double relationWeight = 0.7;

    // 检查实例中的配置
    public void checkConfigInInstance(String json){
        if (null == json) {
            return;
        }
        // 可能会出现异常
        GenerateConfig generateConfig =  JSON.parseObject(json, GenerateConfig.class);
        BeanUtilsPlug.copyPropertiesReturnTarget(generateConfig, this);
    }

    public JSONObject parseToJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entityMaxThreadCount", getEntityMaxThreadCount());
        jsonObject.put("tupleMaxThreadCount", getTupleMaxThreadCount());
        jsonObject.put("tupleConfidenceThreshold", getTupleConfidenceThreshold());
        jsonObject.put("tupleTopK", getTupleTopK());
        jsonObject.put("entitySimThreshold", getEntitySimThreshold());
        jsonObject.put("relationWeight", getRelationWeight());
        return jsonObject;
    }

    public static void main(String[] args) {
        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.checkConfigInInstance(null);
    }

}
