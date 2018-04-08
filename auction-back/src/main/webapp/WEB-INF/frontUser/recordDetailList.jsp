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
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar" style="margin: 10px;">
	<%--用户Id:
	<div class="layui-inline">
		<input class="layui-input" id="userId" autocomplete="off">
	</div>
	地址Id:
	<div class="layui-inline">
		<input class="layui-input" id="addressId" autocomplete="off">
	</div>--%>
	<input type="hidden" id="userId" value="${userId}"/>
	<input type="hidden" id="accountType" value="${accountType}"/>
	<button style="display: none" class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
		<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
	</button>
</div>

<table class="layui-hide" id="data">
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    layui.use(['table','form'], function() {
        findBtns(null, "${parentId}");
        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            where:{
                userId : "${userId}",
                accountType : "${accountType}"
            },
            url : '${path}/user/recordDetail',
            height : "full-58",
            method : 'post',
            page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout : [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],//自定义分页布局//,curr: 5 //设定初始在第 5 页
                groups : 5,//只显示 1 个连续页码
                limits : [ 10, 20, 50, 100, 200 ],
                first : "首页",
                theme : "#FF5722;",
                limit : 10,
                last : "尾页"

            },
            id : 'search',
            cols : [ [ {
                type : 'checkbox',
                fixed : 'left'
            }, {
                type : 'numbers',
                align : 'center',
                title : '序号',
                fixed : "left"
            }, {
                field : 'createTime',
                align : 'center',
                title : '时间',
                templet : '<div>{{toDate(d.createTime)}}</div>'
            }, {
                field : 'transactionTag',
                title : '类型',
                align : 'center'
            }, {
                field : 'productName',
                title : '对象',
                align : 'center'
            },{
                field : 'viewTransactionCoin',
                title : '结果',
                align : 'center'
            },{
                field : 'coin',
                title : '余额',
                align : 'center',
                templet : '<div>{{toMoney(d.coin)}}</div>'
            }] ]

        });
        var $ = layui.$, active = {
            reload : function() {
                //执行重载
                table.reload('search', {
                    page : {
                        //重新从第 1 页开始
                        page : 1
                    },
                    where : {
                        userId : $('#userId').val(),
                        accountType : $('#accountType').val()
                    }
                });
            }
        };
        $('#btnSearch').on('click', function() {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $("#toolBar").keydown(function(event) {
            if (event.keyCode == 13) {
                refreshPage();
            }
        });
        refreshPage = function() {
            $('#btnSearch').trigger("click");
        };
        refreshPage();
    });
</script>
<script>
	function toMoney(money) {
		return money/100;
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>

</body>
</html>