package com.trump.auction.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * 系统参数配置工具类
 * <p>
 *     根据properties文件生成系统变量配置项
 * </p>
 * @author Owen.Yuan 2017/7/22.
 */
@Data
@Slf4j
public class AppVarConfigUtils {

    static String SYS_VAR_CFG_TEMPLATE = "export {0}=\"{1}\"";

    static String RESOURCE_PATH_TEMPLATE = "{0}/toCardPay-impl/src/main/resources/application-{1}.properties";

    static List<String> PROPERTY_NAMES = Arrays.asList(
            "server.port",
            "spring.datasource.url",
            "spring.datasource.username",
            "spring.datasource.password",
            "spring.data.mongodb.uri",
            "spring.activemq.broker-url",
            "spring.activemq.user",
            "spring.activemq.password",
            "spring.redis.password",
            "spring.redis.cluster.nodes",
            "spring.dubbo.registry.address",
            "spring.dubbo.protocol.port",
            "logging.path"
    );

    private String homePath = System.getProperty("user.dir");

    private String env;

    private String appName;

    public AppVarConfigUtils(String appName, String env) {
        this.appName = appName;
        this.env = env;
    }

    public static void main(String[] args) throws IOException {

        AppVarConfigUtils appVarConfig = new AppVarConfigUtils("toCardPay", "prod");

        appVarConfig.invoke();

    }

    public void invoke() throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(new File(getResourcePath()))) {
            properties.load( inputStream );
        }

        SysVarPropHolder sysVarPropHolder = new SysVarPropHolder();

        for (String propertyName : new TreeSet<>(properties.stringPropertyNames())) {
            if ( isEnableSysVar(propertyName) ) {
                final String sysVarName = getSysVarName(propertyName);
                sysVarPropHolder.add(new SysVarProp(sysVarName,
                                getSysVarConfig(sysVarName, properties.get(propertyName)),
                                getFormatProperty(propertyName, sysVarName)));
            }
        }

        log.info("System variable properties:\n{}", sysVarPropHolder);
    }

    private String getResourcePath() {
        return MessageFormat.format( RESOURCE_PATH_TEMPLATE, getHomePath(), getEnv() );
    }

    boolean isEnableSysVar(String propName) {
        return PROPERTY_NAMES.contains( propName );
    }

    String getSysVarName(String propName) {
        String[] propNameParts = propName.split("\\.");
        StringBuilder builder = new StringBuilder();
        builder.append("APP_")
                .append(getAppName().toUpperCase());
        for (String propNamePart : propNameParts) {
            builder.append("_");
            builder.append(getFormattedPropName(propNamePart));
        }
        return builder.toString();
    }

    String getSysVarConfig(String sysVarName, Object propValue) {
        return MessageFormat.format(SYS_VAR_CFG_TEMPLATE, sysVarName, propValue);
    }

    String getFormatProperty(String propertyName, String sysVarName) {
        return propertyName + "=${" + sysVarName + "}";
    }

    private String getFormattedPropName(String propNamePart) {
        return propNamePart.replaceAll("-", "").toUpperCase();
    }

    @Data
    @AllArgsConstructor
    static class SysVarProp {

        private String name;

        private String varConfig;

        private String property;

    }

    static class SysVarPropHolder {

        private List<SysVarProp> sysVarProps = new ArrayList<>();

        public void add(SysVarProp sysVarProp) {
            sysVarProps.add(sysVarProp);
        }

        public List<SysVarProp> get()  {
            return sysVarProps;
        }

        public List<String> getVarConfigs() {
            List<String> varConfigs = new ArrayList<>(sysVarProps.size());
            for (SysVarProp sysVarProp : sysVarProps) {
                varConfigs.add(sysVarProp.getVarConfig());
            }
            return varConfigs;
        }

        public List<String> getProperties() {
            List<String> properties = new ArrayList<>(sysVarProps.size());
            for (SysVarProp sysVarProp : sysVarProps) {
                properties.add(sysVarProp.getProperty());
            }
            return properties;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("##system variables\n");

            List<String> varConfigs = getVarConfigs();
            for (String varConfig : varConfigs) {
                builder.append(varConfig);
                builder.append("\n");
            }

            builder.append("\n");

            builder.append("##properties\n");

            List<String> properties = getProperties();
            for (String property : properties) {
                builder.append(property);
                builder.append("\n");
            }

            return builder.toString();
        }
    }
}
