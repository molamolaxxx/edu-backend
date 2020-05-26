package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 为角色添加权限
     */
    @PostMapping("/relation/{roleId}/{permissionId}")
    @ApiOperation("添加角色与权限的对应关系")
    @PreAuthorize("hasAuthority('ums:permission:rela')")
    public CommonResult addRolePermissionRelation(@PathVariable String roleId, @PathVariable String permissionId){
        // 删除对应role -> permission的关系
        umsPermissionService.addRolePermissionRelation(roleId, permissionId);

        return CommonResult.success(null, "添加角色权限关系成功");
    }

    /**
     * 为角色添加权限(批量)
     */
    @PostMapping("/relation/{roleId}")
    @ApiOperation("添加角色与权限的对应关系（批量）")
    @PreAuthorize("hasAuthority('ums:permission:rela')")
    public CommonResult addRolePermissionRelationInBatch(@PathVariable String roleId, @RequestBody List<String> permissionIdList){
        umsPermissionService.addRolePermissionRelationInBatch(roleId, permissionIdList);
        return CommonResult.success(null, "添加角色权限关系成功（批量）");
    }

    /**
     * 为角色删除权限
     */
    @DeleteMapping("/relation/{roleId}/{permissionId}")
    @ApiOperation("删除角色与权限的对应关系")
    @PreAuthorize("hasAuthority('ums:permission:rela')")
    public CommonResult deleteRolePermissionRelation(@PathVariable String roleId, @PathVariable String permissionId){
        // 删除对应role -> permission的关系
        umsPermissionService.deleteRolePermissionRelation(roleId, permissionId);

        return CommonResult.success(null, "删除角色权限关系成功");
    }

    /**
     * 为角色删除权限（批量）
     */
    @DeleteMapping("/relation/{roleId}")
    @ApiOperation("删除角色与权限的对应关系（批量）")
    @PreAuthorize("hasAuthority('ums:permission:rela')")
    public CommonResult deleteRolePermissionRelationInBatch(@PathVariable String roleId, @RequestBody List<String> permissionIdList){
        umsPermissionService.deleteRolePermissionRelationInBatch(roleId, permissionIdList);
        return CommonResult.success(null, "删除角色权限关系成功（批量）");
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("根据用户id列出权限")
    @PreAuthorize("hasAuthority('ums:permission:find')")
    public CommonResult listByUserId(@PathVariable String userId){
        return CommonResult.success(umsPermissionService.listPermissionsByUserId(userId));
    }

    @GetMapping("/role/{roleId}")
    @ApiOperation("根据角色id列出权限")
    @PreAuthorize("hasAuthority('ums:permission:find')")
    public CommonResult listByRoleId(@PathVariable String roleId){
        return CommonResult.success(umsPermissionService.listPermissionsByRoleId(roleId));
    }

    @GetMapping
    @ApiOperation("列表分页查询")
    @PreAuthorize("hasAuthority('ums:permission:find')")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsPermissionService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    @PreAuthorize("hasAuthority('ums:permission:find')")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsPermissionService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    @PreAuthorize("hasAuthority('ums:permission:save')")
    public CommonResult save(@RequestBody UmsPermission permission){
        permission.setId(IdUtil.generateId("permission"));
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        umsPermissionService.save(permission);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    @PreAuthorize("hasAuthority('ums:permission:update')")
    public CommonResult update(@PathVariable String id, UmsPermission permission){
        permission.setId(id);
        permission.setUpdateTime(LocalDateTime.now());
        boolean result = umsPermissionService.updateById(permission);
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    @PreAuthorize("hasAuthority('ums:permission:delete')")
    public CommonResult delete(@PathVariable String id){
        boolean result = umsPermissionService.removeById(id);

        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
