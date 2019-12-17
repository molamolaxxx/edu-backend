package com.njupt.hpc.edu.project.controller;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import com.njupt.hpc.edu.user.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 前端发出操作请求控制器
 * @date : 2019-12-07 01:14
 **/
@RestController
@RequestMapping("/action")
@Api(tags = "实例操作接口", description = "实例操作接口")
public class PmsActionController {

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private UmsUserService userService;

    @Autowired
    private PmsActionService actionService;

    @PostMapping("/{instanceId}")
    @ApiOperation("运行实例")
    public DeferredResult<CommonResult> start(HttpServletRequest request, @PathVariable String instanceId){
        // 根据实例id和用户id查找实例
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        PmsInstance instance = instanceService.getOne(WrapperUtil.queryByUserIdAndPK(instanceId, user.getId()));
        if (null == instance){
            throw new EduProjectException("根据id与用户id找不到对应的实例");
        }

        DeferredResult result = actionService.start(instance);

        return result;
    }
}
