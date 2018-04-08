package com.trump.auction.trade.util;

/**
 * 常量配置
 */
public class ConstantUtil {
    //拍品成功信息
    public static  final String   AUCTION_TRADE_SUCCESS_LIST="auction.trade.success.list";
    //用户单一出价
    public static  final String  AUCTION_TRADE_BID_USER_="auction.trade.bid.user.";
    //用户精准列表
    public static  final String  AUCTION_TRADE_ROBOT_TRADE="auction.trade.rotbot.trade.";
    public static  final String  AUCTION_TRADE_ROBOT_TRADE_old="auction.trade.rotbot.trade.old.";

    public static  final String  AUCTION_TRADE_TIME="auction.trade.time.";

    //用户列表
    public static  final String  AUCTION_BACK_ROBOT="auction.back.robot.list";

    public static  final String AUCTION_TRADE_ROBOT_COUNT="auction.trade.robot.count";
    
    public static  final String AUCTION_TRADE_ROBOT_NEW_COUNT="auction.trade.robot.new-count.";

    //出价时间
    public static final Integer AUCTION_TRADE_BID_MS=3;
    //支付类型单次1
    public  static final  Integer AUCTION_TRADE_BID_TYPE_ONE=1;
    //支付类型多次2
    public  static final  Integer AUCTION_TRADE_BID_TYPE_TWO=2;

    public  static final  Integer AUCTION_TRADE_TXN_TYPE_ONE=1;
    public  static final  Integer AUCTION_TRADE_TXN_TYPE_TWO=2;
    public  static final  Integer AUCTION_TRADE_TXN_TYPE_THREE=3;

    public  static final  Integer AUCTION_TRADE_STATUS_ONE=1;

    public  static final  Integer  AUACTION_TRADE_DETAIL_STATUS_ONE=1;

    public  static final  Integer  AUACTION_TRADE_DETAIL_STATUS_TWO=2;


    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_TYPE_ONE=1;

    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_TYPE_TWO=2;

    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_TYPE_THREE=3;

    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_USER_TYPE_ONE=1;

    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_USER_TYPE_TWO=2;

    public  static final  Integer  AUACTION_TRADE_BID_DETAIL_USER_TYPE_THREE=3;

    public  static final  Integer  AUCTION_TRADE_TXN_STATUS_ONE=1;

    public  static final  Integer  AUCTION_TRADE_TXN_STATUS_TWO=2;

    public  static final  Integer  AUCTION_TRADE_TRIMSTART=0;

    public  static final  Integer  AUCTION_TRADE_TRIMEND=9;

    public  static final  Integer  AUACTION_USER_TYPE_ONE=1;

    public  static final  Integer  AUACTION_USER_TYPE_TWO=2;

    public  static final  Integer  AUACTION_DETAIL_STATUS_ONE=1;

    public  static final  Integer  AUACTION_DETAIL_STATUS_TWO=2;

    public  static final  Integer  AUACTION_DETAIL_STATUS_THREE=3;

    //出价reidskey
    public static  final String  AUCTION_REACTOR_BID_RESULT_QUEUE="auction.reactor.bid-result-queue.";
    public static  final String  AUCTION_REACTOR_BID_COST="auction.reactor.bid-cost.";
    public static  final String  AUCTION_REACTOR_MULTI_BID_HASH="auction.reactor.multi-bid-hash.";
    public static  final String  AUCTION_REACTOR_MULIT_BID_QUEUE="auction.reactor.multi-bid-queue.";
    public static  final String  AUCTION_REACTOR_AUCTION_CONTEXT="auction.reactor.auction-context.";
    public static  final String  AUCTION_REACTOR_BID_STATISTICS="auction.reactor.bid-statistics.";


    public  static    String  getRedisKey(String key){
        return  "{"+key+"}";
    }


}
