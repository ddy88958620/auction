<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>${website.site_name }</title>
<c:set var="path" value="<%=path%>"></c:set>
<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="<%=common%>/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=common%>/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=common%>/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=common%>/ztree/js/jquery.ztree.excheck.js"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>

<SCRIPT type="text/javascript">
	var path = "${path}";
	var layer;
	var treeObj;
	var interval = setInterval(function() {
		if (curAsyncCount > 0) {
			layer.msg(demoMsg.async);
		}
	}, 500);

	layui.use([ 'layer' ], function() {

	});
	var demoMsg = {
		async : "请稍后,正在加载...",
		expandAllOver : "全部展开完毕",
		asyncAllOver : "后台异步加载完毕",
		asyncAll : "已经异步加载完毕，不再重新加载",
		expandAll : "加载完毕"
	}
	var setting = {
		async : {
			enable : true,
			url : "${path}${url}",
			type : 'post',
			autoParam : [ "id=parentId" ],
			dataFilter : filter,
			otherParam : {
				"roleId" : "${roleId}"
			}
		},
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		callback : {
			beforeAsync : beforeAsync,
			onAsyncSuccess : onAsyncSuccess,
			onAsyncError : onAsyncError
		}
	};
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].isParent = true;
		}
		return childNodes;
	}

	function beforeAsync() {
		curAsyncCount++;
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		curAsyncCount--;
		if (curStatus == "expand") {
			if (treeNode.children.length == 0) {
				treeNode.isParent = false;
				treeObj.updateNode(treeNode);
			}
			expandNodes(treeNode.children);
		} else if (curStatus == "async") {
			asyncNodes(treeNode.children);
		}

		if (curAsyncCount <= 0) {
			if (curStatus != "init" && curStatus != "") {
				//layer.msg((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
				asyncForAll = true;
			}
			curStatus = "";
		}
		expandAll();
		asyncAll();
	}

	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		curAsyncCount--;

		if (curAsyncCount <= 0) {
			curStatus = "";
			if (treeNode != null)
				asyncForAll = true;
		}
	}

	var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
	function expandAll() {
		if (!check()) {
			return;
		}
		if (asyncForAll) {
			layer.msg(demoMsg.expandAll);
			treeObj.expandAll(true);
		} else {
			expandNodes(treeObj.getNodes());
			if (!goAsync) {
				layer.msg(demoMsg.expandAll);
				curStatus = "";
			}
		}
	}
	function expandNodes(nodes) {
		if (!nodes)
			return;
		curStatus = "expand";
		for (var i = 0, l = nodes.length; i < l; i++) {
			treeObj.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			} else {
				goAsync = true;
			}
		}
	}

	function asyncAll() {
		if (!check()) {
			return;
		}
		if (asyncForAll) {
			//layer.msg(demoMsg.asyncAll);
		} else {
			asyncNodes(treeObj.getNodes());
			if (!goAsync) {
				//layer.msg(demoMsg.asyncAll);
				curStatus = "";
			}
		}
	}
	function asyncNodes(nodes) {
		if (!nodes)
			return;
		curStatus = "async";
		for (var i = 0, l = nodes.length; i < l; i++) {
			if (nodes[i].isParent && nodes[i].zAsync) {
				asyncNodes(nodes[i].children);
			} else {
				goAsync = true;
				treeObj.reAsyncChildNodes(nodes[i], "refresh", true);
			}
		}
	}

	function reset() {
		if (!check()) {
			return;
		}
		asyncForAll = false;
		goAsync = false;
		$.fn.zTree.init($("#treeDemo"), setting);
	}

	function check() {
		if (curAsyncCount > 0) {
			return false;
		}
		return true;
	}
	function saveRoleModule(url) {
		var nodes = treeObj.getCheckedNodes(true);
		var len = nodes.length;
		if (len <= 0) {
			parent.layer.msg('未选中要操作的行', function() {
			});
		} else {
			var ids = new Array();
			for (var i = 0; i < len; i++) {
				ids.push(nodes[i].id);
			}
			$.ajax({
				type : "post",
				url : path + url,
				data : {
					ids : ids,
					roleId : "${roleId}"
				},
				dataType : "json",
				beforeSend : function() {
					layer.msg('正在授权' + len + "个菜单", {
						icon : 16
					});
				},
				success : function(result) {
					layer.msg(result.msg, {
						time : 1000
					}, function() {
						if (result.code == '0') {
							parent.layer.close(parent.saveRoleTreeLayer);
						}
					});
				}
			});
		}
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting);
		treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	});
</SCRIPT>
</head>
<body>
	<ul id="treeDemo" class="ztree"></ul>
</body>
</html>