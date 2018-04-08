package com.trump.auction.cust.service.impl;

import com.cf.common.utils.ServiceResult;
import com.cf.common.utils.Status;
import com.trump.auction.cust.enums.SensitiveWordTypeEnum;
import com.trump.auction.cust.service.UserSenstiveWordService;
import com.trump.auction.cust.util.SenstiveWord.SensitiveWordFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

/**
 * @Author=hanliangliang
 * @Date=2018/03/08
 */
@Service
@Slf4j
public class UserSenstiveWordServiceImpl implements UserSenstiveWordService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public ServiceResult checkNickName(String nickName) {
        String code = Status.SUCCESS.getName();
        String msg = "敏感词校验成功";
        Object senstiveWord="";
        if (null==nickName){
             code =Status.ERROR.getName();
             msg = "参数传递异常，昵称不能为空";
             return new ServiceResult(code,msg,senstiveWord);
        }
        try{
            SensitiveWordFilter filter=SensitiveWordFilter.getInstance(jedisCluster);
            //returnName=filter.replaceSensitiveWord(SensitiveWordTypeEnum.NICKNAME.getCode(),nickName,matchType,replaceChar);
            //matchType匹配规则    1：最小匹配规则 2：最大匹配规则  默认给 1
            Set<String> set= filter.getSensitiveWord(SensitiveWordTypeEnum.NICKNAME.getCode(),nickName,1);
            if (set.size() > 0 ) {
                code = Status.ERROR.getName();
                msg = "语句中包含敏感词的个数为：" + set.size() + " ,包含：" + set;
                senstiveWord= set;
            }
        }catch (Exception e){
            logger.error("userNameCheck failed",e);
            msg = "系统异常，校验失败";
        }
        return new ServiceResult(code,msg,senstiveWord);
    }
}
