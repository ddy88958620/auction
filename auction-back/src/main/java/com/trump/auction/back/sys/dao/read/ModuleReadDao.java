package com.trump.auction.back.sys.dao.read;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Module;
import com.trump.auction.back.sys.model.ZTree;

@Repository
public interface ModuleReadDao {
	public List<Module> findAdminAll(HashMap<String, Object> params);

	public List<Module> findUserAll(HashMap<String, Object> params);

	public List<ZTree> findAdminTree(HashMap<String, Object> params);

	public List<ZTree> findUserTree(HashMap<String, Object> params);

	public List<ZTree> findAllTreeByParentId(@Param("moduleParentId") String moduleParentId);

	public List<ZTree> findUserAllTreeByParentId(@Param("parentId") String moduleParentId,@Param("userId") String userId);

	public Module findById(Integer id);

	public int findModuleByUrl(HashMap<String, Object> params);
}
