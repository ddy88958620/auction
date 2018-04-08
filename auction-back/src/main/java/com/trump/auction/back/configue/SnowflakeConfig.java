package com.trump.auction.back.configue;

import com.cf.common.id.IdGenerator;
import com.cf.common.id.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangjian on 2017/12/25.
 */
@Configuration
public class SnowflakeConfig {
    @Value("${snowflake.orders.order-id}")
    private  String snowflakeordersIds;

    @Bean(name="snowflakeOrderId")
    public IdGenerator snowflakeOrderId(){
        return SnowflakeGenerator.create(Long.valueOf(snowflakeordersIds.split(",")[0]),Long.valueOf(snowflakeordersIds.split(",")[1]));
    }
}
