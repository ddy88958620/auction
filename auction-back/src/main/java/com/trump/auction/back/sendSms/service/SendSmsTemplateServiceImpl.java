package com.trump.auction.back.sendSms.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.sendSms.dao.read.SendSmsTemplateReadDao;
import com.trump.auction.back.sendSms.dao.write.SendSmsTemplateWriteDao;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 11:29 2018/3/16
 * @Modified By:
 */
@Service
@Slf4j
public class SendSmsTemplateServiceImpl implements SendSmsTemplateService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendSmsTemplateReadDao sendSmsTemplateReadDao;

    @Autowired
    private SendSmsTemplateWriteDao sendSmsTemplateWriteDao;

    @Override
    public Paging<SendSmsTemplate> selectSendSmsTemplateInfo(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(sendSmsTemplateReadDao.selectSendSmsTemplateInfo(params));
    }

    @Override
    public SendSmsTemplate findByParameter(SendSmsTemplate sendSmsTemplate) {
        return sendSmsTemplateReadDao.findByParameter(sendSmsTemplate);
    }

    @Override
    @Transactional
    public int saveSendSmsTemplate(SendSmsTemplate sendSmsTemplate) {
        return sendSmsTemplateWriteDao.saveSendSmsTemplate(sendSmsTemplate);
    }

    @Override
    @Transactional
    public int updateSendSmsTemplate(SendSmsTemplate sendSmsTemplate) {
        return sendSmsTemplateWriteDao.updateSendSmsTemplate(sendSmsTemplate);
    }

    @Override
    @Transactional
    public int deleteSendSmsTemplate(SendSmsTemplate sendSmsTemplate) {
        return sendSmsTemplateWriteDao.deleteSendSmsTemplate(sendSmsTemplate);
    }
}
