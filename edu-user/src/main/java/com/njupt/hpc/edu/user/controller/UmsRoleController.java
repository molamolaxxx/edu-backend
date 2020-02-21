package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.user.model.UmsRole;
import com.njupt.hpc.edu.user.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 为用户添加角色
     */
    @PostMapping("/relation/{userId}/{roleId}")
    @ApiOperation("为用户添加角色")
    @PreAuthorize("hasAuthority('ums:role:rela')")
    public CommonResult addUserRoleRelation(@PathVariable String userId, @PathVariable String roleId){
        umsRoleService.addUserRoleRelation(userId, roleId);

        return CommonResult.success(null, "添加用户角色关系成功");
    }

    /**
     * 为用户添加角色（批量）
     */
    @PostMapping("/relation/{userId}")
    @ApiOperation("为用户添加角色（批量）")
    @PreAuthorize("hasAuthority('ums:role:rela')")
    public CommonResult addUserRoleRelationInBatch(@PathVariable String userId, @RequestBody List<String> roleIdList){
        umsRoleService.addUserRoleRelationInBatch(userId, roleIdList);
        return CommonResult.success(null, "批量添加用户角色关系成功");
    }

    /**
     * 删除用户所拥有的角色
     */
    @DeleteMapping("/relation/{userId}/{roleId}")
    @ApiOperation("删除用户与角色的对应关系")
    @PreAuthorize("hasAuthority('ums:role:rela')")
    public CommonResult deleteUserRoleRelation(@PathVariable String userId, @PathVariable String roleId){
        // 删除对应user -> role的关系
        umsRoleService.deleteUserRoleRelation(userId, roleId);

        return CommonResult.success(null, "删除用户角色关系成功");
    }

    /**
     * 删除用户所拥有的角色（批量）
     */
    @DeleteMapping("/relation/{userId}")
    @ApiOperation("删除用户与角色的对应关系（批量）")
    @PreAuthorize("hasAuthority('ums:role:rela')")
    public CommonResult deleteUserRoleRelationInBatch(@PathVariable String userId, @RequestBody List<String> roleIdList){

        // 删除对应user -> role的关系
        umsRoleService.deleteUserRoleRelationInBatch(userId, roleIdList);

        return CommonResult.success(null, "批量删除用户角色关系成功");
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("根据用户id列出角色")
    @PreAuthorize("hasAuthority('ums:role:find')")
    public CommonResult listByUserId(@PathVariable String userId){
        return CommonResult.success(umsRoleService.listRolesByUserId(userId));
    }

    @GetMapping
    @ApiOperation("列表分页查询")
    @PreAuthorize("hasAuthority('ums:role:find')")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsRoleService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    @PreAuthorize("hasAuthority('ums:role:find')")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsRoleService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    @PreAuthorize("hasAuthority('ums:role:save')")
    public CommonResult save(@RequestBody UmsRole role){
        role.setId(IdUtil.generateId("role"));
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        umsRoleService.save(role);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    @PreAuthorize("hasAuthority('ums:role:update')")
    public CommonResult update(@PathVariable String id, UmsRole role){
        role.setId(id);
        role.setUpdateTime(LocalDateTime.now());
        boolean result = umsRoleService.updateById(role);
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    @PreAuthorize("hasAuthority('ums:role:delete')")
    public CommonResult delete(@PathVariable String id){
        boolean result = umsRoleService.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
