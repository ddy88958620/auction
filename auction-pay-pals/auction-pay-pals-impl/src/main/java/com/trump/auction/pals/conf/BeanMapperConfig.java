package com.trump.auction.pals.conf;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.mapping.DefaultBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanMapper
 * @author Owen.Yuan 2017/6/15.
 */
@Configuration
public class BeanMapperConfig {

    @Bean(name = "beanMapper")
    public BeanMapper beanMapper() {
        return new DefaultBeanMapper();
    }
}
