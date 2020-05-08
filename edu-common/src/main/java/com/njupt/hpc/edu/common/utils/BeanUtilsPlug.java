package com.njupt.hpc.edu.common.utils;

import org.springframework.beans.BeanUtils;

/**
 * @Author: molamola
 * @Date: 19-7-1 下午5:13
 * @Version 1.0
 * bean复制补丁
 */
public class BeanUtilsPlug {

    /**
     * 复制bean返回目标对象
     * @param source
     * @param target
     * @return
     */
    public static <T>  T copyPropertiesReturnTarget(T source ,T target){
        BeanUtils.copyProperties(source,target);
        return target;
    }

    /**
     * 复制bean返回原对象
     * @param source
     * @param target
     * @return
     */
    public static <T>  T copyPropertiesReturnSource(T source ,T target){
        BeanUtils.copyProperties(source,target);
        return source;
    }
}
