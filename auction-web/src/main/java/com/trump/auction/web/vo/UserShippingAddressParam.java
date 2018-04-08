package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 用户收货地址封装类
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author yll
 * @date 2017年12月27日下午1:12:18
 */
@ToString
public class UserShippingAddressParam {
	@Getter @Setter private Integer id;
	@Getter @Setter private Integer addressId;
	@Getter @Setter private Integer userId;
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
}
