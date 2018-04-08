package com.trump.auction.pals.util;


import java.util.Map;

import com.trump.auction.pals.domain.HtConfigModel;

import redis.clients.jedis.JedisCluster;

/**
 * Created by IntelliJ IDEA
 * YhInfo : zhangsh
 * Date : 2016年12月30日
 */
public class WeChatPayConstant {

	
    public static String getMchId(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.WECHAT);
        return keys.get("auction.wechat.mchId");
    }

    public static String getNotifyUrl(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.WECHAT);
        return keys.get("auction.wechat.notify.url");
    }

    public static String getAppId(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.WECHAT);
        return keys.get("auction.wechat.app.id");
    }

    public static String getWeChatKey(JedisCluster jedisCluster){
    	Map<String, String> keys = jedisCluster.hgetAll(HtConfigModel.WECHAT);
        return keys.get("auction.wechat.key");
    }
    
}
