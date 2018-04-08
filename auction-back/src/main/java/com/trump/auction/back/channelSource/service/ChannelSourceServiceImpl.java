package com.trump.auction.back.channelSource.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.channelSource.dao.read.ChannelSourceDao;
import com.trump.auction.back.channelSource.dao.write.ChannelSourceWriteDao;
import com.trump.auction.back.channelSource.model.ChannelSource;
import com.trump.auction.back.order.model.Logistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author=hanliangliang
 * @Date=2018-01-29
 */
@Service
@Slf4j
public class ChannelSourceServiceImpl implements  ChannelSourceService {

    @Autowired
    private ChannelSourceDao channelSourceDao;

    @Autowired
    private ChannelSourceWriteDao channelSourceWriteDao;
    @Override
    public Paging<ChannelSource> findChannelSourceList(Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        log.info("findChannelSourceList invoke,StartTime:{},params:{}", startTime, params);
        Paging<ChannelSource> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            result = PageUtils.page(channelSourceDao.findChannelSourceList(params));
        } catch (NumberFormatException e) {
            log.error("findChannelSourceList error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findChannelSourceList end,duration:{}", endTime - startTime);
        return result;
    }

    @Override
    public ChannelSource findChannelSourceById(Integer id) {
        long startTime = System.currentTimeMillis();
        log.info("findChannelSource invoke,StartTime:{},params:{}", startTime, id);
        ChannelSource result = null;
        try {
            result = new ChannelSource();
            result= channelSourceDao.findChannelSource(id);
        } catch (NumberFormatException e) {
            log.error("findChannelSource error:", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("findChannelSource end,duration:{}", endTime - startTime);
        return result;
    }

    @Transactional
    @Override
    public int updateChannelSource(ChannelSource channelSource) {
        return channelSourceWriteDao.updateChannelSource(channelSource);
    }

    @Transactional
    @Override
    public int saveChannelSource(ChannelSource channelSource) {
        return channelSourceWriteDao.saveChannelSource(channelSource);
    }

    @Override
    public Boolean findChannelSourceKey(String key) {
        long startTime = System.currentTimeMillis();
        log.info("findChannelSourceKey invoke,StartTime:{},params:{}", startTime, key);
        Boolean result =false;
        try {
            ChannelSource channelSource = channelSourceDao.findChannelSourceKey(key);
            if (channelSource == null) {
                result = true;
            }
        } catch (Exception e) {
            log.error("findChannelSourceKey error:", e);
        }
        return result;
    }

    @Transactional
    @Override
    public int batchDelChannelSource(String [] ids) {
        return channelSourceWriteDao.batchDelChannelSource(ids);
    }
}
