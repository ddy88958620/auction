package com.trump.auction.goods.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 罗显 on 2017/12/21.
 * 商品库存使用表
 */
@Data
@ToString
public class ProductInventoryLogRecordModel implements Serializable {
    private static final long serialVersionUID = -8450599589288041194L;
    private Integer id;
    private Integer product_id;
    private Integer upd_product_num;
    private Integer old_product_num;
    private Integer color_id;
    private Integer sku_id;
    private Integer type;
    private Date    create_time;
    private Date    update_time;
    private Integer user_id;
    private String  user_ip;
}






