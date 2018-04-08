package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.UserRelationModel;

/**
 * @author: zhangqingqiang.
 * @date: 2018/3/31 0031.
 * @Description: 用户关系 .
 */
public interface UserRelationService {

    /**
     * 保存用户关系
     * @param userRelationModel
     * @return
     */
    ServiceResult saveRelation (UserRelationModel userRelationModel);

    /**
     * 查询直属上级
     * @param userId
     * @return
     */
    UserRelationModel selectPid(Integer userId);
}
