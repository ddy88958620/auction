package com.trump.auction.back.sys.model;

public class RoleModule {

	private Integer id;

	private Integer moduleId;

	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "RoleModule [id=" + id + ", moduleId=" + moduleId + ", roleId=" + roleId + "]";
	}

}