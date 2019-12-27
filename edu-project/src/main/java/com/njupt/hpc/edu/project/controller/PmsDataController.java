package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import com.njupt.hpc.edu.user.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
/**
 * <p>
 * 算法数据表 前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/pmsData")
@Api(tags = "算法数据接口", description = "算法数据接口")
public class PmsDataController {

    @Autowired
    private PmsDataService pmsDataService;

    @Autowired
    private UmsUserService userService;

    // for user

    /**
     * 用户查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user")
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        IPage page = pmsDataService.page(new Page(pageNum, pageSize),
                WrapperUtil.querySingleWrapperBuilder("uid",user.getId()));
        return CommonPage.restPage(page);
    }

    /**
     * 用户单个查询
     * @param dataId
     * @param request
     * @return
     */
    @GetMapping("/user/{dataId}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String dataId, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsDataService.getOne(
                WrapperUtil.queryByUserIdAndPK(dataId, user.getId())));
    }

    /**
     * 用户新建数据
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/user")
    @ApiOperation("用户新建数据")
    public CommonResult create(@RequestBody PmsData data, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        data.setUid(user.getId());
        pmsDataService.create(data);
        return CommonResult.success(true, "新建数据成功");
    }

    @PutMapping("/user/{dataId}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String dataId, @RequestParam String name,
                               @RequestParam String desc, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        PmsData data = new PmsData();
        data.setId(dataId);
        data.setUpdateTime(LocalDateTime.now());
        data.setUid(user.getId());
        data.setName(name);
        data.setDescription(desc);
        boolean result = pmsDataService.update(data, WrapperUtil.queryByUserIdAndPK(dataId, user.getId()));
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/user/{dataId}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String dataId, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        boolean result = pmsDataService.remove(dataId, user.getId());
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }

    /**
     * 上传数据
     */
    @PostMapping("/upload")
    @ApiOperation("上传数据")
    public CommonResult upload(MultipartFile file, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsDataService.upload(file));
    }
}
