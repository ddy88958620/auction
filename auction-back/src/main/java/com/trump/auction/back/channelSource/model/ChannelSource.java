package com.trump.auction.back.channelSource.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author=hanliangliang
 * @Date=2018-01-29
 */
@Data
@ToString
public class ChannelSource {
    /**
     * 主键不为空
     */
    @Getter
    private Integer id;

    /**
     * 渠道名
     */
    @Getter
    private String channelName;

    /**
     * 渠道对应的key
     */
    @Getter
    private String  channelKey;

    /**
     * 渠道状态
     */
    @Getter
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    private Date updateTime;

    /**
     * 备注
     */
    @Getter
    private String remark;
}
