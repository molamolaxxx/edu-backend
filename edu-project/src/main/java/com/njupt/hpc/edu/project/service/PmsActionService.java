package com.njupt.hpc.edu.project.service;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.model.PmsInstance;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * <p>
 * 算法操作接口
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsActionService {

    /**
     * 运行一个实例
     */
    DeferredResult<CommonResult> action(PmsInstance instance, InstanceActionType actionType);
}
