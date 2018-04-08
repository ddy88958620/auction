package com.trump.auction.account.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.dto.PointsExchangePresentDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangyichao on 2017-12-19 下午 05:32.
 * <p>
 * 用户账户相关服务
 * </p>
 */
public interface AccountInfoStubService {

	/**
	 * <p>
	 * 获取时时的拍币、赠币、开心币、积分、冻结的拍币
	 * </p>
	 */
	AccountDto getAccountInfo(Integer userId);

	/**
	 * @param userId 用户ID
	 * @param type   1：拍币；2：赠币；3：积分；4：开心币
	 * @return 查询用户账户余额
	 */
	int getAuctionCoinByUserId(Integer userId, Integer type);

    /**
     * 创建充值订单
     * @param userId          用户ID
     * @param userName        用户名
     * @param userPhone       用户手机号
     * @param money           充值金额(元)
     * @param transactionType 充值类型(EnumTransactionTag)
     * @param outTradeNo      交易流水号
     */
    ServiceResult createAccountRechargeOrder(Integer userId, String userName, String userPhone, BigDecimal money, Integer transactionType, String outTradeNo);

	/**
	 * 拍币充值
	 * @param success 成功与否
	 * @param outTradeNo 流水号
	 * @param resultJson   第三方支付返回的json结果
	 * @return 充值结果
	 */
	ServiceResult rechargeUserAccount(boolean success, String outTradeNo, String resultJson);

	/**
	 * @return 获取积分兑换赠币列表
	 */
	List<PointsExchangePresentDto> getPointsExchangeList();

	/**
	 * 获取每天积分兑换赠币次数限制
	 */
	String getPointsExchangeTimesLimit();

	/**
	 * 积分兑换
	 * @param userId      用户ID
	 * @param presentCoin 赠币数量
	 */
	ServiceResult exchangePoints(Integer userId, Integer presentCoin);

	/**
	 * 签到获取积分
	 * <br>
	 * @param userId          用户ID
	 * @param userPhone       用户手机号
	 * @param transactionCoin 签到获取积分数量
	 * @return 结果
	 */
	ServiceResult signGainPoints(Integer userId, String userPhone, Integer transactionCoin);

	/**
	 * 初始化财产账户
	 * @param userId 用户id
	 * @param userPhone 用户手机号
	 * @param userName 用户名
	 * @param accountType 账户类型(EnumAccountType)，其中不包括开心币
	 */
	ServiceResult initUserAccount(Integer userId, String userPhone, String userName,Integer accountType) ;

	/**
	 * （只在竞拍时使用）使用赠币或者拍币支付(优先使用赠币支付)
	 * @param userId 用户id
	 * @param userPhone 用户手机号
	 * @param productName 拍品名称
	 * @param coin 币的数量
	 * @param productImage 商品图片
	 * @param orderId 订单id
	 */
	ServiceResult paymentWithCoin(Integer userId, String userPhone, String productName, Integer coin,String orderId ,String orderSerial,String productImage);

    /**
     * 差价购买，扣除开心币操作
     * @param userId          用户ID
     * @param userPhone       用户手机号码
     * @param transactionCoin 消耗开心币的数量
     * @param orderNo              拍卖期号
     * @param type            1:创建订单 2:使用开心币 3:取消订单(EnumDiffBuyType)
     * PS: 一条开心币不能同时创建两个或更多订单
     */
    ServiceResult reduceBuyCoin(Integer userId, String userPhone, BigDecimal transactionCoin, String orderNo, Integer type);

    /**
     * 返币操作
     * <p>
     * 目前支持四种币
     * <br>
     * 除了开心币外，都可以不用传productId, productName, productImage, coinType
     * </p>
     * @param orderNo         拍卖期号
     * @param transactionCoin 返币数量(个)
     * @param accountType     账户类型(EnumAccountType)
     * @param productId       商品ID
     * @param productName     商品名称
     * @param productImage    商品图片
     * @param coinType        返币类型(EnumBuyCoinType)
     * @param userPhone 用户手机号
	 *  @param userId 用户ID
	 */
    ServiceResult backCoinOperation(String orderNo, Integer transactionCoin, Integer accountType, Integer productId, String productName, String productImage, Integer coinType, String userPhone, Integer userId);

	/**
	 * 定时任务查询首充用户，在拍卖时，首冲金额全部失败的情况下，返回拍币或赠币
	 * @return success or fail
	 */
	ServiceResult returnBuyCoin();

	/**
	 * 拍卖-大转盘积分消耗
	 * @param userId 用户ID
	 * @param transactionPoints 消耗的积分数量
	 */
	ServiceResult lotteryCostPoints(Integer userId, Integer transactionPoints);

	/**
	 * 大转盘中奖-操作账户
	 * @param userId 用户ID
	 * @param accountType 账户类型(EnumAccountType)
	 * @param transactionAmount 获得的奖品数值
	 * @param productName 奖品名称
	 * @param productImage 图片地址(如果是开心币，需传图片地址)
	 * @param coinType        返币类型(EnumBuyCoinType)
	 * @param userPhone 用户手机号
	 */
	ServiceResult lotteryPrizeUserAccount(Integer userId, Integer accountType, Integer transactionAmount, Integer productId, String productName, String productImage, Integer coinType, String userPhone);

	/**
	 * 晒单返币
	 * @param userId 用户ID
	 * @param coinNum 返币数量
	 * @param accountType 币的账户类型(EnumAccountType)
	 */
	ServiceResult backCoinByShareAuctionOrder(Integer userId, Integer coinNum, Integer accountType);

	/**
	 * 首次分享返赠币
	 * @param userId
	 * @param coinNum
	 * @param accountType
	 */
	ServiceResult backShareCoin(Integer userId, Integer coinNum, Integer accountType);
}
