package com.trump.auction.cust.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.UserShippingAddressModel;
import java.util.List;

/**
 * 用户收货地址相关服务
 * @author zhanping
 */
public interface UserShippingAddressStuService {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId 用户ID
     * @return
     */
    List<UserShippingAddressModel> findUserAddressListByUserId(Integer userId);

    /**
     * 根据用户收货地址ID查询用户收货地址信息
     * @param addressId 用户收货地址ID
     * @return
     */
    UserShippingAddressModel findUserAddressItemByAddressId(Integer addressId);

    /**
     * 新增用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    ServiceResult insertUserAddressItem(UserShippingAddressModel obj);

    /**
     * 更新用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    ServiceResult updateUserAddressItem(UserShippingAddressModel obj);

    /**
     * 删除用户收货地址
     * @param addressId 用户收货地址ID
     * @return
     */
    ServiceResult deleteUserAddressItemByAddressId(Integer addressId);

    /**
     * 设置某一条为默认地址
     * 必传 id,userId
     * @param obj
     * @return
     */
    ServiceResult setDefaultUserAddressItem(UserShippingAddressModel obj);

    /**
     * 根据用户ID查询用户默认收货地址
     * @param id 用户ID
     * @return
     */
    UserShippingAddressModel findDefaultUserAddressItemByUserId(Integer id);
}