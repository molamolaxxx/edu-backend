package com.njupt.hpc.edu.project.controller;

import com.njupt.hpc.edu.project.data.content.csv.CSVContentVO;
import com.njupt.hpc.edu.project.data.content.graph.GraphContentVO;
import com.njupt.hpc.edu.project.data.parser.impl.GenerateDataParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-27 12:08
 **/
@RestController
@RequestMapping("/pmsTest")
@Api(tags = "测试接口", description = "测试接口")
public class TestController {

    @Autowired
    GenerateDataParser generateDataParser;

    @GetMapping("/testCSVParser")
    @ApiOperation("测试csv的转化器")
    public CSVContentVO testCSVParser(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return generateDataParser.parseDataCSV("/home/mola/IdeaProjects/edu/data-cache/z2CigmFq-test.csv", offset, limit);
    }

    @GetMapping("/testResultDetailParser")
    @ApiOperation("测试csv的转化器")
    public CSVContentVO testResultDetailParser(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return generateDataParser.parseResultDetail("/home/mola/PycharmProjects/entity_accuracy/app/result/result_87396AtYLg_temp.csv", offset, limit);
    }

    @GetMapping("/testGraphParser")
    @ApiOperation("测试graph的转化器")
    public GraphContentVO testGraphParser(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return generateDataParser.parseGraph("/home/mola/IdeaProjects/edu/data-cache/z2CigmFq-test.csv", offset, limit);
    }
}
