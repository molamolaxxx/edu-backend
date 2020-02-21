package com.njupt.hpc.edu.common.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-21 11:35
 **/
public class IdUtil {

    public static String generateId(String module){
        return String.format("%s_%s%s", module,
                RandomStringUtils.randomAlphanumeric(8),
                String.valueOf(System.currentTimeMillis()).substring(0, 8));
    }

    public static void main(String[] args) {
        System.out.println(generateId("instance"));
    }
}
