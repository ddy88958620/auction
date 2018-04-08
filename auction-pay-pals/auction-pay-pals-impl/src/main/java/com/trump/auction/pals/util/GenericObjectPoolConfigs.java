package com.trump.auction.pals.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis.genericObjectPoolConfig")
public class GenericObjectPoolConfigs  extends GenericObjectPoolConfig{

}
