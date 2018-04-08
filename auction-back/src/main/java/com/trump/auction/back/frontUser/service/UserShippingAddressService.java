package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.frontUser.model.UserShippingAddress;

import java.util.HashMap;

public interface UserShippingAddressService {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param
     * @return
     */
    Paging<UserShippingAddress> findUserAddressListByUserId(HashMap<String,Object> params);

}
