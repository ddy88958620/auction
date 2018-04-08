package com.trump.auction.goods.service;

import com.trump.auction.goods.model.MerchantInfoModel;

import java.util.List;

/**
 * Created by 罗显 on 2017/12/21.
 */
public interface MerchantInfoService {
    /**
     * 成功结果返回 >0
     * 失败结果返回 0
     * 添加商家
     */
    int addMerchantInfo(MerchantInfoModel merchantInfoModel);
    /**
     * 根据条件查询得到的商家集合
     * @param merchantInfoModel id merchant_name phone status
     * @return
     */
    List<MerchantInfoModel> getListMerchantInfo(MerchantInfoModel merchantInfoModel);

    /**
     * 删除商家
     * @param ids
     * @return
     */
    int deleteMerchantInfo(String[] ids);
    /**
     * 修改商家
     */
    int updateMerchantInfo(MerchantInfoModel merchantInfoModel);
}
