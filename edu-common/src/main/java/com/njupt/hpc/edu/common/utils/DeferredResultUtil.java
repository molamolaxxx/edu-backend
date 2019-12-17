package com.njupt.hpc.edu.common.utils;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.cache.DeferredResultCache;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 异步请求构造器
 * @date : 2019-12-17 09:28
 **/
public class DeferredResultUtil {

    /**
     * 构建一个DeferredResult
     * @param actionId 操作id
     * @param timeoutMsg 操作超时后返回的信息
     * @return
     */
    public static DeferredResult build(String actionId, String timeoutMsg){
        DeferredResult<CommonResult> result = new DeferredResult(5000L);
        result.onTimeout(() -> {
            result.setResult(CommonResult.timeout(timeoutMsg));
            // 删除对应键，防止python模块超时返回时占用，使缓存产生冗余
            DeferredResultCache.del(actionId);
        });
        return result;
    }
}
