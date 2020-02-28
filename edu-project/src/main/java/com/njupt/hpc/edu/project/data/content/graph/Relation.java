package com.njupt.hpc.edu.project.data.content.graph;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 关系
 * @date : 2020-02-26 13:02
 **/
@Data
public class Relation extends JSONObject {

    public Relation(String source, String target, String relation) {
        this.put("source", source);
        this.put("target", target);
        this.put("rela", relation);
    }
}
