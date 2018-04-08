package com.trump.auction.pals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:config/*.xml")//读取消费者配置信息
@Configuration
public class RepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepayApplication.class, args);
	}
}
