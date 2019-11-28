package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.user.model.UmsRole;
import com.njupt.hpc.edu.user.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户角色 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/umsRole")
@Api(tags = "用户角色", description = "用户角色")
public class UmsRoleController {


    @Autowired
    private UmsRoleService umsRoleService;


    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsRoleService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsRoleService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody UmsRole role){
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        umsRoleService.save(role);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, UmsRole role){
        role.setId(id);
        role.setUpdateTime(LocalDateTime.now());
        umsRoleService.updateById(role);
        return CommonResult.success(true, "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        umsRoleService.removeById(id);
        return CommonResult.success(true, "删除数据成功");
    }
}
