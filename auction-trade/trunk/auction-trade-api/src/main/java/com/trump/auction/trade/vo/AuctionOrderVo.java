package com.trump.auction.trade.vo;

import lombok.Data;

import java.io.PipedReader;
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

    private static final long serialVersionUID = 5888308302715664749L;

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
     * 出价记录
     */
    List<BidVo> bidVoList;

    /**
     * 主图
     */
    private String masterPic;


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
    /**
     * 起拍名称
     */
    private String startBidName;

    /**
     * 每次加价名称
     */
    private String increaseBidName;

    /**
     * 手续费名称
     */
    private String poundageName;

    /**
     * 倒计时名称
     */
    private String countdownName;

    /**
     * 差价购买名称
     */
    private String differenceName;

    /**
     * 退币比例名称
     */
    private String proportionName;

    /**
     * 下期期次ID
     */
    private Integer nextAuctionId;

    /**
     * 下期开拍时间
     */
    private Date nextStartTime;
    /**
     * 倒计时,毫秒数
     */
    private long dynamicCountdown;

    /**
     * 本期开卖距离现在的秒数
     */
    private long currentCountDown;

    /**
     * 下期开卖距离现在的秒数
     */
    private long nextCountDown;

    /**
     * 下期拍品ID
     */
    private Integer nextAuctionProdId;

    /**
     * 剩余出价次数
     */
    private Integer remainBidCount ;


    /**
     * 用户出价总次数
     */
    private Integer userBidCount;

    /**
     * 已使用次数
     */
    private Integer usedCount;

    /**
     * 竞拍规则
     */
    private String auctionRule;

}
