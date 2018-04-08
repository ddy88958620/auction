package com.trump.auction.back.activity.model;

import com.cf.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 分享活动扩展类
 * @author: zhangqingqiang
 * @date: 2018-03-21 21:06
 **/
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityExt extends ActivityShare implements Serializable{
    private String startDate;
    private String endDate;

    private String entrance;

    public Date getStartDateYmd() {
        if (StringUtils.isEmpty(startDate)){
            return null;
        }
        return DateUtil.getDate(startDate,"yyyy-MM-dd HH:mm:ss");
    }

    public Date getEndDateYmd() {
        if (StringUtils.isEmpty(endDate)){
            return null;

        }
        return DateUtil.getDate(endDate,"yyyy-MM-dd HH:mm:ss");
    }
}
