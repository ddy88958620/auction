package com.trump.auction.back.configue.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "com.trump.auction.back.*.dao.write" }, sqlSessionTemplateRef = "writeTemplate")
public class DataSourceWrite extends DataSourceHelp {

	@Value("${auctionback.datasource.primary.url}")
	private String url;
	@Value("${auctionback.datasource.primary.username}")
	private String username;
	@Value("${auctionback.datasource.primary.password}")
	private String password;
	
	@Bean(name = "writeDataSource")
	@Primary
	public DataSource writeDataSource(){
		DruidDataSource baseDataSource = super.buildDruidDataSource();
		baseDataSource.setUrl(url);
		baseDataSource.setUsername(username);
		baseDataSource.setPassword(password);
		return baseDataSource;
	}

	@Bean(name = "writeSqlSessionFactory")
	@Primary
	public SqlSessionFactory writeSqlSessionFactory() throws Exception {
		return super.sqlSessionFactoryBean(writeDataSource());
	}

	@Bean(name = "writeTemplate")
	@Primary
	public SqlSessionTemplate writeTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "writeTransactionManager")
	@Primary
	public PlatformTransactionManager writeTransactionManager() {
		return new DataSourceTransactionManager(writeDataSource());
	}
}
