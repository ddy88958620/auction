package com.trump.auction.back.frontUser.service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.dao.read.UserInfoDao;
import com.trump.auction.back.frontUser.dao.write.UserInfoWriteDao;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.cust.model.UserInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * Created by wangYaMin on 2017/12/22.
 */
@Repository
@Service
public class UserInfoServiceImpl implements UserInfoService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoWriteDao userInfoWriteDao;

    @Autowired
    private BeanMapper beanMapper;
    @Override
    public Paging<UserInfo> selectUserInfo(HashMap<String, Object> params) {

        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        // 如果前台选择的充值类型是充值
        if(null != params.get("rechargeType") && !params.get("rechargeType").equals("")){
            if(Integer.parseInt(params.get("rechargeType").toString()) == 1){
                params.put("rechargeType","1");
            }else{
                params.put("rechargeType","2,3,4,5,6");
            }
        }

        return PageUtils.page(userInfoDao.selectUserInfo(params));
    }

    @Override
    public List<UserInfo> findAll(String [] ids) {
        return userInfoDao.findAll(ids);
    }

    @Override
    public int updateUserInfoWrite(String id) {
        return userInfoWriteDao.updateUserInfoWrite(id);
    }

    @Override
    public UserInfo findUserInfoById(Integer id) {
        return userInfoDao.selectUserInfoById(id);
    }
    /**
     * 修改用户信息
     * @param userInfoModel 用户信息
     * @return ServiceResult
     */
    @Override
    public ServiceResult updateUserInfoById(UserInfoModel userInfoModel) {
        UserInfo info=beanMapper.map(userInfoModel,UserInfo.class);
        int count = userInfoWriteDao.updateUserInfoById(info);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"修改成功");
        }
        logger.error("updateUserInfoById failed,userInfo:{}",userInfoModel);
        return new ServiceResult(ServiceResult.FAILED,"修改失败");
    }

    @Override
    public UserInfo findUserInfoByUserPhone(String userPhone) {
        return userInfoDao.selectUserInfoByUserPhone(userPhone);
    }
}
