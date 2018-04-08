package com.trump.auction.web.vo;

import lombok.Setter;
import lombok.ToString;

@ToString
public class AccountRechargeRuleVo {

    @Setter private Integer rechargeMoney;
    
    public Integer getRechargeMoney() {
		if(rechargeMoney != null){
			return rechargeMoney/100;
		}
		return 0;
	}
    
    @Setter private Integer detailType;

    @Setter private Integer presentCoin;
    
    public Integer getPresentCoin() {
		if(detailType == 2){
			return (getRechargeMoney() * presentCoin) /10000;
		}
		if(detailType == 3){
			return presentCoin/100;
		}
		return 0;
	}

    @Setter private Integer points;
    
    public Integer getPoints() {
    	if(detailType == 2){
			return (getRechargeMoney() * points) /10000;
		}
		if(detailType == 3){
			return points/100;
		}
		return 0;
	}

}

