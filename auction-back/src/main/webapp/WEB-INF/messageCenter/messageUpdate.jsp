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
</head>
<body>

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" style="margin-left: 550px;">
    <input type="hidden" value="${messageInfo.id}" name="id">

    <fieldset class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 800px;">
        <legend style="margin-bottom: 20px;"><strong>修改消息信息</strong></legend>

        <div class="layui-form-item">
            <label class="layui-form-label">所属分类：</label>
            <div class="layui-input-block" style="width: 100px;">
                <select class="layui-input" name="channelType" id="channelType" autocomplete="off">
                    <c:forEach var="item" items="${messageTypeList}">
                        <option id="${item.help_type}" value="${item.help_type}" <c:if test="${item.help_type eq messageInfo.channelType}">selected="selected"</c:if>>${item.help_str}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">消息排序：</label>
            <div class="layui-input-inline">
                <input type="text" name="orderNum" lay-verify="required|integer" placeholder="请输入排序序号"
                       autocomplete="off"
                       class="layui-input" value="${messageInfo.orderNum}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">外部链接：</label>
            <div class="layui-input-inline" style="width: 30%">
                <input type="text" name="externalUrl" placeholder="请输入外部链接"
                       autocomplete="off"
                       class="layui-input" value="${messageInfo.externalUrl}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">内容标题：</label>
            <div class="layui-input-block" style="width: 30%">
                <input type="text" name="contentTitle" lay-verify="required|contentTitle"
                       placeholder="请输入内容标题"
                       autocomplete="off"
                       class="layui-input" value="${messageInfo.contentTitle}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">内容摘要：</label>
            <div class="layui-input-block" style="width: 30%">
                <textarea style="width: 500px;height: 100px;" lay-verify="required|contentSummary"
                          class="layui-input" autocomplete="off" id="contentSummary" name="contentSummary"
                          placeholder="请输入内容摘要">${messageInfo.contentSummary}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">图片：</label>
            <div class="layui-input-block">
                <div class="layui-upload">
                    <button type="button" class="layui-btn" id="upHeadPage">图片上传</button>
                    <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                        预览：
                        <img class="layui-upload-img" src="<%=photoPathSuffix%>/${messageInfo.imgUrl}"
                             style="width: 100px; height: 100px;" id="imgUrlSl" name="imgUrlSl"/>
                        <input type="hidden" id="imgUrl" name="imgUrl" value="${messageInfo.imgUrl}"/>
                        <p id="upHeadTxt"></p>
                    </blockquote>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">内容：</label>
            <div class="layui-input-inline" style="width: 700px;">
                <textarea id="contentTxt" name="contentTxt" style="display: none;">${messageInfo.contentTxt}
                </textarea>
            </div>
        </div>
        <button style="display: none;" lay-filter="submitBtn" lay-submit="">
        </button>
    </fieldset>
</form>

<script>
    var layedit;
    var content;
    function resetForm() {
        $("#imgUrlSl").attr("src","<%=photoPathSuffix%>/${messageInfo.imgUrl}");
        $("#imgUrl").val("${messageInfo.imgUrl}");
        content = layedit.build('contentTxt');
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    layui.use(['form','upload','layedit'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;
        layedit = layui.layedit;

        layedit.set({
            uploadImage: {
                url: '${path}/upload/uploadFiles?uploadType="richText"' //接口url
                ,type: 'post' //默认post
            }
        });
        content = layedit.build('contentTxt'); //建立编辑器

        //自定义验证规则
        form.verify({
            integer: function (value) {
                layedit.sync(content);
                if (value.length > 11 ) {
                    return '排序序号长度不能超过11位';
                }
                var integer = new RegExp("^\\d+$");
                if (!integer.test(value)) {
                    return "只能是整数!";
                }
            },
            contentTitle : function(value) {
                if (value.length > 100) {
                    return '内容标题长度不能超过100位';
                }
            },
            contentSummary : function(value) {
                if (value.length > 5000) {
                    return '内容摘要长度不能超过5000位';
                }
            }
        });

        form.on('submit(submitBtn)', function (data) {

            $.post("updateMessageCenter", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("修改成功！");
                        parent.layer.closeAll('iframe');
                        parent.window.refreshPage();
                    } else {
                        layer.error("修改失败！");
                    }
                }, "json");
            return false;
        });

        //普通图片上传
        upload.render({
            elem: '#upHeadPage'
            , url: '${path}/upload/uploadFiles'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#imgUrlSl').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code == 0) {
                    $('input[name="imgUrl').attr('value',res.data.src); //图片链接（base64）
                } else {
                    return layer.msg('上传失败');
                }
                //上传成功
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#upHeadTxt');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    });
</script>
</body>
</html>
