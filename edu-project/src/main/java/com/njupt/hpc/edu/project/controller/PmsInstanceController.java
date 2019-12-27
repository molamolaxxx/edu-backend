package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
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
@Api(tags = "算法实例接口", description = "算法实例接口")
public class PmsInstanceController {

    @Autowired
    private PmsInstanceService pmsInstanceService;

    @Autowired
    private UmsUserService userService;

    /**
     * 根据用户id查找相应的实例（分页）
     */
    @GetMapping("/user")
    @ApiOperation("列表分页查询（用户限定）")
    public CommonPage listByUserId(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        IPage page = pmsInstanceService.page(new Page(pageNum, pageSize),
                WrapperUtil.querySingleWrapperBuilder("uid",user.getId()));
        return CommonPage.restPage(page);
    }

    /**
     * 根据实例id查找相应的实例
     */
    @GetMapping("/user/{instanceId}")
    @ApiOperation("根据实例id查找（用户限定）")
    public CommonResult findByIdAndUserId(HttpServletRequest request, @PathVariable String instanceId){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsInstanceService.getOne(WrapperUtil.queryByUserIdAndPK(instanceId, user.getId())));
    }

    @PostMapping("/user")
    @ApiOperation("保存实例（用户限定）")
    public CommonResult save(HttpServletRequest request, @RequestBody PmsInstance instance){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        instance.setId("instance_"+ RandomStringUtils.randomAlphanumeric(8));
        instance.setUid(user.getId());
        instance.setCreateTime(LocalDateTime.now());
        instance.setUpdateTime(LocalDateTime.now());
        instance.setState(InstanceStateEnum.READY.getCode());
        pmsInstanceService.save(instance);
        return CommonResult.success(true, "保存实例成功");
    }

    @PutMapping("/user/{instanceId}")
    @ApiOperation("更新实例（用户限定）")
    public CommonResult update(HttpServletRequest request, @PathVariable String instanceId,@RequestBody PmsInstance instance){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        instance.setId(instanceId);
        instance.setUpdateTime(LocalDateTime.now());
        boolean result = pmsInstanceService.update(instance, WrapperUtil.queryByUserIdAndPK(instanceId, user.getId()));
        return CommonResult.parseResultToResponse(result, "更新实例失败", "更新实例成功");
    }

    @DeleteMapping("/user/{instanceId}")
    @ApiOperation("删除实例（用户限定）")
    public CommonResult delete(HttpServletRequest request, @PathVariable String instanceId){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        boolean result = pmsInstanceService.remove(WrapperUtil.queryByUserIdAndPK(instanceId, user.getId()));
        return CommonResult.parseResultToResponse(result, "删除实例失败", "删除实例成功");
    }

    // for admin

    /**
     * 查看所有实例
     */

    /**
     * 删除实例
     */
}
