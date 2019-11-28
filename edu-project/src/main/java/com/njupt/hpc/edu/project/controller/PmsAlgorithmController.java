package com.njupt.hpc.edu.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.model.PmsAlgorithm;
import com.njupt.hpc.edu.project.service.PmsAlgorithmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 算法表 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/pmsAlgorithm")
@Api(tags = "算法表", description = "算法表")
public class PmsAlgorithmController {


    @Autowired
    private PmsAlgorithmService pmsAlgorithmService;

    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = pmsAlgorithmService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(pmsAlgorithmService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody PmsAlgorithm algorithm){
        algorithm.setCreateTime(LocalDateTime.now());
        algorithm.setUpdateTime(LocalDateTime.now());
        pmsAlgorithmService.save(algorithm);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, PmsAlgorithm algorithm){
        algorithm.setId(id);
        algorithm.setUpdateTime(LocalDateTime.now());
        pmsAlgorithmService.updateById(algorithm);
        return CommonResult.success(true, "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        pmsAlgorithmService.removeById(id);
        return CommonResult.success(true, "删除数据成功");
    }
}
