package com.trump.auction.back.product.dao.read;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 库存DAO
 * @author
 * @date 2017/12/21
 */
@Repository
public interface ProductInventoryLogDao {


    /**
     * 分页查询库存数据
     * @param paramVo
     * @return
     */
   List<InventoryVo> selectByParamVo(ParamVo paramVo);


    /**
     * 获取库存信息通过商品ID
     * @param productId
     * @return
     */
   InventoryVo getInventoryByProductId(@Param("productId")Integer productId);
}
