package com.trump.auction.cust.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.UserInfoDao;
import com.trump.auction.cust.dao.UserPhoneRecordDao;
import com.trump.auction.cust.domain.UserInfo;
import com.trump.auction.cust.domain.UserPhoneRecord;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.service.UserInfoService;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private UserPhoneRecordDao userPhoneRecordDao;
    @Override
    public UserInfoModel findUserInfoById(Integer userId) {
        UserInfo info = userInfoDao.selectUserInfoById(userId);
        return beanMapper.map(info, UserInfoModel.class);
    }

    @Override
    public UserInfoModel findUserIndexInfoById(Integer id) {
        return beanMapper.map(userInfoDao.selectUserIndexInfoById(id),UserInfoModel.class);
    }

    @Override
    public UserInfoModel findUserInfoByUserPhone(String userPhone) {
        return beanMapper.map(userInfoDao.selectUserInfoByUserPhone(userPhone), UserInfoModel.class);
    }

    @Override
    public UserInfoModel saveUserInfo(UserInfoModel userInfoModel) {
        try {
            UserInfo userInfo = beanMapper.map(userInfoModel, UserInfo.class);
            int count = userInfoDao.insertUserInfo(userInfo);
            if (count == 1) {
                log.info("insert user success,userInfo:{}",userInfo);
                return beanMapper.map(userInfoDao.selectUserInfoById(userInfo.getId()), UserInfoModel.class);
            }
        } catch (Exception e) {
            log.error("saveUserInfo error",e);
        }
        return null;
    }

    @Override
    public UserInfoModel findUserInfoByOpenId(String openId, String type) {
        return beanMapper.map(userInfoDao.selectUserInfoByOpenId(openId,type),UserInfoModel.class);
    }

    @Override
    public ServiceResult updateThirdUserInfo(UserInfoModel userInfoModel) {
        String code = "101";
        String msg = "操作失败";

        int count = userInfoDao.updateThirdUserInfo(beanMapper.map(userInfoModel,UserInfo.class));
        if (count==1) {
            code = "200";
            msg = "操作成功";
        }
        log.error("updateThirdUserInfo failed,userInfo:{}",userInfoModel);
        return new ServiceResult(code,msg);
    }

    @Override
	public int updateUserPhoneById(String userPhone, Integer userId) {
		String lastPhone = userInfoDao.selectUserInfoById(userId).getUserPhone();
		if (null != lastPhone && userPhone.equals(lastPhone)) {
			return 0;
		}
		UserPhoneRecord record = new UserPhoneRecord();
		record.setUserPhone(userPhone);
		record.setUserId(userId);
		if (StringUtils.isNotBlank(lastPhone)) {
			record.setUserLastPhone(lastPhone);
		}
		try {
			int count1 = userPhoneRecordDao.insertUserPhoneRecord(record);
			int count2 = userInfoDao.updateUserPhoneById(userPhone, userId);
			if (count1 == 1 && count2 == 1) {
				log.info("updateUserPhoneById success,userId:{}", userId);
				return count1;
			}
		} catch (Exception e) {
			log.error("updateUserPhoneById failed,userId:{}", userId);
		}
		return 0;
	}

    @Override
    public ServiceResult updateLoginPasswordById(String loginPassword, Integer id) {
        int count = userInfoDao.updateLoginPasswordById(loginPassword,id);
        if (count==1) {
            log.info("updateLoginPasswordById success,userId:{}",id);
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateLoginPasswordById failed,userId:{}",id);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public ServiceResult updatePayPasswordById(String payPassword, Integer id) {
        int count = userInfoDao.updatePayPasswordById(payPassword,id);
        if (count==1) {
            log.info("updatePayPasswordById success,userId:{}",id);
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updatePayPasswordById failed,userId:{}",id);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public ServiceResult updateRechargeTypeById(Integer rechargeType, Integer rechargeMoney, Integer id) {
        int count = userInfoDao.updateRechargeTypeById(rechargeType, rechargeMoney, id);
        if (count==1) {
            log.info("updateRechargeTypeById success,userId:{},rechargeType:{},rechargeMoney:{}",id,rechargeType,rechargeMoney);
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateRechargeTypeById failed,userId:{},rechargeType:{},rechargeMoney:{}",id,rechargeType,rechargeMoney);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public ServiceResult updateUserNameAndPwdById(String realName, String loginPassword, Integer id) {
        int count = userInfoDao.updateUserNameAndPwdById(realName, loginPassword, id);
        if (count==1) {
            log.info("updateUserNameAndPwdById success,userId:{}",id);
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateUserNameAndPwdById failed,userId:{}",id);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public ServiceResult updateLoginTypeById(String loginType, Integer id) {
        int count = userInfoDao.updateLoginTypeById(loginType, id);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateLoginTypeById failed,userId:{},loginType:{}",id,loginType);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public List<UserInfoModel> findFirstRechargeList() {
        return beanMapper.mapAsList(userInfoDao.findFirstRechargeList(),UserInfoModel.class);
    }

    @Override
    public ServiceResult updateAddressById(String provinceName, String cityName, Integer id){
        int count = userInfoDao.updateAddressById(provinceName, cityName, id);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateAddressById failed,userId:{},provinceName:{},cityName:{}",id,provinceName,cityName);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public ServiceResult updateUserInfo(UserInfoModel userInfoModel) {
        int count = userInfoDao.updateUserInfo(beanMapper.map(userInfoModel,UserInfo.class));
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateThirdUserInfo failed,userInfo:{}",userInfoModel);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    /**
     * 获取用户登陆信息（设备号、设备名、应用市场等）
     * @param appInfo
     * @param userId
     * @return
     */
    @Override
    public ServiceResult updateAppInfo(String appInfo, Integer userId) {
        int count = userInfoDao.updateAppInfo(appInfo,userId);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("method:updateAppInfo failed,userId:{}",userId);
        return new ServiceResult(ServiceResult.FAILED,"获取用户设备信息失败");
    }

    /**
     * 修改用户信息
     * @param userInfoModel 用户信息
     * @return ServiceResult
     */
    @Override
    public ServiceResult updateUserInfoById(UserInfoModel userInfoModel) {
        int count = userInfoDao.updateUserInfoById(beanMapper.map(userInfoModel,UserInfo.class));
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        log.error("updateThirdUserInfo failed,userInfo:{}",userInfoModel);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }
}
