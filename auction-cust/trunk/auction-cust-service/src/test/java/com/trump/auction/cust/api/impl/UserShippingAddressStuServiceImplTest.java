package com.trump.auction.cust.api.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.CustApplication;
import com.trump.auction.cust.domain.UserShippingAddress;
import com.trump.auction.cust.service.UserShippingAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Author: zhanping
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustApplication.class)
@Transactional
@Rollback
public class UserShippingAddressStuServiceImplTest {

    @Autowired
    private UserShippingAddressService userShippingAddressService;

    @Test
    public void insertUserAddressItem() throws Exception {
        UserShippingAddress address = new UserShippingAddress();
        address.setUserId(342);
        address.setUserName("111112444");
        address.setAddressType(0);
        address.setStatus(0);
        ServiceResult result = userShippingAddressService.insertUserAddressItem(address);
        Integer id = address.getId();
    }

}