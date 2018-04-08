package com.trump.auction.back.sys.service;

import java.util.HashMap;
import java.util.List;

import com.trump.auction.back.sys.model.RoleModule;

public interface RoleModuleService {
	public List<RoleModule> findAll(HashMap<String, Object> params);
}
