package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * 枚举实例类别
 */
@Getter
public enum InstanceTypeEnum {

    // 生成质量评价 包括准确度、sourcerank、transE
    GENERATE_EVALUATE("0","生成质量评价"),
    // 融合质量评价 包括冗余度与对其率
    FUSION_EVALUATE("1","融合质量评价"),
    // 情感分析算法 根据用户评论获取资源评价
    SENTIMENT_EVALUATE("2","资源情感分析")
    ;

    private String code;
    private String desc;

    public static final String _GENERATE_EVALUATE = "0";
    public static final String _FUSION_EVALUATE = "1";
    public static final String _SENTIMENT_EVALUATE = "2";

    InstanceTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
