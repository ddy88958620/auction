package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.trump.auction.cust.api.SendSmsStubService;
import com.trump.auction.cust.constant.CustConstant;
import com.trump.auction.cust.domain.UserInfo;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.service.UserInfoService;
import com.trump.auction.cust.util.DateUtil;
import com.trump.auction.cust.util.SendSmsUtil;
import com.trump.auction.cust.util.WebsiteUtil;
import com.trump.auction.cust.util.sms.Dest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;


@Service(version = "1.0.0")
public class SendSmsStubServiceImpl implements SendSmsStubService {
    private static Logger logger = LoggerFactory.getLogger(SendSmsStubServiceImpl.class);


    @Autowired
    private WebsiteUtil websiteUtil;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private UserInfoService userInfoService;


    @Override
    public Boolean sendSmsByUserId(Integer userId, String phone, String content) {
        Boolean flag = false;
        final String signPre = websiteUtil.getAppConfig(null, "SIGN_PRE");
        try {
            flag = sendSmsUtil.sendSmsDiy(phone, content, signPre);
        } catch (Exception e) {
            logger.error("sendSmsByIdAndCode error", e);
        }

        return flag;
    }

    @Override
    public Boolean sendSmsByUserPhone(String userPhone, String content) {
        Boolean flag = false;
        final String signPre = websiteUtil.getAppConfig(null, "SIGN_PRE");
        try {
            flag = sendSmsUtil.sendSmsDiy(userPhone, content, signPre);
        } catch (Exception e) {
            logger.error("sendSmsByIdAndCode error", e);
        }
        return flag;
    }

    @Override
    public String groupSendSmsByUserPhone(String userPhone, String content){
        String code = "";
        final String signPre = websiteUtil.getAppConfigMarket(null, "SIGN_PRE");
        try {
         List<Dest> destList = new ArrayList<Dest>();
         String []  userPhoneGroup =  userPhone.split(",");
         Dest dest = null;
         String  keys = DateUtil.getDateFormat("yyyyMMddHHmmss");
         for (int i =0;i<userPhoneGroup.length;i++){
             dest = new Dest();
             dest.setDest_id(userPhoneGroup[i]);
             dest.setMission_num(CustConstant.ADVERT + keys);
             destList.add(dest);
         }
            code = sendSmsUtil.sendSmsDiy3(destList,content,keys,signPre);
        } catch (Exception e) {
            logger.error("sendSmsByIdAndCode error", e);
        }
        return code;
    }

    @Override
    public Boolean sendSmsByUserId(Integer id, String content) {
        Boolean flag = false;
        final String signPre = websiteUtil.getAppConfig(null, "SIGN_PRE");
        try {
            UserInfoModel userInfoModel =  userInfoService.findUserInfoById(id);
            if(userInfoModel !=null || StringUtils.isBlank(userInfoModel.getUserPhone())){
                return flag;
            }
            flag = sendSmsUtil.sendSmsDiy(userInfoModel.getUserPhone(), content, signPre);
        } catch (Exception e) {
            logger.error("sendSmsByIdAndCode error", e);
        }
        return flag;
    }

    @Override
    public String getAppTitileByUserId(Integer userId) {
        final String appTitle = websiteUtil.getAppConfig(null, "APP_TITLE");
        return appTitle;
    }

    @Override
    public String getAppTitileByUserPhone(String userPhone) {
        return null;
    }
}
