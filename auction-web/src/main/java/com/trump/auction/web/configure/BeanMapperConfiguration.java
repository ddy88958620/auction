package com.trump.auction.web.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.mapping.BeanMapperBuildCallback;
import com.cf.common.util.mapping.DefaultBeanMapper;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.web.vo.AuctionInfoVo;
import com.trump.auction.web.vo.LastAuctionInfoVo;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * BeanMapper
 * @author Owen.Yuan 2017/10/26.
 */
@Configuration
public class BeanMapperConfiguration {

    private String ossImgDomain;

    public BeanMapperConfiguration(@Value("${aliyun.oss.domain}") String ossImgDomain) {
        this.ossImgDomain = ossImgDomain;
    }

    @Bean(name = "beanMapper")
    public BeanMapper beanMapper() {
        return new DefaultBeanMapper.Builder(new BeanMapperBuildCallback() {

            @Override
            public void callback(MapperFactory mapperFactory) {
                mapperFactory.getConverterFactory().registerConverter("imgPathConverter", imgPathConverter());

                
                mapperFactory.classMap(AuctionInfoModel.class, AuctionInfoVo.class)
                .fieldMap("previewPic", "previewPic").converter("imgPathConverter").add()
                .byDefault()
                .register();
                
                mapperFactory.classMap(LastAuctionInfoVo.class, AuctionInfoVo.class)
                .fieldMap("previewPic", "previewPic").converter("imgPathConverter").add()
                .byDefault()
                .register();
            }
        }).build();
    }

    private BidirectionalConverter<String, String> imgPathConverter() {
        return new BidirectionalConverter<String, String>() {

            @Override
            public String convertTo(String source, Type<String> destinationType, MappingContext mappingContext) {
                if (!StringUtils.hasText(source)) {
                    return source;
                }

                return ossImgDomain + source;
            }

            @Override
            public String convertFrom(String source, Type<String> destinationType, MappingContext mappingContext) {
                return source;
            }
        };
    }

}
