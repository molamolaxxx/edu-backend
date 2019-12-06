package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * 枚举实例运行状态
 */
@Getter
public enum InstanceStateEnum {

    READY("0","准备执行"),
    RUNNING("1","执行中"),
    FINISH("2","已完成"),
    FAILED("-1","执行错误");

    private String code;
    private String desc;

    InstanceStateEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
