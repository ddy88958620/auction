package com.trump.auction.trade.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 拍品信息查询条件
 * @author: zhangqingqiang
 * @date: 2018-01-06 11:10
 **/
public class AuctionInfoQuery implements Serializable {

    @Getter@Setter
    private Integer classifyId;

    @Getter@Setter
    private Integer status;

    @Getter@Setter
    private List<Integer> auctionIds;

    @Getter@Setter
    private Integer auctionProdId;

    @Getter@Setter
    private Integer auctionNo;

    /**
     * 开始时间段
     */
    @Getter@Setter
    private Date startTime;

    /**
     * 结束时间段
     */
    @Getter@Setter
    private Date endTime;

}
