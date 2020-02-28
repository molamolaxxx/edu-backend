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

    public Entity(){}

    public Entity(String name) {
        this.put("name", name);
    }

    public Entity(String name, String type) {
        this.put("name", name);
        this.put("type", type);
    }
}
