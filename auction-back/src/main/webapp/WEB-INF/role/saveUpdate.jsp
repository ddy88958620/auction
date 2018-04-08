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
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body style="margin-top: 20px;">
	<form class="layui-form " action="" id="form">
		<input type="hidden" name="id" value="${role.id }">
		<c:if test="${  empty  role.id}">
			<input type="hidden" name="roleSuper" lay-verify="required" value="${parentId }">
			<div class="layui-form-item">
				<label class="layui-form-label">当前父角色:</label>
				<div class="layui-input-inline">
					<input type="input" style="color: red; width: 200px;" disabled="disabled" lay-verify="required" value="${parentName }" autocomplete="off"
						class="layui-input layui-anim layui-anim-scaleSpring">

				</div>
			</div>
		</c:if>
		<div class="layui-form-item">
			<label class="layui-form-label">角色名称:</label>
			<div class="layui-input-inline">
				<input type="text" name="roleName" required lay-verify="required|roleName" placeholder="请输入角色名称" autocomplete="off" class="layui-input" value="${role.roleName }">
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">角色描述</label>
			<div class="layui-input-block">
				<textarea name="roleSummary" lay-verify="required" maxlength="100" placeholder="请输入角色描述" class="layui-textarea">${role.roleSummary }</textarea>
			</div>
		</div>
		<button style="display: nonw;" lay-filter="submitBtn" lay-submit="">
	</form>

	<script>
		function resetForm() {
			document.getElementById("form").reset();
		}
		function submitForm() {
			$("button[lay-filter='submitBtn']").trigger('click');
		}
		if ("${message}") {
			parent.layer.closeAll('iframe');
			parent.layer.msg('${message}', function() {
			});
		}
		layui.use('form', function() {
			var form = layui.form;
			//自定义验证规则
			form.verify({
				roleName : [ /(.+){2,10}$/, '长度必须在2到10之间' ]
			});
			form.on('submit(submitBtn)', function(data) {
				$.ajax({
					type : "post",
					url : "${path }${url}",
					data : data.field,
					dataType : "json",
					beforeSend : function() {
						layer.load(1, {
							shade : [ 0.1, '#fff' ]
						});
					},
					success : function(result) {
						layer.msg(result.msg, {
							time : 1000
						}, function() {
							if (result.code == '0') {
								parent.layer.closeAll('iframe');
								parent.window.location.reload();
							}
						});
					}
				});
				return false;
			});
		});
	</script>
</body>
</html>