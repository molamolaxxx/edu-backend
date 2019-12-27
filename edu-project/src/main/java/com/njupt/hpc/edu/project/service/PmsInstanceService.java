package com.njupt.hpc.edu.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.project.model.PmsInstance;

/**
 * <p>
 * 算法实例表 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsInstanceService extends IService<PmsInstance> {

    /**
     * 将实例设置为各种标示
     * 1.启动 到1 设置开始时间
     * 2.结束 到2 设置结束时间
     * 3.错误 到-1 设置结束时间
     */
    void updateInstanceState(String instanceId, String actionTypeId);
}
