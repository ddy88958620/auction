package com.trump.auction.order.service.impl;

import com.trump.auction.order.dao.LogisticsCompanyDao;
import com.trump.auction.order.domain.LogisticsCompany;
import com.trump.auction.order.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    @Autowired
    LogisticsCompanyDao logisticsCompanyDao;

    public LogisticsCompany selectByPrimaryKey(Integer id){
        return logisticsCompanyDao.selectByPrimaryKey(id);
    }
    public int deleteByPrimaryKey(Integer id){
        return logisticsCompanyDao.deleteByPrimaryKey(id);
    }
    public int insert(LogisticsCompany obj){
        return logisticsCompanyDao.insert(obj);
    }
    public int insertSelective(LogisticsCompany obj){
        return logisticsCompanyDao.insertSelective(obj);
    }
    public int updateByPrimaryKeySelective(LogisticsCompany obj){
        return logisticsCompanyDao.updateByPrimaryKeySelective(obj);
    }
    public int updateByPrimaryKey(LogisticsCompany obj){
        return logisticsCompanyDao.updateByPrimaryKey(obj);
    }

    /**
     * 查询所有物流公司
     * @return
     */
    public List<LogisticsCompany> findLogisticsCompanyList(){
        return logisticsCompanyDao.findLogisticsCompanyList();
    }
}
