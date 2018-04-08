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
    <div id="roleTree" class="layui-col-xs3 ztree"></div>
    <div class="layui-col-xs9" style="float: right;">
        <div id="toolBar" style="margin-top: 5px;margin-left: 15px;"></div>
        <table class="layui-table" id="data"
               lay-data="{url : '${path}/role/getRoleList',even:true,id : 'search',height : 'full-58',method : 'post',where:{roleId:0},page : {layout : [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],groups : 5,limits : [ 10, 20, 50, 100, 200 ],first : '首页',theme : '#FF5722;',limit : 10,last : '尾页'}}">
            <thead>
            <tr>
                <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
                <th lay-data="{type : 'numbers',align : 'center',title : '序号',fixed : 'left',width: 60		}"></th>
                <th lay-data="{field : 'id',title : 'ID',align : 'center',sort : true,fixed : 'left',width: 60}"></th>
                <th lay-data="{field : 'roleName',align : 'center',title : '角色名称',width : '10%'}"></th>
                <th lay-data="{field : 'roleSummary',title : '角色描述',align : 'center',width : '30%'}"></th>
                <th lay-data="{field : 'roleAddtime',title : '添加时间',align : 'center',templet : '<div>{{toDate(d.roleAddtime)}}</div>',width : 180}"></th>
                <th lay-data="{field : 'roleAddip',align : 'center',title : '添加IP',width : '10%'}"></th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</div>
<script type="text/javascript">
    var myId = "${parentId}";
    var path = "${path}";
    var nowPageId = "${nowPageId}";
    var parentName = "${parentName}";
    var query;
    var table;
    var refresh;
    var showRoleModuleTree;
    var showAddIframe2;
    var showEditIframe2;
    var saveRoleTreeLayer;
    var setting = {
        callback : {
            onDblClick : function(event, treeId, treeNode) {
                nowPageId = treeNode.id;
                parentName = treeNode.name;
                query(nowPageId);
            },
            onExpand : function(event, treeId, treeNode) {
                nowPageId = treeNode.id;
                parentName = treeNode.name;
                query(nowPageId);
            },
            onClick : function(event, treeId, treeNode) {
                nowPageId = treeNode.id;
                parentName = treeNode.name;
                query(nowPageId);
            }
        }
    };
    layui.use([ 'layer', 'table' ], function() {
        table = layui.table;
        query = function (roleId) {
            table.reload('search', {
                page : {
                    //重新从第 1 页开始
                    page : 1
                },
                where : {
                    roleId : roleId
                }
            });
        }
        refresh = function() {
            $.ajax({
                type : "post",
                url : "${path}/role/getRoleList",
                data : {},
                dataType : "json",
                error : function() {
                    layer.msg('服务器开小差了,请稍后重试');
                },
                success : function(result) {
                    if (result.code == "0") {
                        $.fn.zTree.init($("#roleTree"), setting, result.data);
                    } else {
                        layer.msg(result.msg);
                    }
                }
            });
        };
        findBtns(null, "${parentId}");
        refresh();
    });
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/roleModultUtil.js" charset="utf-8"></script>
<script src="<%=common%>/system/role/saveRoleModule.js" charset="utf-8"></script>
</body>
</html>