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
    <script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form" style="margin-left: 30px">
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px;margin-top: 30px">
            <label class="layui-form-label">目标号码： </label>
            <div class="layui-input-inline" style="width: 30%;">
                <input id="phone" name="phone" disabled="disabled" type="text" unselectable="on" class="layui-input"  value="${sendSmsRecord.phone}" />
            </div>
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">接受对象： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled"  id="count" name="count" value="${sendSmsRecord.count}" />
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">返回状态： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled"  id="code" name="code" value="${sendSmsRecord.code}" />
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">创建时间： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled"  id="createTime" name="createTime" value="${sendSmsRecord.createTime}" />
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">发布人： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled"  id="publisher" name="publisher" value="${sendSmsRecord.publisher}" />
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">发布时间： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled"  id="releaseTime" name="releaseTime" value="${sendSmsRecord.releaseTime}" />
        </div>
    </div>
</form>
<script>

    layui.use(['form'], function () {
        var form = layui.form;
        var layer = layui.layer;
    });
</script>
</body>
</html>
