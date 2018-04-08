package com.trump.auction.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频会员兑换码
 * @author wangbo 2018/3/1.
 */
@Data
public class ActivityVideoCdkeysModel implements Serializable {
    private static final long serialVersionUID = -2845393557418621835L;
    private Integer id;
    private String cdkey;
    private Integer cdkeyType;
    private String cdkeyName;
    private Date usefulLife;
    private String activateUrl;
    private Integer isUsed;
    private Date addTime;
    private Integer pageNum;
    private Integer numPerPage;
}
