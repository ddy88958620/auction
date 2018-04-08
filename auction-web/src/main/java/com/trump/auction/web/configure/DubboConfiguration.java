package com.trump.auction.web.configure;

import java.util.ArrayList;
import java.util.List;


import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.cust.api.*;
import com.trump.auction.goods.api.ProductInfoSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.trump.auction.account.api.AccountInfoDetailStubService;
import com.trump.auction.account.api.AccountInfoRecordStubService;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.activity.api.UserSignStubService;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.api.NotificationDeviceStubService;
import com.trump.auction.cust.api.PromotionChannelStubService;
import com.trump.auction.cust.api.SendSmsStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserLoginRecordStubService;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.cust.api.UserSenstiveWordStubService;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.order.api.AddressInfoStuService;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.api.LabelStubService;

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
    private static final int DEFAULT_TIMEOUT = 15000;
    //private static final int DEFAULT_RETRIES = 2;

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(Boolean.FALSE);
        return consumerConfig;
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
    public AccountInfoRecordStubService accountInfoRecordStubService() {
        ReferenceConfig<AccountInfoRecordStubService> reference = new ReferenceConfig<AccountInfoRecordStubService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountInfoRecordStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(false);
        return reference.get();
    }

    @Bean
    public AccountInfoStubService accountInfoStubService() {
        ReferenceConfig<AccountInfoStubService> reference = new ReferenceConfig<AccountInfoStubService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("rechargeUserAccount", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("exchangePoints", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("createAccountRechargeOrder", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("initUserAccount", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("backShareCoin", DEFAULT_TIMEOUT, 0));
        reference.setCheck(false);
        return reference.get();
    }

    @Bean
    public AccountInfoDetailStubService accountInfoDetailStubService() {
        ReferenceConfig<AccountInfoDetailStubService> reference = new ReferenceConfig<AccountInfoDetailStubService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountInfoDetailStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(false);
        return reference.get();
    }

    @Bean
    public OrderAppraisesStubService orderAppraisesStubService() {
        ReferenceConfig<OrderAppraisesStubService> reference = new ReferenceConfig<OrderAppraisesStubService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(OrderAppraisesStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("createOrderAppraises", DEFAULT_TIMEOUT, 0));
        reference.setCheck(false);
        return reference.get();
    }

    @Bean
    public UserSignStubService userSignStubService() {
        ReferenceConfig<UserSignStubService> reference = new ReferenceConfig<UserSignStubService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserSignStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("userSign", DEFAULT_TIMEOUT, 0));
        reference.setCheck(false);
        return reference.get();
    }

    @Bean
    public OrderInfoStubService orderInfoStubService() {
        ReferenceConfig<OrderInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(OrderInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("saveOrder", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("updateOrderStatus", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserProductCollectStubService userProductCollectStubService() {
        ReferenceConfig<UserProductCollectStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserProductCollectStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("saveUserProductCollect", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("cancelUserProductCollect", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public SendSmsStubService sendSmsStubService() {
        ReferenceConfig<SendSmsStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(SendSmsStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("sendSmsByUserPhone", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public AddressInfoStuService addressInfoStuService() {
        ReferenceConfig<AddressInfoStuService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AddressInfoStuService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserShippingAddressStuService userShippingAddressStuService() {
    	 ReferenceConfig<UserShippingAddressStuService> reference = new ReferenceConfig<>();
         reference.setApplication(application);
         reference.setRegistry(registry);
         reference.setInterface(UserShippingAddressStuService.class);
         reference.setTimeout(DEFAULT_TIMEOUT);
         reference.setVersion(DEFAULT_VERSION);
         reference.setMethods(methodConfigs("insertUserAddressItem", DEFAULT_TIMEOUT, 0));
         reference.setMethods(methodConfigs("updateUserAddressItem", DEFAULT_TIMEOUT, 0));
         reference.setMethods(methodConfigs("deleteUserAddressItemByAddressId", DEFAULT_TIMEOUT, 0));
         reference.setMethods(methodConfigs("setDefaultUserAddressItem", DEFAULT_TIMEOUT, 0));
         reference.setCheck(Boolean.FALSE);
         return reference.get();
	}

    @Bean
    public AuctionInfoStubService auctionInfoStubService() {
        ReferenceConfig<AuctionInfoStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AuctionInfoStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public AuctionOrderStubService auctionOrderStubService() {
        ReferenceConfig<AuctionOrderStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AuctionOrderStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public AuctionBidStubService auctionBidStubService() {
        ReferenceConfig<AuctionBidStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AuctionBidStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("bidOperation", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public AccountRechargeRuleStubService accountRechargeRuleStubService() {
        ReferenceConfig<AccountRechargeRuleStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AccountRechargeRuleStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public AlipayStubService alipayStubService() {
        ReferenceConfig<AlipayStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(AlipayStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("toAlipayPay", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("toAlipayBack", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public PaymentStubService paymentStubService() {
        ReferenceConfig<PaymentStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(PaymentStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("createPaymentInfo", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("updatePaymentInfoStatusSuc", 50000, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public WeChatPayStubService weChatPayStubService() {
        ReferenceConfig<WeChatPayStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(WeChatPayStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setMethods(methodConfigs("toWeChatPay", DEFAULT_TIMEOUT, 0));
        reference.setMethods(methodConfigs("toWeChatPayBack", DEFAULT_TIMEOUT, 0));
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public PromotionChannelStubService promotionChannelStubService(){
        ReferenceConfig<PromotionChannelStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(PromotionChannelStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserSenstiveWordStubService userSenstiveWordStubService() {
        ReferenceConfig<UserSenstiveWordStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserSenstiveWordStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public NotificationDeviceStubService notificationDeviceStubService(){
        ReferenceConfig<NotificationDeviceStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(NotificationDeviceStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public ActivityShareStubService activityShareStubService() {
        ReferenceConfig<ActivityShareStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(ActivityShareStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public LabelStubService labelStubService(){
        ReferenceConfig<LabelStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(LabelStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public ProductInfoSubService productInfoSubService() {
        ReferenceConfig<ProductInfoSubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(ProductInfoSubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserLoginRecordStubService userLoginRecordStubService(){
        ReferenceConfig<UserLoginRecordStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserLoginRecordStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }

    @Bean
    public UserRelationStubService userRelationStubService() {
        ReferenceConfig<UserRelationStubService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserRelationStubService.class);
        reference.setTimeout(DEFAULT_TIMEOUT);
        reference.setVersion(DEFAULT_VERSION);
        reference.setCheck(Boolean.FALSE);
        return reference.get();
    }
}
