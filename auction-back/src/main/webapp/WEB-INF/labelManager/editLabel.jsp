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
    <script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form" style="margin-left: 30px">
    <input type="hidden" id="id" name="id" value="${labelManager.id}">
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px;margin-top: 30px">
            <label class="layui-form-label">模板名称:</label>
            <div class="layui-input-inline" style="width: 30%;">
                <input id="labelName" name="labelName" type="text" unselectable="on" class="layui-input" value="${labelManager.labelName}"  />
            </div>
        </div>
    </div>
    <div class="layui-upload" style="margin-left: 95px;margin-top: 30px">
        <label class="layui-form-label">上传标签样式:</label>
        <button type="button" class="layui-btn" id="upload">上传</button>
    </div>
    <div class="layui-form-item" style="margin-left: 95px;margin-top: 30px">
        <label class="layui-form-label">预览图:</label>
        <div id="mutiPic">
           <img id="editImg" src="<%=photoPathSuffix%>/${labelManager.labelPic}"  class="layui-upload-img" style="width:50px;height:50px">
        </div>
        <input type="hidden" id="labelPic" name="labelPic" value="${labelManager.labelPic}"/>
        <p id="labelText"></p>
    </div>
    <div class="layui-form-item" style="margin-left: 95px;margin-top: 30px">
        <label class="layui-form-label">启用/禁用:</label>
        <div class="layui-input-block">
            <input type="radio" name="labelStatus" value="0" title="启用" <c:if test="${labelManager.labelStatus ==0}">checked="checked"</c:if>>
            <input type="radio" name="labelStatus" value="1" title="禁用" <c:if test="${labelManager.labelStatus ==1}">checked="checked"</c:if>>
        </div>
    </div>

    <div class="layui-form-item" style="margin-top: 50px;margin-left: 195px">
        <div class="layui-input-block">
            <button type="submit" lay-submit class="layui-btn" lay-filter="cancle">取消</button>
            <button type="submit" lay-submit class="layui-btn" lay-filter="confirm">确定</button>
        </div>
    </div>
</form>
<script>
    var layer;
    layui.use(['form','layer','upload'], function() {
        var form = layui.form,
            upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#upload'
            ,url: '${path}/upload/uploadFiles'
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    var vue =  "<%=photoPathSuffix%>"+"/"+result;
//                    $("#editImg").attr("src",vue);
                    $("#editImg").css({"display":"none"});
                    $('#mutiPic').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width:50px;height:50px">')
                });
            }
            ,done: function(res){
                if (res.code == 0) {
                    $('input[name="labelPic').attr('value',res.data.src);
                } else {
                    return layer.msg('上传失败');
                }
            }
            ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });

        form.on('submit(confirm)', function(data) {
            var labelPic = $("#labelPic").val();
            var labelStatus = $('input[name="labelStatus"]:checked ').val();
            var labelName = $("#labelName").val();
            var id = $("#id").val();
            $.ajax({
                type : "post",
                url : "${path}/labelManager/editSuccess",
                data : {'id':id,'labelPic':labelPic,'labelStatus':labelStatus,'labelName':labelName},
                dataType : "json",
                success : function(result) {
                    layer.msg(result.msg, {
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

        form.on('submit(cancle)', function(data) {
            parent.layer.closeAll('iframe');
            parent.window.refreshPage();
        });
    });

</script>
</body>
</html>
