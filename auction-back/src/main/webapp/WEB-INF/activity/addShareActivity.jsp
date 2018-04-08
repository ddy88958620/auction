<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    String path = request.getContextPath() + "";
    String common = path + "/resources/";
    String photoPathSuffix = PropertiesUtils.get("aliyun.oos.url");
%>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>${website.site_name }</title>
    <c:set var="path" value="<%=path%>"/>
    <c:set var="photoPathSuffix" value="<%=photoPathSuffix%>"/>
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
    <style>
        .layui-form-label {
            width: 100px;
        }

        .layui-input-block {
            margin-left: 130px;
        }
    </style>
</head>
<body>

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" style="margin-left: 20px;margin-top: 20px;">
    <div class="layui-form-item">
        <input type="hidden" value="${activityId}" name="activityId">
        <label class="layui-form-label">分享入口</label>
        <div class="layui-input-block" style="width: 30%">
            <select name="shareEntrance" lay-verify="required">
                <c:forEach var="entrance" items="${entrances}">
                    <option value="${entrance.key}">
                            ${entrance.value.desc}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">活动名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="activityName" lay-verify="required"
                   placeholder="请输入活动名称" autocomplete="off" class="layui-input" maxlength="20">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">活动时间范围</label>
        <div class="layui-input-inline">
            <input id="startTime" type="text" name="startDate" lay-verify="required"
                   placeholder="请输入开始时间" autocomplete="off" class="layui-input">
        </div>

        <div class="layui-form-mid">至</div>
        <div class="layui-input-inline">
            <input id="endTime" type="text" name="endDate" lay-verify="required"
                   placeholder="请输入结束时间" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">链接地址</label>
        <div class="layui-input-inline">
            <input type="text" name="activityUrl" lay-verify="required"
                   placeholder="请输入活动链接" autocomplete="off" class="layui-input" style="width:415px">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图片：</label>
        <div class="layui-input-block">
            <div class="layui-upload">
                <button type="button" class="layui-btn" id="upHeadPage">图片上传</button>
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;display: none"
                            id="picDiv">
                    预览：
                    <img class="layui-upload-img"
                         style="width: 100px; height: 100px;" id="imgUrlSl" name="imgUrlSl"/>
                    <input type="hidden" id="imgUrl" name="picUrl"/>
                    <p id="upHeadTxt"></p>
                </blockquote>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">分享标题：</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" placeholder="请输入标题"
                   autocomplete="off" class="layui-input" maxlength="12">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">分享副标题：</label>
        <div class="layui-input-inline">
            <input type="text" name="subTitle" lay-verify="required" placeholder="请输入副标题"
                   autocomplete="off" class="layui-input" maxlength="25" style="width:415px">
        </div>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">分享者奖励设置：</label>
        <div class="layui-input-inline">
            <input type="text" name="sharerPoints" lay-verify="required|integer" placeholder="请输入积分"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">积分</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="text" name="sharerCoin" lay-verify="required|integer" placeholder="请输入赠币"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">赠币</div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">新注册奖励设置：</label>
        <div class="layui-input-inline">
            <input type="text" name="registerPoints" lay-verify="required|integer" placeholder="请输入积分"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">积分</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="text"id="registerCoin1" lay-verify="required|integer" placeholder="请输入赠币"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid">-</div>
        <div class="layui-input-inline">
            <input id="registerCoin2" type="text" lay-verify="required" lay-verify="required|integer"
                   placeholder="请输入赠币" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">赠币</div>
    </div>

    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    $(function () {
        var activityId = $('input[name="activityId');
        $('input[name="activityUrl')
    });
    layui.use(['form', 'upload', 'laydate'], function () {
        var $ = layui.$;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        laydate = layui.laydate;
        var now = new Date();
        var start = now.getTime();
        laydate.render({
            elem: '#startTime',
            type: 'datetime',
            min: start
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime'
        });
        //自定义验证规则
        form.verify({
            integer: function (value) {
                if (value.length > 11) {
                    return '排序序号长度不能超过11位';
                } else if (value < 0) {
                    return "请输入有效数字！";
                }
                if (value > 20) {
                    return "最大值20！";
                }
                var integer = new RegExp("^\\d+$");
                if (!integer.test(value)) {
                    return "只能是整数!";
                }
            }, double: function (value) {
                if (value > 100) {
                    return '概率不能大于100%';
                } else if (value < 0) {
                    return "请输入有效数字！";
                }
                var double = new RegExp("^\\d+(?:\\.\\d{1,4})?$");
                if (!double.test(value)) {
                    return "请输入最多保留4位小数的数字";
                }
            }
        });

        form.on('submit(submitBtn)', function (data) {
            var activityId = $('input[name="activityId').val();
            var url = $('input[name="activityUrl').val();
            data.field.activityUrl = url + '?activityId=' + activityId;
            data.field.registerCoin = $('#registerCoin1').val()+'-'+$('#registerCoin2').val();
            $.post("addShareActivity", data.field,
                function (data) {
                    console.log(data.field);
                    if (data.code == '0') {
                        parent.layer.msg("添加成功！");
                        parent.layer.closeAll('iframe');
                        parent.table.reload('search');
                    } else {
                        layer.msg("添加失败！");
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
                    $('#picDiv').show();
                    $('input[name="picUrl').attr('value', res.data.src); //图片链接（base64）
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

    function resetForm() {
        $("#imgUrlSl").removeAttr("src");
        $("#picUrl").val("");
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    function resetProduct() {
        $("#productId").val("");
        $("#productName").val("");
        $("#productPicSl").removeAttr("src");
        $("#productPic").val("");
    }
</script>
</body>
</html>
