package com.trump.auction.trade.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Zookeeper配置
 *
 * @author <a href="mailto:sowen1023@gmail.com">Owen.Yuan</a>
 * @since 2017/9/23
 */
@ConfigurationProperties(prefix = "spring.zookeeper")
public class ZookeeperProperties {

    private List<String> nodes;

    private int sessionTimeout;

    private int baseSleepTime;

    private int maxRetries;

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getBaseSleepTime() {
        return baseSleepTime;
    }

    public void setBaseSleepTime(int baseSleepTime) {
        this.baseSleepTime = baseSleepTime;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getNodesAsString() {
        if (this.nodes == null || nodes.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String node : nodes) {
            stringBuilder.append(node);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}
