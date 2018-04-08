package com.trump.auction.back.messagecenter.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.messagecenter.dao.read.MessageCenterReadDao;
import com.trump.auction.back.messagecenter.dao.write.MessageCenterDao;
import com.trump.auction.back.messagecenter.model.MessageCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消息中心管理
 * @author Created by Administrator on 2018/1/3.
 */
@Slf4j
@Service("messageCenterService")
public class MessageCenterServiceImpl implements MessageCenterService {

    @Autowired
    private MessageCenterDao messageCenterDao;

    @Autowired
    private MessageCenterReadDao messageCenterReadDao;

    /**
     * 保存消息中心信息
     * @param messageCenter
     * @return
     */
    @Override
    public JsonResult saveMessageCenter(MessageCenter messageCenter){
        JsonResult jsonResult = new JsonResult("-1", "保存失败");
        long startTime = System.currentTimeMillis();
        log.info("saveMessageCenter begin... startTime:{},params:{}", startTime, messageCenter);

        if (null == messageCenter) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        int executeCount = messageCenterDao.saveMessageCenter(messageCenter);

        if (executeCount > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("保存成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("saveMessageCenter end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 根据ID删除消息中心信息
     * @param id
     * @return
     */
    @Override
    public JsonResult deleteMessageCenter(String[] id){
        JsonResult jsonResult = new JsonResult("-1", "删除失败");
        long startTime = System.currentTimeMillis();
        log.info("deleteMessageCenter begin... startTime:{},params:{}", startTime, id);

        if (null == id) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        int executeCount = messageCenterDao.deleteMessageCenter(id);

        if (executeCount > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("删除成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("deleteMessageCenter end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 修改消息中心信息
     * @param messageCenter
     * @return
     */
    @Override
    public JsonResult updateMessageCenter(MessageCenter messageCenter){
        JsonResult jsonResult = new JsonResult("-1", "更新失败");
        long startTime = System.currentTimeMillis();
        log.info("updateMessageCenter begin... startTime:{},params:{}", startTime, messageCenter);

        if (null == messageCenter) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        int executeCount = messageCenterDao.updateMessageCenter(messageCenter);

        if (executeCount > 0) {
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("更新成功");
        }

        long endTime = System.currentTimeMillis();
        log.info("updateMessageCenter end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 根据条件查询消息中心列表
     * @param params
     * @return
     */
    @Override
    public JsonResult findMessageCenterList(Map<String,Object> params){
        JsonResult jsonResult = new JsonResult("-1", "查询失败");
        long startTime = System.currentTimeMillis();
        log.info("findMessageCenterList begin... startTime:{},params:{}", startTime, params);

        try {
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            jsonResult.setData(PageUtils.page(messageCenterReadDao.findMessageCenterList(params)));
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("查询成功");
        } catch (Exception e) {
            log.error("findMessageCenterList error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findMessageCenterList end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }

    /**
     * 获取所有消息信息
     * @return
     */
    @Override
    public List<MessageCenter> findMessageCenterAll(){
        long startTime = System.currentTimeMillis();
        log.info("findMessageCenterAll begin... startTime:{}", startTime);

        List<MessageCenter> messageCenterList = null;
        try {
            messageCenterList = messageCenterReadDao.findMessageCenterList(null);
        } catch (Exception e) {
            log.error("findMessageCenterAll error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findMessageCenterAll end... duration:{},result:{}", endTime - startTime, messageCenterList);
        return messageCenterList;
    }

    /**
     * 根据ID查询单条消息中心信息
     * @param id
     * @return
     */
    @Override
    public JsonResult findMessageCenterOne(Integer id){
        JsonResult jsonResult = new JsonResult("-1", "查询失败");
        long startTime = System.currentTimeMillis();
        log.info("findMessageCenterOne begin... startTime:{},params:{}", startTime, id);

        if (null == id) {
            jsonResult.setMsg("参数为空");
            return jsonResult;
        }

        try {
            jsonResult.setData(messageCenterReadDao.selectByPrimaryKey(id));
            jsonResult.setCode(JsonResult.SUCCESS);
            jsonResult.setMsg("查询成功");
        } catch (Exception e) {
            log.error("findMessageCenterOne error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findMessageCenterOne end... duration:{},result:{}", endTime - startTime, jsonResult);
        return jsonResult;
    }
}
