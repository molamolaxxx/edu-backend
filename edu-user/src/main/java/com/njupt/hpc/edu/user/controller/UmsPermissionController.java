package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
/**
 * <p>
 * 用户权限 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/umsPermission")
@Api(tags = "用户权限", description = "用户权限")
public class UmsPermissionController {

    @Autowired
    private UmsPermissionService umsPermissionService;

    @GetMapping("/user/{userId}")
    @ApiOperation("根据用户id列出权限")
    public CommonResult listByUserId(@PathVariable String userId){
        return CommonResult.success(umsPermissionService.listPermissionListByUserId(userId));
    }

    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsPermissionService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsPermissionService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody UmsPermission permission){
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        umsPermissionService.save(permission);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, UmsPermission permission){
        permission.setId(id);
        permission.setUpdateTime(LocalDateTime.now());
        umsPermissionService.updateById(permission);
        return CommonResult.success(true, "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        umsPermissionService.removeById(id);
        return CommonResult.success(true, "删除数据成功");
    }
}
