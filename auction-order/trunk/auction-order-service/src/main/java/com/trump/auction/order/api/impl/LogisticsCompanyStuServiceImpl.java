package com.trump.auction.order.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.api.LogisticsCompanyStuService;
import com.trump.auction.order.domain.LogisticsCompany;
import com.trump.auction.order.model.LogisticsCompanyModel;
import com.trump.auction.order.service.LogisticsCompanyService;

/**
 * 物流公司相关服务
 * @author zhanping
 */
@Service(version = "1.0.0")
public class LogisticsCompanyStuServiceImpl implements LogisticsCompanyStuService {

    @Autowired
    LogisticsCompanyService auctionLogisticsCompanyService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 查询所有物流公司
     * @return
     */
    public List<LogisticsCompanyModel> findLogisticsCompanyList(){
        return beanMapper.mapAsList(auctionLogisticsCompanyService.findLogisticsCompanyList(),LogisticsCompanyModel.class);
    }

    /**
     * 根据物流公司ID查询物流公司
     * @param id 物流公司ID
     * @return
     */
    public LogisticsCompanyModel findLogisticsCompanyItemById(Integer id){
        return beanMapper.map(auctionLogisticsCompanyService.selectByPrimaryKey(id),LogisticsCompanyModel.class);
    }

    /**
     * 新增物流公司信息
     * @param obj
     * @return
     */
    public int insertLogisticsCompanyItem(LogisticsCompanyModel obj){
        return auctionLogisticsCompanyService.insertSelective(beanMapper.map(obj,LogisticsCompany.class));
    }

    /**
     * 根据物流公司ID跟新物流公司信息
     * @param obj
     * @return
     */
    public int updateLogisticsCompanyItemById(LogisticsCompanyModel obj){
        return auctionLogisticsCompanyService.updateByPrimaryKeySelective(beanMapper.map(obj,LogisticsCompany.class));
    }

    /**
     * 根据物流公司ID删除物流公司信息
     * @param id
     * @return
     */
    public int deleteLogisticsCompanyItemById(Integer id){
        return auctionLogisticsCompanyService.deleteByPrimaryKey(id);
    }

}
