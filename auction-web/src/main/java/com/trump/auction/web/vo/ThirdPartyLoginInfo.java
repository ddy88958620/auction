package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangbo 2017/12/21.
 */
@ToString
public class ThirdPartyLoginInfo {
    @Getter @Setter private String openId;
    @Getter @Setter private String name;
    @Getter @Setter private String iconUrl;
    @Getter @Setter private String gender;
    @Getter @Setter private String type;
	@Getter @Setter private String province;
	@Getter @Setter private String city;
}
