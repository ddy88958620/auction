package com.trump.auction.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.api.LabelStubService;
import com.trump.auction.trade.vo.AuctionOrderForListVo;
import com.trump.auction.trade.vo.LabelVo;
import com.trump.auction.trade.vo.ParamVo;
import com.trump.auction.web.vo.HotAuctionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.SpringUtils;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.model.AuctionInfoQuery;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.RedisContants;
import com.trump.auction.web.vo.AuctionInfoVo;
import com.trump.auction.web.vo.LastAuctionInfoVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Controller
@RequestMapping("auction/")
public class AuctionInfoController extends BaseController {

	@Autowired
	private AuctionInfoStubService auctionInfoStubService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	@Autowired
	private UserProductCollectStubService userProductCollectStubService; 
	
	@Autowired
	private JedisCluster jedisCluster;
	
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;

	@Autowired
	private AuctionOrderStubService auctionOrderStubService;

	@Autowired
	private LabelStubService labelStubService;

	/**
	 * 商品分类
	 * @param response
	 */
	@RequestMapping("navigation")
	public void nav(HttpServletResponse response) {
		String str = jedisCluster.get(RedisContants.AUCTION_TRADE_CLASSIFY);
		JSONArray array = null;
		if(null!=str){
			array =JSONArray.parseArray(str);
		}else{
			array = new JSONArray();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classify",array);
		SpringUtils.renderJson(response, JsonView.build(0, "success", jsonObject));
	}
	
	/**
	 * <p>
	 * Title: 拍品列表
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param classifyId 不传就是全部
	 * @param pageNum
	 * @param pageSize
	 */
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response,Integer classifyId,Integer pageNum,Integer pageSize) {
		String userId = getUserIdFromRedis(request);
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null? 10 : pageSize;
		AuctionInfoQuery  auctionQuery = new AuctionInfoQuery(); 
		if(StringUtils.hasText(classifyId + "")){
			auctionQuery.setClassifyId(classifyId);
		}
		if(classifyId==null || classifyId.equals(0)){
			auctionQuery.setClassifyId(null);
		}
		Paging<AuctionInfoModel> page = new Paging<>();
		List<AuctionInfoVo> vos = new ArrayList<>();
		try {
			page =  auctionInfoStubService.queryAuctionInfoByClassify(auctionQuery, pageNum, pageSize);
			List<AuctionInfoModel> models = page.getList();
			vos = beanMapper.mapAsList(models, AuctionInfoVo.class);
			if(StringUtils.hasText(userId)){
				if(vos != null && vos.size() > 0){
					for (AuctionInfoVo vo : vos) {
						boolean isCollectBool = userProductCollectStubService.checkUserProductCollect(Integer.valueOf(userId),
								vo.getAuctionProdId(), vo.getAuctionId());
						Integer isCollect = isCollectBool==true?1:0;
						vo.setIsCollect(isCollect);
						setExtensionData(vo);
					}
				}
			}
		} catch (Exception e) {
			log.error("auction/list error",e);
		}
		vos = vos==null?new ArrayList<AuctionInfoVo>():vos;
		SpringUtils.renderJson(response, JsonView.buildPage(page.getPages(), pageNum, vos));
	}

	private void setExtensionData(AuctionInfoVo vo) {
		ParamVo paramVoForList = new ParamVo();
		paramVoForList.setAuctionId(vo.getAuctionId());
		AuctionOrderForListVo dynamicDataForList = auctionOrderStubService.getDynamicDataForList(paramVoForList);
		//动态数据
		vo.setBidPrice(dynamicDataForList.getBidPrice());
		vo.setDynamicCountdown(dynamicDataForList.getDynamicCountdown());
		List<LabelVo> labelVos = labelStubService.findByProductId(vo.getAuctionProdId());
		//设置标签
		for(LabelVo labelVo:labelVos){
			labelVo.setLabelPic(aliyunOssDomain + labelVo.getLabelPic());
		}
		vo.setLabelVos(labelVos);
	}
	
	/**
	 * <p>
	 * Title: 最新成交记录
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param pageNum
	 * @param pageSize
	 */
	@RequestMapping("lastTrade")
	public void lastTrade(HttpServletRequest request,HttpServletResponse response,Integer pageNum,Integer pageSize) {
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null? 10 : pageSize;
		Paging<AuctionInfoModel> page = new Paging<>();
		List<LastAuctionInfoVo> vos = new ArrayList<>();
		try {
			AuctionInfoQuery  auctionQuery = new AuctionInfoQuery(); 
			auctionQuery.setStatus(2);
			page =  auctionInfoStubService.queryNewestAuctionInfo(auctionQuery, pageNum, pageSize);
			List<AuctionInfoModel> models = page.getList();
			for (AuctionInfoModel model : models) {
				LastAuctionInfoVo lastVo = beanMapper.map(model, LastAuctionInfoVo.class);
				Integer lastAuctionId = auctionInfoStubService.findAuctionById(lastVo.getAuctionProdId());
				lastVo.setUserName(model.getWinUserDesc());
				lastVo.setLastAuctionId(lastAuctionId);
				lastVo.setPreviewPic(aliyunOssDomain+lastVo.getPreviewPic());
				vos.add(lastVo);
			}
		} catch (Exception e) {
			log.error("auction/lastList--error,{}",e);
		}
		SpringUtils.renderJson(response, JsonView.buildPage(page.getPages(), pageNum, vos));
	}
		
}
