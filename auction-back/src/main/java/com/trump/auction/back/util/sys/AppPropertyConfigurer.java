package com.trump.auction.back.util.sys;

import java.util.Map;
import java.util.Properties;

import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <p>
 *     扩展spring property placeholder，获取props配置
 * </p>
 * @author Owen.Yuan 2017/7/24.
 */
public class AppPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> PROPS = new ConcurrentReaderHashMap();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        for ( Object keyItem : props.keySet() ) {
            String key = keyItem.toString().trim();
            String value = props.getProperty( key );
            AppPropertyConfigurer.PROPS.put( key, value );
        }
    }

    public static Object getProperty(String name) {
        return AppPropertyConfigurer.PROPS.get( name );
    }

    public static String getPropertyAsString(String name) {
        Object property = getProperty( name );
        return property == null ? null : property.toString().trim();
    }
}
