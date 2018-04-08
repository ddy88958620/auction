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
			<div id="moduleTree" class="layui-col-xs2 ztree"></div>
			<div class="layui-col-xs10" style="float: right;">
				<div id="toolBar" style="height: 38px;"></div>
				<table class="layui-table" id="data"
					lay-data="{url : '${path}/module/findModuleList',even:true,id : 'search',where : {moduleId : 0},method : 'post',height : 'full-58',page: {layout: [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],groups: 5,limits: [ 10, 20, 50, 100, 200 ],first: '首页',theme: '#FF5722;',limit: 10,last: '尾页'}}">
					<thead>
						<tr>
							<th lay-data="{type:'checkbox', fixed: 'left'}"></th>
							<th lay-data="{type : 'numbers',align : 'center',title : '序号',fixed : 'left',width : 80}"></th>
							<th lay-data="{field : 'id',title : 'ID',align : 'center',sort : true,fixed : 'left',width : 80}"></th>
							<th lay-data="{field : 'moduleName',align : 'center',title : '菜单名称',width : '10%'}"></th>
							<th lay-data="{field : 'moduleUrl',title : '菜单路径',align : 'center',width : '20%'}"></th>
							<th lay-data="{field : 'moduleStyle',title : '事件',align : 'center',width : '150'}"></th>
							<th lay-data="{field : 'iconShow',title : '图标',align : 'center',width : '120',templet:'#iconTpl'}"></th>
							<th lay-data="{field : 'moduleDescribe',title : '菜单描述',align : 'center',width : '20%'}"></th>
							<th lay-data="{field : 'moduleSequence',align : 'center',title : '排序',sort : true,width : 80}"></th>
							<th lay-data="{field : 'moduleView',title : '显示/隐藏',align : 'center',width : 120,templet:'#viewTpl'}"></th>

						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<script type="text/html" id="iconTpl">
		<i class="layui-icon">{{d.iconShow}}</i>
	</script>
	<script type="text/html" id="viewTpl">
  {{#  if(d.moduleView === 1){ }}
 		显示
  {{#  } else { }}
		隐藏
  {{#  } }}
</script>
	<script type="text/javascript">
		//查找当前菜单的子菜单
		var myId = "${parentId}";
		var path = "${path}";
		var treeObj;
		var table;
		var query;
		var nowPageId = 0;
		var parentName = "根目录";
		var showAddIframe2;
		var showEditIframe2;
		var setting = {
			async : {
				enable : true,
				url : "${path}/module/findModuleList",
				autoParam : [ "id=parentId" ],
				otherParam : {
					"type" : "tree"
				},
				dataFilter : filter
			},
			callback : {
				onAsyncSuccess : onAsyncSuccess,
				onDblClick : function(event, treeId, treeNode) {
					nowPageId = treeNode.id;
					parentName = treeNode.name;
					if (treeNode.isParent) {
						query(treeNode.id);
					}
				},
				onExpand : function(event, treeId, treeNode) {
					nowPageId = treeNode.id;
					parentName = treeNode.name;
					query(treeNode.id);
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
			$.fn.zTree.init($("#moduleTree"), setting);
			treeObj = $.fn.zTree.getZTreeObj("moduleTree");
		});
		layui.use([ 'layer', 'table' ], function() {
			table = layui.table;
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
	</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
	<script src="<%=common%>/system/roleModultUtil.js" charset="utf-8"></script>
</body>
</html>