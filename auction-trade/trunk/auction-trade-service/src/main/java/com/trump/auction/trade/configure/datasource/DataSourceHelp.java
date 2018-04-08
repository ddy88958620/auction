package com.trump.auction.trade.configure.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageHelper;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceHelp {
	
	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasesPackage;

	@Value("${auction.bid.detail.table.range}")
	private String auctionBidetailTableRange;

    /*@Autowired
    public DataSource shardingDataSource;
    
    @PostConstruct
    public void init(){
        HashMap<String, DataSource> dataSourceMap  = new HashMap<>();
        dataSourceMap .put("shardingDataSource", shardingDataSource);
        
        TableRuleConfiguration tbUserTableRuleConfig = new TableRuleConfiguration();
        tbUserTableRuleConfig.setLogicTable("auction_bid_detail");
        
        tbUserTableRuleConfig.setActualDataNodes("shardingDataSource.auction_bid_detail_${["+auctionBidetailTableRange+"]}");
        
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(tbUserTableRuleConfig);
        try {
        	shardingDataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage(typeAliasesPackage);
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        props.setProperty("dialect", "mysql");
        props.setProperty("offsetAsPageNum", "true");
        props.setProperty("rowBoundsWithCount", "true");

        try {
            bean.setDataSource(dataSource);
            PageHelper pageHelper = new PageHelper();
            pageHelper.setProperties(props);
            bean.setPlugins(new Interceptor[]{pageHelper});
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources(mapperLocations));
            return bean.getObject();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
