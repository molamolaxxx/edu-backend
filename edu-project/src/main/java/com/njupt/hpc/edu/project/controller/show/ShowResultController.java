package com.njupt.hpc.edu.project.controller.show;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 展示模块结果相关api
 * @date : 2020-03-19 20:14
 **/
@RestController
@RequestMapping("/show/result")
@Api(tags = "算法展示result接口", description = "算法展示result接口")
public class ShowResultController {

    @Autowired
    private PmsResultService resultService;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private GenerateDataParser dataParser;

    @GetMapping("/{instanceId}")
    @ApiOperation("展示模块获取结果数据")
    public CommonResult<ResultDTO> result(@PathVariable String instanceId) {
        if (null == checkTempInstance(instanceId)) {
            return CommonResult.failed("没有对应的实例存在");
        }
        // 根据instanceId获取result
        ResultDTO result = resultService.findByInstanceId(instanceId);
        return CommonResult.success(result);
    }

    @GetMapping("/detail/{instanceId}")
    @ApiOperation("分页展示模块获取结果详细数据")
    public CommonResult<CSVContentVO> detail(@PathVariable String instanceId,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        if (null == checkTempInstance(instanceId)) {
            return CommonResult.failed("没有对应的实例存在");
        }
        // 根据instanceId获取result
        ResultDTO result = resultService.findByInstanceId(instanceId);

        return CommonResult.success(dataParser.parseResultDetail(result.getPath(), offset, limit));
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
