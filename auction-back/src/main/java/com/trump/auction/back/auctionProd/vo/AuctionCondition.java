package com.trump.auction.back.auctionProd.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 查询auctionInfo的过滤条件
 * @author: zhangqingqiang
 * @date: 2018-01-19 14:54
 **/
@Data
@ToString
public class AuctionCondition implements Serializable{

    /**
     * 开始时间段
     */
    private Date startTime;

    /**
     * 结束时间段
     */
    private Date endTime;


}
