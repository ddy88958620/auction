package com.trump.auction.trade.configure;


//import com.trump.auction.trade.dto.Receiver;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Redis config
 * @author Owen.Yuan 2017/10/26.
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;
    static final String DELIMITER = ":";
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
        Set<HostAndPort> nodes = new HashSet<>();

        for (String hostAndPort : redisProperties.getCluster().getNodes()) {
             String[] hostPortPair = hostAndPort.split(DELIMITER);
            nodes.add(new HostAndPort(hostPortPair[0].trim(), Integer.valueOf(hostPortPair[1].trim())));
        }

        return nodes;
    }
//    @Bean
//    public  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic("chant"));
//        return container;
//    }
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

}
