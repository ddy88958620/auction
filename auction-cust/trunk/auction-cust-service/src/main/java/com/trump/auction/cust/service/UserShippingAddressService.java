package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.domain.UserShippingAddress;

import java.util.List;

public interface UserShippingAddressService {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId 用户ID
     * @return
     */
    List<UserShippingAddress> findUserAddressListByUserId(Integer userId);

    /**
     * 根据用户收货地址ID查询用户收货地址信息
     * @param id 用户收货地址ID
     * @return
     */
    UserShippingAddress findUserAddressItemByAddressId(Integer id);

    /**
     * 新增用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    ServiceResult insertUserAddressItem(UserShippingAddress obj);

    /**
     * 更新用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    ServiceResult updateUserAddressItem(UserShippingAddress obj);

    /**
     * 删除用户收货地址
     * @param id 用户收货地址ID
     * @return
     */
    ServiceResult deleteUserAddressItemByAddressId(Integer id);

    /**
     * 设置某一条为默认地址
     * 必传 id,userId
     * @param obj
     * @return
     */
    ServiceResult setDefaultUserAddressItem(UserShippingAddress obj);

    /**
     * 根据用户ID查询用户默认收货地址
     * @param id 用户ID
     * @return
     */
    UserShippingAddress findDefaultUserAddressItemByUserId(Integer id);

}
