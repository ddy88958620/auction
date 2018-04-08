package com.trump.auction.back.appraises.dao.read;

import com.trump.auction.back.appraises.model.OrderAppraisesRules;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


public interface OrderAppraisesRulesReadDao {

    OrderAppraisesRules selectById(Integer id);

	List<OrderAppraisesRules> findAll(HashMap<String, Object> params);

    List<OrderAppraisesRules> findAll();

    OrderAppraisesRules checkLevelExits(String appraisesLevel);

    /**
     * 根据评价规则对象参数查询评价规则对象
     * @param orderAppraisesRules
     * @return
     */
    OrderAppraisesRules findByParameter(OrderAppraisesRules orderAppraisesRules);

    List<OrderAppraisesRules> findAllLevel();

    /**
     * 查询评价规则对象(所有状态)
     */
    OrderAppraisesRules findByParameterAll(@Param("orderAppraisesRules") OrderAppraisesRules orderAppraisesRules);


}