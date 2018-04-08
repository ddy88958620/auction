package com.trump.auction.cust.configure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.RegistryConfig;


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

    //    @Bean
//    public XXXStubService registChannelQueryStubService() {
//        ReferenceConfig<XXXStubService> reference = new ReferenceConfig<>();
//        reference.setApplication(application);
//        reference.setRegistry(registry);
//        reference.setInterface(XXXStubService.class);
//        reference.setTimeout(DEFAULT_TIMEOUT);
//        reference.setVersion(DEFAULT_VERSION);
//        reference.setMethods(methodConfigs("xxxMethod",  DEFAULT_TIMEOUT, DEFAULT_RETRIES));
//        return reference.get();
//    }
//    @Bean
//    public YhInfoStubService registYhInfoStubService() {
//        ReferenceConfig<YhInfoStubService> reference = new ReferenceConfig<>();
//        reference.setApplication(application);
//        reference.setRegistry(registry);
//        reference.setInterface(YhInfoStubService.class);
//        reference.setTimeout(DEFAULT_TIMEOUT);
//        reference.setVersion(DEFAULT_VERSION);
//        reference.setCheck(Boolean.FALSE);
//        reference.setMethods(methodConfigs("findById", DEFAULT_TIMEOUT, DEFAULT_RETRIES));
//        return reference.get();
//    }

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
