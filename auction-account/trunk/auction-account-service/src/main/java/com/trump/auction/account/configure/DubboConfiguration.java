package com.trump.auction.account.configure;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.*;
import com.trump.auction.cust.api.AccountRechargeRuleDetailStubService;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserRelationStubService;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfiguration {
	@Autowired
	private ApplicationConfig application;
	@Autowired
	private RegistryConfig registry;

	private static final String DEFAULT_VERSION = "1.0.0";
	private static final int DEFAULT_TIMEOUT = 5000;
	private static final int DEFAULT_RETRIES = 2;

	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setCheck(Boolean.FALSE);
		return consumerConfig;
	}

	@Bean
	public UserInfoStubService userInfoStubService() {
		ReferenceConfig<UserInfoStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(UserInfoStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setMethods(methodConfigs("findUserInfoById", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
//		reference.setMethods(methodConfigs("findUserInfoById", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public AccountRechargeRuleStubService accountRechargeRuleStubService() {
		ReferenceConfig<AccountRechargeRuleStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(AccountRechargeRuleStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setMethods(methodConfigs("findEnableRule", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public AccountRechargeRuleDetailStubService accountRechargeRuleDetailStubService() {
		ReferenceConfig<AccountRechargeRuleDetailStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(AccountRechargeRuleDetailStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setMethods(methodConfigs("findRuleDetailById", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public AuctionOrderStubService auctionOrderStubService() {
		ReferenceConfig<AuctionOrderStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(AuctionOrderStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setCheck(false);
		reference.setMethods(methodConfigs("getUserOrder", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public ProductInfoSubService productInfoSubService() {
		ReferenceConfig<ProductInfoSubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(ProductInfoSubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setCheck(false);
		reference.setMethods(methodConfigs("getProductByProductId", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public WeChatPayStubService weChatPayStubService() {
		ReferenceConfig<WeChatPayStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(WeChatPayStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setCheck(false);
		reference.setMethods(methodConfigs("queryWeChatPay", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public AlipayStubService alipayStubService() {
		ReferenceConfig<AlipayStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(AlipayStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setCheck(false);
		reference.setMethods(methodConfigs("toAlipayQuery", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
		return reference.get();
	}

	@Bean
	public UserRelationStubService userRelationStubService() {
		ReferenceConfig<UserRelationStubService> reference = new ReferenceConfig<>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(UserRelationStubService.class);
		reference.setTimeout(DEFAULT_TIMEOUT);
		reference.setVersion(DEFAULT_VERSION);
		reference.setCheck(false);
		return reference.get();
	}
	private List<MethodConfig> methodConfigs(String methodName, int timeout, int retries) {
		List<MethodConfig> methodConfigs = new ArrayList<>();
		methodConfigs.add(methodConfig(methodName, timeout, retries));
		return methodConfigs;
	}

	// 方法级配置
	private MethodConfig methodConfig(String methodName, int timeout, int retries) {
		MethodConfig method = new MethodConfig();
		method.setName(methodName);
		method.setTimeout(timeout);
		method.setRetries(retries);
		return method;
	}
}
