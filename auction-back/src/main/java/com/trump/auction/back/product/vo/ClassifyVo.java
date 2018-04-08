package com.trump.auction.back.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Administrator
 * @date 2018/1/26
 */
@Data
public class ClassifyVo implements Serializable {
    private static final long serialVersionUID = 4135302855014554539L;

    private String name;
    private String value;
}
