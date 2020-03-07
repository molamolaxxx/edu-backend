package com.njupt.hpc.edu.project.data.content.csv;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author : molamola
 * @Project: edu
 * @Description: csv每一行的数据
 * @date : 2020-02-26 13:11
 * first relation end
 **/
@Data
public class CSVLine extends JSONObject {

    public String getFirst() {
        return (String) this.get("first");
    }

    public String getEnd() {
        return (String) this.get("end");
    }

    public String getRelation() {
        return (String) this.get("relation");
    }
}
