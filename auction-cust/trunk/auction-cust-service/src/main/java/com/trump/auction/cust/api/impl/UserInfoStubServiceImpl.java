package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class UserInfoStubServiceImpl implements UserInfoStubService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserInfoModel findUserInfoById(Integer userId) {
        return userInfoService.findUserInfoById(userId);
    }

    @Override
    public UserInfoModel findUserIndexInfoById(Integer id) {
        return userInfoService.findUserIndexInfoById(id);
    }

    @Override
    public UserInfoModel findUserInfoByUserPhone(String userPhone) {
        return userInfoService.findUserInfoByUserPhone(userPhone);
    }

    @Override
    public UserInfoModel saveUserInfo(UserInfoModel userInfoModel) {
        return userInfoService.saveUserInfo(userInfoModel);
    }

    @Override
    public UserInfoModel findUserInfoByOpenId(String openId, String type) {
        return userInfoService.findUserInfoByOpenId(openId,type);
    }

    @Override
    public ServiceResult updateThirdUserInfo(UserInfoModel userInfoModel) {
        return userInfoService.updateThirdUserInfo(userInfoModel);
    }

    @Override
    public int updateUserPhoneById(String  userPhone,Integer id) {
        return userInfoService.updateUserPhoneById(userPhone,id);
    }

    @Override
    public ServiceResult updateLoginPasswordById(String loginPassword, Integer id) {
        return userInfoService.updateLoginPasswordById(loginPassword, id);
    }

    @Override
    public ServiceResult updatePayPasswordById(String payPassword, Integer id) {
        return userInfoService.updatePayPasswordById(payPassword, id);
    }

    @Override
    public ServiceResult updateRechargeTypeById(Integer rechargeType, Integer rechargeMoney, Integer id) {
        return userInfoService.updateRechargeTypeById(rechargeType, rechargeMoney, id);
    }

    @Override
    public ServiceResult updateUserNameAndPwdById(String realName, String loginPassword, Integer id) {
        return userInfoService.updateUserNameAndPwdById(realName, loginPassword, id);
    }

    @Override
    public ServiceResult updateLoginTypeById(String loginType, Integer id) {
        return userInfoService.updateLoginTypeById(loginType, id);
    }

    @Override
    public List<UserInfoModel> findFirstRechargeList() {
        return userInfoService.findFirstRechargeList();
    }


    @Override
    public ServiceResult updateAddressById(String provinceName, String cityName, Integer id){
        return userInfoService.updateAddressById(provinceName, cityName, id);
    }

    @Override
    public ServiceResult updateUserInfo(UserInfoModel userInfoModel) {
        return userInfoService.updateUserInfo(userInfoModel);
    }

    @Override
    public ServiceResult updateAppInfo(String appInfo, Integer userId) {
        return userInfoService.updateAppInfo(appInfo,userId);
    }

    @Override
    public ServiceResult updateUserInfoById(UserInfoModel userInfoModel) {
        return userInfoService.updateUserInfoById(userInfoModel);
    }
}
