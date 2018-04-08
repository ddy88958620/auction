package com.trump.auction.order.configure;

import java.util.ArrayList;
import java.util.List;

import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.goods.api.ProductInventoryLogSubService;

/**
 * dubbo configure
 *
 * @author <a href="mailto:sowen1023@gmail.com">Owen.Yuan</a>
 * @since 2017/10/26.
 */
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
    public UserShippingAddressStuService userShippingAddressStuService() {
        ReferenceConfig<UserShippingAddressStuService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserShippingAddressStuService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        reference.setMethods(methodConfigs("findUserAddressItemByAddressId", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
        return reference.get();
    }

    @Bean
    public ProductInventoryLogSubService productInventoryLogSubService() {
        ReferenceConfig<ProductInventoryLogSubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(ProductInventoryLogSubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        List<MethodConfig> methods = new ArrayList<>();
        methods.add(methodConfig("validateStock", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
        methods.add(methodConfig("subtractStock", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
        methods.add(methodConfig("addStock", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
        reference.setMethods(methods);
        return reference.get();
    }

    @Bean
    public AccountInfoStubService accountInfoStubService() {
        ReferenceConfig<AccountInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        List<MethodConfig> methods = new ArrayList<>();
        reference.setMethods(methodConfigs("reduceBuyCoin", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
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
        reference.setCheck(Boolean.FALSE);
        List<MethodConfig> methods = new ArrayList<>();
        reference.setMethods(methodConfigs("toAlipayQuery", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
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
        reference.setCheck(Boolean.FALSE);
        List<MethodConfig> methods = new ArrayList<>();
        reference.setMethods(methodConfigs("queryWeChatPay", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
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
        reference.setCheck(Boolean.FALSE);
        List<MethodConfig> methods = new ArrayList<>();
        reference.setMethods(methodConfigs("getRecordByAuctionId", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
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
