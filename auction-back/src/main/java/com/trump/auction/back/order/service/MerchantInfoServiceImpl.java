package com.trump.auction.back.order.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.order.dao.read.MerchantInfoDao;
import com.trump.auction.back.order.model.MerchantInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 罗显 on 2017/12/25.
 */
@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {

    @Autowired
    private MerchantInfoDao merchantInfoDao;
    @Override
    public Paging<MerchantInfo> getListMerchantInfo(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(merchantInfoDao.getListMerchantInfo(params));
    }

    @Override
    public MerchantInfo getMerchantInfo(Integer id) {
        return merchantInfoDao.getMerchantInfo(id);
    }

    @Override
    public List<MerchantInfo> getListMerchantInfoBystatus(HashMap<String, Object> params) {
        return merchantInfoDao.getListMerchantInfo(params);
    }
}
