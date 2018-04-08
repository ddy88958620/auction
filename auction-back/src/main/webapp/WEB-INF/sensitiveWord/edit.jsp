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

	<input type="hidden" id="id" name="id" value="${sensitiveWord.id}">
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">活动状态:</label>
		<div class="layui-input-inline" style="width: 160px;">
			<input name="status" id="status" value="${sensitiveWord.status}" style="display: none"></input>
			<input type="checkbox"
				   lay-filter="switchsub"
				   <c:if test="${sensitiveWord.status eq 1}">checked</c:if> lay-skin="switch">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*对应模块:</label>
		<div class="layui-input-inline" style="width: 30%">
			<select class="layui-input" id="type" name="type" style="padding-left: 27px;padding-right: 27px;">
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
				   class="layui-input" id="title" name="title" value="${sensitiveWord.title }"/>
		</div>
	</div>

	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">*内容:</label>
		<div class="layui-input-block" style="width: 60%">
			<textarea name="sensitiveWord" id="sensitiveWord"
					  placeholder="请输入敏感词，以逗号分割" class="layui-textarea"
					  style="margin-top: 0px; margin-bottom: 0px; height: 300px;">${sensitiveWord.sensitiveWord }</textarea>
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 80px;">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn" lay-submit lay-filter="check1">提交</button>
			<button type="submit" class="layui-btn" lay-submit lay-filter="reset1">重置</button>
			<button type="submit" class="layui-btn" lay-submit lay-filter="close1">取消</button>
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

        form.on('switch(switchsub)',function (data) {
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            var checked = data.elem.checked;
            $("input[name='status']").val(checked?1:2);
        });

        function submitVerify(){
            var title = $("#title").val();
            if (title == null || title.length == 0){
                return "请填写标题";
            }
            if (title.length>20){
                return "标题长度不能超过20";
            }
            var sensitiveWord = $("#sensitiveWord").val();
            if (sensitiveWord == null || sensitiveWord.length == 0){
                return "请填写敏感词库";
            }
            if (sensitiveWord.length>500){
                return "单个词库长度不能超过500";
            }
            return null;
        }
        form.on('submit(check1)', function(data) {

            var tip = submitVerify();
            if (tip != null && tip.length>0){
                layer.msg(tip, {
                    time : 2000
                }, function() {});
                return false;
            }

            $.ajax({
                type : "post",
                url : "${path }/sensitiveWord/edit",
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

        form.on('submit(reset1)', function(data) {
            document.getElementById("form").reset();
            return false;
        });

        form.on('submit(close1)', function(data) {
            parent.layer.closeAll('iframe');
            return false;
        });

    });

</script>
