package com.trump.auction.back.sys.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.SysUser;

@Repository
public interface SysUserDao {

	public void insert(SysUser backUser);

	public int deleteById(@Param("list") List<String> ids, @Param("adminIds") String adminIds);

	public void updateById(SysUser backUser);

	public void updatePwdById(SysUser backUser);
}
