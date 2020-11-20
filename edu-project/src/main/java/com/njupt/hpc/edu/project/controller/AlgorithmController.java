package com.njupt.hpc.edu.project.controller;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.algorithm.AlgorithmEntity;
import com.njupt.hpc.edu.project.service.AlgorithmService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-11-11 16:49
 **/
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Resource
    private AlgorithmService algorithmService;

    /**
     * 算法心跳
     * @return
     */
    @PostMapping("/heartBeat/{id}")
    public String heartBeat(@PathVariable String id) {
        return algorithmService.heartBeat(id);
    }

    /**
     * 算法注册
     * @param service
     * @return
     */
    @PostMapping("/register")
    public CommonResult<AlgorithmEntity> register(@RequestBody AlgorithmEntity service) {
        return CommonResult.success(algorithmService.register(service));
    }

    /**
     * @param id
     * @return 是否下线成功
     */
    @PostMapping("/offline/{id}")
    private CommonResult<Boolean> offline(@PathVariable String id) {
        return CommonResult.success(algorithmService.offline(id));
    }

    /**
     * 算法结果数据上传
     * @param file
     * @return path
     */
    @PostMapping("/data/upload")
    public CommonResult<String> resultUpload(MultipartFile file) {
        String resultPath = algorithmService.resultUpload(file);
        if (StringUtils.isEmpty(resultPath)) {
            return CommonResult.failed("文件路径不存在");
        }
        return CommonResult.success(resultPath);
    }

    /**
     * 算法输入下载
     * @param dataId
     * @return
     */
    @GetMapping("/data/download/{dataId}")
    public void inputDataDownload(@PathVariable String dataId, HttpServletResponse response) {
        algorithmService.inputDataDownload(dataId, response);
    }

}
