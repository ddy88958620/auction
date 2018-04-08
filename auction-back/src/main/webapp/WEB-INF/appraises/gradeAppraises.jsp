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
    <script src="<%=common%>/system/qrcode.min.js" charset="utf-8"></script>
</head>
<body>
<center>
<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" >
    <input type="hidden" id="id" name="id" value="${id}">
    <input type="hidden" id="content" name="content" value="${content}">
    <input type="hidden" id="level" name="level" value="${level}">
    <input type="hidden" id="valueArray" name="valueArray" value="${valueArray}">
    <input type="hidden" id="isShow" name="isShow" value="${isShow}">
    <div class="layui-form-item" style="width: 550px;padding-top: 20px;">
        <label class="layui-form-label" style="width:150px" >奖励级别：</label>
        <div class="layui-input-inline" style="width: 190px;">
            <select  id="appraisesLevel" class="layui-input" name="appraisesLevel" lay-verify="required" lay-filter="appraisesLevel"  autocomplete="off" >
                <c:forEach var="item" items="${appraisesLevelList}">
                    <option id="${item.appraisesLevel}" value="${item.appraisesLevel}" <c:if test="${item.appraisesLevel eq level}">selected="selected"</c:if>>${item.appraisesLevel}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item" style="width: 550px;padding-top: 5px;">
        <label class="layui-form-label" style="width:150px">基础奖励(积分)： </label>
        <div class="layui-input-inline" >
            <input type="text" unselectable="on"  style="width: 200px" class="layui-input" readonly="readonly" id="baseRewords" name="baseRewords" value="${baseRewords }"/>
        </div>
        <font size="2"  >选择奖励级别后该项自动填充</font>
    </div>
    <div class="layui-form-item" style="width: 550px;padding-top: 5px;">
        <label class="layui-form-label" style="width:150px">出镜奖励(赠币)：</label>
        <div class="layui-input-inline" >
            <input type="text" unselectable="on" style="width: 200px" class="layui-input" lay-verify="showRewords" id="showRewords" name="showRewords" value="${showRewords}"/>
        </div>
        <font size="2" color="red">注：整数，最大5赠币
         </font>
    </div>

    <div class="layui-form-item" >
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="check1">确定</button>
            <button type="reset" lay-submit class="layui-btn" >重置</button>
        </div>
    </div>
</form>
</center>
    <script src="<%=common%>/system/btns.js" charset="utf-8"></script>

<script>
    var baseRewords = $("#baseRewords").val();
    var showRewords = "";
    var appraisesLevel="";
    var valueArray="";
    var isShow ="";
    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }
    var layer;
    layui.use(['form','layer'], function() {

        var form = layui.form;
        var id = $("#id").val();
        var content = $("#content").val();
        //自定义验证规则
        form.verify({
            showRewords:function(value) {
                if (value != null && value != '') {
                    var integer = new RegExp("^([1-9]\\d*|[0]{1,1})$");
                    if (!integer.test(value)) {
                        $("#showRewords").val("");
                        return "只能是整数!";
                    }
                }
                var show = $("#showRewords").val();
                if (parseInt(show) > 5) {
                    $("#showRewords").val("");
                    return "出镜奖励积分不能大于5，请重新输入";
                }else if (parseInt(show) < 0) {
                    $("#showRewords").val("");
                    return "出镜奖励积分小于0，请重新输入";
                }
            }
        });

        form.on('submit(check1)', function(data) {
            showRewords = $("#showRewords").val();
            baseRewords = $("#baseRewords").val();
            appraisesLevel = $("#appraisesLevel").val();
            valueArray = $("#valueArray").val();
            isShow = $("#isShow").val();
            $.ajax({
                type : "post",
                url : "${path }/appraises/orderAppraisesCheck",
                 data:{ "id":id,"content":content,"baseRewords":baseRewords,"showRewords":showRewords,"appraisesLevel":appraisesLevel,"valueArray":valueArray,"isShow":isShow},
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
                            parent.parent.layer.closeAll();
                            parent.parent.window.refreshPage();
                        } else {
                            layer.error("系统错误！");
                        }
                    });
                }
            });
            return false;
        });

        form.on('select(appraisesLevel)', function(data) {
            var appraisesLevel =   $("#appraisesLevel").val();
            $.ajax({
                type : "post",
                url : "${path }/appraises/linkage",
                data:{ "appraisesLevel":appraisesLevel},
                dataType : "json",
                beforeSend : function() {
                    layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });
                },
                success : function(result) {
                    $("#baseRewords").val(result.baseRewords);
                }
            });
        });
    });

</script>
</body>
</html>