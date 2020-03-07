package com.njupt.hpc.edu.project.controller;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.ShowDataDTO;
import com.njupt.hpc.edu.project.model.dto.ShowInstanceDTO;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法展示页面相应接口
 * 此接口访问不需要权限
 * @date : 2020-03-07 17:20
 * todo 结合算法端进行联合测试
 **/
@RestController
@RequestMapping("/pmsShow")
@Api(tags = "算法展示接口", description = "算法展示接口")
public class ShowAlgorithmController {

    // 访客名
    private static final String VISITOR_NAME = "visitor";

    // 临时数据名
    private static final String TEMP_DATA_NAME = "temp_data";

    // 临时实例
    private static final String TEMP_INSTANCE_NAME = "temp_instance";

    @Autowired
    private PmsDataService dataService;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private PmsActionService actionService;

    /**
     * 上传展示数据（每隔15分钟检查一次，删除无用数据）
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("上传并创建临时数据")
    public CommonResult upload(@RequestParam("file") MultipartFile file,
                               @RequestParam("type") String instanceType) {
        // 只允许上传小于1k的数据
        try {
            if (file.getBytes().length > 1024) {
                throw new EduProjectException("展示文件上传不得大于1mb");
            }
        } catch (IOException e) {
            throw new EduProjectException("上传出现错误");
        }
        String path = dataService.upload(file);
        // 创建临时数据
        ShowDataDTO dataDTO = new ShowDataDTO();
        dataDTO.setId(IdUtil.generateId("temp"));
        dataDTO.setUid(VISITOR_NAME);
        dataDTO.setDataPath(path);
        dataDTO.setInstanceType(instanceType);
        dataDTO.setName(TEMP_DATA_NAME);
        dataDTO.setDescription("这是展示用数据");
        Boolean result = dataService.create(dataDTO);
        if (result) {
            return CommonResult.success(dataDTO);
        }
        return CommonResult.failed("创建临时数据失败");
    }

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
        instanceDTO.setUid(VISITOR_NAME);
        instanceDTO.setDescription("这是展示用实例");
        instanceDTO.setName(TEMP_INSTANCE_NAME);
        instanceDTO.setDataId(dataId);
        instanceDTO.setType(data.getInstanceType());
        PmsInstance instance = instanceService.create(instanceDTO);
        // 开始运行
        return CommonResult.success(instanceDTO);
    }

    @PostMapping("/Start")
    @ApiOperation("开始临时实例")
    public DeferredResult<CommonResult> start(String instanceId) {
        PmsInstance instance = checkTempInstance(instanceId);
        return actionService.action(instance, InstanceActionType.START);
    }

    @PostMapping("/info")
    @ApiOperation("开始临时实例")
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
