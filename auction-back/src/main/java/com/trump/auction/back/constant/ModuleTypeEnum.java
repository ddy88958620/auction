package com.trump.auction.back.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 菜单类型
 * 
 * @author God 2017年12月7日 下午1:09:20
 */
public enum ModuleTypeEnum {
	ADD(1, "添加窗口"), EDIT(2, "编辑窗口"), Ajax(3, "Ajax"), TAB(4, "选项卡");
	/*** 值 */
	private final Integer type;
	/*** 名称 */
	private final String name;

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	ModuleTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(Integer type) {
		String name = null;
		for (ModuleTypeEnum tmp : values()) {
			if (type.intValue() == tmp.getType().intValue()) {
				name = tmp.getName();
				break;
			}
		}
		return name;
	}

	public static Map<Integer, String> getAllType() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (ModuleTypeEnum tmp : values()) {
			map.put(tmp.getType(), tmp.getName());
		}
		return map;
	}
}
