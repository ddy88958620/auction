package com.trump.auction.trade.configure.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "com.trump.auction.trade.dao" }, sqlSessionTemplateRef = "writeTemplate")
public class DataSourceWrite extends DataSourceHelp {

	@Bean(name = "writeDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	@Primary
	public DataSource writeDataSource() {
		DataSource source = DruidDataSourceBuilder.create().build();
		return source;
	}

	@Bean(name = "writeSqlSessionFactory")
	@Primary
	public SqlSessionFactory writeSqlSessionFactory(@Qualifier("writeDataSource") DataSource dataSource) throws Exception {
		return super.sqlSessionFactoryBean(dataSource);
	}

	@Bean(name = "writeTemplate")
	@Primary
	public SqlSessionTemplate writeTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "readTransactionManager")
	@Primary
	public PlatformTransactionManager writeTransactionManager(@Qualifier("writeDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
