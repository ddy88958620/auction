package com.trump.auction.back.configue.datasource;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@MapperScan(basePackages = { "com.trump.auction.back.*.dao.read" }, sqlSessionTemplateRef = "readTemplate")
public class DataSourceRead extends DataSourceHelp {

	@Value("${auction.bid.detail.table.range}")
	private String auctionBidetailTableRange;

	@Value("${auctionback.datasource.secondary.url}")
	private String url;
	
	@Value("${auctionback.datasource.secondary.username}")
	private String username;
	
	
	@Value("${auctionback.datasource.secondary.password}")
	private String password;
	
	@Bean(name = "readDataSource")
	public DataSource readDataSource() {
		DruidDataSource baseDataSource = super.buildDruidDataSource();
		baseDataSource.setUrl(url);
		baseDataSource.setUsername(username);
		baseDataSource.setPassword(password);
		return baseDataSource;
	}
	
	public DataSource readDataSource;

	@PostConstruct
	public void init() throws SQLException, CloneNotSupportedException {
		HashMap<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("readDataSource", readDataSource());
		
		TableRuleConfiguration auctionDetail = new TableRuleConfiguration();
		auctionDetail.setLogicTable("auction_detail");
		auctionDetail.setActualDataNodes("readDataSource.auction_detail_${[" + auctionBidetailTableRange + "]}");
		
		TableRuleConfiguration auctionBidInfo = new TableRuleConfiguration();
		auctionBidInfo.setLogicTable("auction_bid_info");
		auctionBidInfo.setActualDataNodes("readDataSource.auction_bid_info_${[" + auctionBidetailTableRange + "]}");
		
		TableRuleConfiguration auctionBidDetail = new TableRuleConfiguration();
		auctionBidDetail.setLogicTable("auction_bid_detail");
		auctionBidDetail.setActualDataNodes("readDataSource.auction_bid_detail_${[" + auctionBidetailTableRange + "]}");

		List<TableRuleConfiguration> ruleList = Arrays.asList(auctionDetail,auctionBidInfo,auctionBidDetail);

		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().addAll(ruleList);
		try {
			readDataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
					new ConcurrentHashMap(), new Properties());
		} catch (SQLException e) {
			log.error("mybatis config init error", e);
		}
	}

	@Bean(name = "readSqlSessionFactory")
	public SqlSessionFactory readSqlSessionFactory() throws Exception {
		return super.sqlSessionFactoryBean(readDataSource);
	}

	@Bean(name = "readTransactionManager")
	public PlatformTransactionManager readTransactionManager() {
		return new DataSourceTransactionManager(readDataSource);
	}

	@Bean(name = "readTemplate")
	public SqlSessionTemplate readTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
