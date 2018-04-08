package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.sun.org.apache.regexp.internal.RE;
import com.trump.auction.cust.CustApplication;
import com.trump.auction.cust.domain.UserLoginRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustApplication.class)
public class UseLoginRecordServiceTest {

    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @Test
    public void testUserPhoneRecord(){

       /* UserLoginRecord record=new UserLoginRecord();
        record.setUserId(1137L);
        record.setLoginTime(new Date());
        record.setLoginIp("192.168.0.0.1");
        record.setAddress("中国上海");
        record.setLoginDevices("IOS-iphone6s");
       ServiceResult result= userLoginRecordService.loginRecord(record);
       System.out.println("==================");
       System.out.println(result.getCode());
       System.out.println(result.getMsg());
       System.out.println("==================");*/

        //List<UserLoginRecord> list=userLoginRecordService.selectRecordByUserId(1137);

        System.out.println("==================");
        /*  System.out.println(list.size());
        for (UserLoginRecord record:list) {
            System.out.println(record.toString());
        }*/
        System.out.println("==================");
        System.out.println(userLoginRecordService.countRecordByUserId(1137));
        System.out.println("==================");
    }


}
