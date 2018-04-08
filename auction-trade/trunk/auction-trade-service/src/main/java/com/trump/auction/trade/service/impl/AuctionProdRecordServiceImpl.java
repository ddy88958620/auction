package com.trump.auction.trade.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.JsonResult;
import com.trump.auction.trade.dao.AuctionProductRecordDao;
import com.trump.auction.trade.domain.AuctionProductRecord;
import com.trump.auction.trade.enums.ResultEnum;
import com.trump.auction.trade.service.AuctionProdRecordService;
import com.trump.auction.trade.vo.AuctionProductRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 拍品快照
 * @author: zhangqingqiang
 * @date: 2018-01-09 14:29
 **/
@Service
@Slf4j
public class AuctionProdRecordServiceImpl implements AuctionProdRecordService {

    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private AuctionProductRecordDao auctionProductRecordDao;

    @Override
    public JsonResult saveRecord(AuctionProductRecordVo record){
        log.info("method:saveRecord start.  params:{}",record);
        int resultCount = 0;
        JsonResult result = new JsonResult();
        try {
            record.setId(null);
            resultCount = auctionProductRecordDao.insert(beanMapper.map(record, AuctionProductRecord.class));
            result.setCode(ResultEnum.SAVE_SUCCESS.getCode());
            result.setMsg(ResultEnum.SAVE_SUCCESS.getDesc());
            result.setData(resultCount);
            log.info("method:saveRecord end.  result:{}",result);
        } catch (Exception e) {
            log.error("method:saveRecord error.  msg:{}",e);
            result.setCode(ResultEnum.SAVE_ERROR.getCode());
            result.setMsg(e.getMessage());
            result.setData(resultCount);
        }
        return result;
    }


}
