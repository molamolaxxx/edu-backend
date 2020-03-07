package com.njupt.hpc.edu.common.cache;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-16 17:25
 * 异步请求结果的缓存
 **/
public class DeferredResultCache {

    // 使用线程安全的Hashmap
    private static Map<String, DeferredResult> deferredResultMap = new ConcurrentHashMap<>();

    public static DeferredResult get(String actionId){
        return deferredResultMap.get(actionId);
    }

    public static void put(String actionId, DeferredResult result){
        deferredResultMap.put(actionId, result);
    }

    public static void del(String requestId){
        deferredResultMap.remove(requestId);
    }
}
