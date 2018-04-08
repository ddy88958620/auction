package com.trump.auction.web.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.trump.auction.web.interceptor.AuthInterceptor;

/**
 * <p>
 * Title: 拦截器配置
 * </p>
 * 
 * @author youlianlai
 * @date 2017年10月31日下午12:39:49
 */
@EnableWebMvc
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
	
	
	//不拦截的请求地址\
	private static final String[]  excludeUris = new String[] {"/captcha","/register",
																"/sendSmsCode",
																"/login","/thirdPartyLogin",
																"/forgetPwd","/resetPwd",
																"/order-commets",
																"/auction/list",
																"/auction/navigation",
																"/auction/lastTrade",
																"/index/common",
																"/index/label",
																"/getH5Url",
																"/error",
																"/auctionAgreementUrl",
																"/myProperty-illustration",
																"/shoppingMoney-illustration",
																"/credit-app/gotoUpdate",
																"/sign-rule",
																"/aboutUsUrl",
																"/helpCenter",
																"/auctionDetail/dynamicData",
																"/auctionDetail/baseData",
																"/auctionDetail/pastRecord",
																"/auctionDetail/appraiseList",
																"/auctionDetail/bidRecord",
																"/recharge/rule",
																"/pay/notify/alipay",
																"/pay/notify/wxpay",
																"/healthCheck","/shareRegister","/sendShareCode",
																"/doRegister","/checkSmsCode","/auctionDetail/dynamicDataForList","/notiDevice/save",
																"/dynamicH5/*","/smsCodeLogin","/share/shareFirst","/share/doRegister","/share/toShare","/share/shareReward"};
	
	@Bean
	AuthInterceptor loadAuthInterceptor(){
		return new AuthInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器并配置拦截的路径
		registry.addInterceptor(loadAuthInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns(excludeUris);
	}
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
