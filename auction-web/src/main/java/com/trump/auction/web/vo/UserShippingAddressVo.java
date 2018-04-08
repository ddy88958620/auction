package com.trump.auction.web.vo;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户地址实体
 * @author wangbo
 */

@ToString
public class UserShippingAddressVo  {
	@JSONField(serialize=false)
    @Setter private Integer id;
    @Setter private Integer addressId;
    @Getter @Setter private String userName;
    @Getter @Setter private String userPhone;
    @Getter @Setter private Integer provinceCode;
    @Getter @Setter private Integer cityCode;
    @Getter @Setter private Integer districtCode;
    @Getter @Setter private String address;
    @Getter @Setter private String provinceName;
    @Getter @Setter private String cityName;
    @Getter @Setter private String districtName;
    @Getter @Setter private Integer addressType;
    @Getter @Setter private String postCode;
    public Integer getAddressId() {
		return id;
	}

}