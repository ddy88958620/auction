package com.trump.auction.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/1/6
 */
@Data
public class ParamVo implements Serializable {

    private static final long serialVersionUID = 1645762187137108673L;

    private Integer id ;


    private Integer userId;

    /**
     * 拍品状态1正在拍 2已拍中 3未拍中
     */
    private Integer auctionStatus;
    /**
     * 页码 默认为第一页
     */
    private int pageNum = 1;
    /**
     * 每页的数量默认为10
     */
    private int pageSize = 10;
    /**
     * 已拍中人userId
     */
    private Integer overUserId;


    /**
     * 拍品期数ID
     */
    private Integer auctionId;


    /**
     * 拍品ID
     */
    private Integer auctionProdId;

}
