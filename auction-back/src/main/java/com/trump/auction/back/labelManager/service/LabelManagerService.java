package com.trump.auction.back.labelManager.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.labelManager.model.LabelAuctionProduct;
import com.trump.auction.back.labelManager.model.LabelManager;

import java.util.List;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 16:07 2018/3/21
 * @Modified By:
 */
public interface LabelManagerService {

    /**
     * 查询全部标签
     * @return
     */
    List<LabelManager> findAll();

    /**
     * 根据参数查询标签信息
     * @param params
     * @return
     */
    Paging<LabelManager> selectLabelManagerInfo(Map<String,Object> params);

    /**
     * 根据标签对象查询
     * @param labelManager
     * @return
     */
    LabelManager findByLabelManager(LabelManager labelManager);

    /**
     * 保存标签对象
     * @param labelManager
     * @return
     */
    int saveLabelManager(LabelManager labelManager);

    /**
     * 修改标签对象
     * @param labelManager
     * @return
     */
    int editSuccessLabelManagerStatus(LabelManager labelManager);

    /**
     * 绑定标签对象
     * @param labelManager
     * @return
     */
    int bindLabelManager(LabelManager labelManager);

    /**
     * 启用与禁用
     * @param labelManager
     * @return
     */
    int updateLabelManagerStatus(LabelManager labelManager);

    /**
     * 删除标签
     * @param labelManager
     * @return
     */
    int deleteLabelManagerInfo(LabelManager labelManager);

    /**
     * 根据参数查询标签拍品
     * @param params
     * @return
     */
    Paging<LabelAuctionProduct> selectLabelAuctionProduct(Map<String,Object> params);

    /**
     * 拥有标签拍品
     * @param params
     * @return
     */
    Paging<LabelAuctionProduct> havelLabelAuctionProduct(Map<String,Object> params);


    /**
     * 该拍品的标签
     * @param param
     * @return
     */
    List<LabelManager> auctinoProductInLabelManager(String  param);

}
