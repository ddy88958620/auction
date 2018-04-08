package com.trump.auction.pals.api.model;



import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA
 * YhInfo : zhangsh
 * Date : 2016/12/26 0026
 * Time : 18:00
 */
@XmlRootElement(name = "ORDER")

@XmlAccessorType(XmlAccessType.FIELD)
public class MobilePayRecvModel implements Serializable {
    private String MCHNTCD;
    private String TYPE;
    private String VERSION;
    private String RESPONSECODE;
    private String RESPONSEMSG;
    private String MCHNTORDERID;
    private String ORDERID;
    private String BANKCARD;
    private String AMT;
    private String SIGN;

    public String getMCHNTCD() {
        return MCHNTCD;
    }

    public void setMCHNTCD(String MCHNTCD) {
        this.MCHNTCD = MCHNTCD;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getRESPONSECODE() {
        return RESPONSECODE;
    }

    public void setRESPONSECODE(String RESPONSECODE) {
        this.RESPONSECODE = RESPONSECODE;
    }

    public String getRESPONSEMSG() {
        return RESPONSEMSG;
    }

    public void setRESPONSEMSG(String RESPONSEMSG) {
        this.RESPONSEMSG = RESPONSEMSG;
    }

    public String getMCHNTORDERID() {
        return MCHNTORDERID;
    }

    public void setMCHNTORDERID(String MCHNTORDERID) {
        this.MCHNTORDERID = MCHNTORDERID;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getBANKCARD() {
        return BANKCARD;
    }

    public void setBANKCARD(String BANKCARD) {
        this.BANKCARD = BANKCARD;
    }

    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    public String getSIGN() {
        return SIGN;
    }

    public void setSIGN(String SIGN) {
        this.SIGN = SIGN;
    }
}
