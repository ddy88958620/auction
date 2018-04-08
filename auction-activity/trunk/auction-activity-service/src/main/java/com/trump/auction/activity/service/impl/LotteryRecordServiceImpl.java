package com.trump.auction.activity.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.activity.dao.LotteryRecordDao;
import com.trump.auction.activity.domain.LotteryRecord;
import com.trump.auction.activity.model.LotteryRecordModel;
import com.trump.auction.activity.service.LotteryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 中奖记录相关
 * @author wangbo 2018/1/10.
 */
@Service
public class LotteryRecordServiceImpl implements LotteryRecordService {
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private LotteryRecordDao lotteryRecordDao;

    @Override
    public ServiceResult addLotteryRecord(LotteryRecordModel lotteryRecordModel) {
        int count = lotteryRecordDao.insertLotteryRecord(beanMapper.map(lotteryRecordModel,LotteryRecord.class));
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public Paging<LotteryRecordModel> findLotteryRecordList(Integer userId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageUtils.page(lotteryRecordDao.selectLotteryRecordList(userId),LotteryRecordModel.class,beanMapper);
    }
}
