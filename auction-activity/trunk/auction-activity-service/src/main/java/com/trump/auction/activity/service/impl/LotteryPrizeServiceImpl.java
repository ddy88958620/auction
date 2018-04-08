package com.trump.auction.activity.service.impl;

import com.alibaba.dubbo.common.json.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.activity.constant.ActivityConstant;
import com.trump.auction.activity.dao.LotteryPrizeDao;
import com.trump.auction.activity.domain.LotteryPrize;
import com.trump.auction.activity.enums.EnumLotteryPrizeType;
import com.trump.auction.activity.model.ActivityUserModel;
import com.trump.auction.activity.model.LotteryPrizeModel;
import com.trump.auction.activity.model.LotteryRecordModel;
import com.trump.auction.activity.service.ActivityUserService;
import com.trump.auction.activity.service.LotteryPrizeService;
import com.trump.auction.activity.service.LotteryRecordService;
import com.trump.auction.activity.util.LotteryUtil;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抽奖奖品相关
 * @author wangbo 2018/1/9.
 */
@Slf4j
@Service
public class LotteryPrizeServiceImpl implements LotteryPrizeService {
    @Autowired
    private LotteryPrizeDao lotteryPrizeDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ActivityUserService activityUserService;
    @Autowired
    private UserInfoStubService userInfoStubService;
    @Autowired
    private AccountInfoStubService accountInfoStubService;
    @Autowired
    private LotteryRecordService lotteryRecordService;
    /*@Autowired
    private UserShippingAddressStuService userShippingAddressStuService;*/

    @Override
    public ServiceResult addLotteryPrize(LotteryPrizeModel lotteryPrizeModel) {
        String latestPrizeNo = lotteryPrizeDao.selectLatestPrizeNo();
        int tmp = Integer.parseInt(latestPrizeNo==null?"0":latestPrizeNo);
        tmp += 1;
        String tmpNo = String.valueOf(tmp);
        //按照规则生成4位的奖品编号
        String prizeNo = tmpNo;
        for (int i = 0; i < 4 - tmpNo.length(); i++) {
            prizeNo = "0" + prizeNo;
        }
        lotteryPrizeModel.setPrizeNo(prizeNo);
        lotteryPrizeModel.setIsOpen(1);
        lotteryPrizeModel.setOrderNumber(0);
        lotteryPrizeModel.setIsPlan1("N");
        lotteryPrizeModel.setIsPlan2("N");

        int count = lotteryPrizeDao.insertLotteryPrize(beanMapper.map(lotteryPrizeModel, LotteryPrize.class));
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public ServiceResult updateRateAndStoreById(LotteryPrizeModel lotteryPrizeModel) {
        LotteryPrize lotteryPrize = beanMapper.map(lotteryPrizeModel,LotteryPrize.class);
        int count = lotteryPrizeDao.updateRateAndStoreById(lotteryPrize);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult updatePrizePlanAndValid(List<String> plan1PrizeNoList, List<String> plan2PrizeNoList, String validPlan) throws Exception {
        final String plan1 = "1";
        final String plan2 = "2";

        int count= lotteryPrizeDao.updateLotteryAndPlanZero();
        if (count>0) {
            if (plan1.equals(validPlan)) {
                count = updatePrizePlan(plan1PrizeNoList,"Y",null,2);
                if (count==0) {
                    throw new Exception("update prize plan failed");
                }
                count = updatePrizePlan(plan2PrizeNoList,null,"Y",null);
            } else if (plan2.equals(validPlan)) {
                count = updatePrizePlan(plan1PrizeNoList,"Y",null,null);
                if (count==0) {
                    throw new Exception("update prize plan failed");
                }
                count = updatePrizePlan(plan2PrizeNoList,null,"Y",2);
            }

            if (count==0) {
                throw new Exception("update prize plan failed");
            }
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }

        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    private int updatePrizePlan(List<String> prizeNoList,String isPlan1,String isPlan2,Integer isOpen) {
        int count = 0;
        for (int i = 0; i < prizeNoList.size(); i++) {
            String prizeNo = prizeNoList.get(i);
            Integer orderNumber = null;
            if (null!=isOpen) {
                orderNumber = i + 1;
            }
            count = lotteryPrizeDao.updatePrizePlan(isPlan1,isPlan2,isOpen,orderNumber,prizeNo);
            if (count==0) {
                return count;
            }
        }
        return count;
    }

    @Override
    public List<LotteryPrizeModel> findOpenPrizeList() {
        return beanMapper.mapAsList(lotteryPrizeDao.selectOpenPrizeList(),LotteryPrizeModel.class);
    }

    @Override
    public List<LotteryPrizeModel> findCanPrizeList() {
        return beanMapper.mapAsList(lotteryPrizeDao.selectCanPrizeList(),LotteryPrizeModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult getLotteryPrizeByRandom(Integer userId, String isExchange) throws Exception {
        UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(userId);
        ActivityUserModel activityUserModel = activityUserService.findActivityUserByUserId(userId);

        String key = ActivityConstant.LOTTERY_EXCHANGE_NUMBER + userId +"";
        String number = jedisCluster.get(key);

        Map<String, String> lotteryMap = jedisCluster.hgetAll(ActivityConstant.ACTIVITY_LOTTERY);

        if (StringUtils.isEmpty(isExchange) || "0".equals(isExchange)) {
            if (!StringUtils.isEmpty(number)) {
                Integer numInt = Integer.valueOf(number);
                if (numInt >= 3) {
                    return new ServiceResult("-1","今日兑换已达三次上限");
                }
            }
            int lotteryTimes = activityUserService.findFreeLotteryTimes(userId);
            if (lotteryTimes < 1) {
                return new ServiceResult("-2","抽奖次数不足");
            }

            ServiceResult serviceResult = activityUserService.updateFreeLotteryTimes(userId,
                    activityUserModel.getFreeLotteryTimes()-1,activityUserModel.getFreeLotteryTimes());
            if(serviceResult.isFail()) {
                log.error("reduce free lottery times error,userId:{},freeLotteryTimes:{}",userId,activityUserModel.getFreeLotteryTimes());
                throw new Exception("reduce free lottery times error");
            }
        }

        //check
        if (!StringUtils.isEmpty(isExchange) && "1".equals(isExchange)) {
            //每天只能三次;
            Long value = getCurrentToCurrentDaySecond();
            if (number == null) {
                jedisCluster.set(key, "1");
                jedisCluster.expire(key, Integer.valueOf(value.toString()));
                number = jedisCluster.get(key);
            } else {
                Integer numInt = Integer.valueOf(number);
                if (numInt >= 3) {
                    return new ServiceResult("-1","今日兑换已达三次上限");
                } else {
                    numInt++;
                    jedisCluster.set(key, numInt.toString());
                    jedisCluster.expire(key, Integer.valueOf(value.toString()));
                }
            }

            // 消耗积分抽奖，扣除用户积分
            int lotteryCost = Integer.valueOf(lotteryMap.get(ActivityConstant.LOTTERY_COST));
            int availablePoints = accountInfoStubService.getAuctionCoinByUserId(userId, EnumAccountType.POINTS.getKey());
            if(availablePoints < lotteryCost) {
                return new ServiceResult("-2","积分不足！");
            }
            ServiceResult serviceResult = accountInfoStubService.lotteryCostPoints(userId,lotteryCost);
            if(serviceResult.isFail()) {
                log.error("cost points error,userId:{},availablePoints:{},lotteryCost:{}",userId,availablePoints,lotteryCost);
                throw new Exception("cost points error");
            }
        }

        //随机抽取奖品
        List<LotteryPrize> lotteryList = lotteryPrizeDao.selectCanPrizeList();
        LotteryUtil lotteryUtil = new LotteryUtil(lotteryList);
        int position = lotteryUtil.randomColunmIndex();
        LotteryPrize prize = lotteryList.get(position);

        JSONObject resultData = new JSONObject();
        resultData.put("no", prize.getPrizeNo());
        resultData.put("pic", prize.getPrizePic());
        resultData.put("orderDesc", prize.getOrderNumber());

        if("9999".equals(prize.getPrizeNo())) {
            resultData.put("orderDesc", 0);
            resultData.put("title1", "很遗憾，未中奖");
            resultData.put("title2", "换个手指试一下吧");
            resultData.put("isGoods", 0);
            return new ServiceResult(ServiceResult.SUCCESS,resultData.toString());
        }

        resultData.put("title1", "恭喜你抽中" + prize.getPrizeName());

        if(prize.getPrizeType().intValue() == EnumLotteryPrizeType.TYPE_VIRTUAL_COINS.getType().intValue()
                && prize.getPrizeTypeSub().intValue() == EnumLotteryPrizeType.TYPE_PRESENT_COIN.getType().intValue()) {
            // 如果奖品是赠币，增加用户赠币，增加赠币记录
            resultData.put("title2", "可在【中奖记录】页面查看");
            resultData.put("isGoods", 0);

            ServiceResult serviceResult = accountInfoStubService.lotteryPrizeUserAccount(userId,EnumAccountType.PRESENT_COIN.getKey(),
                    prize.getAmount(),null,null,null,null,userInfoModel.getUserPhone());
            if (serviceResult.isFail()) {
                log.error("add present coins error,userId:{},prizeTypeSub:{},amount:{}",userId,prize.getPrizeTypeSub(),prize.getAmount());
                throw new Exception("add present coins error");
            }
        }  else if(prize.getPrizeType().intValue() == EnumLotteryPrizeType.TYPE_VIRTUAL_COINS.getType().intValue()
                && prize.getPrizeTypeSub().intValue() == EnumLotteryPrizeType.TYPE_POINTS.getType().intValue()) {
            // 如果奖品是积分，增加用户积分，增加积分记录
            resultData.put("title2", "可在【中奖记录】页面查看");
            resultData.put("isGoods", 0);

            ServiceResult serviceResult = accountInfoStubService.lotteryPrizeUserAccount(userId,EnumAccountType.POINTS.getKey(),
                    prize.getAmount(),null,null,null,null,userInfoModel.getUserPhone());
            if (serviceResult.isFail()) {
                log.error("add points error,userId:{},prizeTypeSub:{},amount:{}",userId,prize.getPrizeTypeSub(),prize.getAmount());
                throw new Exception("add points error");
            }
        } else if(prize.getPrizeType().intValue() == EnumLotteryPrizeType.TYPE_GOODS.getType().intValue()
                && prize.getPrizeTypeSub().intValue() == EnumLotteryPrizeType.TYPE_GOODS_SUB.getType().intValue()) {
            resultData.put("isGoods", 1);

            /*UserShippingAddressModel userAddress = userShippingAddressStuService.findDefaultUserAddressItemByUserId(userId);
            if(null!=userAddress) {
                resultData.put("hasAddress", 1);
                resultData.put("title2", "可在【中奖记录】页面查看");
            } else {
                resultData.put("hasAddress", 0);
                resultData.put("title2", "可在中奖记录页面补充地址信息");
            }*/

            LotteryPrize lotteryPrize = lotteryPrizeDao.selectPrizeByPrizeNo(prize.getPrizeNo());
            if (lotteryPrize.getStore() < 1) {
                log.error("lotteryPrize store is empty,prizeName:{},userId:{}", prize.getPrizeName(),userId);
                throw new Exception("lotteryPrize store is empty");
            }

            int count = lotteryPrizeDao.updateStoreByPrizeNo(prize.getPrizeNo(),lotteryPrize.getStore()-1,lotteryPrize.getStore());
            if(count == 0) {
                log.error("update prize store error,prizeName:{},,userId:{}", prize.getPrizeName(),userId);
                throw new Exception("update prize store error");
            }

        }

        // 创建中奖数据
        LotteryRecordModel record = beanMapper.map(prize, LotteryRecordModel.class);
        record.setUserId(userInfoModel.getId());
        record.setUserName(userInfoModel.getRealName());
        record.setUserPhone(userInfoModel.getUserPhone());

        // 保存中奖记录
        ServiceResult serviceResult = lotteryRecordService.addLotteryRecord(record);
        if (serviceResult.isFail()) {
            log.info("save lottery record error,userId:{},lotteryRecord:{}",userId,record);
            throw new Exception("save lottery record error");
        }
        return new ServiceResult(ServiceResult.SUCCESS,resultData.toString());
    }

    private Long getCurrentToCurrentDaySecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long now = cal.getTimeInMillis() / 1000;

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Long end = cal.getTimeInMillis() / 1000;
        return end - now;
    }
}
