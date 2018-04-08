package com.trump.auction.back.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频会员激活码
 * @author wangbo 2018/2/26.
 */
@Data
public class ActivityVideoCdkeys implements Serializable {
    private static final long serialVersionUID = 2580637557501424951L;

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
