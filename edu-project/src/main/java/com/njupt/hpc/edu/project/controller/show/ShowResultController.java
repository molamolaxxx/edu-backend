package com.njupt.hpc.edu.project.controller.show;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.FusionDataParser;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

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
    private GenerateDataParser generateDataParser;

    @Autowired
    private FusionDataParser fusionDataParser;

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
    //还需要额外传结果明细的类型（冗余或信息缺失）
    public CommonResult<CSVContentVO> detail(@PathVariable String instanceId,
                                             @RequestParam("resultType") String resultType,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        PmsInstance instance=checkTempInstance(instanceId);
        if (null == instance) {
            return CommonResult.failed("没有对应的实例存在");
        }
        // 根据instanceId获取result
        ResultDTO result = resultService.findByInstanceId(instanceId);
        //根据实例的type来选择不同的数据解析
        if(instance.getType().equals(InstanceTypeEnum.GENERATE_EVALUATE.getCode())){
            return CommonResult.success(generateDataParser.parseResultDetail(result.getPath(), offset, limit));
        }
        else {
            //根据结果明细类型，选择合适的解析函数
            JSONObject jsonObject = JSON.parseObject(result.getPath());
            String redundance_path=(String)jsonObject.get("redundance");
            String infoLack_path=(String)jsonObject.get("infoLack");
            if("redundance".equals(resultType)){
                return CommonResult.success(fusionDataParser.parseRedundanceResultDetail(redundance_path, offset, limit));
            }
            else return CommonResult.success(fusionDataParser.parseInfoLackResultDetail(infoLack_path, offset, limit));
        }
    }

    @GetMapping("/detail/download/{id}")
    @ApiOperation("下载结果明细文件")
    public void downloadDetail(@PathVariable String id, HttpServletResponse response) throws IOException {
        resultService.downloadDetail(id, response);
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
