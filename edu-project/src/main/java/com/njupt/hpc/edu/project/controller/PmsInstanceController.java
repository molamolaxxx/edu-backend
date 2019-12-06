package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import com.njupt.hpc.edu.user.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 实例表 前端控制器 用户
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/pmsInstance")
@Api(tags = "算法实例表", description = "算法实例表")
public class PmsInstanceController {

    @Autowired
    private PmsInstanceService pmsInstanceService;

    @Autowired
    private UmsUserService userService;

    /**
     * 根据用户id查找相应的实例（分页）
     */
    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        IPage page = pmsInstanceService.page(new Page(pageNum, pageSize),
                WrapperUtil.querySingleWrapperBuilder("uid",user.getId()));
        return CommonPage.restPage(page);
    }

    /**
     * 根据实例id查找相应的实例
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(HttpServletRequest request, @PathVariable String id){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsInstanceService.getOne(WrapperUtil.queryByUserIdAndPK(id, user.getId())));
    }

    @PostMapping
    @ApiOperation("保存实例")
    public CommonResult save(HttpServletRequest request, @RequestBody PmsInstance instance){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        instance.setId("instance_"+ RandomStringUtils.randomAlphanumeric(8));
        instance.setUid(user.getId());
        instance.setCreateTime(LocalDateTime.now());
        instance.setUpdateTime(LocalDateTime.now());
        pmsInstanceService.save(instance);
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新实例")
    public CommonResult update(HttpServletRequest request, @PathVariable String id, PmsInstance instance){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        instance.setId(id);
        instance.setUpdateTime(LocalDateTime.now());
        boolean result = pmsInstanceService.update(instance, WrapperUtil.queryByUserIdAndPK(id, user.getId()));
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除实例")
    public CommonResult delete(HttpServletRequest request, @PathVariable String id){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        boolean result = pmsInstanceService.remove(WrapperUtil.queryByUserIdAndPK(id, user.getId()));
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }

    /**
     * for admin
     */

    /**
     * 查看所有实例
     */

    /**
     * 删除实例
     */
}
