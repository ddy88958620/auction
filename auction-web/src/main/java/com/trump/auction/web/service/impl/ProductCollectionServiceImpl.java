package com.trump.auction.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cf.common.utils.JsonResult;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.model.ProductInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.cust.model.UserProductCollectModel;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.web.service.ProductCollectionService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.vo.CollectVo;
import com.trump.auction.web.vo.UserProductCollectVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductCollectionServiceImpl implements ProductCollectionService {

	@Autowired
	UserProductCollectStubService  userProductCollectStubService;

	@Autowired
	private AuctionInfoStubService auctionInfoStubService;

	@Autowired
	private AuctionBidStubService auctionBidStubService;

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ProductInfoSubService productInfoSubService;

	@Value("${aliyun.oss.domain}")
	private String aliyunOssDomain;

	@Override
	public HandleResult getUserProductCollections(Integer userId,Integer pageNum,Integer pageSize) {
		HandleResult result = new HandleResult(true);
		UserProductCollectModel userProductCollect = new UserProductCollectModel();
		userProductCollect.setUserId(userId);
		userProductCollect.setStatus(1);
		Paging<CollectVo> page = new Paging<>();
		Paging<UserProductCollectModel> paging =  new Paging<>();
		List<UserProductCollectVo> vos = new ArrayList<>();
		List<CollectVo> hotList = new ArrayList<>();
		try {
			paging =  userProductCollectStubService.findUserProductCollectPage(userProductCollect, pageNum, pageSize);
			vos = beanMapper.mapAsList(paging.getList(), UserProductCollectVo.class);
			if(vos != null && vos.size() > 0 ){
				for (UserProductCollectVo vo : vos) {
					CollectVo collectVo = new CollectVo();
					Integer auctionProdId = vo.getProductId();
					collectVo.setAuctionId(vo.getPeriodsId());
					collectVo.setAuctionProdId(auctionProdId);
					AuctionInfoModel model = auctionInfoStubService.getAuctionInfoById(vo.getPeriodsId());
					if(model != null){
						//	获取最新期数Id
						int lastAuctionId = auctionInfoStubService.findAuctionById(auctionProdId);
						//	设置最新竞拍期数
						collectVo.setLastAuctionId(lastAuctionId);
						collectVo.setProductName(model.getProductName());
						collectVo.setPreviewPic(aliyunOssDomain + model.getPreviewPic());
						collectVo.setBidPrice(model.getProductPrice());

						collectVo.setBidPrice(model.getFinalPrice());

						Integer status = model.getStatus();
						if(status.equals(1)){
							collectVo.setBidPrice(model.getIncreasePrice().multiply(
									new BigDecimal(model.getTotalBidCount())).add(model.getStartPrice()));
						}
						collectVo.setStatus(1);
						if (model.getStatus().equals(2)) {
							collectVo.setStatus(2);
						}
					}
					hotList.add(collectVo);
				}
			}
		} catch (Exception e) {
			log.error("getUserProductCollections error: {}",e);
		}
		page.setPages(paging.getPages());
		page.setList(hotList);
		return result.setData(page);
	}

	@Override
	public HandleResult updateUserProductCollect(Integer userId,Integer auctionProdId,Integer auctionId,Integer type) {
		HandleResult result = new HandleResult(false);
		try {
			if(auctionProdId == null || auctionId == null || type == null){
				return result.setCode(1).setMsg("参数不能为空");
			}
			List<Integer> typeList = Arrays.asList(1,2);
			if(!typeList.contains(type)){
				return result.setCode(2).setMsg("参数不合法");
			}
			AuctionInfoModel auctionInfo = auctionInfoStubService.getAuctionInfoById(auctionId);
			if (null==auctionInfo){
				return result.setCode(2).setMsg("未获取期数信息");
			}
			ProductInfoModel product = productInfoSubService.getProductByProductId(auctionInfo.getProductId());
			if (null==product){
				return result.setCode(2).setMsg("获取商品信息有误");
			}
			int count = product.getCollectCount();
			//添加收藏
			if(type.equals(1)){
				return collectAuctionProduct(userId, auctionProdId, auctionId,count,product.getId());
			}
			//取消收藏
			if(type.equals(2)){
				ServiceResult serviceResult = userProductCollectStubService.cancelUserProductCollect(userId, auctionProdId,auctionId);
				if(serviceResult != null && serviceResult.isSuccessed()){
//					auctionBidStubService.CancelCollectCount(auctionId);
					if (count==0){
						return result.setResult(true);
					}
					JsonResult result1 = productInfoSubService.updateCollectCount(product.getId(),count-1);
					if (!"200".equals(result1.getCode())){
						return result.setCode(4).setMsg("取消收藏失败");
					}
					return result.setResult(true);
				}
			}
		} catch (Exception e) {
			log.error("updateUserProductCollect error: {}",e);
		}
		return result.setCode(1);
	}

	@Override
	public HandleResult collectAuctionProduct(Integer userId,Integer auctionProdId, Integer periodsId,int count,Integer productId) {
		HandleResult result = new HandleResult(false);
		if(auctionProdId == null || periodsId == null){
			return result.setCode(1).setMsg("参数不能为空");
		}
		try {

			if(!auctionProductExist(auctionProdId, periodsId)){
				return result.setCode(2).setMsg("该拍品不存在");
			}

			boolean isCollect = userProductCollectStubService.checkUserProductCollect(userId, auctionProdId, periodsId);
			if(isCollect){
				return result.setCode(3).setMsg("该拍品您已收藏过");
			}

			if(saveCollectInfo(userId, auctionProdId, periodsId)){
//				auctionBidStubService.updCollectCount(periodsId);
				JsonResult result1 = productInfoSubService.updateCollectCount(productId,count+1);
				if (!"200".equals(result1.getCode())){
					return result.setCode(4).setMsg("收藏失败");
				}
				return result.setResult(true);
			}

		} catch (Exception e) {
			log.error("collectAuctionProduct error: {}",e);
		}
		return result.setCode(4).setMsg("收藏失败");
	}

	private boolean saveCollectInfo(Integer userId, Integer auctionProdId, Integer periodsId) {
		UserProductCollectModel obj = new UserProductCollectModel();
		obj.setUserId(userId);
		obj.setProductId(auctionProdId);
		obj.setPeriodsId(periodsId);
		obj.setStatus(1);
		ServiceResult serviceResult = userProductCollectStubService.saveUserProductCollect(obj);
		if(serviceResult != null && serviceResult.isSuccessed()){
			return true;
		}
		return false;
	}

	private boolean auctionProductExist(Integer productId, Integer periodsId) {
		AuctionInfoModel model = auctionInfoStubService.getAuctionInfoById(periodsId);
		if(model == null){
			return false;
		}
		return true;
	}

}
