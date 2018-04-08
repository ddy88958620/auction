<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/22 0022
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>商品信息</legend>
</fieldset>
<form class="layui-form  layui-form-pane1 pzjzsj">

    <div class="layui-form-item">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-block" style="width: 30%">
            <input type="text" name="name" value="${productVo.productName}" autocomplete="off"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">商品价格</label>
        <div class="layui-input-inline">
            <input type="text" name="sort"  autocomplete="off" value="${productVo.productAmount}"
                   class="layui-input">
        </div>
    </div>
</form>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>库存记录</legend>
</fieldset>
<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>原有库存</th>
            <th>现有库存</th>
            <th>操作类型</th>
            <th>操作时间</th>
        </tr>
        </thead>
        <tbody>
       <%-- <c:forEach items="${inventoryLogList}" var="st">
            ${st.oldProductNum}
        </c:forEach>--%>

       <c:forEach items="${inventoryLogList}" var="record">
           <tr>
               <td>${record.oldProductNum}</td>
               <td>${record.updProductNum}</td>
               <td>
                <c:if test="${record.type==1}">后台修改</c:if>
                   <c:if test="${record.type==2}">出库</c:if>
                   <c:if test="${record.type==3}">入库</c:if>
               </td>
               <td>
                   <fmt:formatDate value="${record.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
               </td>
           </tr>
       </c:forEach>




        </tbody>
    </table>
</div>


<script>
    //查看页面，所有的输入框设为只读
    $('input').attr("readonly","readonly");
    $('textarea').attr("readonly","readonly");

</script>
</body>
</html>
