package com.njupt.hpc.edu.project.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法拦截器， 非算法模块直接被拦截
 * @date : 2020-11-12 15:56
 **/
public class AlgorithmFilterInterceptor implements HandlerInterceptor {

    private static final String ALGORITHM_URL_PREFIX = "algorithm";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }
}
