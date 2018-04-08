package com.trump.auction.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/1/12
 */
@Data
public class AuctionVo implements Serializable {
    private static final long serialVersionUID = -637023503583885617L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 状态2为已拍中
     */
    private Integer status;

    /**
     * 期次ID
     */
    private Integer auctionId;
}
