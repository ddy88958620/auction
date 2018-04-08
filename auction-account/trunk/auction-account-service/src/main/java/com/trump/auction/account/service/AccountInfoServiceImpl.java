package com.trump.auction.account.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.id.SnowflakeGenerator;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.GenerateNo;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.constant.RedisConstant;
import com.trump.auction.account.dao.*;
import com.trump.auction.account.domain.*;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.dto.AccountInfoRecordListDto;
import com.trump.auction.account.enums.*;
import com.trump.auction.account.model.AccountInfoRecordModel;
import com.trump.auction.account.util.DateUtil;
import com.trump.auction.cust.api.AccountRechargeRuleDetailStubService;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserRelationStubService;
import com.trump.auction.cust.enums.AccountRechargeRuleDetailDetailTypeEnum;
import com.trump.auction.cust.enums.AccountRechargeRuleRuleUserEnum;
import com.trump.auction.cust.enums.UserRechargeTypeEnum;
import com.trump.auction.cust.model.AccountRechargeRuleDetailModel;
import com.trump.auction.cust.model.AccountRechargeRuleModel;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.model.UserRelationModel;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.vo.AuctionVo;
import com.trump.auction.trade.vo.ParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {
    private BeanMapper beanMapper;
	private JedisCluster jedisCluster;
	private AccountInfoDao accountInfoDao;
	private AccountRechargeOrderDao accountRechargeOrderDao;
	private AccountInfoRecordDao accountInfoRecordDao;
	private AccountInfoDetailDao accountInfoDetailDao;
	private UserInfoStubService userInfoStubService;
	private AccountAuctionInfoDao accountAuctionInfoDao;
	private AccountBackCoinRecordDao accountBackCoinRecordDao;
	private AccountRechargeRuleStubService accountRechargeRuleStubService;
	private AccountRechargeRuleDetailStubService accountRechargeRuleDetailStubService;
	private AuctionOrderStubService auctionOrderStubService;
	private ProductInfoSubService productInfoSubService;

	@Autowired
	private UserRelationStubService userRelationStubService;

	public AccountInfoServiceImpl(BeanMapper beanMapper,JedisCluster jedisCluster, AccountInfoDao accountInfoDao, AccountRechargeOrderDao accountRechargeOrderDao,
								  AccountInfoRecordDao accountInfoRecordDao, AccountInfoDetailDao accountInfoDetailDao, UserInfoStubService userInfoStubService,AccountAuctionInfoDao accountAuctionInfoDao,
								  AccountBackCoinRecordDao accountBackCoinRecordDao, AccountRechargeRuleStubService accountRechargeRuleStubService,
                                  AccountRechargeRuleDetailStubService accountRechargeRuleDetailStubService, AuctionOrderStubService auctionOrderStubService,
                                  ProductInfoSubService productInfoSubService) {
        this.beanMapper = beanMapper;
		this.jedisCluster = jedisCluster;
		this.accountInfoDao = accountInfoDao;
		this.accountRechargeOrderDao = accountRechargeOrderDao;
		this.accountInfoRecordDao = accountInfoRecordDao;
		this.accountInfoDetailDao = accountInfoDetailDao;
		this.userInfoStubService = userInfoStubService;
		this.accountAuctionInfoDao = accountAuctionInfoDao;
		this.accountBackCoinRecordDao = accountBackCoinRecordDao;
		this.accountRechargeRuleStubService = accountRechargeRuleStubService;
		this.accountRechargeRuleDetailStubService = accountRechargeRuleDetailStubService;
		this.auctionOrderStubService = auctionOrderStubService;
		this.productInfoSubService = productInfoSubService;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AccountDto getAccountInfo(Integer userId) {
		if (userId == null || userId == 0) {
			return new AccountDto();
		}
		AccountDto accountDto = accountInfoDao.getAccountInfoByUserId(userId, EnumAccountType.AUCTION_COIN.getKey(), EnumAccountType.PRESENT_COIN.getKey(), EnumAccountType.POINTS.getKey());
		if (accountDto == null) {
			return new AccountDto();
		} else {
			return accountDto;
		}
	}

	/**
	 * 初始化账户,accounType可为空
	 * 根据业务场景，判断初始化账户类型。
	 */
	@Override
	public ServiceResult initUserAccount(Integer userId, String userPhone, String userName, Integer accountType) {
        String accountTypeName = EnumAccountType.getUserAccountTypeName(accountType);
        if (StringUtils.isEmpty(accountTypeName)) {
            log.error("初始化用户账户失败: userId:{}, userPhone:{}, userName:{}, accountType:{}", userId, userPhone, userName, accountType);
            return new ServiceResult(ServiceResult.FAILED, "初始化用户账户失败");
        }
		if (accountType == EnumAccountType.BUY_COIN.getKey().intValue()) {
			log.error("不能初始化用户开心币账户:userId:{}, userPhone:{}, userName:{}, accountType:{}", userId, userPhone, userName, accountType);
			return new ServiceResult(ServiceResult.SUCCESS, "初始化用户账户成功");
		}
		try {
            Map<String, String> map = jedisCluster.hgetAll(RedisConstant.AUCTION_USER_ACCOUNT + userId);
            if (null == map) {
                //根据accountType初始化用户账户信息
                return initUserAccountByAccountType(userId, userPhone, userName, accountType);
            } else {
                String redisAccountType = map.get(accountType.toString());
                //判断redis里有无账户信息记录
                if (StringUtils.isEmpty(redisAccountType)) {
                    //根据accountType初始化用户账户信息
                    return initUserAccountByAccountType(userId, userPhone, userName, accountType);
                }
                return new ServiceResult(ServiceResult.SUCCESS, "初始化用户账户成功");
            }
        } catch (Exception e) {
            log.error("初始化用户账户失败:userId:{}, userPhone:{}, userName:{}, accountType:{}, {}", userId, userPhone, userName, accountType, e);
            return new ServiceResult(ServiceResult.FAILED, "初始化用户账户失败");
        }
    }

    private ServiceResult initUserAccountByAccountType(Integer userId, String userPhone, String userName, Integer accountType) {
        AccountInfo auctionCoin = accountInfoDao.getAuctionCoinByUserId(userId, accountType);
        if (null != auctionCoin) {
            jedisCluster.hset(RedisConstant.AUCTION_USER_ACCOUNT + userId, accountType.toString(), userId.toString());// key, accountType, userId
        } else {
            //初始化-账户信息
            AccountInfo uaa = new AccountInfo();
            uaa.setUserId(userId);
            uaa.setUserPhone(userPhone);
            uaa.setUserName(userName);
            uaa.setCoin(0);
            uaa.setFreezeCoin(0);
            uaa.setAccountType(accountType);
            uaa.setRemark(EnumAccountType.getUserAccountTypeName(accountType));
            try {
                accountInfoDao.insertUserAccount(uaa);
                jedisCluster.hset(RedisConstant.AUCTION_USER_ACCOUNT + userId, accountType.toString(), userId.toString());// key, accountType, userId
            } catch (Exception e) {
                log.error("添加账户信息时失败: {}", uaa);
                return new ServiceResult(ServiceResult.FAILED, "操作失败");
            }
        }
        return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
    }

    @Override
	public int getAuctionCoinByUserId(Integer userId, Integer type) {
		if (type != EnumAccountType.AUCTION_COIN.getKey().intValue() && type != EnumAccountType.PRESENT_COIN.getKey().intValue() && type != EnumAccountType.POINTS.getKey().intValue() && type != EnumAccountType.BUY_COIN.getKey().intValue()) {
			return 0;
		} else {
			//开心币
			if(type == EnumAccountType.BUY_COIN.getKey().intValue()){
				return accountInfoDetailDao.getAccountInfoDetail(userId, type);
			} else {
				//拍币、赠币、积分
				AccountInfo userAuctionAccount = accountInfoDao.getAuctionCoinByUserId(userId, type);
				if (userAuctionAccount == null) {
					return 0;
				}
				return userAuctionAccount.getCoin();
			}
		}
	}

	/**
	 * 创建充值订单
	 */
	@Override
	public ServiceResult createAccountRechargeOrder(Integer userId, String userName, String userPhone, BigDecimal money, Integer transactionType, String outTradeNo) {
		if (userId == null || userId == 0) {
			return new ServiceResult(ServiceResult.FAILED, "用户信息不存在");
		}

		//初始化账户-拍币、赠币、积分
		initUserAccount(userId, userPhone, userName, EnumAccountType.AUCTION_COIN.getKey());
		initUserAccount(userId, userPhone, userName, EnumAccountType.PRESENT_COIN.getKey());
		initUserAccount(userId, userPhone, userName, EnumAccountType.POINTS.getKey());

		String rechargeTypeName = EnumTransactionTag.getEnumTransactionTagByKey(transactionType);
		String orderNo = GenerateNo.getInstance().payRecordNo("R");//订单号

		AccountRechargeOrder accountRechargeOrder = new AccountRechargeOrder();
		accountRechargeOrder.setUserId(userId);
		accountRechargeOrder.setUserName(userName);
		accountRechargeOrder.setUserPhone(userPhone);
		int coin = money.multiply(new BigDecimal(100)).intValue();
		accountRechargeOrder.setOutMoney(coin);//(分)
		accountRechargeOrder.setIntoCoin(coin);//(分)
		accountRechargeOrder.setRechargeType(transactionType);
		accountRechargeOrder.setRechargeTypeName(rechargeTypeName);
		accountRechargeOrder.setOutTradeNo(outTradeNo);//交易流水号  批次号
		accountRechargeOrder.setOrderNo(orderNo);
		accountRechargeOrder.setTradeStatus(EnumRechargeStatus.RECHARGE_ING.getKey());//充值中
		accountRechargeOrder.setOrderStatus(EnumRechargeOrderStatus.ORDER_UNDONE.getKey());//未完成的订单
		int rs = accountRechargeOrderDao.createAccountRechargeOrder(accountRechargeOrder);//生成主键
		if (rs == 1) {
			return new ServiceResult(ServiceResult.SUCCESS, "操作成功", orderNo);
		} else {
			return new ServiceResult(ServiceResult.FAILED, "操作失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult rechargeUserAccount(boolean success, String outTradeNo, String resultJson) throws Exception {
		if (success) {
			if(StringUtils.isEmpty(resultJson)) {
				log.error("充值失败, 回调数据为空: outTradeNo:{} \n resultJson:{}", outTradeNo, resultJson);
				return new ServiceResult(ServiceResult.FAILED, "充值失败, 回调数据为空");
			}
			AccountRechargeOrder accountRechargeOrder = accountRechargeOrderDao.getAccountRechargeOrderByOutTradeNo(outTradeNo);
			if(accountRechargeOrder == null) {
				return new ServiceResult(ServiceResult.FAILED, "充值订单不存在");
			}
            //防止多次充值
            if (accountRechargeOrder.getPayStatus() != null) {
                return new ServiceResult(ServiceResult.FAILED, "充值失败, 充值状态错误");
            }
            AccountInfoRecordModel accountInfoRecordModel = new AccountInfoRecordModel();
			accountInfoRecordModel.setOrderNo(accountRechargeOrder.getOrderNo());
			accountInfoRecordModel.setUserId(accountRechargeOrder.getUserId());
			accountInfoRecordModel.setUserPhone(accountRechargeOrder.getUserPhone());
			accountInfoRecordModel.setTransactionCoin(accountRechargeOrder.getOutMoney());//(分)
			accountInfoRecordModel.setTransactionType(accountRechargeOrder.getRechargeType());
			accountInfoRecordModel.setTransactionTag(accountRechargeOrder.getRechargeTypeName());
			accountInfoRecordModel.setOrderId(accountRechargeOrder.getId().toString());//充值订单的ID
			accountInfoRecordModel.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
			accountInfoRecordModel.setAccountType(EnumAccountType.AUCTION_COIN.getKey());
			int outMoney = accountRechargeOrder.getOutMoney() / 100;
			accountInfoRecordModel.setRemark(accountRechargeOrder.getRechargeTypeName() + outMoney + "拍币");
			accountInfoRecordModel.setProductName(accountRechargeOrder.getRechargeTypeName() + outMoney + "拍币");
			int coin = getAuctionCoinByUserId(accountRechargeOrder.getUserId(), EnumAccountType.AUCTION_COIN.getKey());
			int coinsRemain = coin + accountRechargeOrder.getOutMoney();
			ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);//账户操作和账户记录

            if (serviceResult.isSuccessed()) {
				//更新充值订单状态
				accountRechargeOrder.setTradeStatus(EnumRechargeStatus.RECHARGE_SUCCESS.getKey());
				accountRechargeOrder.setPayStatus(EnumRechargeStatus.RECHARGE_SUCCESS.getKey());
				accountRechargeOrder.setPayRemark(EnumRechargeStatus.RECHARGE_SUCCESS.getValue());
				accountRechargeOrder.setResultJson(resultJson);
				accountRechargeOrder.setOrderStatus(EnumRechargeOrderStatus.ORDER_DONE.getKey());
				int rs = accountRechargeOrderDao.updateUserAccountRechargeOrderStatus(accountRechargeOrder);
				if (rs == 1) {
					//返币操作
                    rechargeBackCoin(accountRechargeOrder);

					updateRechargeType(accountRechargeOrder);

					//分享充值奖励
					shareReward(accountRechargeOrder);
					return new ServiceResult(ServiceResult.SUCCESS, "充值成功");
				} else {
                    log.error("rechargeUserAccount 更新充值订单失败:{}", accountRechargeOrder);
                    throw new Exception("充值失败");
                }
			} else {
                log.error("rechargeUserAccount 充值失败:{}", accountInfoRecordModel);
                throw new Exception("充值失败");
            }
		} else {
			//更新订单为失败状态
			int rs = accountRechargeOrderDao.updateUserAccountRechargeOrderFailed(outTradeNo, EnumRechargeStatus.RECHARGE_FAILED.getKey(), EnumRechargeStatus.RECHARGE_FAILED.getValue(), resultJson);
			if (rs == 1) {
				return new ServiceResult(ServiceResult.SUCCESS, "充值失败,支付时出现错误");
			} else {
                log.error("updateUserAccountRechargeOrderFailed 更新订单为失败状态失败--订单号为:{}", outTradeNo);
                throw new Exception("充值失败");
            }
		}
	}

    /* 充值返币操作 */
    private void rechargeBackCoin(AccountRechargeOrder accountRechargeOrder) {
		try {
			Integer outMoney = accountRechargeOrder.getOutMoney();//(分)
			AccountRechargeRuleModel accountRechargeRuleModel = accountRechargeRuleStubService.findEnableRule();
			if (null != accountRechargeRuleModel) {
				Integer ruleId = accountRechargeRuleModel.getId();
				Integer ruleUser = accountRechargeRuleModel.getRuleUser();//规则用户(1,全部用户，2，首充用户)
				UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(accountRechargeOrder.getUserId());

				//首充用户-只有是首充用户才能返币
				if (ruleUser == AccountRechargeRuleRuleUserEnum.FIRST_USER.getType().intValue() && userInfoModel.getRechargeType() != UserRechargeTypeEnum.NOT_RECHARGE.getType().intValue()) {
					return;
				}

				List<AccountRechargeRuleDetailModel> list = accountRechargeRuleDetailStubService.findRuleDetailByRuleId(ruleId);
				if (null != list && list.size() > 0) {
					Collections.sort(list, new Comparator<AccountRechargeRuleDetailModel>() {
						@Override
						public int compare(AccountRechargeRuleDetailModel o1, AccountRechargeRuleDetailModel o2) {
							//升序排序
							if (o1.getRechargeMoney() > o2.getRechargeMoney()) {
								return 1;
							} else if (o1.getRechargeMoney() == o2.getRechargeMoney().intValue()) {
									return 0;
							} else {
								return -1;
							}
						}
					});
					AccountRechargeRuleDetailModel accountRechargeRuleDetailModel = null;
					for (AccountRechargeRuleDetailModel model : list) {
						//根据区间选择(向下取范围)
						if (outMoney > model.getRechargeMoney()) {
							accountRechargeRuleDetailModel = model;
						} else if (outMoney == model.getRechargeMoney().intValue()) {
							accountRechargeRuleDetailModel = model;
							break;
						} else if (outMoney < model.getRechargeMoney()) {
							break;
						}
					}

					if (null != accountRechargeRuleDetailModel) {
						int detailType = accountRechargeRuleDetailModel.getDetailType();//送币方式(1,不送，2.百分比，3.固定金额)
						Integer presentCoin = accountRechargeRuleDetailModel.getPresentCoin() / 100;//分-->分/100
						Integer points = accountRechargeRuleDetailModel.getPoints() / 100;//分-->分/100
						StringBuilder remark = new StringBuilder("返币规则:");
						if (detailType == AccountRechargeRuleDetailDetailTypeEnum.DO_NOT_SEND.getType()) {
							remark.append(AccountRechargeRuleDetailDetailTypeEnum.DO_NOT_SEND.getName());
							log.info("当前充值不赠币:presentCoin{},points{}", presentCoin, points);
						} else {
							if (detailType == AccountRechargeRuleDetailDetailTypeEnum.ONE_HUNDRED_PERCENT.getType()) {
								Integer presentCoinAmountTmp = outMoney * presentCoin / 10000;//
								Integer pointsAmountTmp = outMoney * points / 10000;
								Integer presentCoinAmount = presentCoinAmountTmp * 100;
								Integer pointsAmount = pointsAmountTmp * 100;
								remark.append(AccountRechargeRuleDetailDetailTypeEnum.ONE_HUNDRED_PERCENT.getName()).append(" ").append(presentCoin).append("%").append("赠币").append(" ").append(points).append("%").append("积分");
								if (presentCoinAmount == 0) {
									log.info("当前充值返赠币的数量为0");
								} else {
									fillRechargeBackCoin(accountRechargeOrder, presentCoinAmount, EnumAccountType.PRESENT_COIN.getKey(), remark);
								}
								if (pointsAmount == 0) {
									log.info("当前充值返积分的数量为0");
								} else {
									fillRechargeBackCoin(accountRechargeOrder, pointsAmount, EnumAccountType.POINTS.getKey(), remark);
								}
							} else {
								if (detailType == AccountRechargeRuleDetailDetailTypeEnum.FIXED_AMOUNT.getType()) {
									Integer prCoin = accountRechargeRuleDetailModel.getPresentCoin();//(分)
									Integer poCoin = accountRechargeRuleDetailModel.getPoints();//(分)
									remark.append(AccountRechargeRuleDetailDetailTypeEnum.FIXED_AMOUNT.getName()).append(" ").append(prCoin / 100).append("赠币").append(" ").append(poCoin / 100).append("积分");
									if (prCoin == 0) {
										log.info("当前充值返赠币的数量为0");
									} else {
										fillRechargeBackCoin(accountRechargeOrder, prCoin, EnumAccountType.PRESENT_COIN.getKey(), remark);
									}
									if (poCoin == 0) {
										log.info("当前充值返积分的数量为0");
									} else {
										fillRechargeBackCoin(accountRechargeOrder, poCoin, EnumAccountType.POINTS.getKey(), remark);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("充值返币失败:{}\n{}", accountRechargeOrder, e);
		}
	}

    /* 充值返币-数据填充 */
    private void fillRechargeBackCoin(AccountRechargeOrder accountRechargeOrder, Integer presentCoinAmount, Integer accountType, StringBuilder remark) throws Exception {
        AccountInfoRecordModel accountInfoRecordModel = new AccountInfoRecordModel();
        accountInfoRecordModel.setOrderNo(accountRechargeOrder.getOrderNo());
        accountInfoRecordModel.setUserId(accountRechargeOrder.getUserId());
        accountInfoRecordModel.setUserPhone(accountRechargeOrder.getUserPhone());
        accountInfoRecordModel.setTransactionCoin(presentCoinAmount);//(分)
        accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_RECHARGE_PRESENT.getKey());
        String transactionTypeName = EnumTransactionTag.TRANSACTION_RECHARGE_PRESENT.getValue();
        accountInfoRecordModel.setTransactionTag(transactionTypeName);
        accountInfoRecordModel.setOrderId(accountRechargeOrder.getId().toString());//充值订单的ID
        accountInfoRecordModel.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
        accountInfoRecordModel.setAccountType(accountType);
        int om = presentCoinAmount / 100;
        String accountTypeName = EnumAccountType.getUserAccountTypeName(accountType);

        accountInfoRecordModel.setRemark(transactionTypeName + remark.toString());//记录此时的返币规则
        accountInfoRecordModel.setProductName(transactionTypeName + om + accountTypeName);
	    int coin = getAuctionCoinByUserId(accountRechargeOrder.getUserId(), accountType);
	    int coinsRemain = coin + presentCoinAmount;
        updateAccount(accountInfoRecordModel, coinsRemain);//账户操作和账户记录
    }


    /* 更新首充状态 */
    private void updateRechargeType(AccountRechargeOrder accountRechargeOrder) {
		try {
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(accountRechargeOrder.getUserId());
			//首充
			if (userInfoModel.getRechargeType() == null || userInfoModel.getRechargeType() == UserRechargeTypeEnum.NOT_RECHARGE.getType().intValue()) {
				userInfoStubService.updateRechargeTypeById(UserRechargeTypeEnum.FIRST_RECHARGE.getType(), accountRechargeOrder.getOutMoney(), userInfoModel.getId());
			} else if (userInfoModel.getRechargeType() != null && userInfoModel.getRechargeType() == UserRechargeTypeEnum.FIRST_RECHARGE.getType().intValue()) {
				userInfoStubService.updateRechargeTypeById(UserRechargeTypeEnum.MULTIPLE_RECHARGE.getType(), null, userInfoModel.getId());
			}
		} catch (Exception e) {
			log.error("更改用户账户首充状态失败:{},{}", accountRechargeOrder, e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult exchangePoints(Integer userId, Integer presentCoin) throws Exception {
		//每天限制兑换次数
		Map<String, String> m = jedisCluster.hgetAll(RedisConstant.EXCHANGE_POINTS_EXCHANGE_LIMIT);
		String limit = m.get(RedisConstant.EXCHANGE_POINTS_EXCHANGE_LIMIT);
		int limitTimes = Integer.parseInt(limit.split(",")[1]);

		//查询当天是否已经兑换过赠币
		int exchangePointsCount = accountInfoRecordDao.getExchangePointsCount(userId, EnumTransactionTag.TRANSACTION_POINTS_EXCHANGE_PRESENT.getKey());
		if (exchangePointsCount >= limitTimes * 2) {//消耗积分和增加赠币，需乘2
			return new ServiceResult(ServiceResult.FAILED, "今天已经" + limitTimes + "次兑换赠币了");
		}

		AccountInfo accountInfo = accountInfoDao.getAuctionCoinByUserId(userId, EnumAccountType.POINTS.getKey());
		if (null == accountInfo) {
			log.error("兑换失败,积分不足\n实际是因为没有积分的账户:userId:{}", userId);
			return new ServiceResult(ServiceResult.FAILED, "兑换失败,积分不足");
		} else {
			if (accountInfo.getCoin() == 0) {
				log.error("兑换失败,积分不足:userId:{}", userId);
				return new ServiceResult(ServiceResult.FAILED, "兑换失败,积分不足");
			}
		}

		//防止赠币账户空指针，进行初始化
		initUserAccount(userId, accountInfo.getUserPhone(), accountInfo.getUserName(), EnumAccountType.PRESENT_COIN.getKey());

		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.EXCHANGE_POINTS);
		String value = map.get(RedisConstant.EXCHANGE_POINTS_PRESENT + presentCoin);
		if (StringUtils.isNotEmpty(value)) {
			JSONObject json = JSONObject.parseObject(value);
			Integer points = json.getInteger("points");//所需积分数量
			if (accountInfo.getCoin() >= points * 100) {
				String remark = "消耗" + points + "积分兑换" + presentCoin + "赠币";

				//更新积分
				AccountInfoRecordModel pointsModel = new AccountInfoRecordModel();
				pointsModel.setUserId(userId);
				pointsModel.setUserPhone(accountInfo.getUserPhone());
				pointsModel.setTransactionCoin(points * 100);//积分(分)
				pointsModel.setTransactionType(EnumTransactionTag.TRANSACTION_POINTS_EXCHANGE_PRESENT.getKey());
				pointsModel.setTransactionTag(EnumTransactionTag.TRANSACTION_POINTS_EXCHANGE_PRESENT.getValue());
				pointsModel.setProductThumbnail(json.getString("productThumbnail"));
				pointsModel.setProductImage(json.getString("productImage"));
				pointsModel.setRemark(remark);
				pointsModel.setOrderId(null);
				pointsModel.setBalanceType(EnumBalanceType.BALANCE_OUT.getKey());
				pointsModel.setAccountType(EnumAccountType.POINTS.getKey());
				pointsModel.setProductName("消耗" + points + "积分兑换" + presentCoin + "赠币");
				int coinsRemain = accountInfo.getCoin() - points * 100;
				ServiceResult serviceResult = updateAccount(pointsModel, coinsRemain);
				if (serviceResult.isSuccessed()) {
					Map<String, String> hashMap = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
					String str = hashMap.get(RedisConstant.COIN_PRESENT);
					JSONObject jsonObject = JSONObject.parseObject(str);
					//更新赠币
					AccountInfoRecordModel presentModel = new AccountInfoRecordModel();
					presentModel.setUserId(userId);
					presentModel.setUserPhone(accountInfo.getUserPhone());
					presentModel.setTransactionCoin(presentCoin * 100);//赠币(分)
					presentModel.setTransactionType(EnumTransactionTag.TRANSACTION_POINTS_EXCHANGE_PRESENT.getKey());
					presentModel.setTransactionTag(EnumTransactionTag.TRANSACTION_POINTS_EXCHANGE_PRESENT.getValue());
					presentModel.setProductThumbnail(jsonObject.getString("productThumbnail"));//缩略图
					presentModel.setProductImage(jsonObject.getString("productImage"));//图片
					presentModel.setRemark(remark);
					presentModel.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
					presentModel.setAccountType(EnumAccountType.PRESENT_COIN.getKey());
					presentModel.setProductName("消耗" + points + "积分兑换" + presentCoin + "赠币");
					int coin = getAuctionCoinByUserId(userId, EnumAccountType.PRESENT_COIN.getKey());
					int coinsRemain2 = coin + presentCoin * 100;
					ServiceResult rs = updateAccount(presentModel, coinsRemain2);
					if (rs.isSuccessed()) {
						return rs;
					} else {
						log.error("exchangePoints 积分兑换赠币失败:{}", presentModel);
						throw new Exception("积分兑换赠币-->增加赠币失败");
					}
				}
			} else {
				return new ServiceResult(ServiceResult.FAILED, "您的积分不足,兑换失败");
			}
		}
		log.error("兑换失败:presentCoin:{}", presentCoin);
		return new ServiceResult(ServiceResult.FAILED, "兑换失败");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult signGainPoints(Integer userId, String userPhone, Integer transactionCoin) throws Exception {
		AccountInfoRecordModel model = new AccountInfoRecordModel();
		model.setUserId(userId);
		model.setUserPhone(userPhone);
		model.setTransactionCoin(transactionCoin * 100);//(分)
		model.setTransactionType(EnumTransactionTag.TRANSACTION_SIGN_GAIN_POINTS.getKey());
		model.setTransactionTag(EnumTransactionTag.TRANSACTION_SIGN_GAIN_POINTS.getValue());
		model.setRemark("签到奖励" + transactionCoin + "积分");
		model.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
		model.setAccountType(EnumAccountType.POINTS.getKey());
		model.setProductName(EnumTransactionTag.TRANSACTION_SIGN_GAIN_POINTS.getValue());
		int coin = getAuctionCoinByUserId(userId, EnumAccountType.POINTS.getKey());
		int coinsRemain = coin + transactionCoin * 100;
		return updateAccount(model, coinsRemain);
	}

	/**
	 * 公共的操作账户的方法
	 * <br>
	 * 增减账户和账户记录操作
	 */
	private ServiceResult updateAccount(AccountInfoRecordModel model, Integer coinsRemain) throws Exception {
		try {
			Integer userId = model.getUserId();
			Integer accountType = model.getAccountType();
			//拍币或赠币，加锁
			String key = RedisConstant.AUCTION_ACCOUNT_LOCK + userId;
			long ttl = jedisCluster.ttl(key);
			if (ttl > 0) {//账户处于锁定状态
				log.error("updateAccount 账户处于锁定状态 - 系统繁忙,请稍后重试: {}", model);
				return new ServiceResult(ServiceResult.FAILED, "系统繁忙,请稍后重试");
			} else {
				jedisCluster.setex(key, 1, userId.toString());
			}

			//要操作账户，必须传收入或支出
			if (model.getBalanceType() == null || (model.getBalanceType().intValue() != EnumBalanceType.BALANCE_IN.getKey() && model.getBalanceType().intValue() != EnumBalanceType.BALANCE_OUT.getKey())) {
				log.error("updateAccount 收入支出类型不对, 要操作账户，必须传收入或支出");
				return new ServiceResult(ServiceResult.FAILED, "操作失败");
			}
			//如果交易是支出
			if (model.getBalanceType().intValue() == EnumBalanceType.BALANCE_OUT.getKey()) {
				model.setTransactionCoin(-model.getTransactionCoin());
			}
			int rs = accountInfoDao.updateAccount(userId, model.getTransactionCoin(), accountType);
			if (rs == 1) {
				//雪花算法
				Long orderNo = SnowflakeGenerator.create().next();

				AccountInfoRecord accountInfoRecord = new AccountInfoRecord();
				accountInfoRecord.setOrderNo(orderNo.toString());
				accountInfoRecord.setUserId(userId);
				accountInfoRecord.setUserPhone(model.getUserPhone());
				accountInfoRecord.setTransactionCoin(model.getTransactionCoin());
				accountInfoRecord.setTransactionType(model.getTransactionType());
				String tag = EnumTransactionTag.getEnumTransactionTagByKey(model.getTransactionType());
				accountInfoRecord.setTransactionTag(tag);
				accountInfoRecord.setCoin(coinsRemain);
				accountInfoRecord.setProductThumbnail(model.getProductThumbnail());
				accountInfoRecord.setProductImage(model.getProductImage());
				accountInfoRecord.setRemark(model.getRemark());
				accountInfoRecord.setOrderId(model.getOrderId());//充值订单的ID
				accountInfoRecord.setOrderSerial(model.getOrderSerial());
				accountInfoRecord.setBalanceType(model.getBalanceType());
				accountInfoRecord.setAccountType(accountType);
				accountInfoRecord.setProductName(model.getProductName());
				rs = accountInfoRecordDao.insertAccountInfoRecord(accountInfoRecord);

				//操作完之后删掉锁
				jedisCluster.del(key);

				if (rs > 0) {
					return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
				} else {
					log.error("积分兑换赠币失败:{}", model);
					throw new Exception("操作失败");
				}
			} else {
				return new ServiceResult(ServiceResult.FAILED, "操作失败");
			}
		} catch (Exception e) {
			log.error("账户操作失败:{}", e);
			throw e;
		}
	}

	/**
	 * 使用赠币或者拍币支付
	 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult paymentWithCoin(Integer userId, String userPhone, String productName, Integer coin, String orderId, String orderSerial, String productImage) throws Exception {
        if (coin != null && coin > 0) {
            Integer presentCoin = getAuctionCoinByUserId(userId, EnumAccountType.PRESENT_COIN.getKey());//赠币(分)
            Integer auctionCoin = getAuctionCoinByUserId(userId, EnumAccountType.AUCTION_COIN.getKey());//拍币(分)

            Integer coinCent = coin * 100;//(分)
            //判断是否可以进行出价(适用于使用赠币和拍币余额进行支付的情况)
            if (presentCoin + auctionCoin < coinCent) {
                return new ServiceResult(ServiceResult.FAILED, "余额不足,出价失败");
            }

			/*创建竞拍支付订单*/
            createAuctionOrder(userId, coinCent, EnumBalanceType.BALANCE_OUT.getKey(), orderId);

            AccountInfoRecordModel accountInfoRecordModel = new AccountInfoRecordModel();
            accountInfoRecordModel.setUserId(userId);
            accountInfoRecordModel.setProductName(productName);
            accountInfoRecordModel.setProductImage(productImage);
            accountInfoRecordModel.setBalanceType(EnumBalanceType.BALANCE_OUT.getKey());
            accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_PAY.getKey());
            accountInfoRecordModel.setRemark(productName + " " + EnumTransactionTag.TRANSACTION_PAY.getValue());
            accountInfoRecordModel.setOrderSerial(orderSerial);
            accountInfoRecordModel.setOrderId(orderId);

            if (presentCoin >= coinCent) {// 全部使用赠币计算
                accountInfoRecordModel.setAccountType(EnumAccountType.PRESENT_COIN.getKey());
                accountInfoRecordModel.setTransactionCoin(coinCent);//(分)
//                accountInfoRecordModel.setCoin(presentCoin - coinCent);//余额
	            int coinsRemain = presentCoin - coinCent;
                ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
                if (serviceResult.isSuccessed()) {
                    return new ServiceResult(ServiceResult.SUCCESS, "支付成功", getPayOutTradeNoList(userId, orderId));
                } else {
                    log.error("paymentWithCoin 支付失败:{}", accountInfoRecordModel);
                    throw new Exception("支付失败");
                }
            } else if (presentCoin > 0 && auctionCoin > 0) {// 使用部分赠币和部分拍币
                /*当前使用所有赠币，剩余币由拍币支付*/
                accountInfoRecordModel.setAccountType(EnumAccountType.PRESENT_COIN.getKey());
                accountInfoRecordModel.setTransactionCoin(presentCoin);//(分)
//                accountInfoRecordModel.setCoin(0);

                ServiceResult serviceResult = updateAccount(accountInfoRecordModel, 0);//使用赠币支付

				/*当前已支付部分赠币--->使用拍币支付剩余部分*/
                if (serviceResult.isSuccessed()) {
                    accountInfoRecordModel.setAccountType(EnumAccountType.AUCTION_COIN.getKey());
                    Integer buyAuctionCoin = coinCent - presentCoin;//需拍币支付金额
//                    accountInfoRecordModel.setCoin(auctionCoin - buyAuctionCoin);//拍币余额
                    accountInfoRecordModel.setTransactionCoin(buyAuctionCoin);
                    int coinsRemain = auctionCoin - coinCent + buyAuctionCoin;
                    serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
                    if (serviceResult.isSuccessed()) {
                        return new ServiceResult(ServiceResult.SUCCESS, "支付成功", getPayOutTradeNoList(userId, orderId));
                    } else {
                        log.error("拍币不足,支付失败");
                        throw new Exception("拍币不足,支付失败");
                    }
                } else {
                    log.error("当前已支付部分赠币--->使用拍币支付剩余部分: 使用赠币支付失败");
                    throw new Exception("支付失败");
                }
            } else {//使用拍币支付
                accountInfoRecordModel.setAccountType(EnumAccountType.AUCTION_COIN.getKey());
                accountInfoRecordModel.setTransactionCoin(coinCent);//(分)
//                accountInfoRecordModel.setCoin(auctionCoin - coinCent);
	            int coinsRemain = auctionCoin - coinCent;
                ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
                if (serviceResult.isSuccessed()) {
                    return new ServiceResult(ServiceResult.SUCCESS, "支付成功", getPayOutTradeNoList(userId, orderId));
                } else {
                    log.error("使用拍币支付失败");
                    throw new Exception("支付失败");
                }
            }
        } else {
            return new ServiceResult(ServiceResult.FAILED, "输入币种数量错误");
        }
    }

    //如果扣币成功，根据订单号返回交易流水
    private List<AccountInfoRecordListDto> getPayOutTradeNoList(Integer userId, String orderId) {
		List<AccountInfoRecord> accountInfoRecord =accountInfoRecordDao.getAccountInfoRecordListByOrderId(userId, orderId);

		for (AccountInfoRecord accountInfoRecordTmp :accountInfoRecord){
			if( accountInfoRecordTmp.getTransactionCoin() !=null && accountInfoRecordTmp.getTransactionCoin()!=0){
				accountInfoRecordTmp.setTransactionCoin(accountInfoRecordTmp.getTransactionCoin()/100);
			}
		}
        return beanMapper.mapAsList(accountInfoRecord,AccountInfoRecordListDto.class);
    }

    /**
     * 1.创建订单<br>
     * 2.差价购买，扣除开心币操作<br>
     * 3.取消订单
     */
    @Override
	@Transactional
    public ServiceResult reduceBuyCoin(Integer userId, String userPhone, BigDecimal transactionCoin, String orderNo, Integer type) throws Exception {
        if (type == EnumDiffBuyType.CREATE_ORDER.getKey().intValue()) {
            //根据期号查询开心币的详情
            List<AccountInfoDetail> list = accountInfoDetailDao.getAvailableBuyCoinById(orderNo, EnumBuyCoinStatus.STATUS_UNUSED.getStatus(), userId);
            if (null == list || list.size() == 0) {
                return new ServiceResult(ServiceResult.FAILED, "开心币已使用或不存在");
            }
            AccountInfoDetail accountInfoDetail = list.get(0);

            Integer coin = accountInfoDetail.getCoin();//(分)
            Integer transactionCoinCent = transactionCoin.multiply(new BigDecimal(100)).intValue();//(分)
            //此处判断的目的是为了：使用的开心币 等于开心币的面值 和 小于开心币的面值 两种情况
            if (coin < transactionCoinCent) {
                log.info("开心币使用有误,请重新操作: accountInfoDetail:{}, transactionCoin * 100:{}", accountInfoDetail, transactionCoin);
                return new ServiceResult(ServiceResult.FAILED, "开心币金额使用有误,请重新操作");
            } else {
                //更新accountInfoDetail表为已使用
                int rs = accountInfoDetailDao.reduceBuyCoin(accountInfoDetail.getId(), EnumBuyCoinStatus.STATUS_ALL_USED.getStatus(), transactionCoinCent);
                if (rs == 1) {
                    return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
                } else {
                    return new ServiceResult(ServiceResult.FAILED, "操作失败");
                }
            }
        } else if (type == EnumDiffBuyType.DIFF_BUY.getKey().intValue()) {
            //根据期号查询所有
            List<AccountInfoDetail> list = accountInfoDetailDao.getAvailableBuyCoinById(orderNo, EnumBuyCoinStatus.STATUS_ALL_USED.getStatus(), userId);
            //创建使用记录
            AccountInfoDetail accountInfoDetail = list.get(0);
            if(null == accountInfoDetail.getUnavailableCoin()) {
            	return new ServiceResult(ServiceResult.FAILED, "开心币已使用");//当status=3, unavailableCoin不为空的时候，可以使用开心币
            }
            int coin = getAuctionCoinByUserId(userId, EnumAccountType.BUY_COIN.getKey());
            //雪花算法
            Long orderId = SnowflakeGenerator.create().next();

            AccountInfoRecord accountInfoRecord = new AccountInfoRecord();
            accountInfoRecord.setOrderNo(orderId.toString());
            accountInfoRecord.setUserId(userId);
            accountInfoRecord.setUserPhone(accountInfoDetail.getUserPhone());
            accountInfoRecord.setTransactionCoin(-accountInfoDetail.getCoin());//全部使用
			Integer transactionType = EnumTransactionTag.TRANSACTION_DIFF_BUY.getKey();
            accountInfoRecord.setTransactionType(transactionType);
            String tag = EnumTransactionTag.getEnumTransactionTagByKey(transactionType);
            accountInfoRecord.setTransactionTag(tag);
            accountInfoRecord.setCoin(coin);
            ProductInfoModel productInfoModel = productInfoSubService.getProductByProductId(accountInfoDetail.getProductId());
            accountInfoRecord.setProductImage(productInfoModel.getPicUrl());
            accountInfoRecord.setRemark(accountInfoDetail.getProductName() + "-" + tag);
            accountInfoRecord.setOrderId(orderNo);//充值订单的ID
            accountInfoRecord.setBalanceType(EnumBalanceType.BALANCE_OUT.getKey());
            accountInfoRecord.setAccountType(EnumAccountType.BUY_COIN.getKey());
            accountInfoRecord.setProductName(accountInfoDetail.getProductName());
            int rs = accountInfoRecordDao.insertAccountInfoRecord(accountInfoRecord);
            if (rs > 0) {
            	accountInfoDetail.setStatus(EnumBuyCoinStatus.STATUS_ALL_USED.getStatus().toString());
            	accountInfoDetail.setUnavailableCoin(null);
	            accountInfoDetailDao.updateAccountInfoDetailStatus(accountInfoDetail);
	            return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
            } else {
                log.error("insertAccountInfoRecord 创建开心币使用记录时出错:{}", accountInfoRecord);
                return new ServiceResult(ServiceResult.FAILED, "操作失败");
            }
        } else if (type == EnumDiffBuyType.CANCEL_ORDER.getKey().intValue()) {
            //根据期号查询所有
            List<AccountInfoDetail> list = accountInfoDetailDao.getAvailableBuyCoinById(orderNo, EnumBuyCoinStatus.STATUS_ALL_USED.getStatus(), userId);
            if (null != list && list.size() > 0) {
                AccountInfoDetail accountInfoDetail = list.get(0);
                if(null == accountInfoDetail.getUnavailableCoin()){
                	return new ServiceResult(ServiceResult.FAILED, "开心币已经使用");//当status=3, unavailableCoin不为空的时候，可以取消订单
                }
                accountInfoDetail.setStatus(EnumBuyCoinStatus.STATUS_UNUSED.getStatus().toString());
                accountInfoDetail.setUnavailableCoin(null);
                int rs = accountInfoDetailDao.updateAccountInfoDetailStatus(accountInfoDetail);
                if (rs == 1) {
                    return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
                } else {
                    return new ServiceResult(ServiceResult.FAILED, "操作失败");
                }
            } else {
                return new ServiceResult(ServiceResult.FAILED, "开心币信息不存在");
            }
        } else {
            log.info("操作错误 type:{}", type);
            return new ServiceResult(ServiceResult.FAILED, "操作错误");
        }
    }

	/**
	 * 返币操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult backCoinOperation(String orderNo, Integer transactionCoin, Integer accountType, Integer productId, String productName, String productImage, Integer coinType, String userPhone, Integer userId) throws Exception {
		//查询订单号是否可用(取最新一条竞拍交易订单)
		AccountInfoRecord accountInfoRecord = accountInfoRecordDao.getAccountInfoRecordByOrderSerial(orderNo, EnumTransactionTag.TRANSACTION_PAY.getKey(), userId);
		if (null == accountInfoRecord) {
			log.error("backCoinOperation, 订单号不存在:orderNo:{}", orderNo);
			return new ServiceResult(ServiceResult.FAILED, "订单号不存在");
		}

		if (transactionCoin == 0) {
			return new ServiceResult(ServiceResult.SUCCESS, "返币为零");
		}

		//根据订单号查询返币成功的记录
		AccountBackcoinRecord accountBackcoinRecord = accountBackCoinRecordDao.getAccountBackCoinRecordByOrderNo(orderNo, accountType, userId);
		if (accountBackcoinRecord != null) {
			return new ServiceResult(ServiceResult.FAILED, "已返币");
		}

		AccountInfoRecordModel model = fillModelByAccountType(transactionCoin, accountType, productName, productImage, userId, orderNo);

		//插入返币记录
		int rs = insertAccountInfoDetail(userId, coinType, model.getTransactionType(), transactionCoin, productId, productName, userPhone, orderNo, accountType);
		if (rs == 1) {
			int coin = getAuctionCoinByUserId(userId, accountType);
			int coinsRemain = coin + transactionCoin * 100;
			//开心币不更新账户表
			if (accountType == EnumAccountType.BUY_COIN.getKey().intValue()) {//开心币
				AccountInfoRecord air = new AccountInfoRecord();
				//雪花算法
				Long snowNo = SnowflakeGenerator.create().next();
				air.setOrderNo(snowNo.toString());
				air.setUserId(userId);
				air.setUserPhone(model.getUserPhone());
				air.setTransactionCoin(model.getTransactionCoin());
				air.setTransactionType(model.getTransactionType());
				String tag = EnumTransactionTag.getEnumTransactionTagByKey(model.getTransactionType());
				air.setTransactionTag(tag);
				air.setCoin(coinsRemain);
				air.setProductThumbnail(model.getProductThumbnail());
				air.setProductImage(model.getProductImage());
				air.setRemark(model.getRemark());
				air.setOrderId(model.getOrderId());//充值订单的ID
				air.setOrderSerial(model.getOrderSerial());
				air.setBalanceType(model.getBalanceType());
				air.setAccountType(accountType);
				air.setProductName(model.getProductName());
				rs = accountInfoRecordDao.insertAccountInfoRecord(air);
				if (rs > 0) {
					AccountBackcoinRecord record = new AccountBackcoinRecord();
					record.setUserId(userId);
					record.setOrderNo(orderNo);
					record.setTransactionCoin(transactionCoin * 100);//(分)
					record.setAccountType(accountType);
					record.setAccountTypeName(EnumAccountType.getUserAccountTypeName(accountType));
					rs = accountBackCoinRecordDao.insertAccountBackCoinRecord(record);
					if (rs == 1) {
						return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
					} else {
						log.error("insertAccountBackCoinRecord 插入返币记录失败:{}", record);
						throw new Exception("返币失败");
					}
				} else {
					log.error("insertAccountBackCoinRecord 插入账户记录失败:{}", air);
					throw new Exception("返币失败");
				}
			} else {//拍币、赠币、积分

				ServiceResult serviceResult = updateAccount(model, coinsRemain);
				if (serviceResult.isSuccessed()) {
					AccountBackcoinRecord record = new AccountBackcoinRecord();
					record.setUserId(userId);
					record.setOrderNo(orderNo);
					record.setTransactionCoin(transactionCoin * 100);//(分)
					record.setAccountType(accountType);
					record.setAccountTypeName(EnumAccountType.getUserAccountTypeName(accountType));
					rs = accountBackCoinRecordDao.insertAccountBackCoinRecord(record);
					if (rs == 1) {
						return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
					} else {
						log.error("insertAccountBackCoinRecord 插入返币记录失败:{}", record);
						throw new Exception("操作失败");
					}
				} else {
					log.error("updateAccount 更新账户信息失败:{}", model);
					throw new Exception("操作失败");
				}
			}
		} else {
			log.error("insertAccountInfoDetail 插入返币详情失败:userId:{}, coinType:{}, transactionType:{}, productId:{}, productName:{}", userId, coinType, model.getTransactionType(), transactionCoin, productId, productName);
			throw new Exception("返币失败");
		}
	}

	private AccountInfoRecordModel fillModelByAccountType(Integer transactionCoin, Integer accountType, String productName, String productImage, Integer userId, String orderNo) {
		AccountInfoRecordModel model = new AccountInfoRecordModel();
		model.setUserId(userId);
		model.setUserPhone(null);
		model.setTransactionCoin(transactionCoin * 100);//(分)

		int auctionCoin = EnumTransactionTag.TRANSACTION_BACK_COIN.getKey();
		int presentCoin = EnumTransactionTag.TRANSACTION_BACK_PRESENT.getKey();
		int points = EnumTransactionTag.TRANSACTION_BACK_POINTS.getKey();
		int buyCoin = EnumTransactionTag.TRANSACTION_BACK_BUY.getKey();
		switch (accountType) {
			case 1:
				setModel(model, auctionCoin);
				if(StringUtils.isEmpty(productImage)){
					Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
					String str = map.get(RedisConstant.COIN_AUCTION);
					JSONObject jsonObject = JSONObject.parseObject(str);
					productImage = jsonObject.getString("productImage");
				}
				break;
			case 2:
				setModel(model, presentCoin);
				if(StringUtils.isEmpty(productImage)){
					Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
					String str = map.get(RedisConstant.COIN_PRESENT);
					JSONObject jsonObject = JSONObject.parseObject(str);
					productImage = jsonObject.getString("productImage");
				}
				break;
			case 3:
				setModel(model, points);
				if(StringUtils.isEmpty(productImage)){
					Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
					String str = map.get(RedisConstant.COIN_POINTS);
					JSONObject jsonObject = JSONObject.parseObject(str);
					productImage = jsonObject.getString("productImage");
				}
				break;
			case 4:
				setModel(model, buyCoin);
				break;
			default:
				break;
		}
		model.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
		model.setAccountType(accountType);
		model.setProductName(productName);
        model.setOrderId(orderNo);
		model.setProductImage(productImage);
		return model;
	}

	private void setModel(AccountInfoRecordModel model, int auctionCoin) {
		model.setTransactionType(auctionCoin);
		model.setTransactionTag(EnumTransactionTag.getEnumTransactionTagByKey(auctionCoin));
		model.setRemark(EnumTransactionTag.getEnumTransactionTagByKey(auctionCoin));
	}

	private int insertAccountInfoDetail(Integer userId, Integer buyCoinType, Integer transactionType, Integer transactionCoin, Integer productId, String productName, String userPhone, String orderNo, Integer accountType) {
		AccountInfoDetail accountInfoDetail = new AccountInfoDetail();
		accountInfoDetail.setOrderNo(orderNo);
		accountInfoDetail.setUserId(userId);
		accountInfoDetail.setUserPhone(userPhone);
		accountInfoDetail.setCoinType(accountType);
		accountInfoDetail.setTransactionType(transactionType);
		accountInfoDetail.setTransactionTag(EnumTransactionTag.getEnumTransactionTagByKey(transactionType));
		accountInfoDetail.setBuyCoinType(buyCoinType);
		accountInfoDetail.setCoin(transactionCoin * 100);//(分)
		accountInfoDetail.setProductId(productId);
		accountInfoDetail.setProductName(productName);
		accountInfoDetail.setStatus(EnumBuyCoinStatus.STATUS_UNUSED.getStatus().toString());
		//获取有效期
		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.AUCTION_BACK);
		String validTime = map.get(RedisConstant.BUY_COIN_VALID_TIME);
		Date now = new Date();
		accountInfoDetail.setValidStartTime(now);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(validTime));
//		calendar.set(Calendar.HOUR_OF_DAY, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.SECOND, 59);
//		calendar.set(Calendar.MILLISECOND, 0);
		accountInfoDetail.setValidEndTime(calendar.getTime());
		accountInfoDetail.setRemark(productName + "-" + EnumTransactionTag.getEnumTransactionTagByKey(transactionType));
		return accountInfoDetailDao.insertAccountInfoDetail(accountInfoDetail);
	}

	private void createAuctionOrder(Integer userId, Integer transactionCoin, Integer balanceType, String orderid) throws Exception {
		AccountAuctionInfo accountAuctionInfo = new AccountAuctionInfo();
		Long orderNo = SnowflakeGenerator.create().next();
		accountAuctionInfo.setOrderNo(orderNo.toString());
		accountAuctionInfo.setUserId(userId);
		accountAuctionInfo.setTransactionCoin(transactionCoin);
		accountAuctionInfo.setBalanceType(balanceType);
		accountAuctionInfo.setOrderId(orderid);
		accountAuctionInfoDao.insertAccountAuctionInfo(accountAuctionInfo);
	}

    @Override
    public ServiceResult returnBuyCoin() {
        ServiceResult result = new ServiceResult(ServiceResult.FAILED,"失败");
        String key = RedisConstant.AUCTION_FIRST_RETURN_COIN;
        long ttl = jedisCluster.ttl(key);
        if (ttl > 0) {
            log.info("returnBuyCoin user first recharge doing");
            return result;
        }

        jedisCluster.setex(key,60*60 , key);

        /*获取首充返币用户列表*/
        List<AuctionVo> returnUserList = getReturnUserList();

        //手动创建首充用户失败列表
        for (AuctionVo auctionVo:returnUserList){
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setId(auctionVo.getUserId());

           if(auctionVo.getStatus()!=null ){
           		//auctionVo.getStatus() == 2  状态等于2的代表竞拍成功，不需要反币
               if(auctionVo.getStatus() != 2){
                   ServiceResult returnResult =  returnBuyAccount(userInfoModel);
                   if(ServiceResult.SUCCESS.equals(returnResult.getCode())){
                       userInfoStubService.updateRechargeTypeById(UserRechargeTypeEnum.FIRST_RECHARGE_SUCCESS.getType(), null, auctionVo.getUserId());
                   }else{
                       userInfoStubService.updateRechargeTypeById(UserRechargeTypeEnum.FIRST_RECHARGE_WAIT.getType(), null, auctionVo.getUserId());
                   }
               }else{
                   userInfoStubService.updateRechargeTypeById(UserRechargeTypeEnum.FIRST_RECHARGE_OVER.getType(), null, auctionVo.getUserId());
               }
           }
        }
        return null;
    }

	/**
     *  查询竞拍记录，判断这些用户首充竞拍是否成功
     */
    private List<AuctionVo> getReturnUserList(){
        List<AuctionVo> newAuctionVoList = new ArrayList<>();
        try {
            //查询首充用户
            List<UserInfoModel> userLinfo = userInfoStubService.findFirstRechargeList();

            /*循环查询这些用户是否交易金额大于首充金额*/

            for (UserInfoModel userInfo:userLinfo){
				List< ParamVo > paramVoList = new ArrayList<>();
                Integer userID = userInfo.getId();
                Integer rechatgeMonery  = userInfo.getRechargeMoney();

                HashMap<String,Object> map = new HashMap<>();
                map.put("userId",userID);
                map.put("transactionType",EnumTransactionTag.TRANSACTION_PAY.getKey());
                List<AccountInfoRecord> accountInfos =  accountInfoRecordDao.getAccountRecordListByParameter(map);

                Integer firstMoney = 0;
                boolean flag=false;

                for (AccountInfoRecord ar :accountInfos){
					if(EnumAccountType.AUCTION_COIN.getKey().equals(ar.getAccountType()) && ar.getOrderSerial()!=null){
						firstMoney = firstMoney+ Math.abs(ar.getTransactionCoin());
						ParamVo paramVo =new ParamVo();
						paramVo.setUserId(ar.getUserId());
						paramVo.setAuctionId(Integer.valueOf(ar.getOrderSerial()));
						paramVoList.add(paramVo);
						if(firstMoney>=rechatgeMonery){
							flag = true;
							break;
						}
					}
				}

				if(flag){
					/*筛选后的用户列表。需查询竞拍记录*/
					List<AuctionVo> auctionVoList = new ArrayList<>();
					if(paramVoList.size()>0){
						try {
							auctionVoList = auctionOrderStubService.getUserOrder(paramVoList);
						} catch (Exception e) {
							log.error("auctionOrderStubService  getUserOrder error ",e);
						}
					}

					Map<Integer,Integer> mapIdStatus = new LinkedHashMap<>();
					for (AuctionVo vo : auctionVoList) {
						Integer idTmp =  mapIdStatus.get(vo.getUserId());
						if(idTmp == null || vo.getStatus() == 2){
							mapIdStatus.put(vo.getUserId(),vo.getStatus());
						}
					}
					for (Map.Entry<Integer, Integer> entry : mapIdStatus.entrySet()) {
						AuctionVo voo = new AuctionVo();
						Integer key = entry.getKey();
						Integer value = entry.getValue();
						voo.setUserId(key);
						voo.setStatus(value);
						newAuctionVoList.add(voo);
					}
				}
            }


        } catch (Exception e) {
           log.error("getReturnUserList error,{}",e);
        }
        return newAuctionVoList;
    }

    private ServiceResult  returnBuyAccount(UserInfoModel userInfoModel){
        ServiceResult result =new  ServiceResult(ServiceResult.FAILED,"系统异常");
        try {
            AccountInfoRecordModel model = new AccountInfoRecordModel();
            UserInfoModel userInfo = userInfoStubService.findUserInfoById(userInfoModel.getId());
            model.setUserId(userInfo.getId());
            model.setUserPhone(userInfo.getUserPhone());
            model.setTransactionCoin(userInfo.getRechargeMoney());//(分)
            model.setTransactionType(EnumTransactionTag.TRANSACTION_BACK_PRESENT.getKey());
            model.setTransactionTag(EnumTransactionTag.TRANSACTION_BACK_PRESENT.getValue());
            model.setRemark("首充" + userInfo.getRechargeMoney() / 100 + "返赠币");
            model.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
            model.setAccountType(EnumAccountType.PRESENT_COIN.getKey());
            model.setProductName(EnumTransactionTag.TRANSACTION_BACK_PRESENT.getValue());
	        int coin = getAuctionCoinByUserId(userInfoModel.getId(), EnumAccountType.PRESENT_COIN.getKey());
	        int coinsRemain = coin + userInfo.getRechargeMoney();
            result =updateAccount(model, coinsRemain);
        } catch (Exception e) {
           log.error("returnBuyAccount error,userId,{},{},",userInfoModel.getId(),e);
        }
        return result;
    }

	/**
	 * 拍卖-大转盘积分消耗
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult lotteryCostPoints(Integer userId, Integer transactionPoints) throws Exception {
		AccountInfo accountInfo = accountInfoDao.getAuctionCoinByUserId(userId, EnumAccountType.POINTS.getKey());
		int points = accountInfo.getCoin();
		if (points >= transactionPoints * 100) {
			//更新积分
			AccountInfoRecordModel pointsModel = new AccountInfoRecordModel();
			pointsModel.setUserId(userId);
			pointsModel.setUserPhone(accountInfo.getUserPhone());
			pointsModel.setTransactionCoin(transactionPoints * 100);//积分(分)
			pointsModel.setTransactionType(EnumTransactionTag.TRANSACTION_LOTTERY_COST.getKey());
			pointsModel.setTransactionTag(EnumTransactionTag.TRANSACTION_LOTTERY_COST.getValue());

			Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
			String str = map.get(RedisConstant.COIN_POINTS);
			JSONObject jsonObject = JSONObject.parseObject(str);

			pointsModel.setProductThumbnail(jsonObject.getString("productThumbnail"));
			pointsModel.setProductImage(jsonObject.getString("productImage"));
			pointsModel.setRemark(EnumTransactionTag.TRANSACTION_LOTTERY_COST.getValue());
			pointsModel.setOrderId(null);
			pointsModel.setBalanceType(EnumBalanceType.BALANCE_OUT.getKey());
			pointsModel.setAccountType(EnumAccountType.POINTS.getKey());
			pointsModel.setProductName("大转盘消耗" + transactionPoints + "积分");
			int coin = getAuctionCoinByUserId(userId, EnumAccountType.POINTS.getKey());
			int coinsRemain = coin - transactionPoints * 100;
			ServiceResult serviceResult = updateAccount(pointsModel, coinsRemain);
			if (serviceResult.isSuccessed()) {
				return new ServiceResult(ServiceResult.SUCCESS, "积分扣除成功");
			} else {
			    log.error("updateAccount 大转盘积分扣除失败:{}", pointsModel);
				throw new Exception("积分扣除失败");
			}
		} else {
			return new ServiceResult(ServiceResult.FAILED, "积分不足");
		}
	}

	/**
	 * 大转盘中奖-操作账户
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceResult lotteryPrizeUserAccount(Integer userId, Integer accountType, Integer transactionAmount, Integer productId, String productName, String productImage, Integer coinType, String userPhone) throws Exception {
		AccountInfo accountInfo = accountInfoDao.getAuctionCoinByUserId(userId, accountType);
		AccountInfoRecordModel accountInfoRecordModel = fillModelByAccountType(transactionAmount, accountType, productName, productImage, userId, null);
		accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_LOTTERY_PRIZE.getKey());
		accountInfoRecordModel.setTransactionTag(EnumTransactionTag.TRANSACTION_LOTTERY_PRIZE.getValue());
		accountInfoRecordModel.setRemark(EnumTransactionTag.TRANSACTION_LOTTERY_PRIZE.getValue() + transactionAmount + productName);
		accountInfoRecordModel.setUserPhone(accountInfo.getUserPhone());

		int coin = getAuctionCoinByUserId(userId, accountType);
		int coinsRemain = coin + transactionAmount * 100;
		ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
		if (serviceResult.isSuccessed()) {
			if (accountType.intValue() == EnumAccountType.BUY_COIN.getKey()) {
			    String orderNo = SnowflakeGenerator.create().next() + "";
				int rs = insertAccountInfoDetail(userId, coinType, EnumTransactionTag.TRANSACTION_LOTTERY_PRIZE.getKey(), transactionAmount, productId, productName, userPhone, orderNo, accountType);
				if (rs == 1) {
					return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
				} else {
				    log.error("insertAccountInfoDetail 插入开心币详情失败:userId:{}, coinType:{}, transactionType:{}, productId:{}, productName:{}", userId, coinType, accountInfoRecordModel.getTransactionType(), transactionAmount, productId, productName);
                }
			} else {
				return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
			}
		} else {
		    log.error("updateAccount 更新账户失败:{}", accountInfoRecordModel);
        }
		throw new Exception("操作失败");
	}

	@Override
	public ServiceResult backCoinByShareAuctionOrder(Integer userId, Integer coinNum, Integer accountType) throws Exception {
		if(accountType != EnumAccountType.BUY_COIN.getKey().intValue()) {
			AccountInfo accountInfo = accountInfoDao.getAuctionCoinByUserId(userId, accountType);

			if(null == accountInfo){
				log.info("accountInfo is null query by userId:{}",userId);
				return new ServiceResult(ServiceResult.FAILED, "账户信息不存在");
			}

			AccountInfoRecordModel accountInfoRecordModel = new AccountInfoRecordModel();
			accountInfoRecordModel.setUserId(userId);
			accountInfoRecordModel.setUserPhone(accountInfo.getUserPhone());
			accountInfoRecordModel.setTransactionCoin(coinNum * 100);
			int coin = getAuctionCoinByUserId(userId, accountType);
			int coinsRemain = coin + coinNum * 100;
			if(accountType == EnumAccountType.AUCTION_COIN.getKey().intValue()) {
				accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_SHARE_BACK_COIN.getKey());
			} else {
				if(accountType == EnumAccountType.PRESENT_COIN.getKey().intValue()) {
					accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_SHARE_BACK_PRESENT_COIN.getKey());
				} else {
					if(accountType == EnumAccountType.POINTS.getKey().intValue()) {
						accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_SHARE_BACK_POINTS.getKey());
					} else {
						return new ServiceResult(ServiceResult.FAILED, "返币类型错误");
					}
				}
			}
			accountInfoRecordModel.setTransactionTag(EnumTransactionTag.getEnumTransactionTagByKey(accountInfoRecordModel.getTransactionType()));
			accountInfoRecordModel.setCoin(accountInfo.getCoin());
			accountInfoRecordModel.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
			accountInfoRecordModel.setAccountType(accountType);
			accountInfoRecordModel.setProductName("晒单返" + coinNum + EnumAccountType.getUserAccountTypeName(accountType));
			accountInfoRecordModel.setRemark(accountInfoRecordModel.getTransactionTag());
			ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
			if(serviceResult.isSuccessed()) {
				return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
			}
		} else {
			return new ServiceResult(ServiceResult.FAILED, "返币类型错误");
		}
		return new ServiceResult(ServiceResult.FAILED, "操作失败");
	}

	/**
	 * 首次分享注册返赠币
	 * @param userId
	 * @param coinNum
	 * @param accountType
	 * @return
	 * @throws Exception
	 */
	@Override
	public ServiceResult backShareCoin(Integer userId, Integer coinNum, Integer accountType) throws Exception {
		if(null==accountType){
			return new ServiceResult(ServiceResult.FAILED, "返币类型错误");
		}
		AccountInfo accountInfo = accountInfoDao.getAuctionCoinByUserId(userId, accountType);

		if(null == accountInfo){
			log.info("accountInfo is null query by userId:{}",userId);
			return new ServiceResult(ServiceResult.FAILED, "账户信息不存在");
		}

		AccountInfoRecordModel accountInfoRecordModel = new AccountInfoRecordModel();
		accountInfoRecordModel.setUserId(userId);
		accountInfoRecordModel.setUserPhone(accountInfo.getUserPhone());
		accountInfoRecordModel.setTransactionCoin(coinNum * 100);
		int coin = getAuctionCoinByUserId(userId, accountType);
		int coinsRemain = coin + coinNum * 100;
		accountInfoRecordModel.setTransactionType(EnumTransactionTag.TRANSACTION_SHARE_POINTS.getKey());
		accountInfoRecordModel.setTransactionTag(EnumTransactionTag.getEnumTransactionTagByKey(accountInfoRecordModel.getTransactionType()));
		accountInfoRecordModel.setCoin(accountInfo.getCoin());
		accountInfoRecordModel.setBalanceType(EnumBalanceType.BALANCE_IN.getKey());
		accountInfoRecordModel.setAccountType(accountType);
		accountInfoRecordModel.setProductName("分享返" + coinNum + EnumAccountType.getUserAccountTypeName(accountType));
		accountInfoRecordModel.setRemark(accountInfoRecordModel.getTransactionTag());
		ServiceResult serviceResult = updateAccount(accountInfoRecordModel, coinsRemain);
		if(serviceResult.isSuccessed()) {
			return new ServiceResult(ServiceResult.SUCCESS, "操作成功");
		}

		return new ServiceResult(ServiceResult.FAILED, "操作失败");
	}

	private void shareReward(AccountRechargeOrder order){

		try {
			UserInfoModel user = userInfoStubService.findUserInfoById(order.getUserId());

			//判断用户是否首充
			if (null==user||!AccountRechargeRuleRuleUserEnum.FIRST_USER.getType().equals(user.getRechargeType())){
                return;
            }

			//上级用户
			UserRelationModel parentRelation = userRelationStubService.selectPid(user.getId());
			int day = DateUtil.getDay(new Date(),parentRelation.getCreateTime());
			if (null==parentRelation||day>30 ){
                return;
            }

			//二级奖励
			UserInfoModel parentUser = userInfoStubService.findUserInfoById(parentRelation.getPid());
			int coin = 0;
			if (null!=parentUser){
				coin = new BigDecimal(order.getOutMoney()*5/100/100).setScale(0,BigDecimal.ROUND_HALF_DOWN).intValue();
                setUserName(parentUser);
                initUserAccount(parentUser.getId(),parentUser.getUserPhone(),parentUser.getRealName(), EnumAccountType.PRESENT_COIN.getKey().intValue());
                backShareCoin(parentUser.getId(),coin,EnumAccountType.PRESENT_COIN.getKey().intValue());
            }


			//一级用户
			int coin1 = 0;
			UserRelationModel superRelation = userRelationStubService.selectPid(parentUser.getId());
			if(null!=superRelation){
				UserInfoModel superUser = userInfoStubService.findUserInfoById(superRelation.getPid());
				if (null!=superUser){
					coin1 = new BigDecimal(order.getOutMoney()*10/100/100).setScale(0,BigDecimal.ROUND_HALF_DOWN).intValue();
					setUserName(superUser);
					initUserAccount(superUser.getId(),superUser.getUserPhone(),superUser.getRealName(), EnumAccountType.PRESENT_COIN.getKey().intValue());
					backShareCoin(superUser.getId(),coin1,EnumAccountType.PRESENT_COIN.getKey().intValue());
				}
			}

			//自身奖励
			int coin2 = coin+coin1;
			backShareCoin(order.getUserId(),coin2,EnumAccountType.PRESENT_COIN.getKey().intValue());
		} catch (Exception e) {
			log.error("method:shareReward. error. for:{}",e);
		}


	}

	private void setUserName(UserInfoModel user) {

		if (!StringUtils.isBlank(user.getNickName())) {
			user.setRealName(user.getNickName());
			return;
		}

		if (!StringUtils.isBlank(user.getWxNickName())) {
			user.setRealName(user.getWxNickName());
			return;
		}

		if (!StringUtils.isBlank(user.getQqNickName())) {
			user.setRealName(user.getQqNickName());
			return;
		}

		if (!StringUtils.isBlank(user.getUserPhone())) {
			user.setRealName(user.getUserPhone());
			return;
		}
	}
}
