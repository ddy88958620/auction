package com.trump.auction.back.sendSms.dao.read;

import com.trump.auction.back.sendSms.model.SendSmsRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 19:30 2018/3/16
 * @Modified By:
 */
@Repository
public interface SendSmsRecordReadDao {

    /**
     * 查询短信模板列表
     * @param params
     * @return 短信模板信息
     */
    List<SendSmsRecord> selectSendSmsRecordInfo(HashMap<String, Object> params);

    /**
     * 查询短信记录
     * @param sendSmsRecord 短信记录对象
     * @return 短信记录对象
     */
    SendSmsRecord findByParameter(SendSmsRecord sendSmsRecord);
}
