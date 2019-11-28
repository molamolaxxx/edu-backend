package com.njupt.hpc.edu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-25 09:21
 **/
@Configuration
@MapperScan({"com.njupt.hpc.edu.*.dao"})
public class MapperConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        // 注入分页插件
        return new PaginationInterceptor();
    }
}
