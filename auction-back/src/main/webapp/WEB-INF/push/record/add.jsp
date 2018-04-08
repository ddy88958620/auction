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
	<script src="<%=common%>/system/push/record/utils.js" charset="utf-8"></script>
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
					   <c:if test="${item.key == 1}">checked</c:if>/>
			</c:forEach>
			<input type="text" class="layui-input" style="display: none;"
				   id="notiType" name="notiType" value="1">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*推送时间:</label>
		<c:forEach var="item" items="${timeType}">
		<div class="layui-input-inline">
				<input type="radio" name="timeTypeRadio" value="${item.key}"
					   title="${item.value}" lay-filter="timeTypeRadio" <c:if test="${item.key == 1}">checked</c:if>/>
			<c:if test="${item.key == 1}"><br></c:if>
			<c:if test="${item.key == 2}">
				<input type="text" class="layui-input" style="width: 182px;display: inline"
					   id="sendTimeSel" name="sendTimeSel" placeholder="yyyy-MM-dd HH:mm">
			</c:if>
		</div>
		</c:forEach>
		<input type="text" class="layui-input" style="display: none;"
			   id="timeType" name="timeType" value="1">
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*标题:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入标题"
				   class="layui-input" id="subject" name="subject"/>
		</div>
	</div>
	<div class="layui-form-item" id="urlDiv">
		<label class="layui-form-label">*链接:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入链接"
				   class="layui-input" id="url" name="url"/>
		</div>
	</div>
	<div class="layui-form-item" id="activityIdDiv" style="display: none;">
		<label class="layui-form-label">*活动:</label>
		<div class="layui-input-inline" style="width: 15%">
			<button class="layui-btn layui-btn-primary" id="selectActivity" name="selectActivity">请选择活动</button>
			<input type="text" class="layui-input" id="activityId" name="activityId" style="display: none;"/>
		</div>
	</div>
	<div class="layui-form-item" id="productIdDiv" style="display: none;">
		<label class="layui-form-label">*拍品:</label>
		<div class="layui-input-inline" style="width: 15%">
			<button class="layui-btn layui-btn-primary"id="selectProduct" name="selectProduct" >请选择拍品</button>
			<input type="text" class="layui-input" id="productId" name="productId" style="display: none;"/>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">*推送标题:</label>
		<div class="layui-input-inline" style="width: 30%">
			<input type="text" placeholder="请输入推送标题"
				   class="layui-input" id="title" name="title"/>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">*推送内容:</label>
		<div class="layui-input-block" style="width: 60%">
			<textarea name="content" id="content"
					  placeholder="请输入推送内容" class="layui-textarea" style="margin-top: 0px; margin-bottom: 0px; height: 200px;"></textarea>
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 80px;">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn"  lay-submit lay-filter="check1">确定</button>
			<button type="submit"  class="layui-btn" lay-submit lay-filter="close">取消</button>
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

            var subject = $("#subject").val();
            if (subject == null || subject.length == 0){
                return "请填写标题";
            }
            var timeType = $("#timeType").val();
            if(timeType == 2){
                var sendTimeSel = $("#sendTimeSel").val();
                if (sendTimeSel == null || sendTimeSel.length == 0){
                    return "请填写推送时间";
                }
			}
			var notiType = $("#notiType").val();
            if (notiType == 1){
                var url = $("#url").val();
                if (url == null || url.length == 0){
                    return "请填写链接";
                }
			}else if(notiType == 2){
                var activityId = $("#activityId").val();
                if (activityId == null || activityId.length == 0){
                    return "请填写活动";
                }
			}else if(notiType == 3){
                var productId = $("#productId").val();
                if (productId == null || productId.length == 0){
                    return "请填写拍品";
                }
			}
            var title = $("#title").val();
            if (title == null || title.length == 0){
                return "请填写推送标题";
            }
            var content = $("#content").val();
            if (content == null || content.length == 0){
                return "请填写推送内容";
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
                url : "${path }/notiRecord/add",
                data :data.field,
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

        form.on('submit(close)', function(data) {
            parent.layer.closeAll('iframe');
            return false;
        });

        laydate.render({
            elem: '#sendTimeSel',
            type: 'datetime',
			format: 'yyyy-MM-dd HH:mm'
        });

        form.on('radio(notiTypeRadio)', function(data){
            console.log(data.elem); //得到radio原始DOM对象
            console.log(data.value); //被点击的radio的value值
			var value = data.value;
            $("#notiType").val(value);
			if (value == 1){
                $("#urlDiv").css("display","block");
                $("#activityIdDiv").css("display","none");
                $("#productIdDiv").css("display","none");
			}else if(value == 2){
                $("#urlDiv").css("display","none");
                $("#activityIdDiv").css("display","block");
                $("#productIdDiv").css("display","none");
			}else if(value == 3){
                $("#urlDiv").css("display","none");
                $("#activityIdDiv").css("display","none");
                $("#productIdDiv").css("display","block");
			}
        });
        form.on('radio(timeTypeRadio)', function(data){
            console.log(data.elem); //得到radio原始DOM对象
            console.log(data.value); //被点击的radio的value值
            var value = data.value;
            $("#timeType").val(value);
        });

        $("#selectProduct").click(function(){
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '选择拍品'
                ,area: ['80%','80%']
                ,shade: 0.3
                ,maxmin: true
                ,content: "selectProduct"
                ,yes: function(){
                    $(that).click();
                },btn2: function(){
                    layer.closeAll();
                }
                ,zIndex: layer.zIndex //重点1
                ,success: function(layero){
                    //layer.selectProduct(layero); //重点2
                }
            });
            return false;
        });

        $("#selectActivity").click(function(){
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '选择拍品'
                ,area: ['80%','80%']
                ,shade: 0.3
                ,maxmin: true
                ,content: "selectActivity"
                ,yes: function(){
                    $(that).click();
                },btn2: function(){
                    layer.closeAll();
                }
                ,zIndex: layer.zIndex //重点1
                ,success: function(layero){
                    //layer.selectProduct(layero); //重点2
                }
            });
            return false;
        });
    });

</script>
