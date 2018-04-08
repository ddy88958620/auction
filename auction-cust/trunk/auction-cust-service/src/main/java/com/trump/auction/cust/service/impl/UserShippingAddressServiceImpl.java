package com.trump.auction.cust.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.UserShippingAddressDao;
import com.trump.auction.cust.domain.UserShippingAddress;
import com.trump.auction.cust.enums.UserShippingAddressStatus;
import com.trump.auction.cust.enums.UserShippingAddressType;
import com.trump.auction.cust.service.UserShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserShippingAddressServiceImpl implements UserShippingAddressService {

    @Autowired
    UserShippingAddressDao userShippingAddressDao;

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId 用户ID
     * @return
     */
    public List<UserShippingAddress> findUserAddressListByUserId(Integer userId) {
        return userShippingAddressDao.findUserAddressListByUserId(userId);
    }

    /**
     * 根据用户收货地址ID查询用户收货地址信息
     * @param id 用户收货地址ID
     * @return
     */
    public UserShippingAddress findUserAddressItemByAddressId(Integer id){
        return userShippingAddressDao.findUserAddressItemByAddressId(id);
    }

    /**
     * 新增用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    public ServiceResult insertUserAddressItem(UserShippingAddress obj){
        String code = "101";
        String msg = "操作失败";
        obj.setCreateTime(new Date());
        obj.setUpdateTime(new Date());
        int count = userShippingAddressDao.insertUserAddressItem(obj);
        if (count>0) {
            code = "200";
            msg = "操作成功";
            if (obj.getAddressType() == UserShippingAddressType.DEFAULT.getType()){
                return setDefaultUserAddressItem(obj);
            }
        }
        return new ServiceResult(code,msg);
    }

    /**
     * 更新用户收货地址
     * @param obj 用户收货地址实体
     * @return
     */
    public ServiceResult updateUserAddressItem(UserShippingAddress obj){
        String code = "101";
        String msg = "操作失败";
        UserShippingAddress address = userShippingAddressDao.findUserAddressItemByAddressId(obj.getId());
        address.setId(null);
        address.setStatus(UserShippingAddressStatus.DISABLED.getType());
        obj.setUpdateTime(new Date());
        int updateResult = userShippingAddressDao.updateUserAddressItem(obj);
        int insertResult = userShippingAddressDao.insertUserAddressItem(address);
        if (updateResult>0 && insertResult>0) {
            code = "200";
            msg = "操作成功";
            if (obj.getAddressType() == UserShippingAddressType.DEFAULT.getType()){
                return setDefaultUserAddressItem(obj);
            }
        }
        return new ServiceResult(code,msg);
    }

    /**
     * 删除用户收货地址
     * @param id 用户收货地址ID
     * @return
     */
    public ServiceResult deleteUserAddressItemByAddressId(Integer id){
        String code = "101";
        String msg = "操作失败";
        int count = userShippingAddressDao.deleteUserAddressItemByAddressId(id);
        if (count==1) {
            code = "200";
            msg = "操作成功";
        }
        return new ServiceResult(code,msg);
    }

    /**
     * 设置某一条为默认地址
     * 必传 id,userId
     * @param obj
     * @return
     */
    public ServiceResult setDefaultUserAddressItem(UserShippingAddress obj){
        int otherResult = userShippingAddressDao.setAllOtherUserAddressItem(obj);
        int defaultResult = userShippingAddressDao.setDefaultUserAddressItem(obj);
        String code = "101";
        String msg = "操作失败";
        if (otherResult>0&&defaultResult>0){
            code = "200";
            msg = "操作成功";
        }
        return new ServiceResult(code,msg);
    }

    /**
     * 根据用户ID查询用户默认收货地址
     * @param id 用户ID
     * @return
     */
    public UserShippingAddress findDefaultUserAddressItemByUserId(Integer id){
        return userShippingAddressDao.findDefaultUserAddressItemByUserId(id);
    }

}
