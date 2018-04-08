package com.trump.auction.back.sendSms.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;

import java.util.HashMap;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 11:29 2018/3/16
 * @Modified By:
 */
public interface SendSmsTemplateService {

    /**
     * 分页查询模板列表信息
     * @param params
     * @return 用户模板信息
     */

    Paging<SendSmsTemplate> selectSendSmsTemplateInfo(HashMap<String, Object> params);

    /**
     * 查询短信模板
     * @param sendSmsTemplate 短信模板对象
     * @return 短信模板对象
     */
    SendSmsTemplate findByParameter(SendSmsTemplate sendSmsTemplate);

    /**
     * 插入短信模板
     * @param sendSmsTemplate 短信模板对象
     * @return 短信模板对象
     */
    int saveSendSmsTemplate(SendSmsTemplate sendSmsTemplate);

    /**
     * 修改短信模板
     * @param sendSmsTemplate 短信模板对象
     * @return 短信模板对象
     */
    int updateSendSmsTemplate(SendSmsTemplate sendSmsTemplate);

    /**
     * 删除短信模板
     * @param sendSmsTemplate 短信模板对象
     * @return 短信模板对象
     */
    int deleteSendSmsTemplate(SendSmsTemplate sendSmsTemplate);
}
