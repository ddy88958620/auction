package com.trump.auction.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoDetailStubService;
import com.trump.auction.account.api.AccountInfoRecordStubService;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.dto.PointsExchangePresentDto;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.model.AccountInfoDetailModel;
import com.trump.auction.account.model.AccountInfoRecordModel;
import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.enums.UserLoginTypeEnum;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;
import com.trump.auction.web.service.PersonalService;
import com.trump.auction.web.util.Base64Utils;
import com.trump.auction.web.util.EnumEntrance;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.HttpHelper;
import com.trump.auction.web.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songruihuan on 2017/12/21.
 */
@Service
public class PersonalServiceImpl implements PersonalService {
    Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);
    @Autowired
    private AccountInfoRecordStubService accountRecordStubService;

    @Autowired
    private AccountInfoStubService accountInfoStubService;

    @Autowired
    private AccountInfoDetailStubService accountInfoDetailStubService;

    @Autowired
    private UserInfoStubService userInfoStubService;

    @Autowired
    private OrderAppraisesStubService orderAppraisesStubService;
    @Autowired
    private AccountInfoRecordStubService accountInfoRecordStubService;
    @Autowired
    private OrderInfoStubService orderInfoStubService;
    @Autowired
    private ActivityShareStubService activityShareStubService;

    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private JedisCluster jedisCluster;

    @Value("${static.resources.domain}")
    private String staticResourcesDomain;
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;

    @Override
    public HandleResult getMyAccount(Integer userId, Integer accountType, Integer listType, String createTime,Integer pageNum,Integer pageSize,HttpServletRequest request) {
        HandleResult handleResult = new HandleResult(0,"我的财产接口,返回成功");
        try {
            AccountInfoRecordModel accountRecordModel = new AccountInfoRecordModel();
            accountRecordModel.setUserId(userId);
            accountRecordModel.setAccountType(accountType);
            if(createTime==null){

            }else{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                accountRecordModel.setCreateTime(sdf.parse(createTime));
            }
            pageNum=pageNum==null?1:pageNum;
            accountRecordModel.setPageNum(pageNum);
            pageSize=pageSize==null?1:pageSize;
            accountRecordModel.setPageSize(pageSize);

            Paging<AccountInfoRecordModel> pageList=null;
            if(null!=accountType && accountType==3){
                pageList =  accountInfoRecordStubService.getPointsRecordList(userId,pageNum,pageSize);
            }else{
                pageList =  accountRecordStubService.getAccountInfoRecordList(accountRecordModel);
            }
            List<AccountInfoVo> list = beanMapper.mapAsList(pageList.getList(), AccountInfoVo.class);
            for(AccountInfoVo e:list){
                e.setProductImage(request.getSession().getAttribute("aliyunOssDomain")+e.getProductImage());
            }
            JSONObject json = new JSONObject();
            json.put("pages", pageList.getPages());
            json.put("pageNum", pageNum);
            json.put("list", list);
            AccountDto accountDto =  accountInfoStubService.getAccountInfo(userId);
            AccountVo accountVo = new AccountVo();
            accountVo.setAuctionCoin(new BigDecimal(accountDto.getAuctionCoin()).divide(new BigDecimal(100)).toString());
            accountVo.setPresentCoin(new BigDecimal(accountDto.getPresentCoin()).divide(new BigDecimal(100)).toString());
            accountVo.setShoppingCoin(new BigDecimal(accountDto.getShoppingCoin()).divide(new BigDecimal(100)).toString());
            json.put("auctionCoin", accountVo.getAuctionCoin());
            json.put("presentCoin", accountVo.getPresentCoin());
            json.put("shoppingCoin", accountVo.getShoppingCoin());
            String appVersion = request.getHeader("appVersion");
            String appMarket = request.getHeader("appMarket");
            json.put("myPropertyUrl", staticResourcesDomain+"myProperty-illustration?appVersion="+appVersion+"&appMarket="+appMarket);//我的财产说明url

            handleResult.setData(json);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("我的财产接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult getPointsExchangeList(Integer userId, Integer accountType)  {
        HandleResult handleResult = new HandleResult(0,"积分接口,返回成功");
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            List<PointsExchangePresentDto> pointsExchangePresentDtoList = accountInfoStubService.getPointsExchangeList();
            int myPoints = accountInfoStubService.getAuctionCoinByUserId(userId,accountType);

            for(PointsExchangePresentDto exchangePresentDto:pointsExchangePresentDtoList){
                exchangePresentDto.setPoints(Integer.valueOf(new BigDecimal(exchangePresentDto.getPoints()).divide(new BigDecimal(100)).toString()));
            }
            map.put("pointsExchangePresentDtoList",pointsExchangePresentDtoList);
            map.put("myPoints",new BigDecimal(myPoints).divide(new BigDecimal(100)).toString());
            map.put("PointsDesc","每日仅兑换"+accountInfoStubService.getPointsExchangeTimesLimit()+"次");
            handleResult.setData(map);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("积分接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult getUserAccountRecordById(Integer id){
        HandleResult handleResult = new HandleResult(0,"财产详情接口,返回成功");
        try {
            AccountInfoRecordModel accountRecordModel = accountRecordStubService.getAccountInfoRecordById(id);
            AccountInfoRecordVo accountInfoRecordVo = beanMapper.map(accountRecordModel, AccountInfoRecordVo.class);
          /* if(accountInfoRecordVo.getAccountType().intValue()==4){
               accountInfoRecordVo.setViewTransactionCoin(new BigDecimal(accountInfoRecordVo.getViewTransactionCoin()).divide(new BigDecimal(100)).setScale(2).toString());
           }else{
               accountInfoRecordVo.setViewTransactionCoin(new BigDecimal(accountInfoRecordVo.getViewTransactionCoin()).divide(new BigDecimal(100)).toString());
           }*/
          //此处使用的是productName
            accountInfoRecordVo.setRemark(accountRecordModel.getProductName());
            handleResult.setData(accountInfoRecordVo);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("财产详情接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult exchangePoints(Integer userId, Integer presentCoin){
        HandleResult handleResult = null;
        try {
            ServiceResult serviceResult = accountInfoStubService.exchangePoints(userId,presentCoin);
            if(serviceResult.isSuccessed()){
                handleResult = new HandleResult(0,"兑换积分接口,返回成功");
            }else{
                handleResult = new HandleResult(1,serviceResult.getMsg());
            }
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("兑换失败");
            logger.error("兑换积分接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult getAppraisesByUserId(String userId,Integer pageNum,Integer pageSize,HttpServletRequest request) {
        HandleResult handleResult = new HandleResult(0,"我的晒单接口,返回成功");
        try {
            Paging<OrderAppraisesModel> pageList = orderAppraisesStubService.getAppraisesByUserId(userId);
            List<OrderAppraisesVo> list = beanMapper.mapAsList(pageList.getList(), OrderAppraisesVo.class);

            for(OrderAppraisesVo e:list){
                OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
                orderInfoQuery.setOrderId(e.getOrderId());
                OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrder(orderInfoQuery);
                e.setProductName(orderInfoModel.getProductName());
                e.setProductPic(request.getSession().getAttribute("aliyunOssDomain")+orderInfoModel.getProductPic());
            }

            JSONObject json = new JSONObject();
            pageNum=pageNum==null?1:pageNum;
            pageSize=pageSize==null?1:pageList.getPages();
            json.put("pages", pageSize);
            json.put("pageNum", pageNum);
            json.put("list", list);

            handleResult.setData(json);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("我的晒单接口返回失败: {}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult queryOrderAppraises(String id,HttpServletRequest request) {
        HandleResult  handleResult = new HandleResult(0,"晒单详情页接口,返回成功");
        try {
            OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.queryOrderAppraises(id);
            OrderAppraisesVo orderAppraisesVo = beanMapper.map(orderAppraisesModel, OrderAppraisesVo.class);
            String[] arr = null;
            if(StringUtils.hasText(orderAppraisesVo.getAppraisesPic())){
            	 arr = orderAppraisesVo.getAppraisesPic().split(",");
                 for(int i=0;i<=arr.length-1;i++){
                     arr[i]=request.getSession().getAttribute("aliyunOssDomain") +arr[i];
                 }
            }
            orderAppraisesVo.setPicArr(arr);
            handleResult.setData(orderAppraisesVo);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
        	logger.error("晒单详情页接口异常:{}",e);
        }
        return handleResult;
    }

    @Override
    public int getAuctionCoinByUserId(Integer userId, Integer type) {
        return  accountInfoStubService.getAuctionCoinByUserId(userId,type);
    }

    @Override
    public HandleResult getShopCoinsList(Integer userId, Integer listType, Integer pageNum, Integer pageSize,HttpServletRequest request) {
        HandleResult handleResult = new HandleResult(0,"我的开心币接口,返回成功");
        try {
            pageNum=pageNum==null?1:pageNum;
            pageSize=pageSize==null?1:pageSize;
            listType=listType==null?1:listType;
            Paging<AccountInfoDetailModel> pageList= accountInfoDetailStubService.getAccountInfoDetailList(userId,pageNum,pageSize,listType);
            int shoppingCoins = accountInfoStubService.getAuctionCoinByUserId(userId, EnumAccountType.BUY_COIN.getKey());

            List<AccountShoppingCoinVo> list = beanMapper.mapAsList(pageList.getList(), AccountShoppingCoinVo.class);
            for(AccountShoppingCoinVo e:list){
                e.setProductImage(request.getSession().getAttribute("aliyunOssDomain")+e.getProductImage());
                e.setCoin(new BigDecimal(e.getCoin()).divide(new BigDecimal(100)).setScale(0).toString());
                if(null==e.getProductName()){
                    e.setProductName("");
                }
            }
            JSONObject json = new JSONObject();
            json.put("pages", pageList.getPages());
            json.put("pageNum", pageNum);
            json.put("list", list);
            if(shoppingCoins==0){
                json.put("shoppingCoins", shoppingCoins / 100);
            }else{
                json.put("shoppingCoins", shoppingCoins / 100);
            }
            String appVersion = request.getHeader("appVersion");
            String appMarket = request.getHeader("appMarket");
            //开心币使用规则url
            json.put("shoppingMoneyUrl", staticResourcesDomain+"shoppingMoney-illustration?appVersion="+appVersion+"&appMarket="+appMarket);

            handleResult.setData(json);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("我的开心币接口异常:{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult getAccountInfo(Integer userId,HttpServletRequest request) {
        HandleResult handleResult = new HandleResult(0,"我的页面接口,返回成功");
        try {
            JSONObject json = new JSONObject();
            AccountDto accountDto =  accountInfoStubService.getAccountInfo(userId);
            AccountVo accountVo = new AccountVo();
            accountVo.setAuctionCoin(new BigDecimal(accountDto.getAuctionCoin()).divide(new BigDecimal(100)).toString());
            accountVo.setPoints(new BigDecimal(accountDto.getPoints()).divide(new BigDecimal(100)).toString());
            accountVo.setPresentCoin(new BigDecimal(accountDto.getPresentCoin()).divide(new BigDecimal(100)).toString());
            accountVo.setFreezeCoin(new BigDecimal(accountDto.getFreezeCoin()).divide(new BigDecimal(100)).toString());
           if(accountDto.getShoppingCoin().intValue()==0){
               accountVo.setShoppingCoin(new BigDecimal(accountDto.getShoppingCoin()).divide(new BigDecimal(100)).toString());
           }else{
               accountVo.setShoppingCoin(new BigDecimal(accountDto.getShoppingCoin()).divide(new BigDecimal(100)).setScale(0).toString());
           }
            json.put("accountDto",accountVo);
            json.put("userId",userId);
           UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(userId);

           //获取用户信息
            buildUserInfo(request,json,userInfoModel);

            Map<String, String> keys = jedisCluster.hgetAll("WEBSITE");
            String qqgroup = keys.get("cs_qqgroup");
            JSONObject jsonObject = new  JSONObject();
            if(StringUtils.hasText(qqgroup)){
                jsonObject =  JSONObject.parseObject(qqgroup);
                json.put("qqNumber", jsonObject.get("qqNumber"));// qq群号
                json.put("qqGroupKey", jsonObject.get("qqKey"));// qq群号
            }
            json.put("helpUrl",staticResourcesDomain+"helpCenter");
            json.put("getAppraisesUrl",staticResourcesDomain+"getAppraises");

            //分享活动信息
            ActivityShareModel activity = activityShareStubService.getActivityByEntrance(EnumEntrance.MY_INFO.getCode());

            if (null!=activity&&null!=activity.getPicUrl()){
                activity.setPicUrl(aliyunOssDomain+activity.getPicUrl());
            }

            json.put("activity",activity);
            handleResult.setData(json);
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("我的页面接口异常:{}",e);
        }
        return handleResult;
    }

    /**
     * 获取用户信息
     * @param request
     * @param json
     * @param userInfoModel
     */
    private void buildUserInfo(HttpServletRequest request,JSONObject json,UserInfoModel userInfoModel){
        json.put("userPhone", userInfoModel.getUserPhone());
        json.put("iconUrl", userInfoModel.getHeadImg());

        boolean res = isHitCompatibleVersion(request);
        String targetKey = "userName";
        if(res){
    		targetKey = "userPhone";
        }
        String loginType = userInfoModel.getLoginType();
        if (UserLoginTypeEnum.LOGIN_TYPE_QQ.getType().equals(loginType)) {
            json.put(targetKey, Base64Utils.decodeStr(userInfoModel.getQqNickName()));
            json.put("iconUrl", userInfoModel.getQqHeadImg());
        }

        if (UserLoginTypeEnum.LOGIN_TYPE_WX.getType().equals(loginType)) {
            json.put(targetKey, Base64Utils.decodeStr(userInfoModel.getWxNickName()));
            json.put("iconUrl", userInfoModel.getWxHeadImg());
        }

        if (UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType().equals(loginType)) {
            json.put(targetKey, userInfoModel.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            json.put("iconUrl", HttpHelper.getRequestPath(request) + "/img/default-head-img.png");
        }

        if (null!=userInfoModel.getNickName()&&!StringUtils.isEmpty(userInfoModel.getNickName())){
            json.put("userName", Base64Utils.decodeStr(userInfoModel.getNickName()));
        }

        if (null!=userInfoModel.getHeadImg()&&!StringUtils.isEmpty(userInfoModel.getHeadImg())){
            json.put("iconUrl", aliyunOssDomain +userInfoModel.getHeadImg());
        }
    }
    
    private boolean isHitCompatibleVersion(HttpServletRequest request){
    	String clientType = request.getHeader("clientType");
        String version =  request.getHeader("appVersion");
        if("android".equals(clientType) && "1.0.0".equals(version)){
        	return true;
        }
        if("ios".equals(clientType) && "1.0".equals(version)){
        	return true;
        }
        return false;
    }

}
