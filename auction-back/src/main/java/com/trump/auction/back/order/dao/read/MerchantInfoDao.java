package com.trump.auction.back.order.dao.read;

import com.trump.auction.back.order.model.MerchantInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 罗显 on 2017/12/25.
 */
@Repository
public interface MerchantInfoDao {

    /**
     * 获取商家分页集合
     * @param params
     * @return
     */
    List<MerchantInfo> getListMerchantInfo(HashMap<String, Object> params);
    /**
     * 根据Id获取商家信息
     * @param id
     */
    MerchantInfo getMerchantInfo(@Param(value = "id") Integer id);

}
