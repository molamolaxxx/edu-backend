package com.njupt.hpc.edu.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.InstanceDTO;
import com.njupt.hpc.edu.project.model.vo.InstanceItemVO;

import java.util.List;

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

    /**
     * 普通用户查看所有正在运行实例的id
     * @return
     */
    List<String> catAllRunningInstanceIdForUser(String userId);

    /**
     * 管理员查看所有正在运行实例的id
     * @return
     */
    List<String> catAllRunningInstanceIdForAdmin();

    /**
     * 删除实例
     * @param instanceId
     * @param userId
     * @return
     */
    Boolean delete(String instanceId, String userId);

    /**
     * 更新实例
     * @param instanceId
     * @return
     */
    Boolean update(InstanceDTO dto, String instanceId);

    /**
     * 创建实例
     * @param dto
     */
    InstanceItemVO create(InstanceDTO dto);

    /**
     * 销毁临时实例、数据、结果
     */
    Boolean deleteTempInstance(PmsInstance instance);

    /**
     * 获取所有的临时实例
     * @return
     */
    List<PmsInstance> getAllTempInstance();


}
