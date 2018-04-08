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
		用户Id:
		<div class="layui-inline">
			<input class="layui-input" id="userId" autocomplete="off" />
		</div>
		地址Id:
		<div class="layui-inline">
			<input class="layui-input" id="addressId" autocomplete="off">
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
				url : '${path}/user/shippingAddressList',
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
				}, {
                	field : 'id',
                	align : 'center',
                	title : '地址Id',
                    width : 120
            	},{
					field : 'userId',
					align : 'center',
					title : '用户Id',
                    width : 120
				},{
                	field : 'userName',
                	align : 'center',
                	title : '用户姓名',
                    width : 120
            	},{
					field : 'userPhone',
					align : 'center',
					title : '用户手机',
                    width : 120
            	},{
					field : 'provinceName',
					align : 'center',
					title : '省级名称',
                    width : 120
				},{
					field : 'cityName',
					align : 'center',
					title : '市级名称',
                    width : 120
				},{
					field : 'districtName',
					align : 'center',
					title : '区级名称',
                    width : 120
				},{
					field : 'townName',
					align : 'center',
					title : '县级名称',
                    width : 120
				},{
                    field : 'address',
                    align : 'center',
                    title : '详细地址',
                    width : 200
                },{
					field : 'addressType',
					title : '地址类型',
					align : 'center',
                	width : 120,
                	templet : '<div>{{toAddressType(d.addressType)}}</div>'
				}, {
					field : 'postCode',
					title : '邮编',
					align : 'center',
                    width : 120
				}, {
					field : 'createTime',
					title : '添加时间',
					align : 'center',
					width : 180,
					templet : '<div>{{toDate(d.createTime)}}</div>'
				}, {
					field : 'updateTime',
					title : '更新时间',
					align : 'center',
					width : 180,
					templet : '<div>{{toDate(d.updateTime)}}</div>'
				}, {
					field : 'status',
					align : 'center',
					title : '状态',
                    width : 180,
                    templet : '<div>{{toStatus(d.status)}}</div>'
				} ] ]

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
                            addressId : $('#addressId').val()
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
        //地址类型（0.默认地址，1.其他地址）
        function toAddressType(addressType) {
            if(addressType =='0'){
                return "默认地址";
            }
            if(addressType =='1'){
                return "其他地址";
            }
        }
		function toStatus(status) {
            if(status =='0'){
                return "启用";
            }
		    if(status =='1'){
				return "禁用";
			}
			if(status =='2'){
                return "删除";
            }
        }
	</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
	<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>

</body>
</html>