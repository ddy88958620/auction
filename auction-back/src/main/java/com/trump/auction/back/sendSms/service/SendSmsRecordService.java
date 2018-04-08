package com.trump.auction.back.sendSms.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.sendSms.model.SendSmsRecord;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;

import java.util.HashMap;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 19:31 2018/3/16
 * @Modified By:
 */
public interface SendSmsRecordService {

    /**
     * 分页查询短信记录列表信息
     * @param params
     * @return 用户模板信息
     */

    Paging<SendSmsRecord> selectSendSmsRecordInfo(HashMap<String, Object> params);

    /**
     * 查询短信记录
     * @param sendSmsRecord 短信记录对象
     * @return 短信记录对象
     */
    SendSmsRecord findByParameter(SendSmsRecord sendSmsRecord);

    /**
     * 插入短信记录
     * @param sendSmsRecord 短信记录对象
     * @return 短信记录对象
     */
    int saveSendSmsRecord(SendSmsRecord sendSmsRecord);
}
