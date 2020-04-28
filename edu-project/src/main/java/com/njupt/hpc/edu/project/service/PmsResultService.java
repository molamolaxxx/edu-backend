package com.njupt.hpc.edu.project.service;

import com.njupt.hpc.edu.project.model.PmsResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsResultService extends IService<PmsResult> {

    /**
     * 创建结果
     * @param dto
     * @return
     */
    Boolean create(ResultDTO dto);

    /**
     * 根据外键instanceId删除result
     * @param instanceId
     * @return
     */
    Boolean deleteByInstanceId(String instanceId);
}
