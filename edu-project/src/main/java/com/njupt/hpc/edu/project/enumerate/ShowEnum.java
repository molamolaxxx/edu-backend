package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法展示模块枚举
 * @date : 2020-03-19 20:42
 **/
@Getter
public enum  ShowEnum {
    // 临时访客名
    VISITOR_NAME("visitor"),
    // 数据id前缀
    TEMP_DATA_NAME("temp_data"),
    // 实例id前缀
    TEMP_INSTANCE_NAME("temp_instance")
    ;

    private String name;


    ShowEnum(String name){
        this.name = name;
    }
}
