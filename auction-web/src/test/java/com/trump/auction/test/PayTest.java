package com.trump.auction.test;


import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.activity.model.ActivityShareModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import com.trump.auction.web.service.PayService;
import com.trump.auction.web.util.HandleResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayTest {

	@Autowired
	PayService payService;
	@Autowired
	ActivityShareStubService activityShareStubService;

	@Test
	public void payTest1() {
//		String bathNo = "73406564425269248";
//		String type = "1";
//		HandleResult result = payService.testToPay(bathNo,type);
//		log.info("payTest reslt: {}",result);
	}

	@Test
	public void payTest() {
		ActivityShareModel activityShareModel =activityShareStubService.getActivityByEntrance(1);
		log.info("payTest reslt: {}",activityShareModel);
	}
}
