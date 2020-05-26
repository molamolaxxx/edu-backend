package com.njupt.hpc.edu.project.controller.show;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.FusionDataParser;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.enumerate.ShowEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.dto.ShowDataDTO;
import com.njupt.hpc.edu.project.service.PmsDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 展示模块数据相关api
 * @date : 2020-03-19 20:14
 **/
@RestController
@RequestMapping("/show/data")
@Api(tags = "算法展示data接口", description = "算法展示data接口")
public class ShowDataController {

    @Autowired
    private PmsDataService dataService;

    @Autowired
    GenerateDataParser generateDataParser;
    @Autowired
    FusionDataParser fusionDataParser;


    @GetMapping("/table")
    @ApiOperation("获取csv文件的表格数据")
    public CommonResult<CSVContentVO> table(@RequestParam("path") String path, @RequestParam("instanceType") String type) {
        if(type==InstanceTypeEnum.GENERATE_EVALUATE.getCode()) {
            return CommonResult.success(generateDataParser.parseCSV(path, 1, 100));
        }
        else return CommonResult.success(fusionDataParser.parseDataCSV(path, 1, 100));
    }

    @GetMapping("/graph")
    @ApiOperation("获取csv文件的图谱数据")
    public CommonResult<GraphContentVO> graph(@RequestParam("path") String path, @RequestParam("instanceType") String type) {
        if(type==InstanceTypeEnum.GENERATE_EVALUATE.getCode()) {
            return CommonResult.success(generateDataParser.parseGraph(path, 1, 100));
        }
        else return CommonResult.success(fusionDataParser.parseGraph(path, 1, 100));
    }

    /**
     * 上传展示数据（每隔15分钟检查一次，删除无用数据）
     * @param file
     * @return
     */
    @PostMapping
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
        dataDTO.setUid(ShowEnum.VISITOR_NAME.getName());
        dataDTO.setDataPath(path);
        dataDTO.setInstanceType(instanceType);
        dataDTO.setName(ShowEnum.TEMP_DATA_NAME.getName());
        dataDTO.setDescription("这是展示用数据");
        Boolean result = dataService.create(dataDTO);
        if (result) {
            return CommonResult.success(dataDTO);
        }
        return CommonResult.failed("创建临时数据失败");
    }

    /**
     * 删除临时数据
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除临时数据")
    public CommonResult delete(@PathVariable String id) {
        PmsData result = dataService.getById(id);
        // 删除操作的幂等
        if (null == result) {
            return CommonResult.success(true);
        }
        // 删除数据
        dataService.remove(id, ShowEnum.VISITOR_NAME.getName());
        return CommonResult.success(true);
    }
}
