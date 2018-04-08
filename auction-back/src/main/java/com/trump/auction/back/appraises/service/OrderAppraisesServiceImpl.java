package com.trump.auction.back.appraises.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.appraises.dao.read.OrderAppraisesReadDao;
import com.trump.auction.back.appraises.model.OrderAppraises;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;
import com.trump.auction.back.order.model.OrderInfo;
import com.trump.auction.back.order.service.OrderInfoService;
import com.trump.auction.back.util.file.DateUtil;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.util.Base64Utils;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderAppraisesServiceImpl implements OrderAppraisesService {
	Logger logger = Logger.getLogger(getClass());

	
	@Autowired
	private OrderAppraisesReadDao orderAppraisesReadDao;
	
	@Autowired
	private OrderAppraisesStubService orderAppraisesStubService;

	@Autowired
	AuctionInfoStubService auctionInfoStubService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private OrderInfoStubService orderInfoStubService;

	@Autowired
	private OrderAppraisesRulesService orderAppraisesRulesService;
	
	@Override
	public Paging<OrderAppraises> findPage(HashMap<String, Object> params) {
		PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
				Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
		
		Paging<OrderAppraises>  list = PageUtils.page(orderAppraisesReadDao.findAll(params));
		
		for (OrderAppraises orderAppraises : list.getList()) {
			
			OrderInfoModel orderInfoModel =  orderInfoStubService.findOneOrderByAcNo(Integer.valueOf(orderAppraises.getBuyId()), orderAppraises.getAuctionNo());
			orderAppraises.setBuyNickName(Base64Utils.decodeStr(orderAppraises.getBuyNickName()));
			orderAppraises.setContent(HtmlUtils.htmlUnescape(Base64Utils.decodeStr(orderAppraises.getContent())));
			
			if(null!=orderInfoModel){
				orderAppraises.setPaidMoney(orderInfoModel.getPaidMoney()+"");

				if(null!=orderInfoModel.getProvinceName() && null!=orderInfoModel.getCityName()){
					orderAppraises.setAddress(orderInfoModel.getProvinceName()+orderInfoModel.getCityName());
				}
				//orderAppraises.setProductName(orderInfoModel.getProductName());
			}
		}
		return list;
	}

	@Override
	public Integer saveOrderAppraises(HashMap<String, Object> params) {
		OrderAppraisesModel aModel  = new OrderAppraisesModel();
		OrderInfo orderInfo =  orderInfoService.findOrderInfoView(params.get("orderId")+"");

		AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(orderInfo.getAuctionNo());
		aModel.setAppraisesPic(params.get("appraisesPic")+"");
		aModel.setContent(Base64Utils.encodeStr(params.get("content")+""));
		aModel.setBuyNickName(Base64Utils.encodeStr(orderInfo.getUserName()));
		aModel.setIsShow(2);
		aModel.setType(2);
		aModel.setCreateTime(DateUtil.formatDate(params.get("createTime")+"","yyyy-MM-dd hh:mm:ss"));
		aModel.setOrderId(params.get("orderId")+"");
		aModel.setBuyId(orderInfo.getBuyId());
		//aModel.setBuyPic(order.getHeadImg());
		aModel.setProductId(auctionInfoModel.getAuctionProdId());
		aModel.setProductName(orderInfo.getProductName());
		aModel.setAuctionNo(orderInfo.getAuctionNo());
		aModel.setBidTimes(orderInfo.getBidTimes());
		
		Integer count = orderAppraisesStubService.createOrderAppraises(aModel);
		return count;
	}


	@Override
	public OrderAppraises selectById(Integer id) {
		return orderAppraisesReadDao.selectById(id);
	}

	@Override
	public OrderAppraises findByOrderId(String orderId) {
		return orderAppraisesReadDao.findByOrderId(orderId);
	}

//	@Override
//	public String returnRemarkLevel(OrderAppraises appraises, String[] pic, String level) {
//		String words,number;
//		String[] arrayWords = new String [2];
//		String[] arrayNumber = new String [2];
//		String [] middleWords = {"0","0"};
//		String [] middleNumber = {"0","0"};
//		int minWords,maxWords,minNumber,maxNumber;
//		// 根据评价内容数与图片数,系统自动评级
//		List<OrderAppraisesRules> gradeAppraisesList = orderAppraisesRulesService.findAll();
//		//	循环遍历评价规则
//		for (int i = 0; i < gradeAppraisesList.size(); i++) {
//			words = gradeAppraisesList.get(i).getAppraisesWords();
//			arrayWords = words.split("-");
//			number = gradeAppraisesList.get(i).getPicNumber();
//			arrayNumber = number.split("-");
//			// words,number可能等于-2这种情况
//			if (null == arrayWords[0] || "".equals(arrayWords[0])) {
//				//  arrayWords[0] = "0";
//			}else{
//				middleWords[0] = arrayWords[0];
//			}
//			if(arrayWords.length==1){
//				middleWords[1]=appraises.getContent().length()+"";
//			}else{
//				middleWords[1]=arrayWords[1];
//			}
//			if( null == arrayNumber[0] || "".equals(arrayNumber[0])){
//				//  arrayNumber[0] = "0";
//			}else{
//				middleWords[0] = arrayNumber[0];
//			}
//			if(arrayNumber.length==1){
//				middleNumber[1]=pic.length+"";
//			}else{
//				middleNumber[1]=arrayNumber[1];
//			}
//			minWords  = Integer.parseInt(middleWords[0]);
//			maxWords  = Integer.parseInt(middleWords[1]);
//			minNumber  = Integer.parseInt(middleNumber[0]);
//			maxNumber  = Integer.parseInt(middleNumber[1]);
//			//  如果评价内容大于等于最小评价内容且小于等于最大评价内容
//			if(appraises.getContent().length()>=minWords && appraises.getContent().length()<=maxWords){
//				if(pic.length>=minNumber && pic.length<=maxNumber){
//					level=gradeAppraisesList.get(i).getAppraisesLevel();
//					break;
//				}
//			}
//		}
//		return level;
//	}
}
