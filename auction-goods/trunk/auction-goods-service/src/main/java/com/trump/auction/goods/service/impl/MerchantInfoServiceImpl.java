package com.trump.auction.goods.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.goods.dao.MerchantInfoDao;
import com.trump.auction.goods.domain.MerchantInfo;
import com.trump.auction.goods.model.MerchantInfoModel;
import com.trump.auction.goods.service.MerchantInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {

    Logger logger = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);

    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private MerchantInfoDao merchantInfoDao;

    @Override
    public int addMerchantInfo(MerchantInfoModel merchantInfoModel) {
        return merchantInfoDao.addMerchantInfo(beanMapper.map(merchantInfoModel,MerchantInfo.class));
    }

    @Override
    public List<MerchantInfoModel> getListMerchantInfo(MerchantInfoModel merchantInfoModel) {
        List<MerchantInfo> getListMerchantInfo = merchantInfoDao.getListMerchantInfo(beanMapper.map(merchantInfoModel,MerchantInfo.class));
        if(getListMerchantInfo !=null && getListMerchantInfo.size() > 0){
            return beanMapper.map(merchantInfoModel,getListMerchantInfo.getClass());
        }else{
            return null;
        }
    }

    @Override
    public int deleteMerchantInfo(String[] ids) {
        return merchantInfoDao.deleteMerchantInfo(ids);
    }

    @Override
    public int updateMerchantInfo(MerchantInfoModel merchantInfoModel) {
        return merchantInfoDao.updateMerchantInfo(beanMapper.map(merchantInfoModel,MerchantInfo.class));
    }
}
