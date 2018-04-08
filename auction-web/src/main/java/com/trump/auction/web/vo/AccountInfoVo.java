package com.trump.auction.web.vo;

import com.cf.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class AccountInfoVo {
   /* @Getter*/
    @Setter
    private Integer id;
    @Setter
    private Integer accountId;
    @Getter
    @Setter
    private String transactionTag;
    @Getter
    @Setter
    private String productImage;
    @Getter
    @Setter
    private String productName;//商品名

    @Setter
    private Date updateTime;

    @Setter private String updateTimeStr ;

    @Getter
    @Setter
    private Integer accountType;//1：拍币；2：赠币；3：积分；4：开心币
   /* @Getter
    @Setter
    private Integer transactionCoin;//拍币*/
    @Getter
    @Setter
    private String viewTransactionCoin;//拍币

    public String getUpdateTimeStr() {
        return DateUtil.getDateFormat(updateTime, "yyyy-MM-dd HH:mm:ss");

    }

    public Integer getAccountId() {
        return id;
    }

}
