package com.trump.auction.reactor.common.repository;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Base class of redis repository
 *
 * @author Owen
 * @since 2018/1/22
 */
public abstract class AbstractRedisRepository {

    public static final String KEY_PREFIX = "auction.reactor";

    protected <T> T convertToBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    protected <T> List<T> convertToBean(List<String> list, Class<T> clazz) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }

        return Lists.transform(list, (json) -> JSON.parseObject(json, clazz));
    }

    protected <V> Map<String, V> convertToBean(Map<Object, Object> src, Class<V> clazz) {
        if (CollectionUtils.isEmpty(src)) {
            return Collections.EMPTY_MAP;
        }

        Map<String, V> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : src.entrySet()) {
            result.put(entry.getKey().toString(), JSON.parseObject(entry.getValue().toString(), clazz));
        }

        return result;
    }

    protected String makeKey(String auctionNo, String... keyNames) {
        Assert.notNull(auctionNo, "[auctionNo]");
        Assert.notNull(keyNames, "[keyNames]");

        StringBuilder result = new StringBuilder(makeKey0(keyNames));

        result.append(".{").append(auctionNo).append("}");

        return result.toString();
    }

    protected String makeKey0(String... keyNames) {
        Assert.notNull(keyNames, "[keyNames]");

        StringBuilder result = new StringBuilder(KEY_PREFIX);

        for (String keyName : keyNames) {
            result.append(".").append(keyName);
        }

        return result.toString();
    }
}
