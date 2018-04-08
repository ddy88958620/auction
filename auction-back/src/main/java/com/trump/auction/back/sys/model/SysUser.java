package com.trump.auction.back.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import lombok.Data;

@Data
public class SysUser implements Serializable {
	private Integer id;
	private String userAccount;
	private String userPassword;
	private String userName;
	private String userSex;
	private String userAddress;
	private String userTelephone;
	private String userMobile;
	private String userEmail;
	private String userQq;
	private Date createDate;
	private Date updateDate;
	private String addIp;
	private String remark;
	private Integer status;
	private Integer defaultPwd;
	private Integer parentId;

	public static final String ADMINISTRATOR_IDS = "10000,10027";
	/** 超级管理员的子用户 */
	public static final Integer PARENT_ID_ADMIN = 0;

	public static boolean isAdmin(String userId) {
		String[] admins = ADMINISTRATOR_IDS.split(",");
		for (String admin : admins) {
			if (userId.equals(admin)) {
				return true;
			}
		}
		return false;
	}

	/** 用户状态 */
	public static final HashMap<Integer, String> ALL_STATUS = new HashMap<Integer, String>();
	/** 启用 */
	public static final Integer STATUS_USE = 1;
	/** 删除 */
	public static final Integer STATUS_DELETE = 2;
	static {
		ALL_STATUS.put(STATUS_USE, "启用");
		ALL_STATUS.put(STATUS_DELETE, "删除");
	}

}
