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

<style>
	.layui-form-item .layui-input-inline {
		width: 600px;
	}
</style>

<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form" style="margin-left: 60px;margin-top: 30px">

	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">*推送类型:</label>
		<div class="layui-input-inline">
			<c:forEach var="item" items="${notiType}">
				<input type="radio" name="notiTypeRadio" value="${item.key}"
					   title="${item.value}" lay-filter="notiTypeRadio"
					   <c:if test="${item.key == record.notiType}">checked</c:if>
					   disabled="disabled"/>
			</c:forEach>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*推送时间:</label>
		<c:forEach var="item" items="${timeType}">
		<div class="layui-input-inline">
				<input type="radio" name="timeTypeRadio" value="${item.key}"
					   title="${item.value}" lay-filter="timeTypeRadio"
					   <c:if test="${item.key == record.timeType}">checked</c:if>
					   disabled="disabled"/>
			<c:if test="${item.key == 1}"><br></c:if>
			<c:if test="${item.key == 2 && record.timeType == 2}">
				<input type="text" class="layui-input" style="width: 182px;display: inline"
					   id="sendTimeSel" name="sendTimeSel"
						value="<fmt:formatDate value="${record.sendTime}" pattern="yyyy-MM-dd HH:mm"/>"
					   disabled="disabled">
			</c:if>
		</div>
		</c:forEach>
		<input type="text" class="layui-input" style="display: none;"
			   id="timeType" name="timeType" value="1">
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*标题:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入标题" value="${record.subject}"
				   class="layui-input" id="subject" name="subject"
				   disabled="disabled"/>
		</div>
	</div>
	<div class="layui-form-item" id="urlDiv"
		 <c:if test="${1 != record.notiType}">style="display: none;"</c:if>>
		<label class="layui-form-label">*链接:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入链接" value="${record.url}"
				   class="layui-input" id="url" name="url"
				   disabled="disabled"/>
		</div>
	</div>
	<div class="layui-form-item" id="activityIdDiv"
		 <c:if test="${2 != record.notiType}">style="display: none;"</c:if>>
		<label class="layui-form-label">*活动ID:</label>
		<div class="layui-input-inline" style="width: 15%">
			<input type="text" value="${record.activityId}"
				   class="layui-input" id="activityId" name="activityId"
				   disabled="disabled"/>
		</div>
	</div>
	<div class="layui-form-item" id="productIdDiv"
		 <c:if test="${3 != record.notiType}">style="display: none;"</c:if>>
		<label class="layui-form-label">*拍品ID:</label>
		<div class="layui-input-inline" style="width: 15%">
			<input type="text" value="${record.productId}"
				   class="layui-input" id="productId" name="productId"
				   disabled="disabled"/>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*推送标题:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" value="${record.title}"
				   class="layui-input" id="title" name="title"
				   disabled="disabled"/>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">*推送内容:</label>
		<div class="layui-input-block" style="width: 60%">
			<textarea name="content" id="content" class="layui-textarea" disabled="disabled"
					  style="margin-top: 0px; margin-bottom: 0px; height: 200px;">${record.content}</textarea>
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

        laydate.render({
            elem: '#sendTimeSel',
            type: 'datetime',
			format: 'yyyy-MM-dd HH:mm'
        });

    });
</script>
