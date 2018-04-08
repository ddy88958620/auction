package com.trump.auction.back.configue.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect;
import com.github.pagehelper.PageHelper;

@Configuration
public class DataSourceHelp {
	
	@Value("${mybatis.mapperLocations}")
	protected String mapperLocations;

	@Value("${mybatis.typeAliasesPackage}")
	protected String typeAliasesPackage;

	@Value("${auction.bid.detail.table.range}")
	protected String auctionBidetailTableRange;

	@Value("${datasource.driver-class-name}")
	protected String driverClassName;
	
	@Value("${datasource.filters}")
	protected String filters;
	
	@Value("${datasource.maxActive}")
    protected int maxActive;
	
	@Value("${datasource.initialSize}")
    protected int initialSize;
	
	@Value("${datasource.maxWait}")
    protected int maxWait;
	@Value("${datasource.minIdle}")
    protected int minIdle;
	
	@Value("${datasource.timeBetweenEvictionRunsMillis}")
    protected int timeBetweenEvictionRunsMillis;
	
	@Value("${datasource.minEvictableIdleTimeMillis}")
    protected int minEvictableIdleTimeMillis;

	@Value("${datasource.validationQuery}")
	protected String validationQuery;
	
	@Value("${datasource.testWhileIdle}")
    protected boolean testWhileIdle;
	
	@Value("${datasource.testOnBorrow}")
    protected boolean testOnBorrow;
	
	@Value("${datasource.testOnReturn}")
    protected boolean testOnReturn;
	@Value("${datasource.poolPreparedStatements}")
    protected boolean poolPreparedStatements;
	
	@Value("${datasource.maxOpenPreparedStatements}")
    protected int maxPoolPreparedStatementPerConnectionSize;
	
	
	public DruidDataSource buildDruidDataSource() {
		DruidDataSource baseDataSource = new DruidDataSource();
		baseDataSource.setMaxActive(maxActive);
		baseDataSource.setInitialSize(initialSize);
		baseDataSource.setMaxWait(maxWait);
		baseDataSource.setMinIdle(minIdle);
		baseDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		baseDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		baseDataSource.setValidationQuery(validationQuery);
		baseDataSource.setTestWhileIdle(testWhileIdle);
		baseDataSource.setTestOnBorrow(testOnBorrow);
		baseDataSource.setTestOnReturn(testOnReturn);
		baseDataSource.setPoolPreparedStatements(poolPreparedStatements);
		baseDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		return baseDataSource;
	}
    
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws ClassNotFoundException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setTypeAliasesPackage(typeAliasesPackage);
        Class<?>[] typeAliases = {
        		Class.forName("com.trump.auction.back.sys.model.Module"),
        		Class.forName("com.trump.auction.back.sys.model.Role"),
        		Class.forName("com.trump.auction.back.sys.model.RoleModule"),
        		Class.forName("com.trump.auction.back.sys.model.SysUser"),
        		Class.forName("com.trump.auction.back.sys.model.UserRole"),
        		Class.forName("com.trump.auction.back.sys.model.ZTree"),
        		Class.forName("com.trump.auction.back.sys.model.SysLog"),
        		Class.forName("com.trump.auction.back.sys.model.Config")};
       
        bean.setTypeAliases(typeAliases);
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
