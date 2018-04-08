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
	商品名称:
	<div class="layui-inline">
		<input class="layui-input" id="productName" name="productName" autocomplete="off">
	</div>
	拍卖期数:
	<div class="layui-inline">
		<input class="layui-input" id="auctionNo" name="auctionNo" autocomplete="off">
	</div>
	期数ID:
	<div class="layui-inline">
		<input class="layui-input" id="id" name="id" onblur="checkNumber()" lay-verify="number" autocomplete="off">
	</div>
	状态:
	<div class="layui-inline">
		<select name="status" id="status" style="width: 168px;height: 33px;" lay-verify="">
			<option value="">请选择</option>
			<option value="1">正在拍</option>
			<option value="2">已完结</option>
			<option value="3">未完成</option>
		</select>
	</div>
	<div class="layui-form-item">
	</div>
	创建时间:
	<div class="layui-inline">
		<input type="text" name="startTime" id="startTime" lay-verify="date"  autocomplete="off" class="layui-input">
	</div>
	至
	<div class="layui-inline">
		<input class="layui-input" name="endTime" id="endTime">
	</div>
	<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
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
    layui.use(['table','form','laydate'], function() {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startTime',
            type: 'datetime'
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime'
        });
        findBtns(null, "${parentId}");

        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/auctionInfo/auctionInfoList',
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
                field : 'id',
                title : '期数ID',
                align : 'center',
                sort : true,
                width: '6%'
            },{
                field : 'auctionNo',
                title : '拍卖期数',
                align : 'center',
                sort : true,
                width: '6%'
            },{
                field : 'productId',
                title : '商品ID',
                align : 'center',
                sort : true,
                width: '6%'
            },{
                field : 'productName',
                title : '商品名称',
                align : 'center',
                width: '15%'
            },{
                field : 'ruleId',
                align : 'center',
                width : '6%',
                title : '规则ID'
            }, {
                field : 'validBidCount',
                align : 'center',
                width : '9%',
                title : '拍品有效出价次数'
            },{
                field : 'freeBidCount',
                align : 'center',
                width : '9%',
                title : '赠币出价次数'
            },{
                field : 'totalBidCount',
                align : 'center',
                width : '9%',
                title : '拍品总出价次数'
            },{
                field : 'robotBidCount',
                title : '机器人出价次数',
                align : 'center',
                width : '9%'
            }, {
                field : 'status',
                title : '状态',
                align : 'center',
                width : '5%',
                sort : false,
                templet : '<div>{{toStatus(d.status)}}</div>'

            }, {
                field : 'startTime',
                title : '创建时间',
                align : 'center',
                sort : true,
                width : '10%',
                templet : '<div>{{toDate(d.startTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
            }
            ] ]

        });
        var $ = layui.$, active = {
            reload : function() {
                if(checkNumber()) {
                    //执行重载
                    table.reload('search', {
                        page: {
                            //重新从第 1 页开始
                            page: 1
                        },
                        where: {
                            id: $('#id').val(),
                            auctionNo: $('#auctionNo').val(),
                            productName: $('#productName').val(),
                            status: $('#status').val(),
                            startTime: $('#startTime').val(),
                            endTime: $('#endTime').val(),
                            classifyName: $('#classifyName').val()

                        }
                    });
                }
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
        }
    });
        //类型状态  1.未完成 2.已完结 3.未完成
        function toStatus(status){
            if (status == '1') {
                return "正在拍";
            }
            if (status == '2') {
                return "已完结";
            }
            if (status == '3') {
                return "未完成";
            }
        }
    function checkNumber() {
        var integer = new RegExp("^\\d+$");
        var id = $("#id").val();
        if(id != null && id != undefined && id != '') {
            if (!integer.test(id)) {
                layer.msg('期数ID只能是整数', function () {
                });
                return false;
            }
        }
        return true;
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionTrade/batchOn.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionTrade/auctionView.js" charset="utf-8"></script>
</body>
</html>