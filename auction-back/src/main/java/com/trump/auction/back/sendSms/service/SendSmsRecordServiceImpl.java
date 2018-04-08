package com.trump.auction.back.sendSms.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.sendSms.dao.read.SendSmsRecordReadDao;
import com.trump.auction.back.sendSms.dao.write.SendSmsRecordWriteDao;
import com.trump.auction.back.sendSms.model.SendSmsRecord;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import com.trump.auction.back.util.file.DateUtil;
import com.trump.auction.cust.api.SendSmsStubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 19:32 2018/3/16
 * @Modified By:
 */
@Service
@Slf4j
public class SendSmsRecordServiceImpl implements SendSmsRecordService {

    @Autowired
    private SendSmsRecordReadDao sendSmsRecordReadDao;

    @Autowired
    private SendSmsRecordWriteDao sendSmsRecordWriteDao;

    @Autowired
    private SendSmsStubService sendSmsStubService;

    @Autowired
    private SendSmsTemplateService sendSmsTemplateService;

    @Override
    public Paging<SendSmsRecord> selectSendSmsRecordInfo(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(sendSmsRecordReadDao.selectSendSmsRecordInfo(params));
    }

    @Override
    public SendSmsRecord findByParameter(SendSmsRecord sendSmsRecord) {
        return sendSmsRecordReadDao.findByParameter(sendSmsRecord);
    }

    @Override
    @Transactional
    public int saveSendSmsRecord(SendSmsRecord sendSmsRecord) {
        SendSmsTemplate sendSmsTemplate = new SendSmsTemplate();
        sendSmsTemplate.setId(sendSmsRecord.getSmsTemplateId());
        sendSmsTemplate = sendSmsTemplateService.findByParameter(sendSmsTemplate);
        // 发送群发短信
        String  code = sendSmsStubService.groupSendSmsByUserPhone(sendSmsRecord.getPhone(),sendSmsTemplate.getContent());
        sendSmsRecord.setCode(Integer.parseInt(code));
        sendSmsRecord.setReleaseTime(new Date());
        return sendSmsRecordWriteDao.saveSendSmsRecord(sendSmsRecord);
    }
}
