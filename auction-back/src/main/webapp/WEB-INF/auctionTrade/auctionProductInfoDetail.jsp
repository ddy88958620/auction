<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form  layui-form-pane1 pzjzsj">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>基本信息</legend>
    </fieldset>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">拍品编号:</label>
            <div class="layui-input-block" style="width: 30%">
                <input type="text" name="id" value="${auctionProductInfo.id}" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">拍品每期延迟时间:</label>
            <div class="layui-input-inline">
                <input type="text" name="shelvesDelayTime" value="${auctionProductInfo.shelvesDelayTime}" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">竞拍开始时间:</label>
            <div class="layui-input-inline">
                <input type="text" name="auctionStartTime"  value="<fmt:formatDate value="${auctionProductInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">分类ID:</label>
            <div class="layui-input-block">
                <input type="text" name="classifyId" value="${auctionProductInfo.classifyId}" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">分类名称:</label>
            <div class="layui-input-block">
                <input type="text" name="classifyName" value="${auctionProductInfo.classifyName}" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">状态:</label>
            <div class="layui-input-inline">
                <label class="layui-form-label">
                    <input type="text" name="status" id="status" value="${auctionProductInfo.status}"
                           autocomplete="off"  class="layui-input"/>
                </label>
            </div>
        </div>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>商品信息</legend>
    </fieldset>
    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="200">
                <col width="200">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>商品ID</th>
                <th>上架数量</th>
                <th>商品价格</th>
                <th>创建时间</th>
                <th>商品名称</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${auctionProductInfo.productId}</td>
                <td>${auctionProductInfo.productNum}</td>
                <td>${auctionProductInfo.productPrice}</td>
                <td>
                    <fmt:formatDate value="${auctionProductInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>${auctionProductInfo.productName}</td>
            </tr>
        </table>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>竞拍规则信息</legend>
    </fieldset>
    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col width="200">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>竞拍规则ID</th>
                <th>竞拍规则名称</th>
                <th>保留价</th>
                <th>浮动金额</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${auctionProductInfo.ruleId}</td>
                <td>${auctionRule.ruleName}</td>
                <td>${auctionProductInfo.floorPrice}</td>
                <td>${auctionProductInfo.floatPrice}</td>
            </tr>
        </table>
    </div>

    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function () {
        });
    }
    if ("${auctionProductInfo.status != null}"){
        var status ='${auctionProductInfo.status}';
        if(status == 1){
            $("#status").val("开拍中");
        }else if(status == 2){
            $("#status").val("准备中");
        }else if(status == 3){
            $("#status").val("定时");
        }else if(status == 4){
            $("#status").val("下架");
        }else if(status == 6){
            $("#status").val("删除");
        }
    }
</script>
</body>
</html>