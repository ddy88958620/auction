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
		<input type="hidden" name="id" value="${channel.id }">
		<div class="layui-form-item">
			<label class="layui-form-label">渠道名称:</label>
			<div class="layui-input-inline">
				<input type="text" name="channelName" lay-verify="required|channelName" placeholder="请输入渠道名称" autocomplete="off" class="layui-input" value="${channelInfo.channelName }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">渠道key:</label>
			<div class="layui-input-inline">
				<input type="text" name="channelKey" lay-verify="required|channelKey" placeholder="请输入渠道key" autocomplete="off" class="layui-input" value="${channelInfo.channelKey }">
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">状态:</label>
		<div class="layui-inline"  >
			<select class="layui-input" id="status" style="padding-left: 27px;padding-right: 27px;">
				<c:forEach var="item" items="${status}">
					<option value="${item.key}" >${item.value}</option>
				</c:forEach>
			</select>
		</div>
	</div>
		<div class="layui-form-item layui-form-text"  style="width: 30%;">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" lay-verify="required|remark" placeholder="请输入备注" class="layui-textarea">${channelInfo.remark }</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" type="submit" lay-submit lay-filter="submitBtn">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
			</div>
		</div>
		<button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
	</form>

	<script>
		function resetForm() {
			document.getElementById("form").reset();
			$("select[name='icon']").val('${module.icon}');
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
                channelName : [ /^[a-zA-Z0-9\u4e00-\u9fa5]{2,10}$/, '必须是2到10之间的数字、字母、汉字' ],
                channelKey :[ /^(.){2,50}$/, '长度必须在2到50之间' ],
                remark : [ /^[\s\S]{1,100}$/, '长度必须在1到100之间' ]
			});
			//监听指定开关
			form.on('switch(moduleViewBoolean)', function(data) {
				var tip = this.checked ? '会' : '不会';
				$('input[name=moduleView]').val(this.checked ? '1' : '2');
				layer.tips('温馨提示：该页面将' + tip + "显示在页面上", data.othis);
			});
			$("input[name='moduleStyle']").focus(function() {
				layer.tips('温馨提示：页面上按钮的点击事件,第一个参数固定this,方便取值,其他根据实际情况追加或追加参数', '#moduleStyle', {
					tips : [ 1, '#3595CC' ],
					time : 4000
				});
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