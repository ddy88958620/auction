package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.goods.api.MerchantInfoSubService;
import com.trump.auction.goods.model.MerchantInfoModel;
import com.trump.auction.goods.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service(version = "1.0.0")
public class MerchantInfoSubServiceImpl implements MerchantInfoSubService {

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Override
    public int addMerchantInfo(MerchantInfoModel merchantInfoModel) {
        return  merchantInfoService.addMerchantInfo(merchantInfoModel);
    }

    @Override
    public List<MerchantInfoModel> getListMerchantInfo(MerchantInfoModel merchantInfoModel) {
        List<MerchantInfoModel> getListMerchantInfo = merchantInfoService.getListMerchantInfo(merchantInfoModel);
        if (getListMerchantInfo !=null && getListMerchantInfo.size() >0){
            return  getListMerchantInfo;
        }else{
            return null;
        }
    }

    @Override
    public int deleteMerchantInfo(String[] ids) {
       return   merchantInfoService.deleteMerchantInfo(ids);
    }

    @Override
    public int updateMerchantInfo(MerchantInfoModel merchantInfoModel) {
       return merchantInfoService.updateMerchantInfo(merchantInfoModel);
    }
}
