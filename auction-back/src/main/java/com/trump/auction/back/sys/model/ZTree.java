package com.trump.auction.back.sys.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZTree {
	private int id;
	private String name;
	private int pid;
	private boolean checked;
	private List<ZTree> children;

	public ZTree(int id, String name, int pid, boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.checked = checked;
	}

	public ZTree(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ZTree(int id, String name, int pid, List<ZTree> children) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.children = children;
	}

}
