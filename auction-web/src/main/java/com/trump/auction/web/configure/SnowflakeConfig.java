package com.trump.auction.web.configure;

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
    @Value("${snowflake.pay.batch-no}")
    private  String snowflakePayBatchNos;

    @Bean(name="snowflakePayBatchNo")
    public IdGenerator snowflakePayBatchNo(){
        return SnowflakeGenerator.create(Long.valueOf(snowflakePayBatchNos.split(",")[0]),Long.valueOf(snowflakePayBatchNos.split(",")[1]));
    }
}
