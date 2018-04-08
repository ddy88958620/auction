package com.trump.auction.web.service;

import com.trump.auction.web.util.HandleResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by songruihuan on 2017/12/21.
 */
public interface BindPhoneService {

	HandleResult handleBindPhoneProcess(Integer userId,String phone,String smsCode,String type);

	HandleResult handleCheckPhone(HttpServletRequest request, HttpServletResponse response,Integer valueOf, String phone,String smsCode);
}
