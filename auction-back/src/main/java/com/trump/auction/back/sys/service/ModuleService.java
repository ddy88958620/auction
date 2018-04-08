package com.trump.auction.back.sys.service;

import java.util.HashMap;
import java.util.List;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.sys.model.Module;
import com.trump.auction.back.sys.model.ZTree;

/**
 * 
 * 类描述：菜单dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 * 
 * @version
 * 
 */
public interface ModuleService {
	public List<ZTree> findUserAllTreeByParentId(String moduleParentId, String userId);

	public List<ZTree> findAllTreeByParentId(String parentId);

	/**
	 * 根据条件查询菜单
	 * 
	 * @param params
	 *            参数名 ：userId，含义：用户id,等于administratorID时则返回全部可见菜单 <br>
	 *            参数名 ：parentId，含义：父节点ID <br>
	 * @return 菜单list
	 */
	public List<Module> findAllModule(HashMap<String, Object> params);

	/**
	 * 根据条件查询菜单
	 * 
	 * @param params
	 *            参数名 ：userId，含义：用户id，等于administratorID时则返回全部可见菜单 <br>
	 * @return 菜单list 树形结构
	 */
	List<ZTree> findModuleTree(HashMap<String, Object> params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	Paging<Module> findPage(HashMap<String, Object> params);

	/**
	 * 根据主键获得菜单
	 * 
	 * @param id
	 * @return
	 */
	Module findById(Integer id);

	/**
	 * 根据主键更新对象
	 * 
	 * @param backModule
	 */
	void updateById(Module backModule);

	/**
	 * 插入对象
	 * 
	 * @param backModule
	 */
	void insert(Module backModule);

	/**
	 * 根据主键删除对象
	 * 
	 * @param ids
	 */
	int deleteById(String[] ids);

	/**
	 * 根据用户ID和url查询
	 * 
	 * @param params
	 *            参数名：id，用户主键ID<br>
	 *            参数名：moduleUrl,权限url
	 */
	public int findModuleByUrl(HashMap<String, Object> params);

	List<ZTree> findRoleTree(HashMap<String, Object> params);
}
