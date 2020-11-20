package com.njupt.hpc.edu.project.interceptor;

import cn.hutool.json.JSONUtil;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.service.AlgorithmService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法拦截器， 非算法模块直接被拦截
 * @date : 2020-11-12 15:56
 **/
@Component
public class AlgorithmInterceptor implements HandlerInterceptor {

    private static final String ALGORITHM_URL_PREFIX = "algorithm";

    private static final String ALGORITHM_HEADER = "algorithm_id";

    @Resource
    private AlgorithmService algorithmService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 注册不拦截
        if (request.getRequestURL().toString().contains("register")) {
            return true;
        }
        String algorithmId = request.getHeader(ALGORITHM_HEADER);
        // 如果算法注册中心不包含对应算法，则拦截
        if (StringUtils.isEmpty(algorithmId) || !algorithmService.contains(algorithmId)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSONUtil.parse(CommonResult.forbidden("非算法接口，无权限")));
            response.getWriter().flush();
            return false;
        }
        return true;
    }
}
