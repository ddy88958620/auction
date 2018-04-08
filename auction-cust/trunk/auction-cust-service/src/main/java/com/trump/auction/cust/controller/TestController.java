package com.trump.auction.cust.controller;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
public class TestController {
	private final static Logger logger = LoggerFactory
			.getLogger(TestController.class);

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserInfoService userInfoService;

	// @RequestMapping(value = { "/", "/index" })
	@RequestMapping(value = "/tttt", method = RequestMethod.GET)
	public String getccc() {

		String userPhone = "123123";
		Integer id = 1;
		UserInfoModel user = userInfoService.findUserIndexInfoById(id);
		Integer sun = userInfoService.updateUserPhoneById(userPhone,id);

		return String.valueOf(sun);
	}

}
