package com.trump.auction.pals.pay;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;
import com.trump.auction.pals.service.AlipayService;
import com.trump.auction.pals.service.WeChatPayService;


/**
 * Created by Administrator on 2017/12/22.
 */

@RunWith(SpringRunner.class)
@SpringBootTest

public class PayApplicationTest {
    Logger logger = LoggerFactory.getLogger(PayApplicationTest.class);



    @Autowired
    WeChatPayService WeChatPayService;

    @Autowired
    AlipayService alipayService;

    @Test
    public void Test(){
        AlipayQueryRequest queryRequest = new AlipayQueryRequest();
        queryRequest.setBatchNo("AH0130142615065584");
        AliPayQueryResponse response = alipayService.toAlipayQuery(queryRequest);
        logger.info("{}" , response);
    }
}