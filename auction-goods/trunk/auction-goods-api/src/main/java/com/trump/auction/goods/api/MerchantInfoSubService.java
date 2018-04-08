package com.trump.auction.goods.api;

import com.trump.auction.goods.model.MerchantInfoModel;

import java.util.List;

/**
 * 商家
 * Created by 罗显 on 2017/12/21.
 */
public interface MerchantInfoSubService {
    /**
     * 成功结果返回 >0
     * 失败结果返回 0
     * 添加商家
     * @param merchantInfoModel
     * @return  成功结果返回 >0  失败结果返回 0
     *
     */
    int addMerchantInfo(MerchantInfoModel merchantInfoModel);
    /**
     * 根据条件查询得到的商家集合
     * @param merchantInfoModel id merchant_name phone status
     * @return 得到商家集合
     */
    List<MerchantInfoModel> getListMerchantInfo(MerchantInfoModel merchantInfoModel);
    /**
     * 根据Id 修改状态
     * 删除商家
     * @param  ids
     */
    int deleteMerchantInfo(String[] ids);
    /**
     * 修改商家
     * @param merchantInfoModel id merchant_name merchant_type phone status
     */
    int updateMerchantInfo(MerchantInfoModel merchantInfoModel);
}
