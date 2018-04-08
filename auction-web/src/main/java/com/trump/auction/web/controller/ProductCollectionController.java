package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.common.util.page.Paging;
import com.trump.auction.web.service.ProductCollectionService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.UserProductCollectVo;

import redis.clients.jedis.JedisCluster;

@RequestMapping("collect/")
@Controller
public class ProductCollectionController extends BaseController {

	@Autowired
	private ProductCollectionService productCollectionService;
	@Autowired
	JedisCluster jedisCluster;
	/**
	 * <p>
	 * Title: 收藏、取消收藏
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param auctionProdId
	 * @param auctionId
	 * @param type
	 */
	@RequestMapping("update")
	public void todo(HttpServletRequest request, HttpServletResponse response,Integer auctionProdId,Integer auctionId,Integer type) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = productCollectionService.updateUserProductCollect(Integer.valueOf(userId),auctionProdId,auctionId,type);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
	}

	/**
	 * <p>
	 * Title: 收藏列表
	 * </p>
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response,Integer pageNum,Integer pageSize) {
		String userId = getUserIdFromRedis(request);
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
		HandleResult result = productCollectionService.getUserProductCollections(Integer.valueOf(userId),pageNum,pageSize);
		Paging<UserProductCollectVo> page = (Paging<UserProductCollectVo>)result.getData();
		SpringUtils.renderJson(response, JsonView.buildPage(page.getPages(), pageNum, page.getList()));
	}
	
}
