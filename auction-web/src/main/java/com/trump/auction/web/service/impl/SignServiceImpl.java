package com.trump.auction.web.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.api.UserSignStubService;
import com.trump.auction.activity.model.UserSignModel;
import com.trump.auction.web.service.SignService;
import com.trump.auction.web.util.HandleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songruihuan on 2017/12/21.
 */
@Service
public class SignServiceImpl implements SignService {
    Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);
    @Autowired
    private UserSignStubService userSignStubService;
    @Override
    public HandleResult userSign(Integer userId){
        HandleResult handleResult = null;
        try {
            ServiceResult serviceResult = userSignStubService.userSign(userId);
            if(serviceResult.getCode().equals("200")){
                handleResult = new HandleResult(0,"用户签到接口,返回成功");
            }else{
                handleResult = new HandleResult(1,serviceResult.getMsg());
            }
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("用户签到接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public HandleResult checkIsSigned(Integer userId){
        HandleResult handleResult = null;
        try {
            ServiceResult serviceResult = userSignStubService.checkIsSigned(userId);
            handleResult = new HandleResult(0,"检查当天是否已签到接口,返回成功");
            if(serviceResult.getCode().equals("200")){
                handleResult.setResult(true);
            }else{
                handleResult.setResult(false);
            }
        } catch (Exception e) {
            handleResult.setCode(1).setMsg("error");
            logger.error("检查当天是否已签到接口异常,{}",e);
        }
        return handleResult;
    }

    @Override
    public UserSignModel findUserSignByUserId(Integer userId){
        UserSignModel userSignModel = userSignStubService.findUserSignByUserId(userId);
        if(userSignModel!=null){
            return userSignModel;
        }
        return null;
    }
}
