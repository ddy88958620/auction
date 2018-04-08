package com.trump.auction.order.dao;

import com.trump.auction.order.domain.AddressInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressInfoDao {
    AddressInfo selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int insert(AddressInfo obj);
    int insertSelective(AddressInfo obj);
    int updateByPrimaryKeySelective(AddressInfo obj);
    int updateByPrimaryKey(AddressInfo obj);
    /**
     * 获取地区列表
     * @param parentId 当前地区ID，为空时，查询顶级地区列表
     * @return
     */
    List<AddressInfo> findAddressInfoListByParentId(Integer parentId);

    /**
     * 获取让你有地区列表
     * @param
     * @return
     */
    List<AddressInfo> findAllAddressInfo();
}
