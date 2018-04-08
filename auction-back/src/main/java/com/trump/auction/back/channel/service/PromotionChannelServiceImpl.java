package com.trump.auction.back.channel.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.JsonResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.channel.dao.read.PromotionChannelReadDao;
import com.trump.auction.back.channel.dao.read.PromotionChannelRecordReadDao;
import com.trump.auction.back.channel.dao.write.PromotionChannelDao;
import com.trump.auction.back.channel.dao.write.PromotionChannelRecordDao;
import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.channel.model.PromotionChannelRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 推广渠道管理
 * @author wangjian 2018-1-19
 */
@Slf4j
@Service("promotionChannelService")
public class PromotionChannelServiceImpl implements PromotionChannelService {

    @Autowired
    private PromotionChannelDao channelDao;

    @Autowired
    private PromotionChannelReadDao channelReadDao;

    @Autowired
    private PromotionChannelRecordReadDao channelRecordReadDao;

    @Autowired
    private PromotionChannelRecordDao channelRecordDao;

    private final  String DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";
    private final  String DEFAULT_END_TIME ="3000-12-01 12:30:20";

    @Override
    public PromotionChannel findByParam(PromotionChannel promotionChannel) {
        return channelReadDao.findByParam(promotionChannel);
    }

    /**
     * 保存推广渠道信息
     * @param promotionChannel
     * @return
     */
    @Override
    @Transactional
    public JsonResult savePromotionChannel(PromotionChannel promotionChannel){
        JsonResult jsonResult = new JsonResult("-1", "保存失败");
        long startTime = System.currentTimeMillis();
        log.info("savePromotionChannel begin... startTime:{},params:{}", startTime, promotionChannel);

        if (null == promotionChannel) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }
        if (promotionChannel.getChannelSource()==""){
            promotionChannel.setChannelSource(null);
        }
        int executeCount = channelDao.insert(promotionChannel);
        //promotionChannel.setId(executeCount);
        PromotionChannelRecord record  =setPromotionChannelRecord(promotionChannel);
        int executeCount1 =channelRecordDao.insert(record);
        if (executeCount > 0 && executeCount1 > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("保存成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("savePromotionChannel end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 根据ID删除推广渠道信息
     * @param channelIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public JsonResult deletePromotionChannel(String[] channelIds){
        JsonResult jsonResult = new JsonResult("-1", "删除失败");
        long startTime = System.currentTimeMillis();
        log.info("deletePromotionChannel begin... startTime:{},params:{}", startTime, channelIds);

        if (null == channelIds) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        int executeCount = channelDao.deleteByPrimaryKey(channelIds);

        if (executeCount > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("删除成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("deletePromotionChannel end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 修改消息中心信息
     * @param promotionChannel
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public JsonResult updatePromotionChannel(PromotionChannel promotionChannel){
        JsonResult jsonResult = new JsonResult("-1", "更新失败");
        long startTime = System.currentTimeMillis();
        log.info("updatePromotionChannel begin... startTime:{},params:{}", startTime, promotionChannel);

        if (null == promotionChannel) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }
        if (StringUtils.isEmpty(promotionChannel.getChannelSource())){
            promotionChannel.setChannelSource(null);
        }
        int executeCount = channelDao.updateByPrimaryKey(promotionChannel);
        //查询最新一次记录
        PromotionChannelRecord promotionChannelRecord= channelRecordReadDao.findPromotionChannelSettlementById(promotionChannel.getId());
        //增加record表记录
        int executeCount2=channelRecordDao.insert(setPromotionChannelRecord(promotionChannel));
        //修改上一次结束时间
        promotionChannelRecord.setEndTime(getDateBefore(promotionChannel.getStartTime(),1));
        int executeCount1 =channelRecordDao.updateEndTime(promotionChannelRecord);
        if (executeCount > 0 && executeCount1 > 0 && executeCount2 > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("更新成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("updatePromotionChannel end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 根据条件查询推广渠道列表
     * @param params
     * @return
     */
    @Override
    public JsonResult findPromotionChannelList(Map<String,Object> params){
        JsonResult jsonResult = new JsonResult("-1", "查询失败");
        long startTime = System.currentTimeMillis();
        log.info("findPromotionChannelList begin... startTime:{},params:{}", startTime, params);

        try {
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            List<PromotionChannel> promotionChannels=channelReadDao.findPromotionChannelList(params);
            for (PromotionChannel promotionChannel:promotionChannels) {
                promotionChannel= setPromotionChannel(promotionChannel);
            }
            jsonResult.setData(PageUtils.page(promotionChannels));
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("查询成功");
        } catch (Exception e) {
            log.error("findPromotionChannelList error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findPromotionChannelList end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 根据渠道ID查询单条推广渠道信息
     * @param channelId
     * @return
     */
    @Override
    public JsonResult findPromotionChannelOne(String channelId){
        JsonResult jsonResult = new JsonResult("-1", "查询失败");
        long startTime = System.currentTimeMillis();
        log.info("findPromotionChannelOne begin... startTime:{},params:{}", startTime, channelId);

        if (null == channelId) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        try {
            PromotionChannel promotionChannel=channelReadDao.selectByPrimaryKey(channelId);
            promotionChannel.setSettlementPrice(channelRecordReadDao.findPromotionChannelSettlementById(promotionChannel.getId()).getSettlementPrice());
            jsonResult.setData(setPromotionChannel(promotionChannel));
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("查询成功");
        } catch (Exception e) {
            log.error("findPromotionChannelOne error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findMessageCenterOne end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    @Override
    public JsonResult findChannelSource(String channelSource) {
        JsonResult jsonResult = new JsonResult("-1", "查询失败");
        long startTime = System.currentTimeMillis();
        log.info("findChannelSourceSource begin... startTime:{},params:{}", startTime, channelSource);

        if (channelSource==null ||channelSource=="") {
            jsonResult.setMsg("参数为空");
            jsonResult.setData(null);
            return jsonResult;
        }

        try {
            jsonResult.setData(channelReadDao.selectByChannelSourceSource(channelSource));
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("查询成功");
        } catch (Exception e) {
            log.error("findChannelSourceKey error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findChannelSourceSource end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    public  PromotionChannel setPromotionChannel(PromotionChannel promotionChannel){
        PromotionChannelRecord promotionChannelRecord=channelRecordReadDao.findPromotionChannelSettlementById(promotionChannel.getId());
        promotionChannel.setSettlementPrice(promotionChannelRecord.getSettlementPrice());
        promotionChannel.setStartTime(DateUtil.getDateFormat(promotionChannelRecord.getStartTime(),DATE_FORMAT));
        promotionChannel.setEndTime(promotionChannelRecord.getEndTime());
        return promotionChannel;
    }

    public PromotionChannelRecord setPromotionChannelRecord(PromotionChannel promotionChannel){
        PromotionChannelRecord promotionChannelRecord = new PromotionChannelRecord();
        int id=promotionChannel.getId();
        promotionChannelRecord.setPromotionChannelId(id);
        promotionChannelRecord.setSettlementMode(promotionChannel.getCooperationMode());
        promotionChannelRecord.setSettlementPrice(promotionChannel.getSettlementPrice());
        promotionChannelRecord.setStartTime(DateUtil.getDateFromStringByFormat(promotionChannel.getStartTime(), DATE_FORMAT));
        promotionChannelRecord.setEndTime(DateUtil.getDateFromStringByFormat(DEFAULT_END_TIME, DATE_FORMAT));
        return  promotionChannelRecord;
    }

    //结束时间减1s
    public Date getDateBefore(String date ,int second){
       Date date1= DateUtil.getDateFromStringByFormat(date,DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.SECOND, -second);
        return calendar.getTime();
    }

    @Override
    public PromotionChannel selectById(Integer id) {
        PromotionChannel channel =null;
        long startTime = System.currentTimeMillis();
        log.info("selectById begin... startTime:{},params:{}", startTime, id);
        if (StringUtils.isEmpty(id.toString())){
            return  channel;
        }
        try {
            channel=channelReadDao.selectById(id);
        }catch(Exception e) {
            log.error("selectById error:", e);
        }
        return channel;
    }
}

