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
<script src="<%=common%>/system/appraises/appraises2.js" charset="utf-8"></script>
</head>
<body>
	<div id="toolBar">
		活动ID:
		<div class="layui-inline">
			<input class="layui-input" id="ruleId"  autocomplete="off" value="${params.id}">
		</div>
		活动名称:
		<div class="layui-inline">
			<input class="layui-input" id="ruleTitle" autocomplete="off" value="${params.ruleTitle}">
		</div>
		活动状态:
		<div class="layui-inline layui-form" style="width: 120px;height: 33px;" >
			<select id="ruleStatus" style="width: 120px;height: 33px;" >
				<option value="">全部</option>
				<option value="0">关闭</option>
				<option value="1">开启</option>
			</select>
		</div>
		创建时间:
		<div class="layui-inline">
			<input class="layui-input" id="createTimeBegin" lay-verify="date"  placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.createTimeBegin}">
		</div>
		&nbsp;至&nbsp;
		<div class="layui-inline">
			<input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.createTimeEnd}">
		</div>
		<%--充值金额:
		<div class="layui-inline">
			<input class="layui-input" id="outMoneyBegin" placeholder="￥" autocomplete="off" size="10" value="${params.outMoneyBegin}">
		</div>
		元&nbsp;到&nbsp;
		<div class="layui-inline">
			<input class="layui-input" id="outMoneyEnd" placeholder="￥" autocomplete="off" size="10" value="${params.outMoneyEnd}">
		</div>--%>
		<%--<div class="divider"></div>--%>
		<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
			<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
		</button>
	</div>

	<table class="layui-hide" id="data">
	</table>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script>
        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        layui.use(['table','form','laydate'], function() {
            findBtns(null, "${parentId}");

            var laydate = layui.laydate;

            laydate.render({
                elem: '#createTimeBegin'
            });
            laydate.render({
                elem: '#createTimeEnd'
            });

            table = layui.table;
            form = layui.form;
			table.render({
				elem : '#data',
				even : true,
				url : '${path}/order/rechargeActivitys',
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
					fixed : "left",
                    width : 120
				}, {
					field : 'id',
					align : 'center',
					title : '活动ID',
                    width : 140
				}, {
					field : 'ruleTitle',
					title : '活动名称',
					align : 'center',
                    width : 180
				}, {
                    field : 'ruleUser',
                    title : '活动用户',
                    align : 'center',
                    width : 160,
                    templet : '<div>{{toRuleUser(d.ruleUser)}}</div>'
                }, {
                    field : 'ruleStatus',
                    title : '活动状态',
                    align : 'center',
                    width : 160,
                    templet : '<div>{{toRuleStatus(d.ruleStatus)}}</div>'
                },{
                    field : 'createTime',
                    title : '开始时间',
                    align : 'center',
                    sort : true,
                    width : 230,
                    templet : '<div>{{toDate(d.ruleStartTime,\'yyyy-MM-dd\')}}</div>'
                },{
                    field : 'createTime',
                    title : '结束时间',
                    align : 'center',
                    sort : true,
                    width : 230,
                    templet : '<div>{{toDate(d.ruleEndTime,\'yyyy-MM-dd\')}}</div>'
                },{
                    field : 'createTime',
                    title : '创建时间',
                    align : 'center',
                    sort : true,
                    width : 230,
                    templet : '<div>{{toDate(d.createTime)}}</div>'
                }, {
                    field : 'remark',
                    title : '备注',
                    align : 'center',
                    width : 230
                },] ]

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
                            id : $('#ruleId').val(),
                            ruleTitle : $('#ruleTitle').val(),
                            ruleStatus : $('#ruleStatus').val(),
                            createTimeBegin : $('#createTimeBegin').val(),
                            createTimeEnd : $('#createTimeEnd').val(),
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
			}
		});
        function toRuleUser(ruleUser) {
            if(ruleUser =='1'){
                return "全部用户";
            }
            if(ruleUser =='2'){
                return "首充用户";
            }
        }
        function toRuleStatus(ruleStatus) {
            if(ruleStatus =='0'){
                return "关闭";
            }
            if(ruleStatus =='1'){
                return "开启";
            }
        }
	</script>
</body>
</html>