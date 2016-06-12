package com.frame.kangan.config;

import com.baomidou.mybatisplus.generator.ConfigGenerator;

/**
 * Created by ouetsu on 16/4/29.
 */
public class ConfigGeneratorTest {
    protected static ConfigGenerator getConfigGenerator() {
        ConfigGenerator cg = new ConfigGenerator();
        cg.setEntityPackage("com.frame.kangan.data.po");//entity 实体包路径

        cg.setMapperPackage("com.frame.kangan.data.mapper");//mapper 映射文件路径

        cg.setServicePackage("com.frame.kangan.data.service");//service 层路径


		/* 此处可以配置 SuperServiceImpl 子类路径，默认如下 */
        //cg.setSuperServiceImpl("com.baomidou.framework.service.impl.SuperServiceImpl");




		/* 此处设置 String 类型数据库ID，默认Long类型 */
        //cg.setConfigIdType(ConfigIdType.STRING);




        cg.setSaveDir("/Users/ankang/Documents/testsss");// 生成文件保存位置


		/*

		 * 设置字段是否为驼峰命名，驼峰 true 下划线分割 false

		 */
        cg.setColumnHump(false);
		/*

		 * 表是否包括前缀

		 * <p>

		 * 例如 mp_user 生成实体类 false 为 MpUser , true 为 User

		 * </p>

		 */
        cg.setDbPrefix(true);
        return cg;
    }
}
