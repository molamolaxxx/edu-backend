package com.njupt.hpc.edu.project.service.impl;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import org.junit.Test;

import java.io.File;

public class PmsDataServiceImplTest {

    @Test
    public void create() {
        File file = new File("/tmp/edu/hhh.csv");
        if (!file.exists()){
            throw new EduProjectException("数据路径不存在");
        }
        // 获取后缀
        Integer index = file.getName().lastIndexOf(".")+1;
        System.out.println(index);
        String suffix = file.getName().substring(index);
        System.out.println(suffix);
        System.out.println(file.length());
        System.out.println(File.separatorChar);
    }
}