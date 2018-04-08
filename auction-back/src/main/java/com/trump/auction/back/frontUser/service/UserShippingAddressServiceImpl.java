package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.dao.read.UserShippingAddressDao;
import com.trump.auction.back.frontUser.model.UserShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserShippingAddressServiceImpl implements UserShippingAddressService {

    @Autowired
    UserShippingAddressDao userShippingAddressDao;

    /**
     * 根据用户ID查询用户收货地址列表
     * @param
     * @return
     */
    public Paging<UserShippingAddress> findUserAddressListByUserId(HashMap<String,Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(userShippingAddressDao.findUserAddressListByUserId(params));
    }


}
