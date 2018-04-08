package com.trump.auction.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trump.auction.trade.vo.AuctionOrderForListVo;
import com.trump.auction.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.vo.AuctionOrderVo;
import com.trump.auction.trade.vo.BidVo;
import com.trump.auction.trade.vo.ParamVo;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.RedisContants;
import com.trump.auction.web.util.SpringUtils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Controller
@RequestMapping("auctionDetail/")
public class AuctionController extends BaseController {

	@Autowired
	private AuctionOrderStubService auctionOrderStubService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	@Autowired
	private AuctionBidStubService auctionBidStubService;
	
	@Autowired
	private UserProductCollectStubService userProductCollectStubService;
	
	@Autowired
	private UserInfoStubService userInfoStubService;
	
	@Autowired
	private AuctionInfoStubService auctionInfoStubService;
	
	@Autowired
	private AccountInfoStubService accountInfoStubService; 
	
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
	
    @Autowired
    private JedisCluster jedisCluster;
    
    @Autowired
    private OrderAppraisesStubService orderAppraisesStubService;
	
	/**
	 * <p>
	 * Title: 动态数据
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param auctionId
	 * @param auctionProdId
	 */
	@RequestMapping("dynamicData")
	public void dynamicData(HttpServletRequest request,HttpServletResponse response,Integer auctionId,Integer auctionProdId) {
		String userId  = getUserIdFromRedis(request);
		try {
 			ParamVo paramVo = new ParamVo();
			paramVo.setAuctionId(auctionId);
			if(userId != null){
				paramVo.setUserId(Integer.valueOf(userId));
			}
			
			AuctionOrderVo auctionOrderVo = auctionOrderStubService.getDynamicData(paramVo);
			DynamicAuctionInfoVo dynamic = beanMapper.map(auctionOrderVo, DynamicAuctionInfoVo.class);
			Integer status = dynamic.getStatus();
			if(status==2){
				dynamic.setDynamicCountdown(0);
			}
			List<String> lastBidsStr = jedisCluster.lrange(RedisContants.AUCTION_TRADE_SUCCESS,0,0);
			if(lastBidsStr != null && lastBidsStr.size() >0 ){
				for (String last : lastBidsStr) {
					JSONObject jsonObject = JSONObject.parseObject(last);
					dynamic.setLastSuccssBids(jsonObject);
				}
			}
			
			List<BidVo> bidVos = auctionOrderVo.getBidVoList();
			if(bidVos != null &&  bidVos.size() > 0){
				List<BidInfoVo> bidInfoVos = beanMapper.mapAsList(bidVos, BidInfoVo.class);
				dynamic.setBidInfoList(bidInfoVos);
			}
			SpringUtils.renderJson(response, JsonView.build(0, "success", dynamic));	
		} catch (Exception e) {
			log.error("auction-detail-dynamicData error: {}",e);
			SpringUtils.renderJson(response, JsonView.build(0, "网络异常", new DynamicAuctionInfoVo()));	
		}
	}

	/**
	 * <p>
	 * Title: 动态数据
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * @param request
	 * @param response
	 * @param auctionId
	 * @param auctionProdId
	 */
	@RequestMapping("dynamicDataForList")
	public void dynamicDataForList(HttpServletRequest request,HttpServletResponse response,Integer[] auctionIds) {
		String userId  = getUserIdFromRedis(request);
		try {
			List<DynamicAuctionInfoForListVo> dynamics = new ArrayList<>();
			if (auctionIds != null && auctionIds.length>0){
				for(Integer auctionId:auctionIds){
					ParamVo paramVo = new ParamVo();
					paramVo.setAuctionId(auctionId);
					if(userId != null){
						paramVo.setUserId(Integer.valueOf(userId));
					}
					AuctionOrderForListVo auctionOrderForListVo = auctionOrderStubService.getDynamicDataForList(paramVo);
					DynamicAuctionInfoForListVo dynamic = beanMapper.map(auctionOrderForListVo, DynamicAuctionInfoForListVo.class);
					if (dynamic != null) {
						dynamics.add(dynamic);
					}
				}
			}
			SpringUtils.renderJson(response, JsonView.build(0, "success", dynamics));
		} catch (Exception e) {
			log.error("auction-detail-dynamicData error: {}",e);
			SpringUtils.renderJson(response, JsonView.build(0, "网络异常", new DynamicAuctionInfoVo()));
		}
	}
	
	/**
	 * <p>
	 * Title: 基础数据
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 */
	@RequestMapping("baseData")
	public void baseData(HttpServletRequest request,HttpServletResponse response,Integer auctionId,Integer auctionProdId) {
		try {
			auctionBidStubService.updPageViewCount(auctionId);
			String userId = getUserIdFromRedis(request);
			ParamVo paramVo = new ParamVo();
			paramVo.setAuctionId(auctionId);
			AuctionOrderVo auctionOrderVo = auctionOrderStubService.getAuctionBaseData(paramVo);
			if(auctionOrderVo.getReturnPercent() != null){
				auctionOrderVo.setReturnPercent(new BigDecimal(auctionOrderVo.getReturnPercent().intValue()));
			}
			BaseAuctionInfoVo baseAuctionInfoVo = beanMapper.map(auctionOrderVo, BaseAuctionInfoVo.class);
			if(StringUtils.hasText(auctionOrderVo.getAuctionRule())){
				baseAuctionInfoVo.setRuleDetail(auctionOrderVo.getAuctionRule());
			}else{
				baseAuctionInfoVo.setRuleDetail("");
			}
			
			if(baseAuctionInfoVo.getPoundage() != null){
				baseAuctionInfoVo.setPoundage(new BigDecimal(baseAuctionInfoVo.getPoundage().intValue()));
			}
			if(isHitReleaseVersion(request)){
				baseAuctionInfoVo.setReturnPercent(new BigDecimal("100"));
			}
			List<JSONObject> masterImgs = new ArrayList<>();
			List<JSONObject> detailImgs = new ArrayList<>();
			if(auctionOrderVo != null && StringUtils.hasText(auctionOrderVo.getMasterPic())){
				String[] masterPics = auctionOrderVo.getMasterPic().split(",");
				String[] pics = auctionOrderVo.getPicUrls().split(",");
				for (String mast : masterPics) {
					JSONObject json = new JSONObject();
					json.put("img", aliyunOssDomain + mast);
					masterImgs.add(json);
				}
				for (String pic : pics) {
					JSONObject json = new JSONObject();
					json.put("img", aliyunOssDomain + pic);
					detailImgs.add(json);
				}
				baseAuctionInfoVo.setMasterPics(masterImgs);
				baseAuctionInfoVo.setDetailPics(detailImgs);
			}
			boolean isCollectBool = false;
			if(userId != null ){
				isCollectBool = userProductCollectStubService.checkUserProductCollect(Integer.valueOf(userId), auctionProdId, auctionId);
			}
			baseAuctionInfoVo.setIsCollect(isCollectBool==true?1:0);
			baseAuctionInfoVo.setAuctionId(auctionId);
			baseAuctionInfoVo.setAuctionProdId(auctionProdId);
			SpringUtils.renderJson(response, JsonView.build(0, "success", baseAuctionInfoVo));
		} catch (Exception e) {
			log.error("auction-detail-baseData：{}",e);
			SpringUtils.renderJson(response, JsonView.build(1, "网络异常", new BaseAuctionInfoVo()));
		}
	}
	
	
	/**
	 * <p>
	 * Title: 
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param bidCount
	 * @param auctionId
	 * @param auctionProdId
	 */
	@RequestMapping("bid")
	public void bid(HttpServletRequest request,HttpServletResponse response,Integer bidCount,
			Integer auctionId,Integer auctionProdId) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = new HandleResult(false);
		
		try {
			Integer bidType = null;
			bidCount = bidCount==null?1:bidCount;
			if(bidCount == 1){
				bidType = 1;
			}else{
				bidType = 2;
			}
			
			if(bidCount > 100000){
				SpringUtils.renderJson(response, JsonView.build(-1, "最多只能委托10万次", result.getData()));
				return ;
			}
			
			AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(auctionId);
			
			if(auctionInfoModel == null || auctionInfoModel.getStatus() == null){
				SpringUtils.renderJson(response, JsonView.build(-1, "拍品不存在", result.getData()));
				return ;
			}
			
			if(auctionInfoModel.getStatus().equals(2)){
				SpringUtils.renderJson(response, JsonView.build(-1, "拍卖已结束", result.getData()));
				return ;
			}
			
			if(auctionInfoModel.getStatus().equals(3)){
				SpringUtils.renderJson(response, JsonView.build(-1, "拍卖未开始", result.getData()));
				return ;
			}
			
			
			Integer num = auctionInfoModel.getPoundage().intValue() * bidCount;
			
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(Integer.valueOf(userId));
			AccountDto accountDto = accountInfoStubService.getAccountInfo(Integer.valueOf(userId));
			if(num > (accountDto.getAuctionCoin()/100+accountDto.getPresentCoin()/100) ){
				SpringUtils.renderJson(response, JsonView.build(-100, "可用币不足", result.getData()));
				return;
			}
			
			String userName = UserSupport.getBase64UserNameByLoginType(userInfoModel);;
			String headImg = userInfoModel.getHeadImg();
			if(StringUtils.hasText(headImg)){
				headImg = aliyunOssDomain + headImg;
			}else{
				headImg = UserSupport.getHeadImgByLoginType(userInfoModel);
			}
			
			
			BidParam bidParam = new BidParam();
			bidParam.setAddress(userInfoModel.getProvinceName()+userInfoModel.getCityName());
			bidParam.setAuctionId(auctionId);
			bidParam.setAuctionProdId(auctionProdId);
			bidParam.setBidCount(bidCount);
			bidParam.setBidType(bidType);
			bidParam.setHdeaImg(headImg);
			bidParam.setUserId(Integer.valueOf(userId));
			bidParam.setUserName(userName);
			bidParam.setUserPhone(userInfoModel.getUserPhone());
			BidResult bidResult = auctionBidStubService.bidOperation(bidParam);
			log.info("auction-bid-result：     userId: {}, bidResult：{}",userId,bidResult);
			if(bidResult != null && bidResult.isCode()){
				result.setResult(true).setMsg(bidResult.getMsg());
			}
			if(bidResult != null && !bidResult.isCode()){
				result.setCode(1).setMsg(bidResult.getMsg());
			}
			SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), new Object()));
			return;
		} catch (Exception e) {
			log.error("auction/bid：{}",e);
			SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), new Object()));
		}
		
	}
	
	/**
	 * <p>
	 * Title: all-in出价
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param auctionId
	 * @param auctionProdId
	 */
	
	@RequestMapping("all-in")
	public void allIn(HttpServletRequest request,HttpServletResponse response,Integer auctionId,Integer auctionProdId) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = new HandleResult(false);
		AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(auctionId);
		
		if(auctionInfoModel == null || auctionInfoModel.getStatus() == null){
			SpringUtils.renderJson(response, JsonView.build(-1, "拍品不存在", result.getData()));
			return ;
		}
		
		if(auctionInfoModel.getStatus().equals(2)){
			SpringUtils.renderJson(response, JsonView.build(-1, "拍卖已结束", result.getData()));
			return ;
		}
		
		if(auctionInfoModel.getStatus().equals(3)){
			SpringUtils.renderJson(response, JsonView.build(-1, "拍卖未开始", result.getData()));
			return ;
		}
		
		
		Integer poundage = auctionInfoModel.getPoundage().intValue();
		UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(Integer.valueOf(userId));
		AccountDto accountDto = accountInfoStubService.getAccountInfo(Integer.valueOf(userId));
		int totalCoin = accountDto.getAuctionCoin()/100+accountDto.getPresentCoin()/100;
		if(totalCoin <=0 || totalCoin < poundage){
			SpringUtils.renderJson(response, JsonView.build(-100, "可用币不足", result.getData()));
			return;
		}
		
		
		Integer bidCount = totalCoin / poundage;
		Integer bidType = 1;
		if(bidCount == 1){
			bidType = 1;
		}else{
			bidType = 2;
		}
		
		String userName = UserSupport.getBase64UserNameByLoginType(userInfoModel);
		String headImg = userInfoModel.getHeadImg();
		if(StringUtils.hasText(headImg)){
			headImg = aliyunOssDomain + headImg;
		}else{
			headImg = UserSupport.getHeadImgByLoginType(userInfoModel);
		} 
		
		BidParam bidParam = new BidParam();
		bidParam.setAddress(userInfoModel.getProvinceName()+userInfoModel.getCityName());
		bidParam.setAuctionId(auctionId);
		bidParam.setAuctionProdId(auctionProdId);
		bidParam.setBidCount(bidCount);
		bidParam.setBidType(bidType);
		bidParam.setHdeaImg(headImg);
		bidParam.setUserId(Integer.valueOf(userId));
		bidParam.setUserName(userName);
		bidParam.setUserPhone(userInfoModel.getUserPhone());
		BidResult bidResult = auctionBidStubService.bidOperation(bidParam);
		log.info("auction-bid-result：     userId: {}, bidResult：{}",userId,bidResult);
		if(bidResult != null && bidResult.isCode()){
			result.setResult(true).setMsg(bidResult.getMsg());
		}
		if(bidResult != null && !bidResult.isCode()){
			result.setCode(1).setMsg(bidResult.getMsg());
		}
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), new Object()));
		return;
	}
	
	
	/**
	 * <p>
	 * Title: 出价记录
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 */
	@RequestMapping("bidRecord")
	public void bidRecord(HttpServletResponse response,Integer auctionId,Integer pageNum,Integer pageSize) {
		pageNum  = pageNum  == null?  1 : pageNum;
		pageSize = pageSize == null? 100 : pageSize;
		JSONObject json = new JSONObject();
		json.put("pages", 0);
		json.put("pageNum", pageNum);
		json.put("list", new ArrayList<>());
		try {

			ParamVo paramVo = new ParamVo();
			paramVo.setAuctionId(auctionId);
			paramVo.setPageNum(pageNum);
			paramVo.setPageSize(pageSize);
			Paging<BidVo>  page = auctionOrderStubService.findBidPage(paramVo);
			List<BidVo> bidVos = page.getList();
			List<BidInfoVo> bidInfoVos = new ArrayList<>();
			
			if(bidVos != null &&  bidVos.size() > 0){
				bidInfoVos = beanMapper.mapAsList(bidVos, BidInfoVo.class);
			}
			
			json.put("pages", 1);
			json.put("pageNum", pageNum);
			json.put("list", bidInfoVos);
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
			return;
		} catch (Exception e) {
			log.error("bidRecord  error :{}",e);
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
		}
		
		
	}
	
	
	/**
	 * <p>
	 * Title: 往期成交
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 */
	@RequestMapping("pastRecord")
	public void pastRecord(HttpServletResponse response,Integer auctionProdId,Integer pageNum,Integer pageSize) {
		
		pageNum  = pageNum  == null?  1 : pageNum;
		pageSize = pageSize == null? 10 : pageSize;
		try {
			ParamVo paramVo = new ParamVo();
			paramVo.setAuctionProdId(auctionProdId);
			paramVo.setPageNum(pageNum);
			paramVo.setPageSize(pageSize);
			Paging<AuctionOrderVo>  page = auctionOrderStubService.findPastOrder(paramVo);
			
			List<LastAuctionInfoVo> lastVos = beanMapper.mapAsList(page.getList(), LastAuctionInfoVo.class);
			JSONObject json = new JSONObject();
			json.put("pages", page.getPages());
			json.put("pageNum", pageNum);
			json.put("list", lastVos);
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
		} catch (Exception e) {
			log.error("pastRecord-error： {}",e);
			JSONObject json = new JSONObject();
			json.put("pages", 0);
			json.put("pageNum", pageNum);
			json.put("list", new ArrayList<>());
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
		}
		
	}
	
	/**
	 * 晒单记录
	 * 
	 * 
	 */
	/**<p>
	 * Title: 
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param response
	 * @param auctionProdId
	 * @param pageNum
	 * @param pageSize
	 */
	@RequestMapping("appraiseList")
	public void appraiseList(HttpServletResponse response,Integer auctionProdId,Integer pageNum,Integer pageSize) {
		try {
			pageNum = pageNum==null?1:pageNum;
			pageSize = pageSize==null?10:pageSize;
			Paging<OrderAppraisesModel> page = orderAppraisesStubService.queryAppraisesByProductId(String.valueOf(auctionProdId), pageNum, pageSize);
			List<OrderAppraisesModel> list = page.getList();
			List<AppraisesVo> appraisesVos = new ArrayList<>();
			if(list != null && list.size() > 0 ){
				for (OrderAppraisesModel model : list) {
					String appraisesPic = model.getAppraisesPic();
					String[] imgs = appraisesPic.split(",");
					ArrayList<String> imgsist = new ArrayList<>();
					for (String img : imgs) {
						imgsist.add(aliyunOssDomain + img) ;
					}
					AppraisesVo appraisesVo = beanMapper.map(model, AppraisesVo.class);
					String headImg = model.getBuyPic();
					if(model.getBuyPic() != null){
						appraisesVo.setHeadImg(headImg);
					}
					appraisesVo.setImgs(imgsist);
					appraisesVos.add(appraisesVo);
				}
			}
			JSONObject json = new JSONObject();
			json.put("pages", page.getPages());
			json.put("pageNum", pageNum);
			json.put("list", appraisesVos);
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
		} catch (Exception e) {
			log.error("appraiseList error： {}",e);
			JSONObject json = new JSONObject();
			json.put("pages", 0);
			json.put("pageNum", pageNum);
			json.put("list", new ArrayList<>());
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
		}
	}
}
