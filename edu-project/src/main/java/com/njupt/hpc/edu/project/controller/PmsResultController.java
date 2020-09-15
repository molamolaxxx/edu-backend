package com.njupt.hpc.edu.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.FusionDataParser;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.PmsResult;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.model.vo.InstanceItemVO;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import com.njupt.hpc.edu.user.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/pmsResult")
@Api(tags = "评价结果接口", description = "评价结果接口")
public class PmsResultController {

    @Autowired
    private PmsResultService pmsResultService;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private UmsUserService userService;

    @Autowired
    private GenerateDataParser generateDataParser;

    @Autowired
    private FusionDataParser fusionDataParser;

    @GetMapping("/user")
    @ApiOperation("列表分页查询")
    public CommonResult list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        UmsUser user = UserUtils.getUserFromRequest(request, userService);
        IPage page = pmsResultService.page(new Page(pageNum, pageSize));
        List<PmsResult> pmsResults=pmsResultService.findByUid(user.getId());
        // 将model转化成前端显示的vo
        List voList = pmsResults.stream().map(item -> BeanUtilsPlug
                .copyPropertiesReturnTarget(item, new ResultDTO())).collect(Collectors.toList());
        page.setRecords(voList);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @GetMapping("/detail/file/{instanceId}")
    @ApiOperation("分页展示获取结果文件数据")
    //还需要额外传文件的类型（冗余或信息缺失）
    public CommonResult<CSVContentVO> detail(@PathVariable String instanceId,
                                             @RequestParam("resultType") String resultType,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        PmsInstance pmsInstance=instanceService.getById(instanceId);
        // 根据instanceId获取result
        ResultDTO result = pmsResultService.findByInstanceId(instanceId);
        //根据实例的type来选择不同的数据解析
        if(pmsInstance.getType().equals(InstanceTypeEnum.GENERATE_EVALUATE.getCode())){
            return CommonResult.success(generateDataParser.parseResultDetail(result.getPath(), offset, limit));
        }
        else {
            //根据结果明细类型，选择合适的解析函数
            JSONObject jsonObject = JSON.parseObject(result.getPath());
            String redundance_path=(String)jsonObject.get("redundance");
            String infoLack_path=(String)jsonObject.get("infoLack");
            if("redundance".equals(resultType)){
                return CommonResult.success(fusionDataParser.parseRedundanceResultDetail(redundance_path, offset, limit));
            }
            else return CommonResult.success(fusionDataParser.parseInfoLackResultDetail(infoLack_path, offset, limit));
        }
    }

    @GetMapping("/detail/{instanceId}")
    @ApiOperation("评价结果详情")
    public CommonResult detail(@PathVariable String instanceId){
        return CommonResult.success(BeanUtilsPlug
                .copyPropertiesReturnTarget(instanceService.getById(instanceId), new InstanceItemVO()));
    }

    @GetMapping("/detail/download/{id}")
    @ApiOperation("下载结果明细文件")
    public void downloadDetail(@PathVariable String id, HttpServletResponse response) throws IOException {
        pmsResultService.downloadDetail(id, response);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        System.out.println(pmsResultService.getById(id));
        return CommonResult.success(pmsResultService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, PmsResult result){
        result.setId(id);
        return CommonResult.parseResultToResponse(pmsResultService.updateById(result),
                "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        boolean result = pmsResultService.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
