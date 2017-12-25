/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━感觉萌萌哒━━━━━━
 */
package com.yeemin.sxf.framework;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * description 数据连接层相关配置
 * author yeemin [yeemin_shon@163.com]
 * time 2017/12/24 - 2:46
 */
@Configuration
@EnableTransactionManagement
public class DaoConfiguration {

    /**
     * 数据库地址
     */
    @Value("${jdbc.url}")
    private String url;

    /**
     * 数据库用户名
     */
    @Value("${jdbc.username}")
    private String username;

    /**
     * 数据库密码
     */
    @Value("${jdbc.url}")
    private String password;

    /**
     * @description 设置数据源
     * @methodName dataSource
     * @return javax.sql.DataSource
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:55
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    /**
     * @description Spring事务
     * @methodName dataSourceTransactionManager
     * @return org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:58
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        dataSourceTransactionManager.setEnforceReadOnly(true);
        return dataSourceTransactionManager;
    }

    /**
     * @description jdbcTemplate
     * @methodName jdbcTemplate
     * @return org.springframework.jdbc.core.JdbcTemplate
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:58
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @description mybatis的SqlSessionFactory
     * @methodName sqlSessionFactoryBean
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 2:23
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfiguration(configuration());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.yeemin.ssm.noxml.entity");
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * @description mybatis扫描配置
     * @methodName mapperScannerConfigurer
     * @return org.mybatis.spring.mapper.MapperScannerConfigurer
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 2:23
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.yeemin.ssm.noxml");
        // 使用MyBatis自带注解
        mapperScannerConfigurer.setAnnotationClass(Mapper.class);
        return mapperScannerConfigurer;
    }

    /**
     * @description Mybatis TranscatioNFactory配置
     * @methodName transactionFactory
     * @return org.apache.ibatis.transaction.TransactionFactory
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 21:28
     */
    @Bean
    public TransactionFactory transactionFactory() {
        return new JdbcTransactionFactory();
    }

    /**
     * @description Mybatis Environment配置
     * @methodName environment
     * @return org.apache.ibatis.mapping.Environment
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 21:28
     */
    @Bean
    public Environment environment() {
        return new Environment("develop", transactionFactory(), dataSource());
    }

    /**
     * @description Mybatis Configuration配置
     * @methodName configuration
     * @return org.apache.ibatis.session.Configuration
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 21:29
     */
    @Bean
    public org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration(environment());
        configuration.setCacheEnabled(true);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseColumnLabel(true);
        configuration.setUseGeneratedKeys(true);
        return configuration;
    }

}
