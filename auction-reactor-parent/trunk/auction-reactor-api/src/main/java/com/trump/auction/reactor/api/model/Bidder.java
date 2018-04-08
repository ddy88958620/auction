package com.trump.auction.reactor.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;

/**
 * 出价人
 *
 * @author Owen
 * @since 2018/1/9
 */
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"userId", "subId"})
public class Bidder implements Serializable {

    /**
     * 自动出价用户编号
     */
    private static final String AUTO_BIDDER_ID = "1";

    /**
     * 用户编号
     */
    @Getter @Setter private String userId;
    /**
     * 用户子编号
     */
    @Getter @Setter private String subId;
    /**
     * 昵称
     */
    @Getter @Setter private String name;
    /**
     * 所在地区
     */
    @Getter @Setter private String addrArea;
    /**
     * 头像地址
     */
    @Getter @Setter private String headImgUrl;
    /**
     * IP 地址
     */
    @Getter @Setter private String ipAddr;
    /**
     * 是否为自动出价用户
     */
    public static boolean isAutoBidder(Bidder bidder) {
        return bidder != null && AUTO_BIDDER_ID.equals(bidder.getUserId());
    }

    /**
     * 获取完整的用户编号
     * <p>
     *     含子用户编号
     * </p>
     */
    @JSONField(serialize = false)
    public String getId() {
        return userId + "-" + subId;
    }

    public static Bidder create(@NonNull String userId, @NonNull String subUserId) {
        Bidder bidder = new Bidder();
        bidder.setUserId(userId);
        bidder.setSubId(subUserId);

        return bidder;
    }

}
