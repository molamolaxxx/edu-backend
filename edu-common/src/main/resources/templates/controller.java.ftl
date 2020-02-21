package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njupt.hpc.edu.common.api.CommonPage;
import com.njupt.hpc.edu.common.api.CommonResult;
import ${package.Entity}.${table.entityName};
import ${package.Service}.${table.serviceName};
import org.apache.commons.lang.RandomStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
</#if>
@Api(tags = "${table.comment!}", description = "${table.comment!}")
public class ${table.controllerName} {


    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping
    @ApiOperation("列表分页查询")
    public CommonPage list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "100") Integer pageSize){
        IPage page = ${table.serviceName?uncap_first}.page(new Page(pageNum, pageSize));
        return CommonPage.restPage(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找")
    public CommonResult findById(@PathVariable String id){
        return CommonResult.success(${table.serviceName?uncap_first}.getById(id));
    }

    @PostMapping
    @ApiOperation("保存数据")
    public CommonResult create(@RequestBody ${entity} ${entity?substring(3)?uncap_first}){
        ${entity?substring(3)?uncap_first}.setId("${entity?substring(3)?uncap_first}_"+ RandomStringUtils.randomAlphanumeric(8));
        ${entity?substring(3)?uncap_first}.setCreateTime(LocalDateTime.now());
        ${entity?substring(3)?uncap_first}.setUpdateTime(LocalDateTime.now());
        ${table.serviceName?uncap_first}.save(${entity?substring(3)?uncap_first});
        return CommonResult.success(true, "保存数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public CommonResult update(@PathVariable String id, ${entity} ${entity?substring(3)?uncap_first}){
        ${entity?substring(3)?uncap_first}.setId(id);
        ${entity?substring(3)?uncap_first}.setUpdateTime(LocalDateTime.now());
        boolean result = ${table.serviceName?uncap_first}.updateById(${entity?substring(3)?uncap_first});
        return CommonResult.parseResultToResponse(result, "更新数据失败", "更新数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据")
    public CommonResult delete(@PathVariable String id){
        boolean result = ${table.serviceName?uncap_first}.removeById(id);
        return CommonResult.parseResultToResponse(result, "删除数据失败", "删除数据成功");
    }
}
</#if>