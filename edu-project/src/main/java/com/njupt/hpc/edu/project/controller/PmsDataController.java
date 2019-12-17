package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.user.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
/**
 * <p>
 * 算法数据表 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/pmsData")
@Api(tags = "算法数据表", description = "算法数据表")
public class PmsDataController {

    @Autowired
    private PmsDataService pmsDataService;

    @Autowired
    private UmsUserService userService;

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
        data.setId("data_"+ RandomStringUtils.randomAlphanumeric(8));
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
        boolean result = pmsDataService.updateById(data);
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        boolean result = pmsDataService.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }

    /**
     * 上传数据
     */
    @PostMapping("/upload")
    public CommonResult upload(){
        return CommonResult.success(null);
    }
}
