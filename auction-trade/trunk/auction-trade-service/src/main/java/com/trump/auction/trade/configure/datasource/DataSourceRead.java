package com.trump.auction.trade.configure.datasource;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@MapperScan(basePackages = { "com.trump.auction.trade.dao.sharding" }, sqlSessionTemplateRef = "readTemplate")
public class DataSourceRead extends DataSourceHelp {

	@Value("${auction.bid.detail.table.range}")
	private String auctionBidetailTableRange;

	@Bean(name = "readDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.sharding")
	public DataSource readDataSource() {
		return (DataSource) DruidDataSourceBuilder.create().build();
	}

	public DataSource readDataSource;

	@PostConstruct
	public void init() {
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
