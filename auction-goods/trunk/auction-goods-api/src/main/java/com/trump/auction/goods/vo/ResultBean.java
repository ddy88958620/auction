package com.trump.auction.goods.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回实体封装
 * @author
 * @date 2017/12/21
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -6852995638588224109L;

    public static final String SUCCESS = "000";

    public static final String FAIL = "999";

    private String msg = "success";

    private String code = SUCCESS;

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL ;
    }

}
