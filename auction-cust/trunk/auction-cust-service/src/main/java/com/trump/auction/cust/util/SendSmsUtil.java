package com.trump.auction.cust.util;

import com.cf.common.util.http.HttpUtil;
import com.cf.common.utils.MD5codingLowCase;
import com.trump.auction.cust.constant.CustConstant;
import com.trump.auction.cust.util.sms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 发送短信
 *
 * @author gaoyuhai 2016-6-17 下午03:50:44
 */
@Component
public class SendSmsUtil {

    private static Logger loger = LoggerFactory.getLogger(SendSmsUtil.class);
    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";// xml
    private static SendSmsUtil instance;
    @Autowired
    private JedisCluster jedisCluster;

    private SendSmsUtil() {
    }



    /**
     * 组装验证码短信
     *
     * @param telephone
     * @param sms
     * @return
     * @throws
     */
    public boolean sendSms(String telephone, String sms) {
//		loger.info("sendSms:" + telephone + "   sms=" + sms);
        Map<String, String> map = jedisCluster.hgetAll(CustConstant.SMS);
        String appsecret = "&app_secret=" + map.get("app_secret");

        XmlDto xmlDto = new XmlDto();
        Head head = new Head();
        head.setApp_key(map.get("app_key"));
        head.setNonce_str(map.get("nonce_str"));
        head.setSign("");

        head.setTime_stamp(DateUtil.getDateFormat("yyyyMMddHHmmss"));
        String keys = DateUtil.getDateFormat("yyyyMMddHHmmss");
        Dest dest = new Dest();
        dest.setDest_id(telephone);
        dest.setMission_num(CustConstant.VERIFY_CODE + keys);
        List<Dest> list = new ArrayList<Dest>();
        list.add(dest);
        Dests ds = new Dests();
        ds.setDest(list);
        Body body = new Body();
        body.setBatch_num(map.get("nonce_str") + keys);
        body.setDests(ds);
        body.setContent(map.get("sign_pre") + CustConstant.SMS_VERIFY_CONTENT.replace("#cont#", sms));
        body.setSms_type(CustConstant.VERIFY_CODE);
        xmlDto.setHead(head);
        xmlDto.setBody(body);

        String smsXml = sortString(xmlDto) + appsecret;
//		loger.info("md5-befor:" + smsXml);
        String smsSign = "";

        try {
            smsSign = new MD5codingLowCase().MD5(String.valueOf(smsXml));
        } catch (Exception e) {
            loger.info("md5-error:" + smsSign);
        }
//		loger.info("md5-after:" + smsSign);
        xmlDto.getHead().setSign(smsSign);

        String stringXml = XmlUtil.bulidMessage(xmlDto);
        stringXml = stringXml.replaceAll("<content>", "<content><![CDATA[").replaceAll("</content>", "]]></content>");
        String requestXml = XML_HEAD + stringXml;
//		loger.info("sendsms:" + telephone + " content:" + xmlDto);
        try {
            String result = HttpUtil.getInstance().doPost(map.get("sms_send_url"), requestXml);
//			loger.info("sendSms-result:" + result);
            if (null != result) {
                Result heads = new Result();
                for (String s : heads.getOutString()) {
                    result = result.replace(s, "");
                }
            } else {
                loger.info("sendsms-result-null");
            }
            Result r = (Result) XmlUtil.readMessage(Result.class, result);
            if (null != r) {
                if (r.getError_code().equals(CustConstant.SMS_SEND_SUCC)) {
                    return true;
                } else {
                    loger.info("sendsms-result-Error_code:" + r.getError_code());
                }
            } else {
                loger.info("sendsms-result-xml-null");
            }
        } catch (Exception e) {
            loger.error("sendSms error, telephone: {}, sms: {}", telephone, sms, e);
        }
        return false;
    }

    /**
     * 自定义短信内容
     *
     * @param telephone 手机号
     * @param content   内容
     * @return
     * @throws
     */
    public boolean sendSmsDiy(String telephone, String content, String signPre) {
//		loger.info("sendSms:" + telephone + "   sms=" + content);
        String keys = DateUtil.getDateFormat("yyyyMMddHHmmss");
        Dest dest = new Dest();
        dest.setDest_id(telephone);
        dest.setMission_num(CustConstant.VERIFY_CODE + keys);
        return sendSmsDiy2(Arrays.asList(dest), content, keys, signPre);
    }

    public boolean sendSmsDiy2(List<Dest> list, String content, String keys, String signPre) {
        Map<String, String> map = jedisCluster.hgetAll(CustConstant.SMS);
        String appsecret = "&app_secret=" + map.get("app_secret");

        XmlDto xmlDto = new XmlDto();
        Head head = new Head();
        head.setApp_key(map.get("app_key"));
        head.setNonce_str(map.get("nonce_str"));
        head.setSign("");
        head.setTime_stamp(DateUtil.getDateFormat("yyyyMMddHHmmss"));
        Body body = new Body();
        body.setBatch_num(map.get("nonce_str") + keys);
        Dests ds = new Dests();
        ds.setDest(list);
        body.setDests(ds);
//		body.setContent(map.get("sign_pre") + content);
        body.setContent(signPre + content);
        body.setSms_type(CustConstant.VERIFY_CODE);
        xmlDto.setHead(head);
        xmlDto.setBody(body);

        String smsXml = sortString(xmlDto) + appsecret;
//		loger.info("md5-befor:" + smsXml);
        String smsSign = "";

        try {
            smsSign = new MD5codingLowCase().MD5(String.valueOf(smsXml));
        } catch (Exception e) {
            loger.info("md5-error:" + smsSign);
        }
//		loger.info("md5-after:" + smsSign);
        xmlDto.getHead().setSign(smsSign);

        String stringXml = XmlUtil.bulidMessage(xmlDto);
        stringXml = stringXml.replaceAll("<content>", "<content><![CDATA[").replaceAll("</content>", "]]></content>");
        String requestXml = XML_HEAD + stringXml;
//		loger.info("sendsms:" + ds + " content:" + xmlDto);
        try {
            String result = HttpUtil.getInstance().doPost(map.get("sms_send_url"), requestXml);
//			loger.info("sendSms-result:" + result);
            if (null != result) {
                Result heads = new Result();
                for (String s : heads.getOutString()) {
                    result = result.replace(s, "");
                }
            } else {
                loger.info("sendsms-result-null");
            }
            Result r = (Result) XmlUtil.readMessage(Result.class, result);
            if (null != r) {
                if (r.getError_code().equals(CustConstant.SMS_SEND_SUCC)) {
                    return true;
                } else {
                    loger.info("sendsms-result-Error_code:" + r.getError_code());
                }
            } else {
                loger.info("sendsms-result-xml-null");
            }
        } catch (Exception e) {
            loger.error("sendSmsDiy2 error, content: {}, keys: {}, signPre: {}", content, keys, signPre);
        }
        return false;
    }

    public String sendSmsDiy3(List<Dest> list, String content, String keys, String signPre) {
        String code="000002";
        Map<String, String> map = jedisCluster.hgetAll(CustConstant.SMS_MARKET);
        String appsecret = "&app_secret=" + map.get("app_secret_market");

        XmlDto xmlDto = new XmlDto();
        Head head = new Head();
        head.setApp_key(map.get("app_key_market"));
        head.setNonce_str(map.get("nonce_str_market"));
        head.setSign("");
        head.setTime_stamp(DateUtil.getDateFormat("yyyyMMddHHmmss"));
        Body body = new Body();
        body.setBatch_num(map.get("nonce_str_market") + keys);
        Dests ds = new Dests();
        ds.setDest(list);
        body.setDests(ds);
//		body.setContent(map.get("sign_pre") + content);
        body.setContent(signPre + content);
        body.setSms_type(CustConstant.ADVERT);
        xmlDto.setHead(head);
        xmlDto.setBody(body);

        String smsXml = sortString(xmlDto) + appsecret;
//		loger.info("md5-befor:" + smsXml);
        String smsSign = "";

        try {
            smsSign = new MD5codingLowCase().MD5(String.valueOf(smsXml));
        } catch (Exception e) {
            loger.info("md5-error:" + smsSign);
        }
//		loger.info("md5-after:" + smsSign);
        xmlDto.getHead().setSign(smsSign);

        String stringXml = XmlUtil.bulidMessage(xmlDto);
        stringXml = stringXml.replaceAll("<content>", "<content><![CDATA[").replaceAll("</content>", "]]></content>");
        String requestXml = XML_HEAD + stringXml;
//		loger.info("sendsms:" + ds + " content:" + xmlDto);
        try {
            String result = HttpUtil.getInstance().doPost(map.get("sms_send_url_market"), requestXml);
//			loger.info("sendSms-result:" + result);
            if (null != result) {
                Result heads = new Result();
                for (String s : heads.getOutString()) {
                    result = result.replace(s, "");
                }
            } else {
                loger.info("sendsms-result-null");
            }
            Result r = (Result) XmlUtil.readMessage(Result.class, result);
            code = r.getError_code();
            if (null != r) {
                if (r.getError_code().equals(CustConstant.SMS_SEND_SUCC)) {
                    return CustConstant.SMS_SEND_SUCC;
                } else {
                    loger.info("sendsms-result-Error_code:" + r.getError_code());
                }
            } else {
                loger.info("sendsms-result-xml-null");
            }
        } catch (Exception e) {
            loger.error("sendSmsDiy3 error, content: {}, keys: {}, signPre: {}", content, keys, signPre);
        }
        return code;
    }

    /**
     * 排序
     *
     * @param xmlDto
     * @return
     */
    private static String sortString(XmlDto xmlDto) {
        if (null != xmlDto) {
            StringBuffer sb = new StringBuffer();
            sb.append("app_key=" + xmlDto.getHead().getApp_key()).append("&batch_num=" + xmlDto.getBody().getBatch_num()).append("&content=" + xmlDto.getBody().getContent());
            List<Dest> list = xmlDto.getBody().getDests().getDest();
            StringBuffer sb0 = new StringBuffer();
            for (Dest d : list) {
                sb0.append("&dest_id=" + d.getDest_id());
            }
            for (Dest d : list) {
                sb0.append("&mission_num=" + d.getMission_num());
            }
            sb.append(sb0).append("&nonce_str=" + xmlDto.getHead().getNonce_str()).append("&sms_type=" + xmlDto.getBody().getSms_type()).append("&time_stamp=" + xmlDto.getHead().getTime_stamp());

            return sb.toString();
        }
        return null;
    }

}
