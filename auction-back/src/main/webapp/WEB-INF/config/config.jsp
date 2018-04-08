<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form " action="" id="form">
    <c:forEach items="${list}" var="cfg" varStatus="count">
        <input type="hidden" name="list[${count.index }].id" value="${cfg.id }"/>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 120px;">${cfg.sysName}</label>
            <c:choose>
                <c:when test="${cfg.inputType == 'textarea' }">
                    <div class="layui-input-block" style="margin-left:150px;">
                        <textarea ${cfg.limitCode } id="configTxt${cfg.id }" style="display: none;"
                                                    name="list[${count.index }].sysValueBig">${cfg.sysValueBig }</textarea>
                        <script>
                            layui.use(['form','layedit'], function () {
                                var form = layui.form;
                                var layedit = layui.layedit;
                                layedit.set({
                                    uploadImage: {
                                        url: '${path}/uploadFiles'
                                    }
                                });
                                var editIndex = layedit.build('configTxt${cfg.id }'); //建立编辑器
                                form.verify({
                                    content${cfg.id }: function (value) {
                                        return layedit.sync(editIndex);
                                    }
                                });
                            });
                        </script>
                    </div>
                </c:when>
                <c:when test="${cfg.inputType == 'textdomain' }">
                    <div class="layui-input-block" style="margin-left:150px;">
                        <textarea ${cfg.limitCode } id="configTxt${cfg.id }"
                                                    name="list[${count.index }].sysValueBig" class="layui-textarea">${cfg.sysValueBig }</textarea>
                    </div>
                </c:when>
                <c:when test="${cfg.inputType == 'password' }">
                    <div class="layui-input-inline" style="width: 300px;">
                        <input type="password" ${cfg.limitCode } name="list[${count.index }].sysValue"
                               value="${cfg.sysValue }" class="layui-input"/>
                    </div>
                    <div class="layui-form-mid layui-word-aux">${cfg.remark}</div>
                </c:when>
                <c:when test="${cfg.inputType == 'image' }">
                    <div class="layui-input-inline" style="width: 300px;">
                        <div class="layui-upload">
                            <button type="button" class="layui-btn" id="up${cfg.id}">上传图片</button>
                            <div class="layui-upload-list">
                                <img class="layui-upload-img" id="img${cfg.id }" src="<%=path%>${cfg.sysValue}">
                                <input type="hidden" name="list[${count.index }].sysValue" value="${cfg.sysValue }"/>
                                <p id="upTxt${cfg.id }"></p>
                            </div>
                        </div>
                    </div>
                    <script>
                        layui.use('upload', function () {
                            var upload = layui.upload;
                            //普通图片上传
                            var uploadInst = upload.render({
                                elem: '#up${cfg.id }'
                                , url: '${path}/uploadFiles'
                                , before: function (obj) {
                                    //预读本地文件示例，不支持ie8
                                    obj.preview(function (index, file, result) {
                                        $('#img${cfg.id }').attr('src', result); //图片链接（base64）
                                    });
                                }
                                , done: function (res) {
                                    //如果上传失败
                                    if (res.code == 0) {
                                        $('input[name="list[${count.index }].sysValue"]').attr('value', res.data.src); //图片链接（base64）
                                    } else {
                                        return layer.msg('上传失败');
                                    }
                                    //上传成功
                                }
                                , error: function () {
                                    //演示失败状态，并实现重传
                                    var demoText = $('#upTxt${cfg.id }');
                                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                                    demoText.find('.demo-reload').on('click', function () {
                                        uploadInst.upload();
                                    });
                                }
                            });
                        });
                    </script>
                    <div class="layui-form-mid layui-word-aux">${cfg.remark}</div>
                </c:when>
                <c:otherwise>
                    <div class="layui-input-inline" style="width: 300px;">
                        <input type="text" ${cfg.limitCode } name="list[${count.index }].sysValue"
                               value="${cfg.sysValue }" class="layui-input"/>
                    </div>
                    <div class="layui-form-mid layui-word-aux">${cfg.remark}</div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="submitBtn">提交</button>
        </div>
    </div>
</form>
<script>
    layui.use(['form', 'layedit'], function () {
        var form = layui.form;
        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                type: "post",
                url: "${path }${url}",
                data: $("#form").serialize(),
                dataType: "json",
                beforeSend: function () {
                    layer.load(1, {
                        shade: [0.1, '#fff']
                    });
                },
                success: function (result) {
                    layer.closeAll('loading');
                    layer.msg(result.msg);
                }
            });
            return false;
        });
    });
</script>
</body>
</html>