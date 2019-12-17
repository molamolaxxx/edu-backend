package com.njupt.hpc.edu.common.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 获取Spring容器
 * @date : 2019-12-17 12:19
 **/
@Component
public class EduApplicationAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
         applicationContext = context;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
