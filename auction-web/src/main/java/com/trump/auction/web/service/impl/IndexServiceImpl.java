package com.trump.auction.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.trump.auction.trade.api.LabelStubService;
import com.trump.auction.trade.vo.AuctionOrderForListVo;
import com.trump.auction.trade.vo.LabelVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.cust.model.UserProductCollectModel;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.vo.AuctionOrderVo;
import com.trump.auction.trade.vo.ParamVo;
import com.trump.auction.web.service.IndexService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.RedisContants;
import com.trump.auction.web.vo.HotAuctionInfoVo;
import com.trump.auction.web.vo.UserProductCollectVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * Created by songruihuan on 2017/12/21.
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);
    
    @Autowired
    JedisCluster jedisCluster;
    
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
    
    @Autowired
    private UserProductCollectStubService userProductCollectStubService;
    
    @Autowired
    private BeanMapper beanMapper;
    
    @Autowired
    private AuctionInfoStubService auctionInfoStubService;
    
    @Autowired
    private AuctionOrderStubService auctionOrderStubService;

	@Autowired
	private LabelStubService labelStubService;
    
	@Override
	public HandleResult getIndexData(String userId, Integer pageNum, Integer pageSize, Integer type) {
		HandleResult handleResult = new HandleResult(true);
		
		List<Integer> typeList = Arrays.asList(1,2,3);
		if(!typeList.contains(type)){
			return handleResult.setCode(1).setMsg("参数不正确");
		}
		pageNum = pageNum == null? 1 : pageNum;
		pageSize =pageSize== null? 10: pageSize; 
		
		
		JSONObject json = new JSONObject();
		json.put("pageNum", pageNum);
		json.put("list", new ArrayList<>());
		
		List<HotAuctionInfoVo> hotList = new ArrayList<>();
		
		try {
			if(type.equals(1)){
		        //热拍中
		        String hotAuctionStr = jedisCluster.get(RedisContants.HOT_AUCTION_PRODS);
		        if (StringUtils.hasText(hotAuctionStr)) {
		        	hotList = JSONArray.parseArray(hotAuctionStr,HotAuctionInfoVo.class);
		        	for (HotAuctionInfoVo infoVo : hotList) {
	        			if(userId != null){
		        			boolean isCollectBool = userProductCollectStubService.checkUserProductCollect(Integer.valueOf(userId), infoVo.getAuctionProdId(),infoVo.getAuctionId());
		        			Integer isCollect = isCollectBool==true?1:0;
		        			infoVo.setIsCollect(isCollect);
	        			}
	        			infoVo.setPreviewPic(aliyunOssDomain+infoVo.getPreviewPic());
	        			/*AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(infoVo.getAuctionId());
	        			infoVo.setProductName(auctionInfoModel.getProductName());
	        			infoVo.setPreviewPic(aliyunOssDomain+infoVo.getPreviewPic());
	        			infoVo.setBidPrice(auctionInfoModel.getFinalPrice());
	        			Integer status = auctionInfoModel.getStatus();
	        			infoVo.setStatus(auctionInfoModel.getStatus());
	        			if(status.equals(1)){
	        				infoVo.setBidPrice(auctionInfoModel.getIncreasePrice().multiply(
	        						new BigDecimal(auctionInfoModel.getTotalBidCount())).add(auctionInfoModel.getStartPrice()));
	        			}*/
						setExtensionData(infoVo);
					}
	        		json.put("pages", 1);
	        		json.put("list", hotList);
		        }
			}
			
			//我在拍
			if(type.equals(2)){
				if(userId == null){
					return handleResult.setCode(-2).setMsg("请先登录");
				}
				ParamVo paramVo = new ParamVo();
				paramVo.setUserId(Integer.valueOf(userId));
				paramVo.setAuctionStatus(1);
				paramVo.setPageNum(pageNum);
				paramVo.setPageSize(pageSize);
				Paging<AuctionOrderVo>  page =  auctionOrderStubService.findAuctionOrder(paramVo);
				hotList = beanMapper.mapAsList(page.getList(), HotAuctionInfoVo.class);
				
				if(hotList != null && hotList.size() > 0 ){
					for (HotAuctionInfoVo vo : hotList) {
						if(userId != null){
		        			boolean isCollectBool = userProductCollectStubService.checkUserProductCollect(Integer.valueOf(userId), vo.getAuctionProdId(),vo.getAuctionId());
		        			Integer isCollect = isCollectBool==true?1:0;
		        			vo.setIsCollect(isCollect);
	        			}
	        			AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(vo.getAuctionId());
	        			vo.setProductName(auctionInfoModel.getProductName());
	        			vo.setPreviewPic(aliyunOssDomain+vo.getPreviewPic());
	        			vo.setBidPrice(auctionInfoModel.getFinalPrice());
	        			vo.setStatus(auctionInfoModel.getStatus());
	        			Integer status = auctionInfoModel.getStatus();
	        			vo.setStatus(auctionInfoModel.getStatus());
	        			if(status.equals(1)){
	        				vo.setBidPrice(auctionInfoModel.getIncreasePrice().multiply(
	        						new BigDecimal(auctionInfoModel.getTotalBidCount())).add(auctionInfoModel.getStartPrice()));
	        			}
						setExtensionData(vo);
					}
				}
				json.put("pages", page.getPages());
				json.put("list", hotList);
			}
			
			//我的收藏
			if(type.equals(3)){
				if(userId == null){
					return handleResult.setCode(-2).setMsg("请先登录");
				}
				UserProductCollectModel userProductCollect = new UserProductCollectModel();
				userProductCollect.setUserId(Integer.valueOf(userId));
				Paging<UserProductCollectModel> paging =  userProductCollectStubService.findUserProductCollectPage(userProductCollect, pageNum, pageSize);
				List<UserProductCollectVo> vos = new ArrayList<>();
				vos = beanMapper.mapAsList(paging.getList(), UserProductCollectVo.class);
				if(vos != null && vos.size() > 0 ){
					for (UserProductCollectVo vo : vos) {
						HotAuctionInfoVo hotAuctionInfoVo = new HotAuctionInfoVo();
						Integer auctionProdId = vo.getProductId();
						hotAuctionInfoVo.setAuctionId(vo.getPeriodsId());
						hotAuctionInfoVo.setAuctionProdId(auctionProdId);
						AuctionInfoModel model = auctionInfoStubService.getAuctionInfoById(vo.getPeriodsId());
						// 获取最新期数Id
						int lastAuctionId = auctionInfoStubService.findAuctionById(auctionProdId);
						if(model != null){
							hotAuctionInfoVo.setLastAuctionId(lastAuctionId);
							hotAuctionInfoVo.setProductName(model.getProductName());
							hotAuctionInfoVo.setPreviewPic(aliyunOssDomain + model.getPreviewPic());
							hotAuctionInfoVo.setBidPrice(model.getFinalPrice());
							hotAuctionInfoVo.setStatus(model.getStatus());
							Integer status = model.getStatus();
							hotAuctionInfoVo.setStatus(model.getStatus());
		        			if(status.equals(1)){
		        				hotAuctionInfoVo.setBidPrice(model.getIncreasePrice().multiply(
		        						new BigDecimal(model.getTotalBidCount())).add(model.getStartPrice()));
		        			}
						}
						hotAuctionInfoVo.setIsCollect(1);

						setExtensionData(hotAuctionInfoVo);

						hotList.add(hotAuctionInfoVo);
					}
				}
				json.put("pages", paging.getPages());
				json.put("list", hotList);
			}
			
		} catch (Exception e) {
			log.error("getIndexData error： {}",e);
		}
	
		return handleResult.setData(json);
	}

	private void setExtensionData(HotAuctionInfoVo vo) {
		ParamVo paramVoForList = new ParamVo();
		paramVoForList.setAuctionId(vo.getAuctionId());
		AuctionOrderForListVo dynamicDataForList = auctionOrderStubService.getDynamicDataForList(paramVoForList);
		vo.setBidPrice(dynamicDataForList.getBidPrice());
		vo.setDynamicCountdown(dynamicDataForList.getDynamicCountdown());
		List<LabelVo> labelVos = labelStubService.findByProductId(vo.getAuctionProdId());
		for(LabelVo labelVo:labelVos){
			labelVo.setLabelPic(aliyunOssDomain + labelVo.getLabelPic());
		}
		vo.setLabelVos(labelVos);
	}
	@Override
	public HandleResult getIndexCommonData() {
		HandleResult result = new HandleResult(true);
		List<JSONObject> banner = new ArrayList<>();
		List<JSONObject> icons = new ArrayList<>();
		List<JSONObject> lastBids = new ArrayList<>();
		try {
			jedisCluster.get("");
			String bannerStr = jedisCluster.get(RedisContants.INDEX_BANNER);
			if(StringUtils.hasText(bannerStr)){
				bannerStr = JSONObject.parseObject(bannerStr).getString("banner");
		        banner = parseJsonStr(banner, bannerStr);
			}
			
			String iconsStr = jedisCluster.get(RedisContants.INDEX_ICON);
			if(StringUtils.hasText(iconsStr)){
				iconsStr = JSONObject.parseObject(iconsStr).getString("icon");
				icons = parseJsonStr(icons, iconsStr);
			}
			List<String> lastBidsStr = jedisCluster.lrange(RedisContants.AUCTION_TRADE_SUCCESS,0,9);
			if(lastBidsStr != null && lastBidsStr.size() >0 ){
				for (String last : lastBidsStr) {
					JSONObject jsonObject = JSONObject.parseObject(last);
					lastBids.add(jsonObject);
				}
			}
		} catch (Exception e) {
			log.error("getIndexCommonData error: {}",e);
		}
        
        JSONObject json = new JSONObject();
	    json.put("banner",banner);
	    json.put("icons",icons);
	    json.put("lastBids",lastBids);
		return result.setData(json);
	}

	private List<JSONObject> parseJsonStr(List<JSONObject> list, String jsonStr) {
		if (StringUtils.hasText(jsonStr)) {
            list = JSONArray.parseArray(jsonStr, JSONObject.class);
            if(list != null && list.size() > 0){
            	Iterator<JSONObject> iterator = list.iterator();
            	while (iterator.hasNext()) {
            		JSONObject object = iterator.next();
            		if(object.getString("displayType").equals("2")){
            			iterator.remove();
            		}
				}
            }
            for (JSONObject obj : list) {
            	obj.remove("displayType");
				obj.put("img", aliyunOssDomain+obj.getString("img"));
			}
        }
		return list;
	}
}
