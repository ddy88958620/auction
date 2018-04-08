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
<script src="<%=common%>/system/appraises/appraises.js" charset="utf-8"></script>
<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;" >
		<select id="status" style="width: 120px;height: 33px;" >
			<option value="">全部</option>
			<c:forEach var="item" items="${OrderAppraisesStatusList}">
				<option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
	</div>

	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户ID:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;">
		<input class="layui-input" id="buyId" name="buyId" autocomplete="off">
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拍品名称:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;">
		<input class="layui-input" id="productName" name="productName" autocomplete="off">
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;">
		<select  id="type" style="width: 120px;height: 33px;"  autocomplete="off">
			<option value="">全部</option>
			<c:forEach var="item" items="${orderAppraisesTypeList}">
				<option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否显示:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;">
		<select  id="isShow" style="width: 120px;height: 33px;"  autocomplete="off">
			<option value="">全部</option>
			<c:forEach var="item" items="${OrderAppraisesShowList}">
				<option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
			</c:forEach>
		</select>
	</div>
	<br/><br/>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;晒单级别:
	<div class="layui-inline layui-form" style="width: 120px;height: 33px;">
		<select  id="appraisesLevel" style="width: 120px;height: 33px;"  autocomplete="off">
			<option value="">全部</option>
			<c:forEach var="item" items="${orderAppraisesRulesList}">
				<option value="${item.appraisesLevel}" <c:if test="${item.appraisesLevel eq params.appraisesLevel}">selected="selected"</c:if>>${item.appraisesLevel}</option>
			</c:forEach>
		</select>
	</div>
	<div class="layui-inline">
		<%--<label class="layui-form-label">晒单时间:</label>--%>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;晒单时间:
		<div class="layui-input-inline">
			<input type="text" name="createTimeStart" id="createTimeStart" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		</div>
	</div>
	至
	<div class="layui-inline">
		<div class="layui-input-inline">
			<input type="text" name="createTimeEnd" id="createTimeEnd" lay-verify="date1"  placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		</div>
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
		var saveUserRole;
		var saveRoleWindow;
		layui.use(['table','form'], function() {
			findBtns(null, "${parentId}");
			table = layui.table;
			form = layui.form;
			table.render({
				elem : '#data',
				even : true,
				url : '${path}/appraises/getAppraisesPage',
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
					title : 'ID',
					align : 'center',
					sort : true,
					fixed : "left",
					width : 80
				}
				, {
					field : 'buyId',
					title : '用户ID',
					align : 'center',
					sort : true,
					fixed : "left",
					width : 80
				}, {
					field : 'buyNickName',
					align : 'center',
					title : '用户名'
				}, {
					field : 'createTime',
					title : '晒单时间',
					align : 'center',
					width : 120,
					templet : '<div>{{toDate(d.createTime,\'yyyy-MM-dd\')}}</div>'
				}, {
					field : 'type',
					title : '晒单类型',
					align : 'center',
					width : 120,
					templet : '<div>{{toType(d.type)}}</div>'
				}, {
					field : 'status',
					title : '状态',
					align : 'center',
					sort : true,
					templet : '<div>{{statusView(d.status)}}</div>'
				}, {
					field : 'productName',
					title : '晒单商品',
					align : 'center',
					sort : true
				},{
					field : 'isShow',
					title : '是否显示',
					align : 'center',
					sort : true,
					templet : '<div>{{isShowView(d.isShow)}}</div>'
				}, {
					field : 'paidMoney',
					title : '成交价',
					align : 'center',
					sort : true
				}, {
					field : 'bidTimes',
					title : '出价次数',
					align : 'center'
				}, {
					field : 'address',
					title : '所在地区',
					align : 'center',
					sort : true
				},{
                        field : 'appraisesLevel',
                        title : '评论级别',
                        align : 'center',
                        sort : true
                    }
				
				] ]

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
							status : $('#status').val(),
							buyId : $('#buyId').val(),
							type : $('#type').val(),
							createTimeStart : $('#createTimeStart').val(),
							createTimeEnd : $('#createTimeEnd').val(),
							productName : $('#productName').val(),
							isShow :$('#isShow').val(),
                            appraisesLevel :$('#appraisesLevel').val()
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
	</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
//时间选择器
  laydate.render({
    elem: '#createTimeEnd'
  });
  
  laydate.render({
    elem: '#createTimeStart'
  });
  
});
</script>
<script type="text/javascript">

	function toType(type){
	 if (type == '1') {
		 type = "用戶";
	    } else if (type == '2') {
	    	type = "系統";
	    }
	 	return type;
	}
	function statusView(status) {
	    if (status == '1') {
	    	status = "待审核";
	    } else if (status == '2') {
	    	status = "已审核";
	    } 
	    return status;
	}
	function isShowView(isShow) {
	    if (isShow == '1') {
	    	isShow = "显示";
	    } else if (isShow == '2') {
	    	isShow = "不显示";
	    } 
	    return isShow;
	}

</script>
</body>
</html>