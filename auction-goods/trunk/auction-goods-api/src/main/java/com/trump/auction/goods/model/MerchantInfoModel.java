package com.trump.auction.goods.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家
 * Created by 罗显 on 2017/12/21.
 */
@Data
@ToString
public class MerchantInfoModel implements Serializable{

    private static final long serialVersionUID = -7412535364349658883L;

    private Integer id;
    private String  merchantName;
    private Integer merchantType; //  类型商家类型( 0.第三方 1.渠道 2.自营)
    private String  phone;
    private Date    createTime;
    private Date    updateTime;
    private String  userIp;
    private Integer userId;
    private Integer status; //0启用1停用
}

