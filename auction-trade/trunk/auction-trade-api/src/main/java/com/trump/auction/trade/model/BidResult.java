package com.trump.auction.trade.model;

import lombok.Data;

import java.io.Serializable;

/**
 * code  0001 成功 0002 失败
 */
@Data
public class BidResult implements Serializable {

    private  String  code;
    private  String  msg;
    private  String  errMsg;
    public static String SUCCESS="0001";

    public   boolean isCode(){
        if((SUCCESS).equals(code)){
            return true;
        }else{
            return  false;
        }
    }
    public static BidResult successStatus(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0001");
        bidResult.setMsg("修改状态成功");
        return bidResult;
    }

    public static BidResult errorStatus(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0002");
        bidResult.setMsg("修改状态失败");
        return bidResult;
    }

    public BidResult successBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0001");
        bidResult.setMsg("出价成功");
        return bidResult;
    }

    public BidResult errorParamBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0002");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("参数异常");
        return bidResult;
    }

    public BidResult errorPaymentBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0003");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("扣款失败");
        return bidResult;
    }

    public BidResult errorServiceBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0004");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("调用扣款服务失败");
        return bidResult;
    }


    public BidResult errorSystemBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0005");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("系统异常");
        return bidResult;
    }

    public BidResult errorRepeatBid(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0006");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("重复出价");
        return bidResult;
    }

    public BidResult errorSBid(String auctionId){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0007");
        bidResult.setMsg("");
        bidResult.setErrMsg(auctionId+"已有用户中奖");
        return bidResult;
    }
    public BidResult errorTxnDetail(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0008");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("落地数据失败");
        return bidResult;
    }
    public BidResult errorDetail(){
        BidResult bidResult=new BidResult();
        bidResult.setCode("0009");
        bidResult.setMsg("出价失败");
        bidResult.setErrMsg("落地数据失败");
        return bidResult;
    }

}
