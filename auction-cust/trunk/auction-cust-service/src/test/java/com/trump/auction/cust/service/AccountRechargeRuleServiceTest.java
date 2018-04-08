package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.CustApplication;
import com.trump.auction.cust.domain.AccountRechargeRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Author: hanliangliang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustApplication.class)
public class AccountRechargeRuleServiceTest {

    @Autowired
    AccountRechargeRuleService accountRechargeRuleService;

    @Test
    public void findEnableRule() throws Exception {
        System.out.print("rule:111");
        AccountRechargeRule rule = accountRechargeRuleService.findEnableRule();
        System.out.print("rule:"+rule.toString());
    }

   @Autowired
   private  UserSenstiveWordService userSenstiveWordService;

   @Test
    public void testSenstiveWord(){
       String userName=null;
       System.out.println(userName);
       ServiceResult result =userSenstiveWordService.checkNickName(userName);
       System.out.println(result.getCode());
       System.out.println(result.getMsg());
       System.out.println(result.getExt());
   }
}