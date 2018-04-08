package com.trump.auction.account.constant;

/**
 * Created by wangyichao on 2017-12-21 下午 04:50.
 * redis常量
 */
public class RedisConstant {
    //***********************************************************sys_config key ****************************************************************//
	public static final String EXCHANGE_POINTS = "EXCHANGE_POINTS";//积分兑换赠币规则
	public static final String EXCHANGE_POINTS_PRESENT = "EXCHANGE_POINTS_PRESENT_";//积分兑换赠币具体规则
	public static final String EXCHANGE_POINTS_EXCHANGE_LIMIT = "EXCHANGE_POINTS_EXCHANGE_LIMIT";//积分兑换赠币具体规则

	public static final String COIN_IMAGE = "COIN_IMAGE";//币--图片
	public static final String COIN_AUCTION = "COIN_AUCTION";//拍币--图片
	public static final String COIN_PRESENT = "COIN_PRESENT";//赠币--图片
	public static final String COIN_POINTS = "COIN_POINTS";//积分--图片

    //返币相关配置
    public static final String AUCTION_BACK = "AUCTION_BACK";//返币KEY
    public static final String BUY_COIN_VALID_TIME = "BUY_COIN_VALID_TIME";//返开心币的有效时间

    //***********************************************************自定义key ****************************************************************//

    public static final String AUCTION_USER_ACCOUNT = "com.trump.auction.auction_user_account.user_id.";//用户账户的信息(只用于查询用户三种账户是否存在。PS: by wangyichao)

    public static final String AUCTION_ACCOUNT_LOCK = "com.trump.auction.auction_account_lock.user_id.";//账户锁

	//首冲返币
	public static final String AUCTION_FIRST_RETURN_COIN = "com.trump.auction.auction_first_return_coin";//用户首充返币任务锁

}
