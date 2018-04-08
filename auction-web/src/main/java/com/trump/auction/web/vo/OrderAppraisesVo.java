package com.trump.auction.web.vo;

import com.cf.common.utils.DateUtil;
import com.trump.auction.web.util.Base64Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.util.HtmlUtils;


public class OrderAppraisesVo {

    @Setter
    private Integer id;
    @Getter
    @Setter
    private String orderId;
    @Setter
    private Integer OrderAppraisesId;
    @Setter
    private String buyNickName;
    @Getter
    @Setter
    private String buyPic;

    @Setter
    private Date createTime;
    @Getter
    @Setter
    private String appraisesPic;

    @Setter
    private String content;
    @Getter
    @Setter
    private Integer bidTimes;

    @Setter private String updateTimeStr ;

    @Getter @Setter private String[] picArr ;
    @Setter
    @Getter
    private String productName;
    @Setter
    @Getter
    private String productPic;

  public String getUpdateTimeStr(){
      return DateUtil.getDateFormat(createTime, "yyyy-MM-dd HH:mm:ss");
  }

    public Integer getOrderAppraisesId() {
        return id;
    }

    @Override
    public String toString() {
        return "OrderAppraisesVo{" +
                "id=" + id +
                ", OrderAppraisesId=" + OrderAppraisesId +
                ", buyNickName='" + buyNickName + '\'' +
                ", buyPic='" + buyPic + '\'' +
                ", updateTime=" + createTime +
                ", appraisesPic='" + appraisesPic + '\'' +
                ", content='" + content + '\'' +
                ", bidTimes=" + bidTimes +
                ", updateTimeStr='" + updateTimeStr + '\'' +
                ", picArr=" + Arrays.toString(picArr) +
                '}';
    }

    public String getBuyNickName() {
        return Base64Utils.decodeStr(buyNickName);
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(Base64Utils.decodeStr(content));
    }
}
