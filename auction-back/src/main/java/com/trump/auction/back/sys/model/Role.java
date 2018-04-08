package com.trump.auction.back.sys.model;

import lombok.Data;

import java.util.Date;
@Data
public class Role {
	private Integer id;

	private String roleName;

	private String roleSummary;

	private Integer roleSuper;

	private Date roleAddtime;

	private String roleAddip;

}