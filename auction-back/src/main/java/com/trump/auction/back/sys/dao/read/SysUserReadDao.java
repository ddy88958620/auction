package com.trump.auction.back.sys.dao.read;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.sys.model.SysUser;

@Repository
public interface SysUserReadDao {
	public Integer findLogin(@Param("userMobile") String userMobile, @Param("pwd") String pwd);

	public SysUser findById(@Param("id") Integer id, @Param("adminIds") String adminIds);

	public List<ZTree> findByParentId(@Param("parentId") String parentId, @Param("adminIds") String adminIds);

	public List<SysUser> findAll(HashMap<String, Object> params);

	/**
	 * 根据用户主键ID查询该用户及该用户的子用户（包含当前用户且向下无限递归）
	 * 
	 * @param parentId
	 * @return 如果存在id=parentId的记录则至少会返回该记录(无子记录)
	 */
	public List<String> findIdsByParentIds(@Param("parentId") String parentId);
}
