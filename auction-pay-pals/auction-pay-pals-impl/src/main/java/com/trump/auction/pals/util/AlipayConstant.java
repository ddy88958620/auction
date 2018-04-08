package com.trump.auction.pals.util;


import java.util.Map;

import com.trump.auction.pals.domain.HtConfigModel;

import redis.clients.jedis.JedisCluster;

/**
 * Created by IntelliJ IDEA
 * YhInfo : zhangsh
 * Date : 2016年12月30日
 */
public class AlipayConstant {

	
    public static String getGatewayUrl(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.ALIPAY);
        return keys.get("auction.alipay.gateWay.url");
    }

    public static String getNotifyUrl(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.ALIPAY);
        return keys.get("auction.alipay.notify.url");
    }

    public static String getAppId(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.ALIPAY);
        return keys.get("auction.alipay.app.id");
    }

    public static String getPublicKey(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.ALIPAY);
        return keys.get("auction.alipay.public.key");
    }
    
    public static String getPrivateKey(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.ALIPAY);
        return keys.get("auction.alipay.private.key");
    }

}
