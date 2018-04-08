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
        .layui-form-label {width: 100px;}
        .layui-input-block {margin-left: 130px;}
    </style>
</head>
<body>

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" style="margin-left: 20px;margin-top: 20px;">
    <div class="layui-form-item">
        <label class="layui-form-label">奖品名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="prizeName" lay-verify="required"
                   placeholder="请输入奖品名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图片：</label>
        <div class="layui-input-block">
            <div class="layui-upload">
                <button type="button" class="layui-btn" id="upHeadPage">图片上传</button>
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                    预览：
                    <img class="layui-upload-img"
                         style="width: 100px; height: 100px;" id="imgUrlSl" name="imgUrlSl"/>
                    <input type="hidden" id="imgUrl" name="prizePic"/>
                    <p id="upHeadTxt"></p>
                </blockquote>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">奖品库存：</label>
        <div class="layui-input-inline">
            <input type="text" name="store" lay-verify="required|integer" placeholder="请输入奖品库存"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">中奖概率：</label>
        <div class="layui-input-inline">
            <input type="text" name="prizeRate" lay-verify="required|double" placeholder="请输入中奖概率"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid">%</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">奖品类型：</label>
        <div class="layui-input-inline">
            <select class="layui-input" name="prizeType" id="prizeType" autocomplete="off" lay-filter="prizeType">
                <option value="">请选择</option>
                <c:forEach var="item" items="${allType}">
                    <option value="${item.key}">${item.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="layui-input-inline">
            <select class="layui-input" name="prizeTypeSub" id="prizeTypeSub" lay-filter="prizeTypeSub"></select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">数量：</label>
        <div class="layui-input-inline">
            <input type="text" name="amount" lay-verify="required|integer" placeholder="请输入数量"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div id="buyCoinTypeDiv" style="display: none;">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 100px;">购物币类型：</label>
            <div class="layui-input-inline">
                <select class="layui-input" name="buyCoinType" id="buyCoinType" lay-filter="buyCoinType">
                    <option value="">请选择</option>
                    <c:forEach var="item" items="${allBuyCoinType}">
                        <option value="${item.key}">${item.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div id="productDiv" style="display: none;">
        <div class="layui-form-item">
            <label class="layui-form-label">商品id：</label>
            <div class="layui-input-inline">
                <input type="text" id="productId" name="productId" placeholder="请输入商品id"
                       autocomplete="off" class="layui-input">
            </div>
            <div class="layui-input-inline">
                <input type="button" class="layui-btn" id="productSearch" value="查询"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称：</label>
            <div class="layui-input-inline">
                <input type="text" id="productName" name="productName"
                       autocomplete="off" class="layui-input" readonly>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品图片：</label>
            <div class="layui-input-inline">
                <img class="layui-upload-img" style="width: 100px; height: 100px;" id="productPicSl" name="productPicSl"/>
                <input type="hidden" id="productPic" name="productPic"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-block" style="width: 30%">
            <textarea style="width: 500px;height: 100px;" class="layui-input" autocomplete="off"
                      id="remark" name="remark" placeholder="请输入备注"></textarea>
        </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    layui.use(['form','upload'], function () {
        var $ = layui.$;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        //自定义验证规则
        form.verify({
            integer: function (value) {
                if (value.length > 11 ) {
                    return '排序序号长度不能超过11位';
                } else if (value<0) {
                    return "请输入有效数字！";
                }
                var integer = new RegExp("^\\d+$");
                if (!integer.test(value)) {
                    return "只能是整数!";
                }
            },double:function (value) {
                if (value > 100 ) {
                    return '概率不能大于100%';
                } else if (value<0) {
                    return "请输入有效数字！";
                }
                var double = new RegExp("^\\d+(?:\\.\\d{1,4})?$");
                if (!double.test(value)) {
                    return "请输入最多保留4位小数的数字";
                }
            }
        });

        form.on('submit(submitBtn)', function (data) {
            $.post("add", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("添加成功！");
                        parent.layer.closeAll('iframe');
                        parent.table.reload('search');
                    } else {
                        layer.error("添加失败！");
                    }
                }, "json");
            return false;
        });

        form.on('select(prizeType)', function(data){
            $("#prizeTypeSub option").remove();
            var optionHtml = "<option value=''>请选择</option>";
            $("#prizeTypeSub").append(optionHtml);
            var subType = null;
            if (data.value==100) {
                subType = eval('${goodsTypeArray}');
            } else if (data.value==200) {
                subType = eval('${coinsTypeArray}');
            } else if (data.value==300) {
                subType = eval('${cdkeysTypeArray}');
            }
            for (var obj in subType) {
                optionHtml = "<option value='" + subType[obj].key + "'>" + subType[obj].value + "</option>";
                $("#prizeTypeSub").append(optionHtml);
            }
            $("#buyCoinTypeDiv").hide();
            $("#buyCoinType").val("");
            $("#productDiv").hide();
            resetProduct();
            form.render('select');
        });

        form.on('select(prizeTypeSub)',function(data){
            if (data.value==204) {
                $("#buyCoinTypeDiv").show();
            } else {
                $("#buyCoinTypeDiv").hide();
                $("#buyCoinType").val("");
                form.render('select');
                $("#productDiv").hide();
                resetProduct();
            }
        });

        form.on('select(buyCoinType)',function(data){
            if (data.value=='1') {
                $("#productDiv").show();
            } else {
                $("#productDiv").hide();
                resetProduct();
            }
        });

        $("#productSearch").on("click",function(){
            var productId = $("#productId").val();
            $.post("${path}/lotteryPrize/searchProduct", {productId:productId}, function (result) {
                if (result.code == '0') {
                    $("#productName").val(result.data.productName);
                    $("#productPicSl").attr("src", '${photoPathSuffix}'+result.data.picUrl);
                    $("#productPic").val(result.data.picUrl);
                } else {
                    layer.msg("商品不存在！");
                }
            }, "json");
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
                    $('input[name="prizePic').attr('value',res.data.src); //图片链接（base64）
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
        $("#prizePic").val("");
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
