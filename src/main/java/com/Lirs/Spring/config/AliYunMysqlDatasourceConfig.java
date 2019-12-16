package com.Lirs.Spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = AliYunMysqlDatasourceConfig.PACKAGE,sqlSessionFactoryRef = "ali_SqlSessionFactory")
public class AliYunMysqlDatasourceConfig {
    //配置扫描mapper接口的路径
    static final String PACKAGE = "com.Lirs.Spring.aliMapper";
    //配置扫描mapper.xml文件的路径
    //static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Bean(name = "ali_mysqlDataSource")
    @ConfigurationProperties("spring.datasource.druid.alimysql")
    public DataSource ali_mysqlDataSource(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();

        return dataSource;
    }

    @Bean(name = "ali_mysqlDataSourceTransactionManager")
    public DataSourceTransactionManager ali_mysqDataSourceTransactionManager(){
        return new DataSourceTransactionManager(ali_mysqlDataSource());
    }

    @Bean(name = "ali_SqlSessionFactory")
    public SqlSessionFactory aliSqlSessionFactory(@Qualifier(value = "ali_mysqlDataSource") DataSource dataSource)
            throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        //sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
        // .getResources(AliYunMysqlDatasourceConfig.MAPPER_LOCATION));
        return sessionFactoryBean.getObject();
    }
}
