package com.trump.auction.back.sendSms.dao.write;

import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 10:07 2018/3/17
 * @Modified By:
 */
@Repository
public interface SendSmsTemplateWriteDao {

    /**
     * 插入短信模板对象
     * @param sendSmsTemplate 信模板对象
     * @return 成功数
     */
    int saveSendSmsTemplate(SendSmsTemplate sendSmsTemplate);

    /**
     * 修改短信模板对象
     * @param sendSmsTemplate 短信模板对象
     * @return 成功数
     */
    int updateSendSmsTemplate(SendSmsTemplate sendSmsTemplate);

    /**
     * 删除短信模板对象
     * @param sendSmsTemplate 短信模板对象
     * @return 成功数
     */
    int deleteSendSmsTemplate(SendSmsTemplate sendSmsTemplate);
}
