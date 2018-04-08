package com.trump.auction.account.api;

import com.cf.common.util.page.Paging;
import com.trump.auction.account.dto.AccountInfoDetailDto;
import com.trump.auction.account.model.AccountInfoDetailModel;

import java.util.List;

/**
 * Created by dingxp on 2017-12-26 0027.
 * 用户账户详情服务
 */
public interface AccountInfoDetailStubService {

    /**
     * 插入一条账户详情记录
     */
    int insertAccountInfoDetail(AccountInfoDetailDto accountInfoDetailDto);

    /**
     * 获取用户账户详情列表
     */
    @Deprecated
    List<AccountInfoDetailModel> getAccountInfoDetailListByUserId(Integer userId);

    /**
     * @param id 主键ID
     * @return 根据主键获取一条账户记录
     */
    AccountInfoDetailModel getAccountInfoDetailById(Integer id);


    /**
     * @param userId   用户ID
     * @param pageNum  页数
     * @param pageSize 分页大小
     * @param listType 1：可用开心币；2：过期或已使用开心币
     * @return 获取开心币列表
     */
    Paging<AccountInfoDetailModel> getAccountInfoDetailList(Integer userId, Integer pageNum, Integer pageSize, Integer listType);

}
