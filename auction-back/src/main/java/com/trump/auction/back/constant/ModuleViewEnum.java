package com.trump.auction.back.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 菜单显示与隐藏
 * 
 * @author God 2017年12月7日 下午1:09:20
 */
public enum ModuleViewEnum {
	SHOW(1, "显示"), HIDE(2, "隐藏");
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

	ModuleViewEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(Integer type) {
		String name = null;
		for (ModuleViewEnum tmp : values()) {
			if (type.intValue() == tmp.getType().intValue()) {
				name = tmp.getName();
				break;
			}
		}
		return name;
	}

	public static Map<Integer, String> getAllType() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (ModuleViewEnum tmp : values()) {
			map.put(tmp.getType(), tmp.getName());
		}
		return map;
	}
}
