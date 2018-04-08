package com.trump.auction.cust.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.UserRelationDao;
import com.trump.auction.cust.domain.UserRelation;
import com.trump.auction.cust.model.UserRelationModel;
import com.trump.auction.cust.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户关系
 * @author: zhangqingqiang
 * @date: 2018-03-31 14:38
 **/
@Service
public class UserRelationServiceImpl implements UserRelationService {
    @Autowired
    private UserRelationDao userRelationDao;
    @Autowired
    private BeanMapper beanMapper;

    @Override
    public ServiceResult saveRelation(UserRelationModel userRelationModel) {
        ServiceResult result = new ServiceResult();
        try {
            int count = userRelationDao.insert(beanMapper.map(userRelationModel, UserRelation.class));

            if (count == 0) {
                result.setMsg("保存用户关系失败");
                result.setCode("101");
            }
        } catch (Exception e) {
            result.setMsg("保存用户关系失败");
            result.setCode("101");
        }
        return result;
    }

    @Override
    public UserRelationModel selectPid(Integer userId) {
        return beanMapper.map(userRelationDao.selectPid(userId),UserRelationModel.class);
    }
}
