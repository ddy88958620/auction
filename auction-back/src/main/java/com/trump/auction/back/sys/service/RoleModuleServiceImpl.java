package com.trump.auction.back.sys.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.back.sys.dao.read.RoleModuleReadDao;
import com.trump.auction.back.sys.dao.write.RoleModuleDao;
import com.trump.auction.back.sys.model.RoleModule;

@Service
public class RoleModuleServiceImpl implements RoleModuleService {
	@Autowired
	private RoleModuleDao roleModuleDao;
	@Autowired
	private RoleModuleReadDao roleModuleReadDao;

	@Override
	public List<RoleModule> findAll(HashMap<String, Object> params) {
		return roleModuleReadDao.findAll(params);
	}

}
