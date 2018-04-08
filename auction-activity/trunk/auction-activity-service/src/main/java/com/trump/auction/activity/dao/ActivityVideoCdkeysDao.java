package com.trump.auction.activity.dao;

import com.trump.auction.activity.domain.ActivityVideoCdkeys;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频会员兑换码数据操作相关
 * @author wangbo 2018/3/1.
 */
@Repository
public interface ActivityVideoCdkeysDao {
    /**
     * 查询要插入的Key是否存在
     */
    List<String> selectVideoCdkeysByKey(List<ActivityVideoCdkeys> list);

    /**
     * 批量插入视频卡券
     *
     * @param list 待插入的集合
     * @return
     */
    Integer insertVideoCdkeys(List<ActivityVideoCdkeys> list);
}
