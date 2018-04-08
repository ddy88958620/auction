package com.trump.auction.back.labelManager.dao.read;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.labelManager.model.LabelAuctionProduct;
import com.trump.auction.back.labelManager.model.LabelManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 16:07 2018/3/21
 * @Modified By:
 */
@Repository
public interface LabelManagerReadDao {

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
    List<LabelManager> selectLabelManagerInfo(Map<String,Object> params);

    /**
     * 根据标签对象查询
     * @param labelManager
     * @return
     */
    LabelManager findByLabelManager(LabelManager labelManager);

    /**
     * 拥有标签拍品
     * @param params
     * @return
     */
    List<LabelAuctionProduct> haveLabelAuctionProduct(Map<String,Object> params);

    /**
     * 查询该拍品所拥有的标签
     * @param params
     * @return
     */
    List<LabelManager> auctinoProductInLabelManager(String  params);
}
