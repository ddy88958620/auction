package com.trump.auction.order.service.impl;

import com.trump.auction.order.dao.AddressInfoDao;
import com.trump.auction.order.domain.AddressInfo;
import com.trump.auction.order.service.AddressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressInfoServiceImpl implements AddressInfoService {

    @Autowired
    AddressInfoDao addressInfoDao;

    public AddressInfo selectByPrimaryKey(Integer id){
        return addressInfoDao.selectByPrimaryKey(id);
    }
    public int deleteByPrimaryKey(Integer id){
        return addressInfoDao.deleteByPrimaryKey(id);
    }
    public int insert(AddressInfo obj){
        return addressInfoDao.insert(obj);
    }
    public int insertSelective(AddressInfo obj){
        return addressInfoDao.insertSelective(obj);
    }
    public int updateByPrimaryKeySelective(AddressInfo obj){
        return addressInfoDao.updateByPrimaryKeySelective(obj);
    }
    public int updateByPrimaryKey(AddressInfo obj){
        return addressInfoDao.updateByPrimaryKey(obj);
    }
    /**
     * 获取地区列表
     * @param parentId 当前地区ID，为空时，查询顶级地区列表
     * @return
     */
    public List<AddressInfo> findAddressInfoListByParentId(Integer parentId){
        return addressInfoDao.findAddressInfoListByParentId(parentId);
    }

    /**
     * 获取让你有地区列表
     * @param
     * @return
     */
    @Override
    public List<AddressInfo> findAllAddressInfo(){
        return addressInfoDao.findAllAddressInfo();
    }
}
