package com.trump.auction.order.api;



import com.trump.auction.order.model.AddressInfoModel;

import java.util.List;

/**
 * 地区相关服务
 * @author zhanping
 */
public interface AddressInfoStuService {

    /**
     * 获取地区列表
     * @param parentId 当前地区ID，为空时，查询顶级地区列表
     * @return
     */
    List<AddressInfoModel> findAddressInfoListByParentId(Integer parentId);

    /**
     * 获取让你有地区列表
     * @param
     * @return
     */
    List<AddressInfoModel> findAllAddressInfo();
}
