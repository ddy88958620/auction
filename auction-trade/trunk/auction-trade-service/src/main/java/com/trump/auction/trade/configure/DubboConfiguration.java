package com.trump.auction.trade.configure;

import com.alibaba.dubbo.config.*;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.goods.api.ProductInventoryLogSubService;
import com.trump.auction.order.api.OrderInfoStubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.trump.auction.account.api.AccountInfoStubService;
import java.util.ArrayList;
import java.util.List;

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
    private static final int DEFAULT_RETRIES = 1;

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(Boolean.FALSE);
        return consumerConfig;
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
    @Bean
    public AccountInfoStubService  accountInfoStubService(){
        ReferenceConfig<AccountInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        reference.setMethods(methodConfigs("paymentWithCoin", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
        return reference.get();
    }
    @Bean
    public OrderInfoStubService orderInfoStubService(){
        ReferenceConfig<OrderInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(OrderInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        reference.setMethods(methodConfigs("saveOrder", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("saveAuctionOrder", DEFAULT_TIMEOUT, 0));
        return reference.get();
    }

    @Bean
    public ProductInventoryLogSubService productInventoryLogSubService(){
        ReferenceConfig<ProductInventoryLogSubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(ProductInventoryLogSubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserInfoStubService userInfoStubService() {
        ReferenceConfig<UserInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("updateUserPhoneById", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("saveUserInfo", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("updateAddressById", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("updateLoginTypeById", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("updateThirdUserInfo", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }



}
