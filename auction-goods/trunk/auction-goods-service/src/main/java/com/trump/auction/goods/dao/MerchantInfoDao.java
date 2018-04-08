package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.MerchantInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Repository
public interface MerchantInfoDao {

    /**
     * 成功结果返回 >0
     * 失败结果返回 0
     * 添加商家
     */
    int addMerchantInfo(MerchantInfo merchantInfo);
    /**
     * 根据条件查询得到的商家集合
     * @param merchantInfo id merchant_name phone status
     * @return
     */
    List<MerchantInfo> getListMerchantInfo(MerchantInfo merchantInfo);

    /**
     * 删除商家
     * @param ids
     * @return
     */
    int deleteMerchantInfo(String[] ids);
    /**
     * 修改商家
     */
    int updateMerchantInfo(MerchantInfo merchantInfo);
}
