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
<form class="layui-form  layui-form-pane1 pzjzsj" method="POST">
    <div class="layui-form-item">
    </div>
    <input type="hidden"  name="id" class="layui-input"
           autocomplete="off" placeholder="0"/>
    <div class="layui-form-item">
        <label class="layui-form-label">机器人名称</label>
            <div class="layui-input-block" style="width:30%">
                        <input id="name" lay-verify="required" name="name" class="layui-input"
                                  autocomplete="off" placeholder="">
            </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">机器人地址</label>
            <div class="layui-input-block" style="width:30%">
                <input type="text" lay-verify="required" name="address" class="layui-input"
                       autocomplete="off" placeholder="0"/>
            </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">机器人状态</label>
            <div class="layui-input-inline" style="width:30%">
                <select name="status">
                    <option value='2' <c:if test="${params.status eq '2'}"> </c:if>>启用
                    </option>
                    <option value='1' <c:if test="${params.status eq '1'}" > </c:if>>停用</option>
                </select>
            </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">机器人使用次数</label>
            <div class="layui-input-inline" style="width:30%">
                <input type="numbers" lay-verify="required" name="numbers" class="layui-input"
                       autocomplete="off" placeholder="0"/>
            </div>
    </div>

    <div class="layui-upload">
        <label class="layui-form-label">上传头像</label>
        <button type="button" class="layui-btn" id="test1">机器人头像</button>
        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
            预览图：
            <div class="layui-upload-list"><img id="demo1" class="layui-upload-img" style="width: 100px;height: 150px;"></div>
            <input type="hidden" id="headImg" name="headImg"/>
            <p id="demoText"></p>
        </blockquote>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
</form>
<script>
    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '${path}/upload/uploadFiles'
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                //如果上传失败
                if(res.code == 0){
                    $('input[name="headImg').attr('value',res.data.src); //图片链接（base64）
                }else{
                    return layer.msg('上传失败');
                }
                //上传成功
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
    });

</script>
<script>
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }


    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

//时间选择器
        laydate.render({
            elem: '#test5'
            , type: 'datetime'
        });

        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                type: "post",
                url: "${path }/robot/saveRobot",
                data: data.field,
                dataType: "json",
                beforeSend: function () {
                    layer.load(1, {
                        shade: [0.1, '#fff']
                    });
                },
                success: function (result) {
                    layer.msg(result.msg, {
                        time: 1000
                    }, function () {
                        if (result.code == '0') {
                            parent.layer.closeAll('iframe');
                            parent.window.refreshPage();
                        }
                    });
                }
            });
            return false;
        });
    });

</script>
</body>
