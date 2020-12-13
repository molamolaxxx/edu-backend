package com.njupt.hpc.edu.common.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: edu-backend
 * @description: 课程情感评价模型训练模块的config
 * @author: Su
 * @create: 2020-12-9 16:02
 **/
@Configuration
@EnableConfigurationProperties({SentimentConfig.class})
@ConfigurationProperties(prefix = "edu.sentiment")
@Data
public class SentimentConfig {
    /**
     * 词嵌入的向量维度
     */
    private Integer embedding_vector_size = 10;

    /**
     * 文本最长单词数
     */
    private Integer maxlen = 100;
    /**
     * lstm层的维度
     */
    private Integer lstm_size = 32;

    /**
     * 迭代次数
     */
    private Integer epochs = 10;

    /**
     * 批量大小
     */
    private Integer batch_size = 128;

    /**
     * 训练集和验证集比例
     */
    private Double validation_split = 0.2;

    // 检查实例中的配置
    public void checkConfigInInstance(String json){
        if (null == json) {
            return;
        }
        // 可能会出现异常
        SentimentConfig sentimentConfig =  JSON.parseObject(json, SentimentConfig.class);
        BeanUtilsPlug.copyPropertiesReturnTarget(sentimentConfig, this);
    }

    public JSONObject parseToJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("embedding_vector_size",getEmbedding_vector_size());
        jsonObject.put("maxlen", getMaxlen());
        jsonObject.put("lstm_size", getLstm_size());
        jsonObject.put("epochs", getEpochs());
        jsonObject.put("batch_size", getBatch_size());
        jsonObject.put("validation_split", getValidation_split());
        return jsonObject;
    }

    public static void main(String[] args) {
        FusionConfig fusionConfig = new FusionConfig();
        fusionConfig.checkConfigInInstance(null);
    }
}
