package com.njupt.hpc.edu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.project.dao.PmsInstanceMapper;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 算法实例表 服务实现类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Service
public class PmsInstanceServiceImpl extends ServiceImpl<PmsInstanceMapper, PmsInstance> implements PmsInstanceService {

    @Override
    public void updateInstanceState(String InstanceId, String actionTypeId) {
        switch (actionTypeId){
            // 启动，将实例设置成运行状态
            case InstanceActionType._START: {
                updateInstanceStateDetail(InstanceId, InstanceStateEnum.RUNNING.getCode());
                break;
            }
            // 停止与完成，将实例设置成完成状态
            case InstanceActionType._FINISH: {
                updateInstanceStateDetail(InstanceId, InstanceStateEnum.FINISH.getCode());
                break;
            }
            // 错误，将实例设置成错误状态
            case InstanceActionType._ERROR: {
                updateInstanceStateDetail(InstanceId, InstanceStateEnum.FAILED.getCode());
                break;
            }
        }
    }

    /**
     * 具体执行更新实例运行状态
     * @param InstanceId 实例id
     * @param state 更新后的实例状态
     */
    private void updateInstanceStateDetail(String InstanceId, String state){
        PmsInstance instance = this.getById(InstanceId);
        instance.setState(state);
        // 如果是开始操作，填充起始时间
        if (state.equals(InstanceStateEnum.RUNNING.getCode())){
            instance.setStartTime(LocalDateTime.now());
        }
        // 如果是结束、错误（算法模块）或是停止（客户端）操作，填充结束时间
        if (state.equals(InstanceStateEnum.FINISH.getCode()) || state.equals(InstanceStateEnum.FAILED.getCode())){
            instance.setFinishTime(LocalDateTime.now());
        }
        this.updateById(instance);
    }
}
