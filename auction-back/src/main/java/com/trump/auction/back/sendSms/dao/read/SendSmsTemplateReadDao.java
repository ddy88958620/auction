package com.trump.auction.back.sendSms.dao.read;

import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 11:06 2018/3/16
 * @Modified By:
 */
@Repository
public interface SendSmsTemplateReadDao {

    /**
     * 查询短信模板列表
     * @param params
     * @return 短信模板信息
     */
    List<SendSmsTemplate> selectSendSmsTemplateInfo(HashMap<String, Object> params);

    /**
     * 查询短信模板
     * @param sendSmsTemplate 短信模板对象
     * @return 短信模板对象
     */
    SendSmsTemplate findByParameter(SendSmsTemplate sendSmsTemplate);
}
