package com.trump.auction.back.sys.model;

import java.util.Date;

public class SysLog {
	private Integer id;
	private Integer userId;
	private String logType;
	private String userLog;
	private Date addTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getUserLog() {
		return userLog;
	}

	public void setUserLog(String userLog) {
		this.userLog = userLog;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public SysLog() {
		super();
	}

	/**
	 * 构造日志对象
	 * 
	 * @param userId
	 *            当前登录的用户主键
	 * @param logType
	 *            日志类别，用于快速检索到操作，建议每个controller中的每一个方法 使用一个
	 * @param userLog
	 *            注意！注意！注意！注意！注意：这里要把本方法中相关的数据对象toString(参考User.java中的toString方法)
	 *            或者人为的描述出此次执行的方法都做了什么
	 */
	public SysLog(Integer userId, String logType, String userLog) {
		super();
		this.userId = userId;
		this.logType = logType;
		this.userLog = userLog;
	}

}
