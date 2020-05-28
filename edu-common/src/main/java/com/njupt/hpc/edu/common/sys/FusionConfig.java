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
 * @description: 聚合模块的config
 * @author: Su
 * @create: 2020-05-13 16:02
 **/
@Configuration
@EnableConfigurationProperties({FusionConfig.class})
@ConfigurationProperties(prefix = "edu.fusion")
@Data
public class FusionConfig {

    /**
     * 实例评价最大线程数(1-4)
     */
    private Integer instanceMaxThreadCount = 4;

    /**
     * 实体评价最大线程数(1-8)
     */
    private Integer entityMaxThreadCount = 8;
    /**
     * 实体属性相似度权重
     */
    private Double entityAttributeSimWeight = 0.5;

    /**
     * 实体冗余阀值
     */
    private Double entityRedunceWeight = 0.97;

    // 检查实例中的配置
    public void checkConfigInInstance(String json){
        if (null == json) {
            return;
        }
        // 可能会出现异常
        FusionConfig fusionConfig =  JSON.parseObject(json, FusionConfig.class);
        BeanUtilsPlug.copyPropertiesReturnTarget(fusionConfig, this);
    }

    public JSONObject parseToJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("instanceMaxThreadCount", getInstanceMaxThreadCount());
        jsonObject.put("entityMaxThreadCount", getEntityMaxThreadCount());
        jsonObject.put("entityAttributeSimWeight", getEntityAttributeSimWeight());
        jsonObject.put("entityRedunceWeight", getEntityRedunceWeight());
        return jsonObject;
    }

    public static void main(String[] args) {
        FusionConfig fusionConfig = new FusionConfig();
        fusionConfig.checkConfigInInstance(null);
    }

}
