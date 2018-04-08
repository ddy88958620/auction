package com.trump.auction.back.sys.dao.write;

import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Module;

@Repository
public interface ModuleDao {

	public int deleteById(String[] ids);

	public void updateById(Module backModule);

	public void insert(Module backModule);

}
