package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.user.dto.LoginForm;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */

@RestController
@RequestMapping("/umsUser")
@Api(tags = "用户", description = "用户")
public class UmsUserController {

    @Autowired
    private UmsUserService umsUserService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * todo 删除用户所拥有的角色
     */
    @DeleteMapping("/role/{roleId}")
    public CommonResult deleteUserRole(@PathVariable String roleId){
        return CommonResult.success(null);
    }

    /**
     * todo 为用户添加角色（批量）
     */
    @PutMapping("/role")
    public CommonResult updateUserRole(@RequestBody List<String> roleId){
        return CommonResult.success(null);
    }

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public CommonResult login(@RequestBody LoginForm form){
        String token = umsUserService.login(form.getUsername(), form.getPassword());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap, "登录成功");
    }

    /**
     * 用户刷新token
     * @return
     */
    @GetMapping("/refreshToken")
    @ApiOperation("用户刷新token")
    public CommonResult refreshToken(){
        return CommonResult.success(null);
    }

    @GetMapping
    @ApiOperation("列表分页查询")
    @PreAuthorize("hasAuthority('ums:user:list')")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsUserService.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsUserService.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult save(@RequestBody UmsUser user){
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        umsUserService.save(user);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, UmsUser user){
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        umsUserService.updateById(user);
        return CommonResult.success(true, "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        umsUserService.removeById(id);
        return CommonResult.success(true, "删除数据成功");
    }
}
