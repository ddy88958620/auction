package com.trump.auction.cust.configure;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.mapping.DefaultBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMapperConfiguration {

    @Bean(name = "beanMapper")
    public BeanMapper beanMapper() {
        return new DefaultBeanMapper();
    }
}
