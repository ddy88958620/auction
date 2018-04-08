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
		<input type="hidden" name="id" value="${module.id }">
		<c:if test="${  empty  module.id}">
			<div class="layui-form-item">
				<label class="layui-form-label">当前父菜单:</label>
				<div class="layui-input-inline">
					<input type="input" style="color: red; width: 200px;" disabled="disabled" lay-verify="required" value="${parentName }" autocomplete="off"
						class="layui-input layui-anim layui-anim-scaleSpring"> <input type="hidden" name="moduleParentId"  lay-verify="required" value="${parentId }"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		</c:if>
		<div class="layui-form-item">
			<label class="layui-form-label">菜单名称:</label>
			<div class="layui-input-inline">
				<input type="text" name="moduleName" lay-verify="required|moduleName" placeholder="请输入菜单名称" autocomplete="off" class="layui-input" value="${module.moduleName }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">菜单路径:</label>
			<div class="layui-input-inline">
				<input type="text" name="moduleUrl" lay-verify="required|moduleUrl" placeholder="请输入菜单路径" autocomplete="off" class="layui-input" value="${module.moduleUrl }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">排序:</label>
			<div class="layui-input-inline">
				<input type="number" name="moduleSequence" lay-verify="required|number" placeholder="菜单排序" autocomplete="off" class="layui-input" value="${module.moduleSequence }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">菜单</label>
			<div class="layui-input-block">
				<input type="checkbox" checked="" name="moduleViewBoolean" lay-skin="switch" lay-filter="moduleViewBoolean" lay-text="显示|隐藏"> <input type="hidden" name="moduleView"
					value="1" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">事件名:</label>
			<div class="layui-input-block">
				<input type="text" name="moduleStyle" id="moduleStyle" lay-verify="required" placeholder="请输入事件名" autocomplete="off" class="layui-input" value="${module.moduleStyle }">
			</div>
		</div>
		<div class="layui-form-item layui-icon">
			<label class="layui-form-label">菜单图标:</label>
			<div class="layui-input-inline">
				<select name="icon" lay-verify="required">
					<option value="#xe68e;">&#xe68e;</option>
					<option value="#xe6c6;">&#xe6c6;</option>
					<option value="#xe6c5;">&#xe6c5;</option>
					<option value="#xe662;">&#xe662;</option>
					<option value="#xe661;">&#xe661;</option>
					<option value="#xe660;">&#xe660;</option>
					<option value="#xe65d;">&#xe65d;</option>
					<option value="#xe65f;">&#xe65f;</option>
					<option value="#xe671;">&#xe671;</option>
					<option value="#xe65c;">&#xe65c;</option>
					<option value="#xe756;">&#xe756;</option>
					<option value="#xe735;">&#xe735;</option>
					<option value="#xe65e;">&#xe65e;</option>
					<option value="#xe659;">&#xe659;</option>
					<option value="#xe715;">&#xe715;</option>
					<option value="#xe705;">&#xe705;</option>
					<option value="#xe6b2;">&#xe6b2;</option>
					<option value="#xe6af;">&#xe6af;</option>
					<option value="#xe69c;">&#xe69c;</option>
					<option value="#xe698;">&#xe698;</option>
					<option value="#xe657;">&#xe657;</option>
					<option value="#xe658;">&#xe658;</option>
					<option value="#xe65a;">&#xe65a;</option>
					<option value="#xe65b;">&#xe65b;</option>
					<option value="#xe681;">&#xe681;</option>
					<option value="#xe67c;">&#xe67c;</option>
					<option value="#xe7a0;">&#xe7a0;</option>
					<option value="#xe857;">&#xe857;</option>
					<option value="#xe652;">&#xe652;</option>
					<option value="#xe651;">&#xe651;</option>
					<option value="#xe6fc;">&#xe6fc;</option>
					<option value="#xe6ed;">&#xe6ed;</option>
					<option value="#xe688;">&#xe688;</option>
					<option value="#xe645;">&#xe645;</option>
					<option value="#xe611;">&#xe611;</option>
					<option value="#xe614;">&#xe614;</option>
					<option value="#xe60f;">&#xe60f;</option>
					<option value="#xe615;">&#xe615;</option>
					<option value="#xe641;">&#xe641;</option>
					<option value="#x1002;">&#x1002;</option>
					<option value="#xe63d;">&#xe63d;</option>
					<option value="#xe63e;">&#xe63e;</option>
					<option value="#xe620;">&#xe620;</option>
					<option value="#xe628;">&#xe628;</option>
					<option value="#x1006;">&#x1006;</option>
					<option value="#x1007;">&#x1007;</option>
					<option value="#xe629;">&#xe629;</option>
					<option value="#xe600;">&#xe600;</option>
					<option value="#xe617;">&#xe617;</option>
					<option value="#xe606;">&#xe606;</option>
					<option value="#xe609;">&#xe609;</option>
					<option value="#xe60a;">&#xe60a;</option>
					<option value="#xe62c;">&#xe62c;</option>
					<option value="#x1005;">&#x1005;</option>
					<option value="#xe61b;">&#xe61b;</option>
					<option value="#xe610;">&#xe610;</option>
					<option value="#xe602;">&#xe602;</option>
					<option value="#xe603;">&#xe603;</option>
					<option value="#xe62d;">&#xe62d;</option>
					<option value="#xe62e;">&#xe62e;</option>
					<option value="#xe62f;">&#xe62f;</option>
					<option value="#xe61f;">&#xe61f;</option>
					<option value="#xe601;">&#xe601;</option>
					<option value="#xe630;">&#xe630;</option>
					<option value="#xe631;">&#xe631;</option>
					<option value="#xe654;">&#xe654;</option>
					<option value="#xe642;">&#xe642;</option>
					<option value="#xe640;">&#xe640;</option>
					<option value="#xe61a;">&#xe61a;</option>
					<option value="#xe621;">&#xe621;</option>
					<option value="#xe632;">&#xe632;</option>
					<option value="#xe618;">&#xe618;</option>
					<option value="#xe608;">&#xe608;</option>
					<option value="#xe633;">&#xe633;</option>
					<option value="#xe61c;">&#xe61c;</option>
					<option value="#xe634;">&#xe634;</option>
					<option value="#xe607;">&#xe607;</option>
					<option value="#xe635;">&#xe635;</option>
					<option value="#xe636;">&#xe636;</option>
					<option value="#xe60b;">&#xe60b;</option>
					<option value="#xe619;">&#xe619;</option>
					<option value="#xe637;">&#xe637;</option>
					<option value="#xe61d;">&#xe61d;</option>
					<option value="#xe604;">&#xe604;</option>
					<option value="#xe612;">&#xe612;</option>
					<option value="#xe605;">&#xe605;</option>
					<option value="#xe638;">&#xe638;</option>
					<option value="#xe60c;">&#xe60c;</option>
					<option value="#xe616;">&#xe616;</option>
					<option value="#xe613;">&#xe613;</option>
					<option value="#xe61e;">&#xe61e;</option>
					<option value="#xe60d;">&#xe60d;</option>
					<option value="#xe64c;">&#xe64c;</option>
					<option value="#xe60e;">&#xe60e;</option>
					<option value="#xe622;">&#xe622;</option>
					<option value="#xe64f;">&#xe64f;</option>
					<option value="#xe64d;">&#xe64d;</option>
					<option value="#xe639;">&#xe639;</option>
					<option value="#xe623;">&#xe623;</option>
					<option value="#xe63f;">&#xe63f;</option>
					<option value="#xe643;">&#xe643;</option>
					<option value="#xe647;">&#xe647;</option>
					<option value="#xe648;">&#xe648;</option>
					<option value="#xe649;">&#xe649;</option>
					<option value="#xe626;">&#xe626;</option>
					<option value="#xe627;">&#xe627;</option>
					<option value="#xe62b;">&#xe62b;</option>
					<option value="#xe63a;">&#xe63a;</option>
					<option value="#xe624;">&#xe624;</option>
					<option value="#xe63b;">&#xe63b;</option>
					<option value="#xe650;">&#xe650;</option>
					<option value="#xe64b;">&#xe64b;</option>
					<option value="#xe63c;">&#xe63c;</option>
					<option value="#xe62a;">&#xe62a;</option>
					<option value="#xe64e;">&#xe64e;</option>
					<option value="#xe646;">&#xe646;</option>
					<option value="#xe625;">&#xe625;</option>
					<option value="#xe64a;">&#xe64a;</option>
					<option value="#xe644;">&#xe644;</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">菜单描述</label>
			<div class="layui-input-block">
				<textarea name="moduleDescribe" lay-verify="required|moduleDescribe" placeholder="请输入菜单描述" class="layui-textarea">${module.moduleDescribe }</textarea>
			</div>
		</div>
		<button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
	</form>

	<script>
		<c:if test="${not empty  module}">
		if ("${module.moduleView}" == 2) {
			$("input[name='moduleViewBoolean']").attr("checked", false);
		}
		$("select[name='icon']").val('${module.icon}');
		</c:if>
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
                moduleName : [ /^[a-zA-Z0-9\u4e00-\u9fa5]{2,10}$/, '必须是2到10之间的数字、字母、汉字' ],
                moduleUrl :[ /^(.){2,50}$/, '长度必须在2到50之间' ],
                moduleDescribe : [ /^[\s\S]{1,100}$/, '长度必须在1到100之间' ]
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