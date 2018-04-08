<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
%>
<head>
<c:set var="path" value="<%=path%>"></c:set>
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
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
	<div class="layui-fluid">
		<div class="layui-row">
			<div id="userTree" class="layui-col-xs2 ztree"></div>
			<div class="layui-col-xs10" style="float: right;">
				<div id="toolBar" style="height: 38px;"></div>
				<table class="layui-table" id="data"
					lay-data="{url : '${path}/user/findUserByParentId',even:true,id : 'search',where : {},method : 'post',height : 'full-58',page: {layout: [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],groups: 5,limits: [ 10, 20, 50, 100, 200 ],first: '首页',theme: '#FF5722;',limit: 10,last: '尾页'}}">
					<thead>
						<tr>
							<th lay-data="{type:'checkbox', fixed: 'left'}"></th>
							<th lay-data="{	type : 'numbers',	align : 'center',	title : '序号',	fixed : 'left'}"></th>
							<th lay-data="{	field : 'id',	title : 'ID',	align : 'center',	sort : true,	fixed : 'left'}"></th>
							<th lay-data="{	field : 'userAccount',	align : 'center',	title : '用户名'}"></th>
							<th lay-data="{	field : 'userName',	title : '姓名',	align : 'center'}"></th>
							<th lay-data="{	field : 'userSex',	title : '性别',	align : 'center',	width : 80}"></th>
							<th lay-data="{	field : 'userAddress',	title : '地址',	align : 'center'}"></th>
							<th lay-data="{	field : 'userTelephone',	title : '电话',	align : 'center'}"></th>
							<th lay-data="{	field : 'userMobile',	title : '手机',	align : 'center'}"></th>
							<th lay-data="{	field : 'userEmail',	title : '邮箱',	align : 'center'}"></th>
							<th lay-data="{	field : 'userQq',	title : 'QQ',	align : 'center'}"></th>
							<th lay-data="{	field : 'createDate',	title : '添加时间',	align : 'center',	width : 120,	templet : '<div>{{toDate(d.createDate,\'yyyy-MM-dd\')}}</div>'}"></th>
							<th lay-data="{	field : 'createDate',	title : '更新时间',	align : 'center',	width : 180,	templet : '<div>{{toDate(d.updateDate)}}</div>'}"></th>
							<th lay-data="{	field : 'addIp',	align : 'center',	title : '添加IP'}"></th>
							<th lay-data="{	field : 'remark',	align : 'center',	title : '添加备注'}"></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//查找当前菜单的子菜单
		var myId = "${parentId}";
		var path = "${path}";
		var treeObj;
		var table;
		var query;
		var form;
		var parentUserId = "${parentUserId}";
		var parentUserName = "${parentUserName}";
		var showAddIframe2 = function(obj) {
			if (parentUserId != null && parentUserId != '' && parentUserId != undefined) {
				$(obj).attr("urlHelp", '?parentUserId=' + parentUserId + "&parentUserName=" + parentUserName);
				showAddIframe(obj);
			} else {
				layer.msg('请选择父节点', function() {
				});
			}
		};
		var setting = {
			async : {
				enable : true,
				url : "${path}/user/findUserByParentId",
				autoParam : [ "id=parentId" ],
				otherParam : {
					"type" : "tree"
				},
				dataFilter : filter
			},
			callback : {
				onAsyncSuccess : onAsyncSuccess,
				onDblClick : function(event, treeId, treeNode) {
					parentUserId = treeNode.id;
					parentUserName = treeNode.name;
					if (treeNode.isParent) {
						query(treeNode.id);
					}
				},
				onExpand : function(event, treeId, treeNode) {
					parentUserId = treeNode.id;
					parentUserName = treeNode.name;
					query(treeNode.id);
				},
				onClick : function(event, treeId, treeNode) {
					parentUserId = treeNode.id;
					parentUserName = treeNode.name;
				}
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
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			if (treeNode != null && treeNode.children.length == 0) {
				treeNode.isParent = false;
				treeObj.updateNode(treeNode);
			}
		}
		$(document).ready(function() {
			$.fn.zTree.init($("#userTree"), setting);
			treeObj = $.fn.zTree.getZTreeObj("userTree");
		});
		layui.use([ 'layer', 'form', 'table' ], function() {
			table = layui.table;
			form = layui.form;
			query = function(parentId) {
				table.reload('search', {
					page : {
						//重新从第 1 页开始
						page : 1
					},
					where : {
						parentId : parentId
					}
				});
			};
			findBtns(null, "${parentId}");
		});
		refreshPage = function() {
			window.location.reload();
		}
	</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
	<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
</body>
</html>