package com.trump.auction.back.sys.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.back.sys.dao.write.SysLogDao;
import com.trump.auction.back.sys.model.SysLog;

@Service
public class SysLogServiceImpl implements SysLogService {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	SysLogDao sysLogDao;

	@Override
	public int insertLog(SysLog sysLog) {
		return sysLogDao.insertLog(sysLog);
	}

}
