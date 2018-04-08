package com.trump.auction.account.service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.account.dao.AccountInfoDetailDao;
import com.trump.auction.account.domain.AccountInfoDetail;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.model.AccountInfoDetailModel;
import com.trump.auction.account.util.DateUtil;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.model.ProductInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dingxp on 2017-12-26 0027.
 * 开心币类型
 */
@Service
@Slf4j
public class AccountInfoDetailServiceImpl implements AccountInfoDetailService {
	private BeanMapper beanMapper;
	private AccountInfoDetailDao accountInfoDetailDao;
	private ProductInfoSubService productInfoSubService;

	AccountInfoDetailServiceImpl(BeanMapper beanMapper, AccountInfoDetailDao accountInfoDetailDao, ProductInfoSubService productInfoSubService) {
		this.beanMapper = beanMapper;
		this.accountInfoDetailDao = accountInfoDetailDao;
		this.productInfoSubService = productInfoSubService;
	}

    @Override
    public List<AccountInfoDetailModel> getAccountInfoDetailListByUserId(Integer userId) {
        List<AccountInfoDetail> accountInfoDetailList =  accountInfoDetailDao.getAccountInfoDetailListByUserId(userId);
        return beanMapper.mapAsList(accountInfoDetailList,AccountInfoDetailModel.class);
    }

    @Override
    public AccountInfoDetailModel getAccountInfoDetailById(Integer id) {
        return beanMapper.map(accountInfoDetailDao.getAccountInfoDetailById(id), AccountInfoDetailModel.class);
    }

    @Override
    public int insertAccountInfoDetail(AccountInfoDetailModel accountInfoDetailModel) {
        return accountInfoDetailDao.insertAccountInfoDetail(beanMapper.map(accountInfoDetailModel, AccountInfoDetail.class));
    }

	@Override
	public Paging<AccountInfoDetailModel> getAccountInfoDetailList(Integer userId, Integer pageNum, Integer pageSize, Integer listType) {
		if(listType != 1 && listType != 2){
			return new Paging<>();
		}
		PageHelper.startPage(pageNum, pageSize);
		//根据coinType查询
		Paging<AccountInfoDetailModel> paging = PageUtils.page(accountInfoDetailDao.getAccountInfoDetailList(userId, listType, EnumAccountType.BUY_COIN.getKey()), AccountInfoDetailModel.class, beanMapper);
		List<AccountInfoDetailModel> list = paging.getList();
		for (AccountInfoDetailModel a : list) {
			Date date = a.getValidEndTime();
            int days = DateUtil.getDay(date, new Date());
            if (days < 0) {
                days = 0;
            }
            a.setDaysRemain(days);//有效天数
			//开心币的图片(此处现查是考虑到商品的信息更改)
			ProductInfoModel productInfoModel = productInfoSubService.getProductByProductId(a.getProductId());
			a.setProductImage(productInfoModel.getPicUrl());
		}
		return paging;
	}

}
