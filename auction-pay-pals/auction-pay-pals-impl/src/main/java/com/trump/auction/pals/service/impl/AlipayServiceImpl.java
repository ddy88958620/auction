package com.trump.auction.pals.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cf.common.utils.GenerateNo;
import com.trump.auction.pals.api.constant.EnumAlipayStatus;
import com.trump.auction.pals.api.constant.EnumOrderType;
import com.trump.auction.pals.api.constant.EnumPayMode;
import com.trump.auction.pals.api.constant.EnumPayStatus;
import com.trump.auction.pals.api.constant.EnumPayType;
import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayBackRequest;
import com.trump.auction.pals.api.model.alipay.AlipayBackResponse;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;
import com.trump.auction.pals.dao.PayRecordDao;
import com.trump.auction.pals.domain.PayRecord;
import com.trump.auction.pals.domain.ThirdPartyAsk;
import com.trump.auction.pals.domain.alipay.AlipayConfig;
import com.trump.auction.pals.service.AlipayService;
import com.trump.auction.pals.service.ThirdPartyAskService;
import com.trump.auction.pals.util.AlipayConstant;

import redis.clients.jedis.JedisCluster;

/**
 * 支付宝相关API
 */
@Service
public class AlipayServiceImpl implements AlipayService{

    private Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ThirdPartyAskService partyAskService;
    
    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public AlipayPayResponse toAlipayPay(AlipayPayRequest apr) {
        AlipayPayResponse response;
        logger.error("START:  userId:{} , amount:{} , batchNo:{}", apr.getUserId(), apr.getMoney(), apr.getBatchNo());
        try {
            String orderNo = GenerateNo.getInstance().payRecordNo16(EnumOrderType.AH.getType());
            String moneyAmount = new DecimalFormat("0.00").format(apr.getMoney() / 100.0);// 格式化金额
            //String moneyAmount = "0.01";//测试

            String appid = AlipayConstant.getAppId(jedisCluster);
            String privateKey = AlipayConstant.getPrivateKey(jedisCluster);
            String publicKey = AlipayConstant.getPublicKey(jedisCluster);
            String gatewayUrl = AlipayConstant.getGatewayUrl(jedisCluster);
            String notifyUrl = AlipayConstant.getNotifyUrl(jedisCluster);

            AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(apr.getBody());
            model.setSubject(apr.getSubject());
            model.setOutTradeNo(orderNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(moneyAmount);
            model.setProductCode("QUICK_MSECURITY_PAY");
            payRequest.setBizModel(model);
            payRequest.setNotifyUrl(notifyUrl);

            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid,privateKey,AlipayConfig.format, AlipayConfig.input_charset, publicKey, AlipayConfig.sign_type);
            AlipayTradeAppPayResponse payResponse = new AlipayTradeAppPayResponse();
            payResponse = alipayClient.sdkExecute(payRequest);
            String payBody =  payResponse.getBody();

            ThirdPartyAsk partyAsk = new ThirdPartyAsk();
            partyAsk.setUserId(apr.getUserId());
            partyAsk.setOrderType(EnumPayType.ALIPAY.getType());
            partyAsk.setOrderNo(orderNo);
            partyAsk.setReqParams(payBody);
            partyAsk.setNotifyParams(JSONObject.toJSONString(payRequest));
            partyAsk.setStatus(EnumPayStatus.STATUS_WAIT.getType());
            partyAskService.insert(partyAsk);
            
            PayRecord payRecord = new PayRecord();
			payRecord.setBatchNo(apr.getBatchNo());
			payRecord.setOrderNo(orderNo);
			payRecord.setMerchantId(appid);
			//payRecord.setPayAmount(apr.getMoney());
			
			payRecord.setPayAmount(1);//测试
			
			payRecord.setPayFrom(apr.getPayFrom());//支付来源
			payRecord.setPayMode(EnumPayMode.ZB.getType());
			payRecord.setPayType(EnumPayType.WECHAT.getType());
			payRecord.setStatus(EnumPayStatus.STATUS_WAIT.getType());
			payRecord.setUserId(apr.getUserId());
            
			payRecordDao.insertSelective(payRecord);

            response = new AlipayPayResponse(AlipayPayResponse.SUCCESS, "支付成功", payBody,apr.getBatchNo());
        } catch (Exception e) {
            logger.error("AlipayServiceImpl.toAlipayPay error", e);
            response = new AlipayPayResponse(AlipayPayResponse.FAILED, "支付失败");
        }
        return response;
    }

    @Override
    public AlipayBackResponse toAlipayBack(AlipayBackRequest abr) {
        Map<String,String> params = abr.getParams();
        logger.info("支付宝回调结果参数：{}",JSONObject.toJSONString(abr.getParams()));
        // 验签金额
        BigDecimal total_amount = StringUtils.isBlank(params.get("total_amount")) ? new BigDecimal(0.00) :
                new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100));
        String orderNo = params.get("out_trade_no");
        String trade_status = params.get("trade_status");
        //String msg = params.get("msg");

        AlipayBackResponse response = new AlipayBackResponse(AlipayBackResponse.FAILED, "支付失败");
        if (StringUtils.isNotBlank(orderNo)) {
            ThirdPartyAsk partyAsk = partyAskService.findByOrderNo(orderNo);
            PayRecord payRecord = payRecordDao.selectByOrderNo(orderNo);

            if(null !=  payRecord && !EnumPayStatus.STATUS_SUC.getType().equals(payRecord.getStatus()) && payRecord.getPayAmount().equals(total_amount.intValue())){
            	
            	if (null != partyAsk && !EnumPayStatus.STATUS_SUC.getType().equals(partyAsk.getStatus())) {
                    ThirdPartyAsk partyAskNew = new ThirdPartyAsk();
                    partyAskNew.setOrderNo(partyAsk.getOrderNo());
                    if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                        partyAskNew.setStatus(EnumPayStatus.STATUS_SUC.getType());
                        payRecord.setStatus(EnumPayStatus.STATUS_SUC.getType());
                        response = new AlipayBackResponse(AlipayBackResponse.SUCCESS, "支付成功", payRecord.getBatchNo(), payRecord.getPayFrom());
                    }
                    else{
                        partyAskNew.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                        payRecord.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                        response = new AlipayBackResponse(AlipayBackResponse.FAILED, "支付失败");
                    }
                    partyAskService.updateByOrderNo(partyAskNew);
                    payRecordDao.updateByOrderNo(payRecord);
                }
                else{
                	if(null==partyAsk){
                		response = new AlipayBackResponse(AlipayBackResponse.FAILED, "该支付数据不存在");
                	}else {
                        response = new AlipayBackResponse(AlipayBackResponse.SUCCESS, "该订单已支付成功过", payRecord.getBatchNo(), payRecord.getPayFrom());
    				}
                }
            	
            }else{
            	if(null==payRecord){
            		response = new AlipayBackResponse(AlipayBackResponse.FAILED, "该支付数据不存在");
            	}else if(!payRecord.getPayAmount().equals(total_amount.intValue())) {
                    response = new AlipayBackResponse(AlipayBackResponse.FAILED, "该订单支付金额验证不正确", payRecord.getBatchNo(), payRecord.getPayFrom());
				}else{
					response = new AlipayBackResponse(AlipayBackResponse.SUCCESS, "该订单已支付成功过", payRecord.getBatchNo(), payRecord.getPayFrom());
				}
            }
        }
        return response;
    }

    @Override
    public AliPayQueryResponse toAlipayQuery(AlipayQueryRequest aqr){
        AliPayQueryResponse queryResult = new AliPayQueryResponse(AliPayQueryResponse.FAILED, "查询异常");
        String batchNo = aqr.getBatchNo();
        logger.info("toAlipayQuery param：batchNo:{}",batchNo);

        if (StringUtils.isBlank(batchNo)) {
            queryResult.setMsg("batchNo is null");
            return queryResult;
        }

        try {
            PayRecord payRecord = payRecordDao.selectByBatchNo(aqr.getBatchNo());
            String appid = AlipayConstant.getAppId(jedisCluster);
            String privateKey = AlipayConstant.getPrivateKey(jedisCluster);
            String publicKey = AlipayConstant.getPublicKey(jedisCluster);
            String gatewayUrl = AlipayConstant.getGatewayUrl(jedisCluster);

            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privateKey, AlipayConfig.format, AlipayConfig.input_charset, publicKey, AlipayConfig.sign_type);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(payRecord.getOrderNo());
            request.setBizModel(model);

            //通过alipayClient调用API，获得对应的response类
            AlipayTradeQueryResponse response = alipayClient.execute(request);

            ThirdPartyAsk partyAsk = partyAskService.findByOrderNo(payRecord.getOrderNo());
            ThirdPartyAsk partyAskNew = new ThirdPartyAsk();
            partyAskNew.setOrderNo(partyAsk.getOrderNo());
            PayRecord newPayRecord = new PayRecord();
            newPayRecord.setOrderNo(partyAsk.getOrderNo());

            if(response.isSuccess()){
                logger.info("alipayClient.execute() success");
                String resultBody = response.getBody();
                logger.info("resultBody...{}", resultBody);
                String tradeStatus = response.getTradeStatus();

                if (StringUtils.isNotBlank(tradeStatus)) {

                    if (null != partyAsk && !EnumPayStatus.STATUS_SUC.getType().equals(partyAsk.getStatus())) {
                        //同步修改第三方请求表中的状态
                        if (EnumAlipayStatus.TRADE_SUCCESS.getType().equals(tradeStatus) || EnumAlipayStatus.TRADE_FINISHED.getType().equals(tradeStatus)) {
                            partyAskNew.setStatus(EnumPayStatus.STATUS_SUC.getType());
                            newPayRecord.setStatus(EnumPayStatus.STATUS_SUC.getType());
                        } else if (EnumAlipayStatus.WAIT_BUYER_PAY.getType().equals(tradeStatus)) {
                            partyAskNew.setStatus(EnumPayStatus.STATUS_WAIT.getType());
                            newPayRecord.setStatus(EnumPayStatus.STATUS_WAIT.getType());
                        } else {
                            partyAskNew.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                            newPayRecord.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                        }
                    }
                }

                queryResult.setCode(AliPayQueryResponse.SUCCESS);
                queryResult.setMsg(response.getTradeStatus());
            } else {
                partyAskNew.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                newPayRecord.setStatus(EnumPayStatus.STATUS_FAILED.getType());
                logger.info("alipayClient.execute() failed");
                queryResult.setMsg("调用支付宝查询方法失败");
            }

            int askResult = partyAskService.updateByOrderNo(partyAskNew);
            payRecordDao.updateByOrderNo(newPayRecord);
            logger.info("thirdPartyAsk.updateByOrderNo() executeSize:{}", askResult);
        } catch (Exception e) {
            logger.error("toAlipayQuery error:", e);
        }

        logger.info("queryResult: code:{}, msg:{}", queryResult.getCode(), queryResult.getMsg());
        return queryResult;
    }

	@Override
	public String queryBatchNoByOrderNo(String orderNo) {
		return payRecordDao.queryBatchNoByOrderNo(orderNo);
	}
}

