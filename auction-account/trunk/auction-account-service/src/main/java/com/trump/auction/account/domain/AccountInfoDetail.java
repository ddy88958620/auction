package com.trump.auction.account.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class AccountInfoDetail implements Serializable{
    private static final long serialVersionUID = -4203689192585738777L;

    private Integer id;//主键
    private String orderNo;//拍卖期号
    private Integer userId ;//用户id
    private String userPhone;//用户手机号
    private Integer	coinType;//'类型：1：拍币；2：赠币；3：积分；4：开心币
    private Integer	transactionType;//'来源(1:注册赠送；2:微信充值；3:支付宝充值 4:充值赠送；6:订单拍币返回；7:取消差价购买...)
    private String	transactionTag;// '交易名称
    private Integer	buyCoinType; //'开心币类型:1：全场币，2：特定商品币
    private Integer	coin; // '数量/金钱(分)
    private Integer	availableCoin; //'可用
    private Integer	unavailableCoin; // '不可用【冻结】并非账户冻结
    private Integer productId;//商品ID
	private String productName;//商品名称
	private String	status; // '交易状态(1:未使用；2:已使用，3、部分使用...)
    private Date validStartTime;//'开心币有效开始时间
    private Date	validEndTime; // '开心币有效结束时间
    private String	remark; //'备注
    private Date	createTime;
    private Date	updateTime;
}
