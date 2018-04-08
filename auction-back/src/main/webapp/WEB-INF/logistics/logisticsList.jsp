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
<div id="toolBar">
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	商品名称:
	<div class="layui-inline">
		<input class="layui-input" id="productName" autocomplete="off" value="${params.productName}">
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	用户名:
	<div class="layui-inline">
		<input class="layui-input" id="userName" autocomplete="off" value="${params.userName}">
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	用户手机:
	<div class="layui-inline">
		<input class="layui-input" id="userPhone"lay-verify="phone" autocomplete="off" value="${params.userPhone}">
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	成交时间:
	<div class="layui-inline">
		<input class="layui-input" id="createTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.createTimeBegin}">
	</div>
	&nbsp;&nbsp;至&nbsp;&nbsp;
	<div class="layui-inline">
		<input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.createTimeEnd}">
	</div><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	订单编号:
	<div class="layui-inline">
		<input class="layui-input" id="orderId" autocomplete="off" value="${params.orderId}">
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	物流状态:
	<div class="layui-inline layui-form" style="width: 100px;">
		<select class="layui-input" id="logisticsStatus">
			<c:forEach var="item" items="${logisticsStatusList}">
				<option value="${item.key}" <c:if test="${item.key eq params.logisticsStatus}">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
	</div>
	订单类型:
	<div class="layui-inline layui-form" style="width: 100px;">
		<select class="layui-input" id="orderType">
			<c:forEach var="item" items="${orderTypeList}">
				<option value="${item.key}" <c:if test="${item.key eq params.orderType}">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
	</div>
	<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch" style="margin-left: 20px;">
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
        var laydate = layui.laydate;

        laydate.render({
            elem: '#createTimeBegin'
        });
        laydate.render({
            elem: '#createTimeEnd'
        });

        findBtns(null, "${parentId}");

        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/logistics/findListPage',
            height : "full-130",
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
                field : 'orderId',
                title : '订单号',
                align : 'center',
                sort : true,
                fixed : "left",
                width : 180
            }, {
                field : 'createTime',
                align : 'center',
                title : '成交时间',
                templet : '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                sort : true,
                width : 180
            }, {
                field : 'productName',
                title : '商品名称',
                align : 'center'
            }, {
                field : 'transName',
                title : '用户姓名',
                align : 'center'
            }, {
                field : 'transPhone',
                title : '用户手机',
                align : 'center'
            }, {
                field : 'logisticsStatus',
                title : '物流状态',
                align : 'center',
                templet : '<div>{{toStatus(d.logisticsStatus)}}</div>'
            }, {
                field : 'orderAmount',
                title : '成交价',
                align : 'center',
                sort : true
            }, {
                field : 'orderType',
                title : '订单类型',
                align : 'center',
                templet : '<div>{{toType(d.orderType)}}</div>'
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
                        productName : $('#productName').val(),
                        userName : $('#userName').val(),
                        userPhone : $('#userPhone').val(),
                        createTimeBegin : $('#createTimeBegin').val(),
                        createTimeEnd : $('#createTimeEnd').val(),
                        orderId : $('#orderId').val(),
                        logisticsStatus : $('#logisticsStatus').val(),
                        orderType : $('#orderType').val()
                    }
                });
            }
        };
        $('#btnSearch').on('click', function() {
            var productName = $('#productName').val();
            var userName = $('#userName').val();
            var userPhone = $('#userPhone').val();
            var createTimeBegin = $('#createTimeBegin').val();
            var createTimeEnd = $('#createTimeEnd').val();
            var orderId = $('#orderId').val();
            var logisticsStatus = $('#logisticsStatus').val();
            var orderType = $('#orderType').val();


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

    function toType(type){
        //获取select元素的引用
        var eduElement = document.getElementById("orderType");
        //1 获取所有的option标签
        var optionElements = eduElement.getElementsByTagName("option");
        //2 遍历option
        for(var i = 0; i<optionElements.length; i++){
            var optionElement = optionElements[i];
            var text = optionElement.text;
            var value = optionElement.value;
            if (value == type) {
                type = text;
            }
        }
        return type;
    }

    function toStatus(status){
        //获取select元素的引用
        var eduElement = document.getElementById("logisticsStatus");
        //1 获取所有的option标签
        var optionElements = eduElement.getElementsByTagName("option");
        //2 遍历option
        for(var i = 0; i<optionElements.length; i++){
            var optionElement = optionElements[i];
            var text = optionElement.text;
            var value = optionElement.value;
            if (value == status) {
                status = text;
            }
        }
        return status;
    }

</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/order/deliverGoods.js" charset="utf-8"></script>
<script src="<%=common%>/system/order/orderView.js" charset="utf-8"></script>
</body>
</html>