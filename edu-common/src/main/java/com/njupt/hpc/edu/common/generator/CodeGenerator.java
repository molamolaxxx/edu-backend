package com.njupt.hpc.edu.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : molamola
 * @Project: mybatis-plus-test
 * @Description:
 * @date : 2019-11-01 00:37
 **/
public class CodeGenerator {

    public static void main(String[] args) {
        // 生成用户模块
//        config("edu-user","com.njupt.hpc.edu.user"
//                ,new String[]{"ums_role","ums_user","ums_permission"})
//                .execute();

        // 生成项目模块
        config("edu-project","com.njupt.hpc.edu.project"
                ,new String[]{"pms_instance","pms_data","pms_result"})
                .execute();
    }

    private static AutoGenerator config(String moduleName, String parentPackageName, String[] tableList){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir")+"/"+moduleName;
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("molamola");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://localhost:3306/edu?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("314");
        mpg.setDataSource(dsc);

        // 自定义模板输出路径
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String mapperPath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> mapperList = new ArrayList<>();
        // 自定义配置会被优先输出
        mapperList.add(new FileOutConfig(mapperPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" +
                        tableInfo.getEntityName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(mapperList);
        mpg.setCfg(cfg);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackageName);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("model");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tableList);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setStrategy(strategy);

        return mpg;
    }

}
