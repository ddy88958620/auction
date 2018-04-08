<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>--%>
<!DOCTYPE html>
<html>
	<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
//	String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");

%>
<head>
	<meta charset="utf-8">
	<title>layui</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<c:set var="path" value="<%=path%>"></c:set>
	<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
	<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>

	<link rel="stylesheet" href="<%=common%>/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=common%>/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=common%>/ztree/js/jquery.ztree.core.js"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>

<div style="margin-top: 10px;margin-left: 40px;font-size: small;color: #9F9F9F">上次更新时间：
	<fmt:formatDate value="${sensitiveWord.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
</div><br>
<di style="margin-top: -10px;margin-left: 40px;font-size: small;color: #9F9F9F">更新用户：${sensitiveWord.userName }</di>
<hr style="margin-bottom: 30px">

<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form" style="margin-left: 60px;">

	<input type="hidden" id="id" value="${sensitiveWord.id}">

	<div class="layui-form-item">
		<label class="layui-form-label">*对应模块:</label>
		<div class="layui-input-inline" style="width: 30%">
			<select class="layui-input" id="type" name="type"
					style="padding-left: 27px;padding-right: 27px;"
					disabled="disabled">
				<c:forEach var="item" items="${types}">
					<option <c:if test="${item.key eq sensitiveWord.type}">selected="selected"</c:if>
							value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*标题:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入标题"
				   class="layui-input" disabled="disabled" id="title" name="title" value="${sensitiveWord.title }"/>
		</div>
	</div>

	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">*内容:</label>
		<div class="layui-input-block" style="width: 60%">
			<textarea name="sensitiveWord" id="sensitiveWord"
					  placeholder="请输入敏感词，以逗号分割" disabled="disabled" class="layui-textarea"
					  style="margin-top: 0px; margin-bottom: 0px; height: 300px;">${sensitiveWord.sensitiveWord }</textarea>
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 80px;">
		<div class="layui-input-block">
			<button type="submit"  class="layui-btn" lay-submit lay-filter="close">返回</button>
		</div>
	</div>
</form>

<script>

    layui.use(['form', 'layedit', 'laydate'], function(){
        <c:if test="${not empty isAudited}">
        parent.layer.msg("${isAudited}");
        parent.layer.closeAll('iframe');
        parent.window.refreshPage();
        </c:if>
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;

        form.on('submit(close)', function(data) {
            parent.layer.closeAll('iframe');
            return false;
        });
    });
</script>
