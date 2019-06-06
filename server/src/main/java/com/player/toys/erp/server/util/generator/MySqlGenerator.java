package com.player.toys.erp.server.util.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName MySqlGenerator
 * @Description
 * @Author Player
 * @Date 2019-05-16 15:44
 * @Version 1.0
 **/
public class MySqlGenerator {
    public static void main(String[] args) {
        Generator();
    }

    public static void Generator() {

        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath+rb.getString("OutputDir"));
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(rb.getString("author"));
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setActiveRecord(true); // 是否支持 AR
        gc.setFileOverride(true); //文件覆盖(全新文件)
        gc.setIdType(IdType.AUTO);//主键策略
        mpg.setGlobalConfig(gc);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getString("parent"));
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service"+ ".impl");
        pc.setEntity("bean");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath+rb.getString("OutputDirXml") + "/mapper/" + tableInfo.getEntityName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(rb.getString("tableName").split(","));
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }




}
