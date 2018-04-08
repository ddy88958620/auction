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
    <div class="layui-inline">
        <div class="layui-form-label">奖品编号：</div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="prizeNo" name="prizeNo"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-form-label">手机号：</div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="userPhone" name="userPhone"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-form-label">订单号：</div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="orderNo" name="orderNo"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label">中奖时间：</label>
        <div class="layui-input-inline">
            <input type="text" name="beginDate" id="beginDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid">-</div>
        <div class="layui-input-inline">
            <input type="text" name="endDate" id="endDate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 查询
    </button>
</div>
<table class="layui-hide" id="data">
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    layui.use(['table','form','laydate'], function() {
        findBtns(null, "${parentId}");
        var laydate = layui.laydate;
        laydate.render({
            elem: '#beginDate'
        });
        laydate.render({
            elem: '#endDate'
        });

        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/lotteryRecord/findList',
            height : "full-90",
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
                type : 'numbers',
                align : 'center',
                title : '序号',
                fixed : "left"
            },{
                field : 'userId',
                title : '用户Id',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'userName',
                title : '用户名',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'userPhone',
                title : '手机号',
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
                field : 'orderNo',
                align : 'center',
                title : '订单号',
                fixed : "left",
                sort : true
            }, {
                field : 'addTime',
                title : '中奖时间',
                align : 'center',
                fixed : "left",
                templet : '<div>{{toDate(d.addTime)}}</div>',
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
                    },
                    where : {
                        prizeNo : $('#prizeNo').val(),
                        userPhone : $('#userPhone').val(),
                        orderNo : $('#orderNo').val(),
                        beginDate : $('#beginDate').val(),
                        endDate : $('#endDate').val()
                    }
                });
            }
        };
        $('#btnSearch').on('click', function() {
            var prizeNo = $('#prizeNo').val();
            var userPhone = $('#userPhone').val();
            var orderNo = $('#orderNo').val();
            var beginDate = $('#beginDate').val();
            var endDate = $('#endDate').val();

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
        }
    });
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>

</body>
</html>