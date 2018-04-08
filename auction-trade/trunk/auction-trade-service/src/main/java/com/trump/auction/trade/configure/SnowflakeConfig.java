package com.trump.auction.trade.configure;

import com.cf.common.id.IdGenerator;
import com.cf.common.id.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Value("${snowflake.trade.txn-id}")
    private  String snowflakeordersIds;

    @Bean(name="snowflakeTradeTxnId")
    public  IdGenerator  snowflakeTradeTxnId(){
        return SnowflakeGenerator.create(Long.valueOf(snowflakeordersIds.split(",")[0]),Long.valueOf(snowflakeordersIds.split(",")[1]));
    }
}
