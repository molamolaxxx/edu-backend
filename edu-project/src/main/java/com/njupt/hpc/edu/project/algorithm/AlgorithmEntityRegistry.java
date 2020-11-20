package com.njupt.hpc.edu.project.algorithm;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-11-11 16:05
 **/
@Component
@Slf4j
public class AlgorithmEntityRegistry {

    /**
     * 最大超时时间：1分钟
     */
    private long maxTimeout = 60 * 1000L;

    /**
     * 算法存储map
     * id -> entity
     */
    private final Map<String, AlgorithmEntity> algorithmMap = new ConcurrentHashMap<>(16);

    /**
     * 实例code -> entity
     */
    private final Map<String, Set<AlgorithmEntity>> instanceCodeMap = new ConcurrentHashMap<>(16);

    /**
     * heartBeat检查线程池
     */
    private final ScheduledExecutorService clientHeartBeatExecutor = initAlgorithmStatusCheckService();

    /**
     * 实例code集合
     */
    private Set<String> instanceCodeSet = new HashSet<>(
            Arrays.asList(InstanceTypeEnum.values())
                    .stream().map(e -> e.getCode()).collect(Collectors.toList()));;

    @PostConstruct
    public void init() {
        clientHeartBeatExecutor.scheduleAtFixedRate(
                () -> {
                    // 检查超时服务并下架
                    for (Map.Entry<String, AlgorithmEntity> entry : algorithmMap.entrySet()) {
                        AlgorithmEntity service = entry.getValue();
                        if (System.currentTimeMillis() - service.getLastHeartBeat() > maxTimeout) {
                            log.warn("服务{}，下线，服务id = {}", service.getName(), service.getId());
                            algorithmMap.remove(service.getId());
                            instanceCodeMap.get(service.getId()).remove(service);
                        }
                    }
                }, 30,30, TimeUnit.SECONDS);
        for (String instanceCode : instanceCodeSet) {
            instanceCodeMap.put(instanceCode, new HashSet<>());
        }
    }

    private ScheduledExecutorService initAlgorithmStatusCheckService() {
        return Executors.newScheduledThreadPool(1);
    }

    /**
     * 注册算法
     * @param service
     * @return
     */
    public synchronized AlgorithmEntity register(AlgorithmEntity service) {
        if (StringUtils.isEmpty(service.getId())) {
            service.setId(UUID.randomUUID().toString());
        }
        if (algorithmMap.containsKey(service.getId())) {
            throw new EduProjectException("算法服务注册id重复");
        }

        for (String code : service.getInstanceCodeList()) {
            if (instanceCodeSet.contains(code)) {
                Set<AlgorithmEntity> group = instanceCodeMap.get(code);
                group.add(service);
            }
        }
        service.setStartTime(System.currentTimeMillis());
        service.setLastHeartBeat(System.currentTimeMillis());

        // 存入map
        algorithmMap.putIfAbsent(service.getId(), service);
        log.warn("服务{}，上线，服务id = {}", service.getName(), service.getId());
        return service;
    }

    /**
     * 下架算法
     * @param id
     * @return
     */
    public synchronized boolean offline(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new EduProjectException("算法id不能为空");
        }
        if (algorithmMap.keySet().contains(id)) {
            // 1、移除实例map中的算法
            AlgorithmEntity algorithmEntity = algorithmMap.get(id);
            for (String code : algorithmEntity.getInstanceCodeList()) {
                instanceCodeMap.get(code).remove(algorithmEntity);
            }
            // 2、移除注册表中的算法
            algorithmMap.remove(id);
            return true;
        }
        return false;
    }

    /**
     * 心跳
     * @param id
     */
    public void heartBeat(String id) {
        if (!algorithmMap.containsKey(id)) {
            throw new EduProjectException("算法服务id不存在");
        }
        algorithmMap.get(id).setLastHeartBeat(System.currentTimeMillis());
    }

    public Map<String, AlgorithmEntity> getAlgorithmMap() {
        return algorithmMap;
    }

    public Map<String, Set<AlgorithmEntity>> getInstanceCodeMap() {
        return instanceCodeMap;
    }
}
