package com.frame.kangan.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/** 
* @ClassName: FrameHikariConfig 
* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年5月30日 下午6:14:39 
*  
*/
@Configuration
@PropertySource("classpath:application.properties")
@MapperScan(basePackages="com.frame.kangan.data.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class FrameHikariConfig{
	@Autowired
    private Environment env;
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setAutoCommit(false);
        config.setJdbcUrl(env.getProperty("db.url"));
        config.setUsername(env.getProperty("db.user"));
        config.setPassword(env.getProperty("db.password"));
        return new HikariDataSource(config);
    }
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
////        ClassPathResource[] cpr = { new ClassPathResource("FrameImageMapper.xml") };
////        sessionFactory.setMapperLocations( cpr );
//        sessionFactory.setTypeAliasesPackage( "com.frame.kangan.data.po" );
        return sessionFactory.getObject();
    }
}
