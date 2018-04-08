<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
    <script src="<%=common%>/system/qrcode.min.js" charset="utf-8"></script>
</head>
<body>

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" style="margin-left: 500px;">

    <fieldset class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 1000px;">
        <legend style="margin-bottom: 20px;"><strong>修改评级规则信息</strong></legend>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 120px">ID:</label>
            <div class="layui-input-inline">
                <input type="text" name="id" lay-verify="required" value="${orderAppraisesRules.id}"
                       autocomplete="off" unselectable="on" readonly="readonly" id="id"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"  style="width: 120px">评论级别:</label>
            <div class="layui-input-inline">
                <input type="text" name="appraisesLevel" id="appraisesLevel" lay-verify="required|appraisesLevel" placeholder="请输入评论级别"
                       autocomplete="off" readonly="readonly"
                       class="layui-input" value="${orderAppraisesRules.appraisesLevel}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"  style="width: 120px">评论字数范围:</label>
            <div class="layui-input-inline"  style="width: 100px;">
                <input name="minAppraisesWords" placeholder="最小值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text"
                       value="${(orderAppraisesRules.appraisesWords.split("-"))[0]}" readonly="readonly">
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline" style="width: 100px;">
                <input name="maxAppraisesWords" placeholder="最大值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text"
                       value="${(orderAppraisesRules.appraisesWords.split("-"))[1]}" readonly="readonly">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"  style="width: 120px">图片数量范围:</label>
            <div class="layui-input-inline"  style="width: 100px;">
                <input name="minPicNum" placeholder="最小值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text"
                       value="${(orderAppraisesRules.picNumber.split("-"))[0]}" readonly="readonly">
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline" style="width: 100px;">
                <input name="maxPicNum" placeholder="最大值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text"
                       value="${(orderAppraisesRules.picNumber.split("-"))[1]}" readonly="readonly">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 120px">基础奖励(积分):</label>
            <div class="layui-input-inline">
                <input type="text" name="baseRewords" lay-verify="required|integer|base" placeholder="请输入基础奖励" autocomplete="off" class="layui-input"
                       value="${orderAppraisesRules.baseRewords}" />
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 120px">出镜奖励(赠币):</label>
            <div class="layui-input-inline">
                <input type="text" name="showRewords" id="showRewords" lay-verify="integer|show" placeholder="请输入出镜奖励" autocomplete="off" class="layui-input"
                       value="${orderAppraisesRules.showRewords}"/>
            </div>
        </div>
        <div class="layui-form-item" style="margin-top: 40px;">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn"  lay-submit lay-filter="check1">提交</button>
                <button type="reset"  class="layui-btn" lay-submit lay-filter="reset" onclick="resetForm()">重置</button>
                <button type="text"  class="layui-btn" lay-submit lay-filter="close" onclick="closeForm()">返回</button>
            </div>
        </div>
        <button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
    </fieldset>
</form>

<script>
    function resetForm() {
        document.getElementById("form").reset();
        return false;
    }
    function closeForm() {
        parent.layer.closeAll('iframe');
        return false;
    }
    $.ajaxSetup({
        async: false
    });
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    layui.use(['form','upload'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;
        layedit = layui.layedit;

        //自定义验证规则
        form.verify({
            appraisesLevel:function (value) {
                var reg=/^[a-zA-Z]*$/;
                if(!reg.test(value)) {
                    return "只能输入字母！";
                }
                $("#appraisesLevel")[0].value=value.toUpperCase();
            },
            integer: function (value) {
                if(value != null && value != '' ) {
                    var integer = new RegExp("^([1-9]\\d*|[0]{1,1})$");
                    if (!integer.test(value)) {
                        //$("#showRewords").val("");
                        return "只能是正整数，请重新输入!";
                    }
                }
            },
            show:function(value){
                var show=parseInt($("#showRewords").val());
                if (show>5){
                    //$("#showRewords").val("");
                    return "出镜奖励积分不大于5，请重新输入";
                }
            },
            base:function(value){
                if (value>500){
                   // $("#baseRewords").val("");
                    return "基础奖励最高500积分,请重新输入!";
                }
            }
        });

        form.on('submit(check1)', function (data) {

            $.post("updateAppraisesRules", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("修改成功！");
                        parent.layer.closeAll('iframe');
                        parent.window.refreshPage();
                    } else {
                        parent.layer.msg("修改失败！"+data.msg);
                    }
                }, "json");
            return false;
        });

    });
    function resetForm() {
        document.getElementById("form").reset();
    }

</script>
</body>
</html>
