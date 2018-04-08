package com.trump.auction.pals.api.model.wechat;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class WeChatPayBackRequest implements Serializable {
    private Map<String,String> params;
    private String dataJson;
}
