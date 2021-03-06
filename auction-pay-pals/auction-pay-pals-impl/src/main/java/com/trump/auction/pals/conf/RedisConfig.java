package com.trump.auction.pals.conf;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Redis config
 * @author Owen.Yuan 2017/7/13.
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    static final String DELIMITER = ":";


//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new RedisObjectSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        return template;
//    }

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        config.setMaxTotal(redisProperties.getPool().getMaxActive());
        config.setMinIdle(redisProperties.getPool().getMinIdle());
        config.setMaxIdle(redisProperties.getPool().getMaxIdle());
        return config;
    }

    @Bean
    public JedisCluster jedisCluster(GenericObjectPoolConfig genericObjectPoolConfig) {
        return new JedisCluster(getHostAndPorts(),
                redisProperties.getTimeout(),
                redisProperties.getTimeout(),
                redisProperties.getCluster().getMaxRedirects(),
                redisProperties.getPassword(),
                genericObjectPoolConfig);
    }

    private Set<HostAndPort> getHostAndPorts() {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();

        for (String hostAndPort : redisProperties.getCluster().getNodes()) {
             String[] hostPortPair = hostAndPort.split(DELIMITER);
            nodes.add(new HostAndPort(hostPortPair[0].trim(),
                    Integer.valueOf(hostPortPair[1].trim())));
        }

        return nodes;
    }


}
