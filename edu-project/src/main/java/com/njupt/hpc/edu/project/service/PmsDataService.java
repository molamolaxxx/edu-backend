package com.njupt.hpc.edu.project.service;

import com.njupt.hpc.edu.project.model.PmsData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 算法数据表 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsDataService extends IService<PmsData> {

    /**
     * 根据实例id查找数据信息
     * @param instanceId
     * @return
     */
    PmsData findDataByInstanceId(String instanceId);
}
