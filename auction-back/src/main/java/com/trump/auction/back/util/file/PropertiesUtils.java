package com.trump.auction.back.util.file;

import org.springframework.util.Assert;

import com.trump.auction.back.util.sys.AppPropertyConfigurer;

public abstract class PropertiesUtils {


    public static String get(String key) {
        Assert.hasText(key, "Parameter 'key' must not be empty.");
        return AppPropertyConfigurer.getPropertyAsString(key);
    }
}
