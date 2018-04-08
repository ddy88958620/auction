package com.trump.auction.activity.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.activity.constant.ActivityConstant;
import com.trump.auction.activity.dao.UserSignDao;
import com.trump.auction.activity.domain.UserSign;
import com.trump.auction.activity.model.UserSignModel;
import com.trump.auction.activity.service.UserSignService;
import com.trump.auction.activity.util.DateUtil;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 签到实现类
 * @author wangbo 2017/12/21.
 */
@Service
@Slf4j
public class UserSignServiceImpl implements UserSignService {
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private UserSignDao userSignDao;
    @Autowired
    private AccountInfoStubService accountInfoStubService;
    @Autowired
    private UserInfoStubService userInfoStubService;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public int insertUserSign(Integer userId) {
        UserSign userSign = new UserSign();
        userSign.setUserId(userId);
        userSign.setSeriesSignDays(1);
        return userSignDao.insertUserSign(userSign);
    }

    @Override
    public int updateUserSign(UserSignModel userSignModel) {
        return userSignDao.updateUserSign(beanMapper.map(userSignModel, UserSign.class));
    }

    @Override
    public UserSignModel findUserSignByUserId(Integer userId) {
        return beanMapper.map(userSignDao.selectUserSignByUserId(userId),UserSignModel.class);
    }

    @Override
    public ServiceResult checkIsSigned(Integer userId) {
        String msg = "未签到";
        String todayDate = DateUtil.getFormatDate(new Date());
        int num = userSignDao.checkIsSigned(userId,todayDate);
        if (num==1) {
            msg = "已签到";
            return new ServiceResult(ServiceResult.SUCCESS,msg);
        }
        return new ServiceResult(ServiceResult.FAILED,msg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult userSign(Integer userId) throws Exception {
        UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(userId);
        if (null!=userInfoModel) {
            UserSign userSign = userSignDao.selectUserSignByUserId(userId);
            int seriesDays = 1;
            if (null==userSign) {
                //用户第一次签到
                userSign = new UserSign();
                userSign.setUserId(userId);
                userSign.setSeriesSignDays(seriesDays);
                int count = userSignDao.insertUserSign(userSign);
                return addPointsBySignResult(userInfoModel, seriesDays, count);
            } else {
                Calendar calendar = Calendar.getInstance();
                String todayDate = DateUtil.getFormatDate(calendar.getTime());
                calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)-1);
                String yesterdayDate = DateUtil.getFormatDate(calendar.getTime());
                //检查今日是否已签到
                int result = userSignDao.checkIsSigned(userId,todayDate);
                if (result==0) {
                    String signDate = DateUtil.getFormatDate(userSign.getLastSignTime());
                    if (yesterdayDate.equals(signDate)) {
                        //连续签到，签到天数+1之后，取模
                        Map<String, String> signConfigMap = jedisCluster.hgetAll(ActivityConstant.USER_SIGN);
                        int signCycle = Integer.parseInt(signConfigMap.get(ActivityConstant.SIGN_CYCLE));
                        seriesDays = userSign.getSeriesSignDays() + 1;
                        seriesDays = seriesDays % signCycle == 0 ? signCycle : seriesDays % signCycle;
                    }
                    UserSign updateObj = new UserSign();
                    updateObj.setUserId(userId);
                    updateObj.setSeriesSignDays(seriesDays);
                    int count = userSignDao.updateUserSign(updateObj);
                    return addPointsBySignResult(userInfoModel, seriesDays, count);
                }
                log.error("user has been signed today,userId:{}",userId);
                return new ServiceResult(ServiceResult.FAILED, "今日已签到");
            }
        }
        log.error("user is not exits,userId:{}",userId);
        return new ServiceResult(ServiceResult.FAILED, "用户不存在");
    }

    /**
     * 根据签到结果，发放积分
     * @param userInfoModel 用户信息
     * @param seriesDays 连续签到天数
     * @param count 签到结果
     * @return 执行结果
     * @throws Exception 发放积分失败，抛出异常，回滚数据
     */
    private ServiceResult addPointsBySignResult(UserInfoModel userInfoModel, int seriesDays, int count) throws Exception {
        if (count==1) {
            //签到成功
            int signPoints = getSignPoints(seriesDays);
            ServiceResult serviceResult = updateUserPoints(userInfoModel,signPoints);
            if (serviceResult.isSuccessed()) {
                //发放积分成功
                return serviceResult;
            } else {
                //发放积分失败，抛出异常，回滚数据
                log.error("add points error,userInfoModel:{},signPoints:{}",userInfoModel,signPoints);
                throw new Exception("updateUserPoints failed");
            }
        } else {
            //签到失败
            log.error("user sign error,userInfoModel:{},seriesDays:{}",userInfoModel,seriesDays);
            return new ServiceResult(ServiceResult.FAILED, "签到失败");
        }
    }

    /**
     * 赠送积分到用户账户
     * @param userInfoModel 用户信息
     * @param signPoints 签到赠送的积分数
     */
    private ServiceResult updateUserPoints(UserInfoModel userInfoModel, int signPoints){
        ServiceResult serviceResult = accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),
                userInfoModel.getRealName(), EnumAccountType.POINTS.getKey());
        if (serviceResult.isSuccessed()) {
            ServiceResult updateResult = accountInfoStubService.signGainPoints(userInfoModel.getId(),userInfoModel.getUserPhone(),signPoints);
            if (updateResult.isSuccessed()) {
                log.info("user sign success，userInfoModel:{},signPoints:{}",userInfoModel,signPoints);
                return new ServiceResult(ServiceResult.SUCCESS, "签到成功，积分发放成功");
            } else {
                log.info("user sign signGainPoints failed,userInfoModel:{},signPoints:{}",userInfoModel,signPoints);
                return new ServiceResult(ServiceResult.FAILED, "积分发放失败");
            }
        } else {
            log.error("init user account error,userInfoModel:{}",userInfoModel);
            return new ServiceResult(ServiceResult.FAILED, "积分发放失败");
        }
    }

    /**
     * 根据连续签到天数，计算签到赠送积分数
     * @param seriesDays 连续签到天数
     * @return 积分数
     */
    private int getSignPoints(Integer seriesDays) {
        Map<String, String> signConfigMap = jedisCluster.hgetAll(ActivityConstant.USER_SIGN);
        int basePoints = Integer.parseInt(signConfigMap.get(ActivityConstant.BASE_POINTS));
        int extraPoints = Integer.parseInt(signConfigMap.get(ActivityConstant.EXTRA_POINTS));
        int signCycle = Integer.parseInt(signConfigMap.get(ActivityConstant.SIGN_CYCLE));

        int signPoints = 0;
        if (seriesDays<signCycle) {
            signPoints = basePoints;
        } else if (seriesDays==signCycle) {
            signPoints = basePoints + extraPoints;
        }
        return signPoints;
    }
}