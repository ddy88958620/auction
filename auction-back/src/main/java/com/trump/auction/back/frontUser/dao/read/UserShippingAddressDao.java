package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.UserShippingAddress;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserShippingAddressDao {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param
     * @return
     */
    List<UserShippingAddress> findUserAddressListByUserId(HashMap<String,Object> params);
}
