package com.njupt.hpc.edu.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.FusionDataParser;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.dto.DataDTO;
import com.njupt.hpc.edu.project.model.vo.DataVO;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    GenerateDataParser generateDataParser;

    @Autowired
    FusionDataParser fusionDataParser;

    // for user

    /**
     * 用户查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user")
    @ApiOperation("列表分页查询")
    public CommonResult list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        IPage<PmsData> page = pmsDataService.page(new Page(pageNum, pageSize),
                WrapperUtil.querySingleWrapperBuilder("uid",user.getId()));
        // 将model转化成前端显示的vo
        List voList = page.getRecords().stream().map(item -> BeanUtilsPlug
                .copyPropertiesReturnTarget(item, new DataVO())).collect(Collectors.toList());
        page.setRecords(voList);
        return CommonResult.success(CommonPage.restPage(page));
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
     * 用户根据实例类型查找
     */
    @GetMapping("/instance")
    @ApiOperation("根据实例类型查找")
    public CommonResult findByInstanceType(@RequestParam String typeId, HttpServletRequest request) {
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsDataService.findByInstanceType(typeId));
    }

    /**
     * 用户新建数据
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/user")
    @ApiOperation("用户新建数据")
    public CommonResult create(@RequestBody DataDTO dto, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        dto.setUid(user.getId());
        DataVO dataVO=pmsDataService.create(dto);
        return CommonResult.success(dataVO, "新建数据成功");
    }

    @PutMapping("/user/{dataId}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String dataId, @RequestBody DataDTO dto, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        dto.setUid(user.getId());
        boolean result = pmsDataService.update(dto, dataId);
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/user/{dataId}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String dataId, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        boolean result = pmsDataService.remove(dataId);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }

    /**
     * 上传数据
     */
    @PostMapping("/upload")
    @ApiOperation("上传数据")
    public CommonResult upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        return CommonResult.success(pmsDataService.upload(file));
    }

    /**
     * 上传数据的表格可视化
     */
    @GetMapping("/table")
    @ApiOperation("获取csv文件的表格数据")
    public CommonResult<CSVContentVO> table(@RequestParam("path") String path, @RequestParam("instanceType") String type) {
        if(type.equals(InstanceTypeEnum.GENERATE_EVALUATE.getCode())) {
            return CommonResult.success(generateDataParser.parseDataCSV(path, 1, 100));
        }
        else return CommonResult.success(fusionDataParser.parseDataCSV(path, 1, 100));

    }

    /**
     * 上传数据的图谱可视化
     */
    @GetMapping("/graph")
    @ApiOperation("获取csv文件的图谱数据")
    public CommonResult<GraphContentVO> graph(@RequestParam("path") String path, @RequestParam("instanceType") String type) {
        if(type.equals(InstanceTypeEnum.GENERATE_EVALUATE.getCode())){
            return CommonResult.success(generateDataParser.parseGraph(path, 1, 100));
        }
        else return CommonResult.success(fusionDataParser.parseGraph(path, 1, 100));
    }
}
