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
public class AlgorithmServiceRegistry {

    /**
     * 最大超时时间：3分钟
     */
    private long maxTimeout = 3 * 60 * 1000L;

    /**
     * 算法存储map
     * id -> entity
     */
    private final Map<String, AlgorithmService> algorithmMap = new ConcurrentHashMap<>(16);

    /**
     * 实例code -> entity
     */
    private final Map<String, List<AlgorithmService>> instanceCodeMap = new ConcurrentHashMap<>(16);

    /**
     * heartBeat检查线程池
     */
    private final ScheduledExecutorService clientHeartBeatExecutor = initAlgorithmStatusCheckService();

    @PostConstruct
    public void init() {
        clientHeartBeatExecutor.schedule(
                () -> {
                    // 检查超时服务并下架
                    for (Map.Entry<String, AlgorithmService> entry : algorithmMap.entrySet()) {
                        AlgorithmService service = entry.getValue();
                        if (System.currentTimeMillis() - service.getLastHeartBeat() > maxTimeout) {
                            log.warn("服务{}，下架，服务id = {}", service.getName(), service.getId());
                            algorithmMap.remove(service.getId());
                        }
                    }
                }, 30, TimeUnit.SECONDS);
    }

    private ScheduledExecutorService initAlgorithmStatusCheckService() {
        return Executors.newScheduledThreadPool(1);
    }

    /**
     * 注册算法
     * @param service
     * @return
     */
    public AlgorithmService register(AlgorithmService service) {
        if (StringUtils.isEmpty(service.getId())) {
            service.setId(UUID.randomUUID().toString());
        }
        if (algorithmMap.containsKey(service.getId())) {
            throw new EduProjectException("算法服务注册id重复");
        }
        HashSet<String> instanceCodeSet = new HashSet<>(
                Arrays.asList(InstanceTypeEnum.values())
                        .stream().map(e -> e.getCode()).collect(Collectors.toList()));
        for (String code : service.getInstanceCodeList()) {
            if (instanceCodeSet.contains(code)) {
                List<AlgorithmService> group = instanceCodeMap.getOrDefault(code, new ArrayList<>());
                group.add(service);
            }
        }
        service.setStartTime(System.currentTimeMillis());
        service.setLastHeartBeat(System.currentTimeMillis());

        // 存入map
        return algorithmMap.putIfAbsent(service.getId(), service);
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


}
