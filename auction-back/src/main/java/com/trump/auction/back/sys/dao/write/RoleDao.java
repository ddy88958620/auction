package com.trump.auction.back.sys.dao.write;

import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Role;

@Repository
public interface RoleDao {

	public int deleteById(String[] ids);

	public void updateById(Role backRole);

	public void insert(Role backRole);

}
