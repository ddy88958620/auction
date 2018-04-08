package com.trump.auction.back.auctionProd.vo;

import com.trump.auction.trade.vo.BidVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * 竞拍订单VO
 * @author Administrator
 * @date 2018/1/6
 */
@Data
public class AuctionOrderVo implements Serializable {


    private static final long serialVersionUID = 9002389803877970760L;
    private Integer id ;

    private Integer userId;
    /**
     * 用户类型 1机器人，2真实用户
     */
    private Integer userType;

    private String userName;

    /**
     * 拍品期数ID
     */
    private Integer auctionId;
    /**
     * 拍品期数
     */
    private Integer auctionNo;

    /**
     * 拍品ID
     */
    private Integer auctionProdId;


    /**
     * 拍品名称
     */
    private String productName;

    /**
     * 状态（1正在拍 2已拍中 3未拍中）
     */
    private Integer status;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 销售金额
     */
    private BigDecimal salesAmount;

    /**
     * 出价次数
     */
    private Integer bidCount;


    /**
     * 拍品预览图
     */
    private String previewPic;

    /**
     * 商品详细逗号隔开
     */
    private String picUrls;

    /**
     * 拍卖期数
     */
    private Integer auctionNum;

    /**
     * 成交金额
     */
    private BigDecimal finalPrice;

    /**
     * 起拍金额
     */
    private BigDecimal startPrice;

    /**
     * 出价金额
     */
    private BigDecimal bidPrice;

    /**
     * 返还购物币
     */
    private BigDecimal returnPrice;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 退币比例
     */
    private BigDecimal returnPercent;


    /**
     * 手续费
     */
    private BigDecimal  poundage;

    /**
     * 每次可加价金额
     */
    private BigDecimal increasePrice;

    /**
     * 差价购买标识(1.可以2.不可以)
     */
    private Integer buyFlag;
    /**
     * 倒计时
     */
    private Integer countdown;

    /**
     * 拍品围观人数
     */
    private Integer pageView;

    /**
     * 拍品收藏人数
     */
    private Integer collectCount;

    /**
     * 拍品出价人数
     */
    private Integer personCount;






    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImg;

    /**
     * 地址
     */
    private String address;

    /**
     * 拍币
     */
    private Integer pCoin;
    /**
     * 赠币
     */
    private Integer zCoin;



}
