package com.trump.auction.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 出价记录VO
 * @author Administrator
 * @date 2018/1/6
 */
@Data
public class BidVo implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 出价金额
     */
    private BigDecimal bidPrice;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createTime;

}
