package com.trump.auction.back.order.service;


import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.order.dao.read.LogisticsDao;
import com.trump.auction.back.order.model.Logistics;
import com.trump.auction.order.api.LogisticsStuService;
import com.trump.auction.order.enums.EnumLogisticsStatus;
import com.trump.auction.order.model.LogisticsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 物流管理
 * @author Created by wangjian on 2017/12/25.
 */
@Slf4j
@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private LogisticsStuService logisticsStuService;

    /**
     * 分页查询物流列表
     * @param params
     * @return
     */
    @Override
    public Paging<Logistics> findLogisticsPage(Map<String, Object> params){
        long startTime = System.currentTimeMillis();
        log.info("findLogisticsPage invoke,StartTime:{},params:{}", startTime, params);

        Paging<Logistics> result = null;
        try {
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            result = PageUtils.page(logisticsDao.findLogisticsInfoList(params));
        } catch (NumberFormatException e) {
            log.error("findLogisticsPage error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findLogisticsPage end,duration:{}", endTime - startTime);
        return result;
    }

    /**
     * 发货
     * @param logistics
     * @return
     */
    @Override
    public boolean deliverGoods(Logistics logistics){
        long startTime = System.currentTimeMillis();
        log.info("deliverGoods invoke,StartTime:{},params:{}", startTime, logistics);
        boolean flag = false;

        if (null == logistics){
            log.warn("deliverGoods param logistics is null");
            return flag;
        }

        try {
            LogisticsModel logisticsModel = new LogisticsModel();
            logisticsModel.setOrderId(logistics.getOrderId());
            logisticsModel.setLogisticsId(logistics.getLogisticsId());
            logisticsModel.setLogisticsName(logistics.getLogisticsName());
            logisticsModel.setLogisticsInfo(logistics.getLogisticsInfo());
            logisticsModel.setReceiverName(logistics.getReceiverName());
            logisticsModel.setSendAddress(logistics.getSendAddress());
            logisticsModel.setSendPhone(logistics.getSendPhone());
            logisticsModel.setSendName(logistics.getSendName());
            logisticsModel.setRemark(logistics.getRemark());
            logisticsModel.setStartTime(new Date());
            logisticsModel.setLogisticsStatus(EnumLogisticsStatus.DISPATCHED.getValue());

            int executeCount = logisticsStuService.updateLogisticsItemById(logisticsModel);

            log.info("execute size:{}", executeCount);
            if (executeCount >= 0) {
                flag = true;
            }
        } catch (Exception e) {
            log.error("deliverGoods error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("deliverGoods end,duration:{}", endTime - startTime);
        return flag;
    }
}
