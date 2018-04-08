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
<body>
	<form class="layui-form " action="" id="form">
		<input type="hidden" name="id" value="${sysUser.id }">
		<c:if test="${  empty  sysUser}">
			<div class="layui-form-item">
				<label class="layui-form-label">当前父用户:</label>
				<div class="layui-input-block">
					<input type="input" style="color: red; width: 200px;" disabled="disabled" value="${parentUserName }" autocomplete="off" class="layui-input layui-anim layui-anim-scaleSpring">
					<input type="hidden" name="parentId" value="${parentUserId }" autocomplete="off" class="layui-input">
				</div>
			</div>
		</c:if>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">用户名:</label>
			<div class="layui-input-inline">
				<input type="text" name="userAccount" lay-verify="required|userAccount" placeholder="请输入用户名" autocomplete="off" class="layui-input" value="${sysUser.userAccount }">
			</div>
			<label class="layui-form-label">密码:</label>
			<div class="layui-input-inline">
				<input type="password" name="userPassword" lay-verify="required|userPassword" placeholder="请输入密码" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">姓名:</label>
			<div class="layui-input-inline">
				<input type="text" name="userName" lay-verify="required|userName" placeholder="请输入姓名" autocomplete="off" class="layui-input" value="${sysUser.userName }">
			</div>
			<label class="layui-form-label">性别:</label>
			<div class="layui-input-inline layui-icon">
				<input type="radio" name="userSex" value="男" title="&#xe662;" checked=""> <input type="radio" name="userSex" value="女" title="&#xe661;">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">地址:</label>
			<div class="layui-input-inline">
				<input type="text" name="userAddress" lay-verify="required|userAddress" placeholder="请输入地址" autocomplete="off" class="layui-input" value="${sysUser.userAddress }">
			</div>
			<label class="layui-form-label">电话:</label>
			<div class="layui-input-inline">
				<input type="text" name="userTelephone" lay-verify="required|number" placeholder="请输入电话" autocomplete="off" class="layui-input" value="${sysUser.userTelephone }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机:</label>
			<div class="layui-input-inline">
				<input type="text" name="userMobile" lay-verify="required|userMobile" placeholder="请输入手机" autocomplete="off" class="layui-input" value="${sysUser.userMobile }">
			</div>
			<label class="layui-form-label">邮箱:</label>
			<div class="layui-input-inline">
				<input type="text" name="userEmail" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" value="${sysUser.userEmail }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">QQ:</label>
			<div class="layui-input-inline">
				<input type="text" name="userQq" lay-verify="required|number" placeholder="请输入QQ" maxlength="11" autocomplete="off" class="layui-input" value="${sysUser.userQq }">
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" maxlength="50" placeholder="请输入备注" class="layui-textarea">${sysUser.remark }</textarea>
			</div>
		</div>
		<button style="display: none;" lay-filter="submitBtn" lay-submit="" />
	</form>

	<script>
		<c:if test="${not empty  sysUser}">
		$("input[name='userSex'][value='${sysUser.userSex}']").attr("checked", true);
		</c:if>
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
				userAccount : function(value, item) {
					if (!new RegExp("^[a-zA-Z0-9]{6,20}$").test(value)) {
						return '用户名只能是6-20位英文和数字';
					}
				},
				userPassword : [ /^$|(.+){6,16}$/, '长度必须在6到16之间' ],
				userName : function(value, item) {
                    if (!new RegExp("^[\\s\\S]{2,10}$").test(value)) {
						return '长度必须在2到10之间';
					} else if (!new RegExp("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$").test(value)) {
						return '姓名格式错误';
					}
				},
                userAddress : [ /^[\s\S]{2,100}$/, '长度必须在2到100之间' ],
				userMobile : [ /^[1][3,4,7,5,8,9][0-9]{9}$/, '手机号码格式错误' ]

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
								parent.window.refreshPage();
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