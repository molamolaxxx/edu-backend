package com.njupt.hpc.edu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.user.dto.LoginForm;
import com.njupt.hpc.edu.user.dto.UserInfo;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import com.njupt.hpc.edu.user.service.UmsRoleService;
import com.njupt.hpc.edu.user.service.UmsUserService;
import com.njupt.hpc.edu.user.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @Autowired
    private UmsRoleService umsRoleService;

    @Autowired
    private UmsPermissionService permissionService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 列出当前用户信息（包括权限与角色)
     */
    @GetMapping("/userInfo")
    @ApiOperation("列出当前用户信息（包括权限与角色)")
    public CommonResult userInfo(HttpServletRequest request){
        // 用户信息
        UmsUser user = UserUtils.getUserFromRequest(request, umsUserService);
        UserInfo info = new UserInfo();
        info.setUmsUser(user);
        info.setRoles(umsRoleService.listRolesByUserId(user.getId()));
        info.setPermissions(permissionService.listPermissionsByUserId(user.getId()));
        return CommonResult.success(info);
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
    public CommonResult refreshToken(HttpServletRequest request){
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",umsUserService.refreshToken((String) request.getAttribute("token")));
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping
    @ApiOperation("列表分页查询")
    @PreAuthorize("hasAuthority('ums:user:find')")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = umsUserService.page(new Page<>(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    @PreAuthorize("hasAuthority('ums:user:find')")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(umsUserService.getById(id));
    }


    /**
     * 创建用户
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation("保存数据")
    @PreAuthorize("hasAuthority('ums:user:save')")
    public CommonResult save(@RequestBody UmsUser user){
        user.setId("user_"+ RandomStringUtils.randomAlphanumeric(8));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        // 设置密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        umsUserService.save(user);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    @PreAuthorize("hasAuthority('ums:user:update')")
    public CommonResult update(@PathVariable String id, UmsUser user){
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        // 设置密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean result = umsUserService.updateById(user);
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    @PreAuthorize("hasAuthority('ums:user:delete')")
    public CommonResult delete(@PathVariable String id){
        boolean result = umsUserService.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
