package com.njupt.hpc.edu.project.data.content.graph;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-26 12:54
 **/
@Data
public class Entity extends JSONObject {

    private final static String NONE_VALUE = "none";

    public Entity(){}

    public Entity(String name) {
        this.put("name", name);
    }

    public Entity(String name, String type) {
        // 如果构建实体时传入值为null，则传入默认值
        if (null == name) {
            name = NONE_VALUE;
        }
        if (null == type) {
            type = NONE_VALUE;
        }
        this.put("name", name);
        this.put("type", type);
    }
}
