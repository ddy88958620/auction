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
<fieldset class="layui-elem-field layui-field-title">
	<legend>商品详情</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="">
	<div class="layui-form-item">
		<label class="layui-form-label">商品ID</label>
		<div class="layui-input-inline">
			<input type="text" name="username" value="${productVo.productId}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品名称</label>
		<div class="layui-input-inline">
			<input type="text" name="username" value="${productVo.productName}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品标题</label>
		<div class="layui-input-inline">
			<input type="text" name="productTitle" value="${productVo.productTitle}" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">市场价格</label>
		<div class="layui-input-inline">
			<input type="text" name="username" value="${productVo.salesAmount}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品图片</label>
		<div class="layui-input-inline">
			<table>
				<tr>
					<c:forEach var="picInfo" items="${productVo.pics}">
						<td>
							<img src="<%=photoPathSuffix%>/${picInfo.picUrl}" style="width: 100px;height: 60px;"/>
						</td>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>


	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">竞拍规则</label>
		<div class="layui-input-block">
			<textarea class="layui-textarea">
				${productVo.rules}
			</textarea>
		</div>
	</div>

</form>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/productManage/productManageList.js" charset="utf-8"></script>

<script>
	//查看页面，所有的输入框设为只读
    $('input').attr("readonly","readonly");
    $('textarea').attr("readonly","readonly");
</script>
</body>
</html>