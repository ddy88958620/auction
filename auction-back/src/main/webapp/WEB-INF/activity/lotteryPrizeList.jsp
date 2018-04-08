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
	<c:set var="path" value="<%=path%>"/>
	<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
	<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar" class="layui-form-item" style="margin: 10px;">
    <%--<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 查询
    </button>--%>
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
            url : '${path}/lotteryPrize/findList',
            height : "full-70",
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
            },{
                field : 'id',
                title : '奖品Id',
                align : 'center',
                sort : true,
                fixed : "left"
            }, {
                field : 'prizeNo',
                title : '奖品编号',
                align : 'center',
                sort : true,
                fixed : "left"
            }, {
                field : 'prizeName',
                title : '奖品名称',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'prizeCount',
                title : '已抽中',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'store',
                title : '库存',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'isOpen',
                title : '是否开启',
                align : 'center',
                fixed : "left",
                templet : '<div>{{toStatus(d.isOpen)}}</div>',
                sort : true
            },{
                field : 'isPlan1',
                align : 'center',
                title : '方案1',
                fixed : "left",
                templet : '<div>{{toPlan(d.isPlan1)}}</div>',
                sort : true
            }, {
                field : 'isPlan2',
                title : '方案2',
                align : 'center',
                fixed : "left",
                templet : '<div>{{toPlan(d.isPlan2)}}</div>',
                sort : true
            }, {
                field : 'prizeRate',
                title : '中奖概率',
                align : 'center',
                fixed : "left",
                templet:'<div>{{d.prizeRate}}%</div>',
                sort : true
            }, {
                field : 'orderNumber',
                title : '排序',
                align : 'center',
                fixed : "left",
                sort : true
            }, {
                field : 'remark',
                title : '备注',
                align : 'center',
                fixed : "left",
                sort : true
            }] ]

        });
        var $ = layui.$, active = {
            reload : function() {
                //执行重载
                table.reload('search', {
                    page : {
                        //重新从第 1 页开始
                        page : 1
                    }
                });
            }
        };
    });
    function toStatus(status){
        if (status == '1') {
            return "关闭";
        }
        if (status == '2') {
            return "开启";
        }
    }
    function toPlan(plan){
        if (plan == 'N') {
            return "否";
        }
        if (plan == 'Y') {
            return "是";
        }
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>

</body>
</html>