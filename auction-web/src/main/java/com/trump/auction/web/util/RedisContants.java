package com.trump.auction.web.util;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author yll
 * @date 2017年12月19日下午6:41:33
 */
public class RedisContants {
	//登陆key
	public static String LOGIN_KEY = "auction.login.key_" ;
	
	public static String LOGIN_CHECK_KEY = "auction.login.check.key_" ;
	
	/**
	 * 图形验证码
	 */
	public static String RCaptchaKey = "auction_captcha_key";
	
	public static String ForgetPwd = "auction_forget_pwd_key_";	
	
	/**
	 * 首页banner
	 */
	public static String INDEX_BANNER = "auction.back.index.banner";
	
	/**
	 * 首页icon
	 */
	public static String INDEX_ICON = "auction.back.index.icon";
	
	/**
	 * 成功拍中
	 */
	public static String  AUCTION_TRADE_SUCCESS = "auction.trade.success.list";
	
	/**
	 * 拍品分类
	 */
	public static String AUCTION_TRADE_CLASSIFY = "auction_trade_classify";
	
	/**
	 * 消息中心
	 */
	public static String MESSAGES_CENTER = "auction.back.messages.center";
	
	/**
	 * 推荐拍品
	 */
	public static  String RECOMMEND_AUCTION_PRODS = "recommend_auction_trade_prods";
	
	/**
	 * 热拍的拍品
	 */
	public static String HOT_AUCTION_PRODS = "hot_auction_trade_prods";

	/**
	 * App上架登陆方式
	 */
	public final static String APP_SHELF_SPECIES ="shelf_species";

	/**
	 * 不同应用市场不同版本返币规则
	 */
	public final static String APP_COIN_RULE ="app_coin_rule";
}
