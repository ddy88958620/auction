package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.UserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationDao {
    int deleteByPrimaryKey(@Param("pid") Integer pid, @Param("sid") Integer sid);

    int insert(UserRelation record);

    int insertSelective(UserRelation record);

    UserRelation selectByPrimaryKey(@Param("pid") Integer pid, @Param("sid") Integer sid);

    int updateByPrimaryKeySelective(UserRelation record);

    int updateByPrimaryKey(UserRelation record);

    /**
     * 查询直属上级
     * @param userId
     * @return
     */
    UserRelation selectPid(@Param("userId") Integer userId);
}