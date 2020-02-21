package com.njupt.hpc.edu.config;

import com.njupt.hpc.edu.common.sys.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-14 13:58
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserConfig userConfig;

    /**
     * 将机器绝对路径映射到url
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String iconRootPath = userConfig.getHeaderIconPath();
        if (!iconRootPath.endsWith(File.separator))
            iconRootPath += File.separator;
        registry.addResourceHandler("/iconHeader/**")
                .addResourceLocations("file:"+ iconRootPath);
    }
}
