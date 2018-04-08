package com.trump.auction.web.service;

import javax.servlet.http.HttpServletRequest;

import com.trump.auction.web.util.HandleResult;

/**
 * Created by songruihuan on 2017/12/21.
 */
public interface PersonalService {

   HandleResult getMyAccount(Integer userId, Integer accountType, Integer listType,String createTime,Integer pageNum,Integer pageSize,HttpServletRequest request) ;
   HandleResult getPointsExchangeList(Integer userId, Integer accountType) ;
   HandleResult getUserAccountRecordById(Integer id);
   HandleResult exchangePoints(Integer userId, Integer presentCoin) ;
   HandleResult getAppraisesByUserId(String userId,Integer pageNum,Integer pageSize,HttpServletRequest request);
   HandleResult queryOrderAppraises(String id,HttpServletRequest request);
   int getAuctionCoinByUserId(Integer userId, Integer type);
   HandleResult getShopCoinsList(Integer userId, Integer listType,Integer pageNum,Integer pageSize,HttpServletRequest request);
   HandleResult getAccountInfo(Integer userId,HttpServletRequest request);

}
