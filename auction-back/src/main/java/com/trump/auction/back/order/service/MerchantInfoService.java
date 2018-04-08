package com.trump.auction.back.order.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.order.model.MerchantInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 罗显 on 2017/12/25.
 */
public interface MerchantInfoService {

    /**
     * 获取商家列表
     * @param params
     * @return
     */
    Paging<MerchantInfo> getListMerchantInfo(HashMap<String, Object> params);
    /**
     * 根据Id获取商家信息
     * @param id
     */
    MerchantInfo getMerchantInfo(Integer id);
    /**
     * 根据状态查询商家
     */
    List<MerchantInfo> getListMerchantInfoBystatus(HashMap<String, Object> params);

}
