package com.trump.auction.pals.api.model.wechat;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatPayQueryRequest implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8554260868843597555L;
    // 批次号
    private String batchNo;
}
