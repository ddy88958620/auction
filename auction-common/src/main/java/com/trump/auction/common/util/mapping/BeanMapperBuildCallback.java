package com.trump.auction.common.util.mapping;

import ma.glasnost.orika.MapperFactory;

/**
 * 类型转换回调
 *
 * @author <a href="mailto:sowen1023@gmail.com">Owen.Yuan</a>
 * @since 2017/11/16
 */
public interface BeanMapperBuildCallback {

    void callback(MapperFactory factory);
}
