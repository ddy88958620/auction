package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 评价晒单参数封装
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:07:24
 */
@ToString
public class AppraiseParam {

    @Getter @Setter private String orderId;
    @Getter @Setter private String appraisesPic;
    @Getter @Setter private String content;

}
