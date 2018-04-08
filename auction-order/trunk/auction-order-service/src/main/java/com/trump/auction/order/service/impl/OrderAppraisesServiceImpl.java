package com.trump.auction.order.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.order.dao.OrderAppraisesDao;
import com.trump.auction.order.dao.OrderInfoDao;
import com.trump.auction.order.domain.OrderAppraises;
import com.trump.auction.order.domain.OrderInfo;
import com.trump.auction.order.enums.EnumAppraisesShow;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.service.OrderAppraisesRulesService;
import com.trump.auction.order.service.OrderAppraisesService;
import com.trump.auction.order.service.OrderInfoService;
import com.trump.auction.order.util.Base64Utils;

/**
 * 评价管理
 * Created by wangjian on 2017/12/20.
 */
@Slf4j
@Service("auctionOrderInfoService")
public class OrderAppraisesServiceImpl implements OrderAppraisesService {

	private static Logger logger = LoggerFactory.getLogger(OrderAppraisesServiceImpl.class);
    @Autowired
    private OrderAppraisesDao orderAppraisesDao;

    @Autowired
    private BeanMapper beanMapper;
    
    @Autowired
	private OrderInfoService orderInfoService ;
    
    @Autowired
    private AccountInfoStubService accountInfoStubService;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
	private OrderAppraisesRulesService orderAppraisesRulesService;
	@Override
	public Paging<OrderAppraisesModel> getAppraisesByUserId(String userId) {
		return PageUtils.page(beanMapper.mapAsList(orderAppraisesDao.getAppraisesByUserId(userId),OrderAppraisesModel.class)) ;
	}

	@Override
	public Integer createOrderAppraises(OrderAppraisesModel orderAppraisesModel) {
		if (orderAppraisesModel.getContent()==null){
			orderAppraisesModel.setContent("");
		}
		if (orderAppraisesModel.getAppraisesPic()==null){
			orderAppraisesModel.setAppraisesPic("");
		}
		//解码匹配
		orderAppraisesModel.setContent(Base64Utils.decodeStr(orderAppraisesModel.getContent()));
		//根据规则判定等级接口
		orderAppraisesModel.setAppraisesLevel(orderAppraisesRulesService.orderAppraisesRulesLevelCheck(orderAppraisesModel.getContent(),orderAppraisesModel.getAppraisesPic()));
		orderAppraisesModel.setContent(Base64Utils.encodeStr(orderAppraisesModel.getContent()));
		return orderAppraisesDao.insertAppraise(beanMapper.map(orderAppraisesModel, OrderAppraises.class));
	}

	@Override
	public OrderAppraisesModel queryOrderAppraises(String appraisesId) {
		return beanMapper.map(orderAppraisesDao.selectByPrimaryKey(Integer.valueOf(appraisesId)), OrderAppraisesModel.class);
	}

	@Override
	public void orderAppraisesCheck(String appraisesId, String isShow,String baseRewords,String showRewords,String level,String valueArray) {
		OrderInfoModel orderInfoModel = new OrderInfoModel();
		OrderAppraises orderAppraises =  orderAppraisesDao.selectByPrimaryKey(Integer.valueOf(appraisesId));

		if(EnumAppraisesShow.SHOW.getValue().equals(Integer.valueOf(isShow))){
			//审核成功更改订单状态
			try {
				OrderInfo orderInfo =  orderInfoDao.selectByPrimaryKey(orderAppraises.getOrderId());
				if(!EnumOrderStatus.LIUPAI.getValue().equals(orderInfo.getOrderStatus()) ){
					orderInfoModel.setOrderId(orderAppraises.getOrderId());
					orderInfoModel.setOrderStatus(EnumOrderStatus.COMPLETE.getValue());
					orderInfoService.updateOrderStatus(orderInfoModel);
				}
			} catch (Exception e) {
				logger.error("更改订单状态失败:{}", e);
			}
			
			try {
				int paresentCoin =0,baseCoin =0 ;
				if(null !=showRewords && !"".equals(showRewords)){
					paresentCoin = Integer.parseInt(showRewords);
				}
				if(null !=baseRewords && !"".equals(baseRewords)){
					baseCoin = Integer.parseInt(baseRewords);
				}
				//晒单成功，增加baseRewords积分
				accountInfoStubService.backCoinByShareAuctionOrder(Integer.valueOf(orderAppraises.getBuyId()),baseCoin, 3);
				accountInfoStubService.backCoinByShareAuctionOrder(Integer.valueOf(orderAppraises.getBuyId()),paresentCoin, 2);
			} catch (NumberFormatException e) {
				logger.error("增加积分失败:{}", e);
			}
		}
		String appraisesPic =  orderAppraises.getAppraisesPic();
		if(null == appraisesPic || "".equals(appraisesPic) || "null".equals(appraisesPic)){
			if(null == valueArray || "".equals(valueArray)){
				appraisesPic = appraisesPic;
			}else{
				appraisesPic = valueArray+",";
			}
		}else{
			if(null == valueArray || "".equals(valueArray)){
				appraisesPic = appraisesPic;
			}else {
				appraisesPic+=valueArray+",";
			}
		}
		orderAppraisesDao.orderAppraisesCheck(appraisesId,isShow,level,appraisesPic);
	}

	@Override
	public Paging<OrderAppraisesModel> queryAppraisesByProductId(String productId,Integer pageNum, Integer pageSize) {
		
		long startTime = System.currentTimeMillis();
		log.info("queryAppraisesByProductId invoke,StartTime:{},params:{},{},{}", startTime, productId, pageNum, pageSize);
		
		if (null == productId || "".equals(productId) ) {
            throw new IllegalArgumentException("queryAppraisesByProductId param productId is null!");
        }
		
		Paging<OrderAppraisesModel>  orderAppraises = null;
		
        PageHelper.startPage(pageNum, pageSize);
        try {
        	orderAppraises = PageUtils.page(orderAppraisesDao.queryAppraisesByProductId(productId),OrderAppraisesModel.class,beanMapper);
        } catch (Exception e) {
            log.error("findAllOrder error:", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("queryAppraisesByProductId end,duration:{}", endTime - startTime);
        
		return orderAppraises;
		
		
	}

	@Override
	public OrderAppraisesModel getNewestAppraises(String userId, String orderId) {
		return beanMapper.map(orderAppraisesDao.getNewestAppraises(userId,orderId), OrderAppraisesModel.class);
	}

}