package com.trump.auction.order.domain;

import java.util.Date;

public class OrderAppraises {
    private Integer id;

    private String orderId;

    private String buyId;
    
    private String buyNickName;
    
    private String buyPic;
    
    private String merchantId;

    private Integer productId;
    
    private String productName;

    private Date createTime;

    private Date updateTime;

    private String appraisesPic;

    private Integer isShow;

    private String content;
    
    private Integer bidTimes;
    
    private Integer type;
    
    private String status;
    
    private Integer auctionNo;

    /**
     * 评论级别
     */
    private String appraisesLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId == null ? null : buyId.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppraisesPic() {
        return appraisesPic;
    }

    public void setAppraisesPic(String appraisesPic) {
        this.appraisesPic = appraisesPic == null ? null : appraisesPic.trim();
    }

    public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getBuyNickName() {
		return buyNickName;
	}

	public void setBuyNickName(String buyNickName) {
		this.buyNickName = buyNickName;
	}

	public String getBuyPic() {
		return buyPic;
	}

	public void setBuyPic(String buyPic) {
		this.buyPic = buyPic;
	}

	public Integer getBidTimes() {
		return bidTimes;
	}

	public void setBidTimes(Integer bidTimes) {
		this.bidTimes = bidTimes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getAuctionNo() {
		return auctionNo;
	}

	public void setAuctionNo(Integer auctionNo) {
		this.auctionNo = auctionNo;
	}

    public String getAppraisesLevel() {
        return appraisesLevel;
    }
    public void setAppraisesLevel(String appraisesLevel) {
        this.appraisesLevel = appraisesLevel;
    }
}