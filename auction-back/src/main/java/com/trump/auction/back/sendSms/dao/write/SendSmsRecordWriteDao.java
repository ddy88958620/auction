package com.trump.auction.back.sendSms.dao.write;

import com.trump.auction.back.sendSms.model.SendSmsRecord;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 15:53 2018/3/18
 * @Modified By:
 */
@Repository
public interface SendSmsRecordWriteDao {

    /**
     * 插入短信记录对象
     * @param sendSmsRecord 短信记录对象
     * @return 成功数
     */
    int saveSendSmsRecord(SendSmsRecord sendSmsRecord);
}
