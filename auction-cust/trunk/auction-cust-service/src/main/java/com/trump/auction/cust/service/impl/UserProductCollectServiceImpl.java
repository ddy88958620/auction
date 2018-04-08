package com.trump.auction.cust.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.cust.dao.UserProductCollectDao;
import com.trump.auction.cust.domain.UserProductCollect;
import com.trump.auction.cust.model.UserProductCollectModel;
import com.trump.auction.cust.service.UserProductCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangYaMin on 2017/12/22.
 */
@Slf4j
@Service
public class UserProductCollectServiceImpl implements UserProductCollectService {

    @Autowired
    private UserProductCollectDao userProductCollectDao;

    @Autowired
    private BeanMapper beanMapper;


    @Override
    public Paging<UserProductCollectModel> findUserProductCollectPage(UserProductCollectModel userProductCollect, Integer pageNum, Integer pageSize) {
        log.info("findUserProductCollectPage order params:{}{}{}", userProductCollect.toString(), pageNum, pageSize);
        if (pageNum != null && pageSize != null) {
            pageNum = pageNum == 0 ? 1 : pageNum;
            pageSize = pageSize == 0 ? 10 : pageSize;
            PageHelper.startPage(pageNum, pageSize);
        }
        Paging<UserProductCollectModel> result = null;
        try {
            result = PageUtils.page(userProductCollectDao.selectUserProductCollectByUserId(beanMapper.map(userProductCollect, UserProductCollect.class)), UserProductCollectModel.class, beanMapper);
            log.info("query UserProductCollect size:" + result.getList().size());
        } catch (Throwable e) {
            log.error("findUserProductCollectPage error:", e);
            throw new IllegalArgumentException("findUserProductCollectPage error!");
        }
        return result;
    }

    @Override
    public ServiceResult saveUserProductCollect(UserProductCollectModel obj) {
        String code = "101";
        String msg = "操作失败";
        int count = userProductCollectDao.insertUserProductCollect(beanMapper.map(obj,UserProductCollect.class));
        if (count==1) {
            code = "200";
            msg = "操作成功";
        }
        return new ServiceResult(code,msg);
    }

    @Override
    public ServiceResult updateUserProductCollect(String status, Integer id) {
        int count = userProductCollectDao.updateUserProductCollect(status,id);
        if (count>0) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public boolean checkUserProductCollect(Integer userId, Integer productId, Integer periodsId) {
        int count = userProductCollectDao.selectUserProductCollectCount(userId,productId,periodsId);
        return count>0;
    }

    @Override
    public ServiceResult cancelUserProductCollect(Integer userId, Integer productId, Integer periodsId) {
        int count = userProductCollectDao.cancelUserProductCollect(userId, productId, periodsId);
        if (count>0) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

}
