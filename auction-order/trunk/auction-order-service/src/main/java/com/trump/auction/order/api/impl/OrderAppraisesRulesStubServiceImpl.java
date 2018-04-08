package com.trump.auction.order.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.order.api.OrderAppraisesRulesStubService;
import com.trump.auction.order.model.OrderAppraisesRulesModel;
import com.trump.auction.order.service.OrderAppraisesRulesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/09
 */
@Service(version = "1.0.0")
public class OrderAppraisesRulesStubServiceImpl  implements OrderAppraisesRulesStubService{
    @Autowired
    private OrderAppraisesRulesService orderAppraisesRulesService;

    @Override
    public List<OrderAppraisesRulesModel>  queryAllRules() {
        return orderAppraisesRulesService.queryAllRules();
    }

    @Override
    public String orderAppraisesRulesLevelCheck(String appraisesWords, String appraisesPic) {
        return orderAppraisesRulesService.orderAppraisesRulesLevelCheck( appraisesWords,  appraisesPic);
    }
}
