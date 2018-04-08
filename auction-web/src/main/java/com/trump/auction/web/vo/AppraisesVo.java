package com.trump.auction.web.vo;

import java.util.Date;
import java.util.List;

import org.springframework.web.util.HtmlUtils;

import com.cf.common.utils.DateUtil;
import com.trump.auction.web.util.Base64Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AppraisesVo {
	
	@Setter private Integer id;
	
	@Setter Integer appraisesId;
	
	public Integer getAppraisesId() {
		return id;
	}

	@Setter private String buyNickName;
	
	@Getter @Setter private String headImg;

    @Setter private Date createTime;
    @Setter private String appraisesPic;

    @Setter private String content;

    @Setter private String updateTimeStr ;

    @Getter @Setter private List<String> imgs ;

    public String getUpdateTimeStr(){
      return DateUtil.getDateFormat(createTime, "yyyy-MM-dd HH:mm:ss");
    }


    public String getBuyNickName() {
        return Base64Utils.decodeStr(buyNickName);
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(Base64Utils.decodeStr(content));
    }
}
