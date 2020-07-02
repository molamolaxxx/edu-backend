package com.njupt.hpc.edu.project.controller.show;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.ShowEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.ShowInstanceDTO;
import com.njupt.hpc.edu.project.model.vo.InstanceItemVO;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

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
    private PmsResultService resultService;

    @Autowired
    private PmsActionService actionService;

    /**
     * 创建临时匿名实例（每隔30分钟检查一次，删除已完成无用实例）
     */
    @PostMapping("/{userId}/{dataId}")
    @ApiOperation("创建临时实例")
    public CommonResult create(@PathVariable("userId") String userId,
                               @PathVariable("dataId") String dataId,
                               @RequestBody Map<String, String> config) {
        // 查询数据
        PmsData data = dataService.getById(dataId);
        if (null == data) {
            throw new EduProjectException("数据不存在");
        }
        // 创建一个临时实例
        ShowInstanceDTO instanceDTO = new ShowInstanceDTO();
        instanceDTO.setId(IdUtil.generateId("temp"));
        instanceDTO.setUid(userId);
        instanceDTO.setDescription("这是展示用实例");
        instanceDTO.setConfig(config.get("config"));
        instanceDTO.setName(ShowEnum.TEMP_INSTANCE_NAME.getName());
        instanceDTO.setDataId(dataId);
        instanceDTO.setType(data.getInstanceType());
        instanceService.create(instanceDTO);
        return CommonResult.success(instanceDTO);
    }

    @PostMapping("/start/{instanceId}")
    @ApiOperation("开始临时实例")
    public DeferredResult<CommonResult> start(@PathVariable String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.START);
    }

    @PostMapping("/stop/{instanceId}")
    @ApiOperation("停止临时实例")
    public DeferredResult<CommonResult> stop(@PathVariable String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.STOP);
    }

    @GetMapping("/info/{instanceId}")
    @ApiOperation("获取临时实例的信息")
    public DeferredResult<CommonResult> info(@PathVariable String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.INFO);
    }

    @DeleteMapping("/{instanceId}")
    @ApiOperation("销毁临时实例")
    public CommonResult destroy(@PathVariable String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        // 删除操作的幂等
        if (null == instance) {
            return CommonResult.success(true);
        }
        instanceService.deleteTempInstance(instance);
        return CommonResult.success(true);
    }

    // 检查匿名实例的合法性
    private PmsInstance checkTempInstance(String instanceId) {
        PmsInstance instance = instanceService.getById(instanceId);
        if (null == instance) {
            return null;
        }
        if (!instanceId.endsWith("temp")) {
            throw new EduProjectException("实例为非匿名实例，调用失败");
        }
        return instance;
    }
}
