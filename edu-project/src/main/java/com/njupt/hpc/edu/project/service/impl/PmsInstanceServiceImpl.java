package com.njupt.hpc.edu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.dao.PmsInstanceMapper;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.InstanceDTO;
import com.njupt.hpc.edu.project.model.vo.InstanceItemVO;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private PmsActionService actionService;

    @Autowired
    private PmsDataService dataService;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private PmsResultService resultService;


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

    @Override
    public List<String> catAllRunningInstanceIdForUser(String userId) {
        Map<String, Object> queryMap=new HashMap<>();
        queryMap.put("state",InstanceStateEnum.RUNNING.getCode());
        queryMap.put("uid",userId);
        List<PmsInstance> instanceList = this.list(WrapperUtil.queryWrapperBuilder(queryMap));
        List<String> instanceIdList = instanceList.stream().map(e -> e.getId()).collect(Collectors.toList());
        return instanceIdList;
    }

    @Override
    public List<String> catAllRunningInstanceIdForAdmin() {
        List<PmsInstance> instanceList = this.list(WrapperUtil.querySingleWrapperBuilder("state",InstanceStateEnum.RUNNING.getCode()));
        List<String> instanceIdList = instanceList.stream().map(e -> e.getId()).collect(Collectors.toList());
        return instanceIdList;
    }

    @Override
    public Boolean delete(String instanceId, String userId) {
        // 检测实例是否能被删除
        checkInstanceDeleteable(instanceId);
        return this.remove(WrapperUtil.queryByUserIdAndPK(instanceId, userId));
    }

    @Override
    public Boolean update(InstanceDTO dto, String instanceId) {
        // 检测实例是否能被修改
        checkInstanceChangeable(instanceId);
        PmsInstance instance = (PmsInstance) BeanUtilsPlug
                .copyPropertiesReturnTarget(dto, new PmsInstance());
        instance.setId(instanceId);
        instance.setUpdateTime(LocalDateTime.now());
        return this.update(instance, WrapperUtil.queryByUserIdAndPK(instanceId, dto.getUid()));
    }

    @Override
    public InstanceItemVO create(InstanceDTO dto) {
        PmsInstance instance = (PmsInstance) BeanUtilsPlug
                .copyPropertiesReturnTarget(dto, new PmsInstance());
        if (instance.getId() == null) {
            instance.setId(IdUtil.generateId("instance"));
        }
        instance.setCreateTime(LocalDateTime.now());
        instance.setUpdateTime(LocalDateTime.now());
        instance.setState(InstanceStateEnum.READY.getCode());
        this.save(instance);
        InstanceItemVO instanceVO=(InstanceItemVO) BeanUtilsPlug.copyPropertiesReturnTarget(instance,new InstanceItemVO());
        return instanceVO;
    }

    /**
     * 检查实例是否能被删除
     */
    private void checkInstanceDeleteable(String instanceId){
        // 判断实例是否能被删除
        PmsInstance instanceToDel = this.getById(instanceId);
        if (null != instanceToDel &&
                instanceToDel.getState().equals(InstanceStateEnum.RUNNING.getCode())){
            throw new EduProjectException("实例正在运行，无法被删除");
        }
    }

    /**
     * 检测实例是否能被修改
     */
    private void checkInstanceChangeable(String instanceId){
        // 判断实例是否能被修改
        PmsInstance instanceToChange = this.getById(instanceId);
        if (null != instanceToChange &&
                !instanceToChange.getState().equals(InstanceStateEnum.READY.getCode())){
            throw new EduProjectException("实例处于无法被修改的状态");
        }
    }

    /**
     * 具体执行更新实例运行状态
     * @param InstanceId 实例id
     * @param state 更新后的实例状态
     */
    private void updateInstanceStateDetail(String InstanceId, String state){
        PmsInstance instance = this.getById(InstanceId);
        if (null == instance) {
            throw new EduProjectException("实例不存在，无法更新其状态");
        }
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

    @Transactional
    @Override
    public Boolean deleteTempInstance(PmsInstance instance) {
        // 如果实例是运行状态，发消息改变算法端状态
        if (instance.getState().equals(InstanceStateEnum.RUNNING.getCode())){
            // 死信队列确保消息能够送达
            actionService.action(instance, InstanceActionType.STOP);
        }
        // 直接删除实例
        instanceService.removeById(instance.getId());
        // 删除实例对应数据
        dataService.removeById(instance.getDataId());
        // 删除结果
        resultService.deleteByInstanceId(instance.getId());

        return true;
    }

    @Override
    public List<PmsInstance> getAllTempInstance() {
        return this.list().stream()
                .filter(instance -> instance.getId().endsWith("temp"))
                .collect(Collectors.toList());
    }
}
