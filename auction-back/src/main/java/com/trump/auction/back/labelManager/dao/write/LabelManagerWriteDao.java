package com.trump.auction.back.labelManager.dao.write;

import com.trump.auction.back.labelManager.model.LabelAuctionProduct;
import com.trump.auction.back.labelManager.model.LabelManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 19:51 2018/3/21
 * @Modified By:
 */
@Repository
public interface LabelManagerWriteDao {

    /**
     * 保存标签对象
     * @param labelManager
     * @return
     */
    int saveLabelManager(LabelManager labelManager);

    /**
     * 绑定标签对象
     * @param labelManager
     * @return
     */
    int bindLabelManager(LabelManager labelManager);

    /**
     * 修改标签对象
     * @param labelManager
     * @return
     */
    int editSuccessLabelManager(LabelManager labelManager);

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
    List<LabelAuctionProduct> selectLabelAuctionProduct(Map<String,Object> params);

}
