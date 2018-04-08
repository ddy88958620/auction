<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
	String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");
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
	<br/>
	机器人ID:
	<div class="layui-inline">
		<input class="layui-input" id="id" autocomplete="off" onblur="checkNumber()" lay-verify="number" name="id">
	</div>
	机器人名称:
	<div class="layui-inline">
		<input class="layui-input" id="name" autocomplete="off">
	</div>
	机器人状态:
	<div class="layui-inline">
		<select name="status" id="status" style="width: 168px;height: 33px;" lay-verify="">
			<option value="">请选择</option>
			<option value="1">停用</option>
			<option value="2">启用</option>
		</select>
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
            url : '${path}/robot/findListPage',
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
                title : '机器人Id',
                align : 'center',
                sort : true
            }, {
                field : 'name',
                title : '机器人名称',
                align : 'center',
                sort : true
            }, {
                field : 'address',
                title : '机器人地址',
                align : 'center',
                sort : true
            },{
                field : 'status',
                title : '机器人状态',
                align : 'center',
                templet : '<div>{{toStatus(d.status)}}</div>'
            }/*,{
                field : 'sDate',
                title : '启用时间',
                align : 'center',
                fixed : "left",
                templet : '<div>{{toDate(d.sDate,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                sort : true
            }, {
                field : 'eDate',
                title : '停用时间',
                align : 'center',
                fixed : "left",
                templet : '<div>{{toDate(d.eDate,\'yyyy-MM-dd hh:mm:ss\')}}</div>'

            }*/,{
                field : 'numbers',
                align : 'center',
                title : '机器人使用次数'
            }, {
                field : 'headImg',
                align : 'center',
                title : '机器人头像',
                templet: '<div><img  src="<%=photoPathSuffix%>/{{ d.headImg}}"></div>'
            }] ]

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
                            name: $('#name').val(),
                            id: $('#id').val(),
                            status: $('#status').val()
                        }
                    });
                }
            }
        };
        $('#btnSearch').on('click', function() {
            var name = $('#name').val();
            var id = $('#id').val();
            var status = $('#status').val();

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
    function checkNumber() {
        var integer = new RegExp("^\\d+$");
        var id = $("#id").val();
        if(id != null && id != undefined && id != '') {
            if (!integer.test(id)) {
                layer.msg('机器人ID只能是整数', function () {
                });
                return false;
            }
        }
        return true;
    }
    //类型状态  1.停用 2.启用
    function toStatus(status){
        if (status == '1') {
            return "停用";
        }
        if (status == '2') {
            return "启用";
        }
    }

</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
</body>
</html>