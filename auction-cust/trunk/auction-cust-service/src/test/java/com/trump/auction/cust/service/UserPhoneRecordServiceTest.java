package com.trump.auction.cust.service;

import com.trump.auction.cust.CustApplication;
import com.trump.auction.cust.domain.UserPhoneRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustApplication.class)
public class UserPhoneRecordServiceTest {

    @Autowired
    private UserPhoneRecordService userPhoneRecordStubService;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testUserPhoneRecord(){
       // userPhoneRecordStubService.insertUserPhoneRecord("15262055016","17621481905",1137);
       //userInfoService.updateUserPhoneById("15262055016",1137)  ;
       // List<UserPhoneRecord> list =userPhoneRecordStubService.selectAll();
       //List<UserPhoneRecord> list =userPhoneRecordStubService.selectRecordById(1137);
        int count=userInfoService.updateUserPhoneById("15262055016",1137);
        System.out.println("============");
        /*for ( UserPhoneRecord recoprd:list) {
            System.out.println(recoprd.toString());
            System.out.println(recoprd.getAddTime());
        }*/
        System.out.println("============");
    }


}
