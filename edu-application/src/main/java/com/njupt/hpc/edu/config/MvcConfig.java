package com.njupt.hpc.edu.config;

import com.njupt.hpc.edu.common.sys.UserConfig;
import com.njupt.hpc.edu.project.interceptor.AlgorithmInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-14 13:58
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private UserConfig userConfig;

    @Resource
    private AlgorithmInterceptor algorithmInterceptor;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(algorithmInterceptor).addPathPatterns("/algorithm/**");
    }
}
