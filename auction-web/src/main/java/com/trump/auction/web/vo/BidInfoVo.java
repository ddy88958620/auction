package com.trump.auction.web.vo;

import java.math.BigDecimal;

import com.trump.auction.web.util.Base64Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 出价记录VO
 * @author Administrator
 * @date 2018/1/6
 */
@ToString
public class BidInfoVo {

	@Getter @Setter private String headImg = "";
	/**
     * 用户名
     */
	@Setter private String userName;
	
	public String getUserName() {
		if(userName == null){
			return "";
		}
		return Base64Utils.decodeStr(userName);
	}
    
	@Getter @Setter private String statusDesc = "领先";

    /**
     * 出价金额
     */
	@Getter @Setter private BigDecimal bidPrice;
   
    /**
     * 昵称
     */
	@Setter private String nickName;
	
	public String getNickName() {
		if(nickName == null){
			return "";
		}
		return Base64Utils.decodeStr(nickName);
	}

	@Getter @Setter private String address = "";
    
	
	

}
