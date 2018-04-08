package com.trump.auction.order.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.api.AddressInfoStuService;
import com.trump.auction.order.model.AddressInfoModel;
import com.trump.auction.order.service.AddressInfoService;

/**
 * 地区相关服务
 * @author zhanping
 */
@Service(version = "1.0.0")
public class AddressInfoStuServiceImpl implements AddressInfoStuService {

    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 获取地区列表
     * @param parentId 当前地区ID，为空时，查询顶级地区列表
     * @return
     */
    public List<AddressInfoModel> findAddressInfoListByParentId(Integer parentId){
        return beanMapper.mapAsList(addressInfoService.findAddressInfoListByParentId(parentId),AddressInfoModel.class);
    }

    /**
     * 获取让你有地区列表
     * @param
     * @return
     */
    public List<AddressInfoModel> findAllAddressInfo(){
        return beanMapper.mapAsList(addressInfoService.findAllAddressInfo(), AddressInfoModel.class);
    }
}
