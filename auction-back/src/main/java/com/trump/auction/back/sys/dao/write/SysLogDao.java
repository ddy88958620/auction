package com.trump.auction.back.sys.dao.write;

import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.SysLog;

@Repository
public interface SysLogDao {
	int insertLog(SysLog sysLog);
}
