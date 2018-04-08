package com.trump.auction.back.activity.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.activity.dao.read.LotteryRecordReadDao;
import com.trump.auction.back.activity.model.LotteryRecord;
import com.trump.auction.back.activity.service.LotteryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 中奖记录相关
 * @author wangbo 2018/1/23.
 */
@Service
public class LotteryRecordServiceImpl implements LotteryRecordService {
    @Autowired
    private LotteryRecordReadDao lotteryRecordReadDao;

    @Override
    public Paging<LotteryRecord> findLotteryRecordListByPage(Map<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(lotteryRecordReadDao.selectLotteryRecordList(params));
    }
}
