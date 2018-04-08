package com.trump.auction.web.service;

import javax.servlet.http.HttpServletRequest;

import com.trump.auction.web.util.HandleResult;

/**
 * Created by songruihuan on 2017/12/21.
 */
public interface RechargeService {

	HandleResult prePay(HttpServletRequest request,Integer userId, Integer money, String payType);

}
