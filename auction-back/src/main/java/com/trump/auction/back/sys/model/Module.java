package com.trump.auction.back.sys.model;

import java.util.List;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class Module {
	private Integer id;

	private String moduleName;

	private String moduleUrl;
	private String moduleStyle;

	private String moduleDescribe;
	private Integer moduleSequence;
	private Integer moduleView;

	private Integer moduleParentId;

	private List<Module> moduleList;
	private String icon;
	private String iconShow;

	public String getIconShow() {
		String show = getIcon();
		if (StringUtils.isNotBlank(show)) {
			show = "&" + show;
		}
		return show;
	}
}