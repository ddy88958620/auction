package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.cust.domain.UserShippingAddress;
import com.trump.auction.cust.model.UserShippingAddressModel;
import com.trump.auction.cust.service.UserShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户收货地址相关服务
 * @author zhanping
 */
@Service(version = "1.0.0")
public class UserShippingAddressStuServiceImpl implements UserShippingAddressStuService {

    @Autowired
    private UserShippingAddressService userShippingAddressService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId 用户ID
     * @return
     */
    public List<UserShippingAddressModel> findUserAddressListByUserId(Integer userId){
        return beanMapper.mapAsList(userShippingAddressService.findUserAddressListByUserId(userId),UserShippingAddressModel.class);
    }

    /**
     * 根据用户收货地址ID查询用户收货地址信息
     * @param addressId 用户收货地址ID
     * @return
     */
    public UserShippingAddressModel findUserAddressItemByAddressId(Integer addressId){
        return beanMapper.map(userShippingAddressService.findUserAddressItemByAddressId(addressId), UserShippingAddressModel.class);
    }

    /**
     * 新增用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    public ServiceResult insertUserAddressItem(UserShippingAddressModel obj){
        return userShippingAddressService.insertUserAddressItem(beanMapper.map(obj,UserShippingAddress.class));
    }

    /**
     * 更新用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    public ServiceResult updateUserAddressItem(UserShippingAddressModel obj){
        return userShippingAddressService.updateUserAddressItem(beanMapper.map(obj,UserShippingAddress.class));
    }

    /**
     * 删除用户收货地址
     * @param addressId 用户收货地址ID
     * @return
     */
    public ServiceResult deleteUserAddressItemByAddressId(Integer addressId){
        return userShippingAddressService.deleteUserAddressItemByAddressId(addressId);
    }

    /**
     * 设置某一条为默认地址
     * 必传 id,userId
     * @param obj
     * @return
     */
    public ServiceResult setDefaultUserAddressItem(UserShippingAddressModel obj){
        return userShippingAddressService.setDefaultUserAddressItem(beanMapper.map(obj,UserShippingAddress.class));
    }

    /**
     * 根据用户ID查询用户默认收货地址
     * @param id 用户ID
     * @return
     */
    public UserShippingAddressModel findDefaultUserAddressItemByUserId(Integer id){
        return beanMapper.map(userShippingAddressService.findDefaultUserAddressItemByUserId(id), UserShippingAddressModel.class);
    }
}