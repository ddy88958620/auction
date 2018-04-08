package com.trump.auction.cust.api.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.CustApplication;
import com.trump.auction.cust.enums.UserLoginTypeEnum;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author wangbo 2017/12/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest (classes = CustApplication.class)
@Transactional
@Rollback
public class UserInfoStubServiceImplTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void findUserInfoById() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserPhone("15093248786");
        userInfoModel.setRealName("wangbo");
        userInfoModel.setUserType("1");
        userInfoModel.setAddIp("10.168.1.27");
        userInfoModel.setStatus("1");
        userInfoModel = userInfoService.saveUserInfo(userInfoModel);

        userInfoModel = userInfoService.findUserInfoByUserPhone(userInfoModel.getUserPhone());

        userInfoModel = userInfoService.findUserInfoById(userInfoModel.getId());
        assertEquals("15093248786",userInfoModel.getUserPhone());
    }

    @Test
    public void findUserInfoByUserPhone() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserPhone("15093248786");
        userInfoModel.setRealName("wangbo");
        userInfoModel.setUserType("1");
        userInfoModel.setAddIp("10.168.1.27");
        userInfoModel.setStatus("1");
        userInfoModel = userInfoService.saveUserInfo(userInfoModel);

        userInfoModel = userInfoService.findUserInfoByUserPhone(userInfoModel.getUserPhone());
        assertEquals("wangbo",userInfoModel.getRealName());
    }

    @Test
    public void saveUserInfo() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserPhone("15093248786");
        userInfoModel.setRealName("wangbo");
        userInfoModel.setUserType("1");
        userInfoModel.setAddIp("10.168.1.27");
        userInfoModel.setStatus("1");
        userInfoModel.setUserFrom("1");
        UserInfoModel newUser;
        newUser = userInfoService.saveUserInfo(userInfoModel);
        assertNotEquals(null,newUser);
    }

    @Test
    public void findUserInfoByOpenId() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setWxOpenId("1111");
        userInfoModel.setWxNickName("wangbo");
        userInfoModel.setWxHeadImg("http://test");
        userInfoModel.setUserType("1");
        userInfoModel.setStatus("1");
        userInfoModel = userInfoService.saveUserInfo(userInfoModel);

        userInfoModel = userInfoService.findUserInfoByOpenId(userInfoModel.getWxOpenId(), UserLoginTypeEnum.LOGIN_TYPE_WX.getType());
        assertEquals("wangbo",userInfoModel.getWxNickName());
    }

    @Test
    public void updateThirdUserInfo() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setWxOpenId("1111");
        userInfoModel.setWxNickName("wangbo");
        userInfoModel.setWxHeadImg("http://test");
        userInfoModel.setUserType("1");
        userInfoModel.setStatus("1");
        userInfoModel = userInfoService.saveUserInfo(userInfoModel);

        UserInfoModel updateInfo = new UserInfoModel();
        updateInfo.setId(userInfoModel.getId());
        updateInfo.setWxNickName("wangbo1");
        updateInfo.setWxHeadImg("http://test1");
        ServiceResult result = userInfoService.updateThirdUserInfo(updateInfo);
        assertEquals("200",result.getCode());
    }

    @Test
    public void findUserIndexInfoById(){
        UserInfoModel userInfoModel = userInfoService.findUserIndexInfoById(322);
        System.out.println(userInfoModel.toString());
    }


    @Test
    public void updateUserPhoneById(){
        String userPhone = "123123";
        Integer id = 1;
        Integer sun = userInfoService.updateUserPhoneById(userPhone,id);
        System.out.println(sun.toString());
    }
}