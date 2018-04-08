package com.trump.auction.order.service.impl;

import com.cf.common.utils.ServiceResult;
import com.cf.common.utils.Status;
import com.trump.auction.order.enums.SensitiveWordTypeEnum;
import com.trump.auction.order.service.AppraisesSenstiveWordService;
import com.trump.auction.order.util.SensitiveWordFilter;
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
public class AppraisesSenstiveWordServiceImpl implements AppraisesSenstiveWordService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public ServiceResult checkAppraises(String appraises) {
        String code = Status.ERROR.getName();
        String msg = "敏感词校验成功";
        Object senstiveWord="";
        if (null==appraises){
            code = Status.ERROR.getName();
            msg = "参数传递异常，评论不能为空";
            return new ServiceResult(code,msg,senstiveWord);
        }
        try{
            SensitiveWordFilter filter= SensitiveWordFilter.getInstance(jedisCluster);
            //returnName=filter.replaceSensitiveWord(SensitiveWordTypeEnum.NICKNAME.getCode(),nickName,matchType,replaceChar);
            //matchType匹配规则    1：最小匹配规则 2：最大匹配规则  默认给 1
            Set<String> set= filter.getSensitiveWord(SensitiveWordTypeEnum.SUNBILL.getCode(),appraises,1);
            if (set.size() > 0 ) {
                code = Status.SUCCESS.getName();
                msg = "校验成功,语句中包含敏感词的个数为：" + set.size() + " ,包含：" + set;
                senstiveWord= set;
            }
        }catch (Exception e){
            logger.error("userNameCheck failed",e);
            msg = "系统异常，校验失败";
        }
        return new ServiceResult(code,msg,senstiveWord);
    }
}
