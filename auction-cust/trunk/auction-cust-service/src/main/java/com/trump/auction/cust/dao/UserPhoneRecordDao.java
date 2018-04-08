package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.UserPhoneRecord;
import com.trump.auction.cust.model.UserPhoneRecordModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 * 用户手机换绑记录
 */
@Repository
public interface UserPhoneRecordDao {

    /**
     * 新增用户手机换绑记录
     * @param record 换绑信息
     * @return 成功返回1，失败返回0
     */
    int insertUserPhoneRecord(UserPhoneRecord record);

    /**
     * 查询换绑所有记录
     * @return 成功返回1，失败返回0
     */
    List<UserPhoneRecord> selectAll();

    /**
     * 根据用户id查询换绑记录
     * @param id 用户id
     * @return 成功返回1，失败返回0
     */
    List<UserPhoneRecord> selectRecordById(Integer id);
}
