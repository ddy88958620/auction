package com.trump.auction.back.activity.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.activity.dao.read.LotteryPrizeReadDao;
import com.trump.auction.back.activity.model.LotteryPrize;
import com.trump.auction.back.activity.service.LotteryPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 大转盘奖品相关
 * @author wangbo 2018/1/17.
 */
@Service
public class LotteryPrizeServiceImpl implements LotteryPrizeService {
    @Autowired
    private LotteryPrizeReadDao lotteryPrizeReadDao;

    @Override
    public Paging<LotteryPrize> findLotteryPrizeListByPage(Map<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(lotteryPrizeReadDao.selectLotteryPrizeList());
    }

    @Override
    public LotteryPrize findLotteryPrizeById(Integer id) {
        return lotteryPrizeReadDao.selectLotteryPrizeById(id);
    }

    @Override
    public List<Map<String, String>> findPlan1LotteryList() {
        return lotteryPrizeReadDao.selectPlan1LotteryList();
    }

    @Override
    public List<Map<String, String>> findPlan2LotteryList() {
        return lotteryPrizeReadDao.selectPlan2LotteryList();
    }

    @Override
    public String findPrizeNameByPrizeNo(String prizeNo) {
        return lotteryPrizeReadDao.selectPrizeNameByPrizeNo(prizeNo);
    }
}
