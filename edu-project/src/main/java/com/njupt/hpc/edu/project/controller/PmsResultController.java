package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.project.model.PmsResult;
import com.njupt.hpc.edu.project.service.PmsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/pmsResult")
@Api(tags = "评价结果接口", description = "评价结果接口")
public class PmsResultController {


    @Autowired
    private PmsResultService pmsResultService;

    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = pmsResultService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(pmsResultService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody PmsResult result){
        result.setId("result_"+ RandomStringUtils.randomAlphanumeric(8));
        pmsResultService.save(result);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, PmsResult result){
        result.setId(id);
        return CommonResult.parseResultToResponse(pmsResultService.updateById(result),
                "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        boolean result = pmsResultService.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
