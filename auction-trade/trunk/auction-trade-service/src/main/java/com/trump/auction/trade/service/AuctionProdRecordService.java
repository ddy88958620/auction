package com.trump.auction.trade.service;

import com.cf.common.utils.JsonResult;
import com.trump.auction.trade.vo.AuctionProductRecordVo;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/9 0009.
 * @Description: 拍品快照 .
 */
public interface AuctionProdRecordService {

    JsonResult saveRecord(AuctionProductRecordVo record);
}
