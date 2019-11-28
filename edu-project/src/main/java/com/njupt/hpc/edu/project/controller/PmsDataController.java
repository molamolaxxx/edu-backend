package com.njupt.hpc.edu.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.service.PmsDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 算法数据表 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/pmsData")
@Api(tags = "算法数据表", description = "算法数据表")
public class PmsDataController {


    @Autowired
    private PmsDataService pmsDataService;

    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = pmsDataService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(pmsDataService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody PmsData data){
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        pmsDataService.save(data);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, PmsData data){
        data.setId(id);
        data.setUpdateTime(LocalDateTime.now());
        pmsDataService.updateById(data);
        return CommonResult.success(true, "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        pmsDataService.removeById(id);
        return CommonResult.success(true, "删除数据成功");
    }
}
