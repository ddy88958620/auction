package com.trump.auction.trade.enums;

/**
 * @author Created by wangjian on 2017/12/26.
 */
public class SysConstant {
    /** 后台session名 */
    public static final String SYS_USER = "SYS_USER_";
    /** 系统参数中返回list时使用的key的后缀 */
    public static final String SYS_CONFIG_LIST = "_LIST";
    public static final String ALLOW_IP = "ALLOW_IP";
    /** 网站参数 */
    public static final String WEBSITE = "WEBSITE";

    /** 帮助信息类型 */
    public static final String HELP_TYPE = "help_type";

    /** 合作方式 */
    public static final String COOPERATION_MODE = "cooperation_mode";

    /** banner在sys_config中的key */
    public static final String AUCTION_BACK_INDEX_BANNER = "auction.back.index.banner";

    /** icon按钮在sys_config中的key */
    public static final String AUCTION_BACK_INDEX_ICON = "auction.back.index.icon";
    
    /** 需要屏蔽的关键字在sys_config中的key */
    public static final String SHIELDED_KEYWORD = "auction.shielded.keyWord";

    public static final String LOGIN_CHECK = "auction.login.check.key_";
    /**
     * 热门拍品的redis key
     */
    public final static String AUCTION_BACK_AUCTION_PROD_HOT="hot_auction_trade_prods";

    /**
     * 推荐拍品的redis key
     */
    public final static String AUCTION_BACK_AUCTION_PROD_RECOMMEND="recommend_auction_trade_prods";

}
