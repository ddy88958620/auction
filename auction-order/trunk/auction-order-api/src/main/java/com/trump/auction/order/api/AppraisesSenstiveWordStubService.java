package com.trump.auction.order.api;

import com.cf.common.utils.ServiceResult;

/**
 * 晒单评价敏感词库相关服务
 * @author hanliangliang
 */
public interface AppraisesSenstiveWordStubService {

    /**
     * 检验晒单评论是否合法
     * @param appraises 用户晒单评论
     * @return ServiceResult
     * @return code 0:成功  500:错误,存在敏感词汇
     * @return msg  提示信息
     * @return ext  敏感词汇集合
     */
    ServiceResult checkAppraises(String appraises);
}
