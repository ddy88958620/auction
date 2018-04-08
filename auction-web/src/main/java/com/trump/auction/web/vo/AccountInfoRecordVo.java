package com.trump.auction.web.vo;

import com.cf.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class AccountInfoRecordVo {

    @Getter
    @Setter
    private String transactionTag;
    @Getter
    @Setter
    private String remark;
    @Getter
    @Setter
    private String orderNo;

    @Setter
    private Date createTime;

    @Setter private String createTimeStr ;

    @Getter
    @Setter
    private Integer accountType;//1：拍币；2：赠币；3：积分；4：开心币
    /*@Getter
    @Setter
    private String coin;*/
    @Getter
    @Setter
    private String viewTransactionCoin;

    public String getCreateTimeStr() {
        return DateUtil.getDateFormat(createTime, "yyyy-MM-dd HH:mm:ss");

    }

}
