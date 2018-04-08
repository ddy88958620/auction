package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.dao.read.UserProductCollectDao;
import com.trump.auction.back.frontUser.model.UserProductCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


/**
 * Created by wangYaMin on 2017/12/22.
 */
@Repository
public class UserProductCollectServiceImpl implements UserProductCollectService {

    @Autowired
    private UserProductCollectDao userProductCollectDao;


    @Override
    public Paging<UserProductCollect> selectUserProductCollectByUserId(HashMap<String,Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(userProductCollectDao.selectUserProductCollectByUserId(params));

    }

}
