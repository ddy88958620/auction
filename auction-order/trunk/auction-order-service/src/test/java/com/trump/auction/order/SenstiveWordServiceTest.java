package com.trump.auction.order;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.OrderApplication;
import com.trump.auction.order.service.AppraisesSenstiveWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: hanliangliang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class SenstiveWordServiceTest {
    @Autowired
    private AppraisesSenstiveWordService appraisesSenstiveWordService;

    @Test
    public void testSenstiveWord(){
        String appraises="副厅长6合彩偷情溜冰毒";
        System.out.println(appraises);
        ServiceResult result =appraisesSenstiveWordService.checkAppraises(appraises);
        System.out.println(result.getCode());
        System.out.println(result.getMsg());
        System.out.println(result.getExt());
    }

}