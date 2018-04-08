package com.trump.auction.order.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.mapping.DefaultBeanMapper;



/**
 * BeanMapper
 * @author Owen.Yuan 2017/10/26.
 */
@Configuration
public class BeanMapperConfiguration {

    @Bean(name = "beanMapper")
    public BeanMapper beanMapper() {
        return new DefaultBeanMapper();
    }
}
