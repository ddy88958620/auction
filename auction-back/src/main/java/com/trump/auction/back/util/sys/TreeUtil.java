package com.trump.auction.back.util.sys;

import java.util.ArrayList;
import java.util.List;

import com.trump.auction.back.sys.model.ZTree;

/**
 * 
 * 类描述：树形结构工具类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-29 下午03:00:36 <br>
 * 
 * @version
 * 
 */
public class TreeUtil {
	private static TreeUtil instance;

	public static TreeUtil getInstance() {
		if (instance == null) {
			synchronized (TreeUtil.class) {
				if (instance == null)
					instance = new TreeUtil();
			}
		}
		return instance;
	}

	private TreeUtil() {
	}

	/**
	 * 将传入的集合转换为树形结构字符串
	 * 
	 * @param url
	 * @param pId
	 * @param id
	 * @param tg
	 * @return
	 */
	public List<ZTree> getZTree(int pId, List<ZTree> list) {
		List<ZTree> list2 = new ArrayList<>();
		for (ZTree zTree : list) {
			if (zTree.getPid() == pId) {
				list2.add(new ZTree(zTree.getId(), zTree.getName(), zTree.getPid(), getZTree(zTree.getId(), list)));
			}
		}
		return list2;
	}

	public List<ZTree> createCheckedTree(List<ZTree> list1, List<ZTree> list2, int pId) {
		List<ZTree> list = new ArrayList<>();
		for (ZTree zTree : list1) {
			boolean checked = false;
			for (ZTree zTree2 : list2) {
				if (zTree.getId() == zTree2.getId()) {
					checked = true;
				}
			}
			list.add(new ZTree(zTree.getId(), zTree.getName(), pId, checked));
		}
		return list;
	}
}
