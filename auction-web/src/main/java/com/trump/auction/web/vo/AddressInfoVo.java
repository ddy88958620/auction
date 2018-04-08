package com.trump.auction.web.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
public class AddressInfoVo {
	@JSONField(ordinal=1)
	@Getter @Setter private Integer i;
	@JSONField(ordinal=2)
	@Getter @Setter private String n;
	@JSONField(ordinal=3)
    @Getter @Setter private List<AddressInfoVo> c;
    
    @JSONField(serialize=false)
    @Getter @Setter private Integer id;
    @JSONField(serialize=false)
    @Getter @Setter private String addressName;
    
}
