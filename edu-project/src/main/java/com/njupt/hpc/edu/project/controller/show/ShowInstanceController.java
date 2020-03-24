package com.njupt.hpc.edu.project.controller.show;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.enumerate.ShowEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.ShowInstanceDTO;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 展示页面实例相关api
 * @date : 2020-03-19 20:13
 **/
@RestController
@RequestMapping("/show/instance")
@Api(tags = "算法展示instance接口", description = "算法展示instance接口")
public class ShowInstanceController {

    @Autowired
    private PmsDataService dataService;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private PmsActionService actionService;

    /**
     * 创建临时匿名实例（每隔30分钟检查一次，删除已完成无用实例）
     */
    @PostMapping("/create")
    @ApiOperation("创建临时实例")
    public CommonResult create(String dataId) {
        // 查询数据
        PmsData data = dataService.getById(dataId);
        if (null == data) {
            throw new EduProjectException("数据不存在");
        }
        // 创建一个临时实例
        ShowInstanceDTO instanceDTO = new ShowInstanceDTO();
        instanceDTO.setId(IdUtil.generateId("temp"));
        instanceDTO.setUid(ShowEnum.VISITOR_NAME.getName());
        instanceDTO.setDescription("这是展示用实例");
        instanceDTO.setName(ShowEnum.TEMP_INSTANCE_NAME.getName());
        instanceDTO.setDataId(dataId);
        instanceDTO.setType(data.getInstanceType());
        PmsInstance instance = instanceService.create(instanceDTO);
        return CommonResult.success(instanceDTO);
    }

    @PostMapping("/start")
    @ApiOperation("开始临时实例")
    public DeferredResult<CommonResult> start(String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.START);
    }

    @PostMapping("/info")
    @ApiOperation("获取临时实例的信息")
    public DeferredResult<CommonResult> info(String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.INFO);
    }

    @PostMapping("/destroy")
    @ApiOperation("销毁临时实例")
    public CommonResult destroy(String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        // 如果实例是运行状态，发消息改变算法端状态
        if (instance.getState().equals(InstanceStateEnum.RUNNING)){
            // 死信队列确保消息能够送达
            actionService.action(instance, InstanceActionType.STOP);
        }
        // 直接删除实例
        instanceService.removeById(instanceId);
        // 删除实例对应数据
        dataService.removeById(instance.getDataId());
        return CommonResult.success(true);
    }

    // 检查匿名实例的合法性
    private PmsInstance checkTempInstance(String instanceId) {
        PmsInstance instance = instanceService.getById(instanceId);
        if (null == instance) {
            throw new EduProjectException("匿名实例不存在");
        }
        if (!instanceId.startsWith("temp")) {
            throw new EduProjectException("实例为非匿名实例，调用失败");
        }
        return instance;
    }
}
