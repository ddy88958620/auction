package com.trump.auction.goods.enums;

/**
 * 返回结果枚举
 * @author
 *
 */
public enum ResultCode {
	SUCCESS("000","成功"),
	FAIL("999","失败"),
	PARAM_MISSING("1001","参数缺失"),
	OBJECT_ISNOT_EXIST("2001","对象不存在"),
	OBJECT_IS_EXIST("2002","对象已存在"),
	PRODUCT_GROUNDING("3001","产品已上架"),
	PRODUCT_INVENTORY_ISNOT_EXIST("3101","不存在库存信息"),
	PRODUCT_INVENTORY_LT_ZERO("3102","库存小于0"),
	PRODUCT_INVENTORY_NUM_ERROR("3103","库存对象数量不符"),
	PRODUCT_STAGES_DETAIL_ISNOT_EXIST("4001","该商品分期信息不存在")
	;

	private ResultCode(String code){
		this.code=code;
	}
	private ResultCode(String code, String msg){
		this.code=code;
		this.msg=msg;
	}

	private String code;
	
	private String msg;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
