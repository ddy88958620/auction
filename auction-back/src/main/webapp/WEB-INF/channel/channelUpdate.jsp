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
    <input type="hidden" value="${channelInfo.id}" name="id">

    <fieldset class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 1000px;">
        <legend style="margin-bottom: 20px;"><strong>修改推广渠道信息</strong></legend>

        <div class="layui-form-item">
            <label class="layui-form-label">渠道ID：</label>
            <div class="layui-input-inline">
                <input type="text" name="channelId" lay-verify="required" value="${channelInfo.channelId}"
                       autocomplete="off" unselectable="on" readonly="readonly" id="channelId"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">渠道名称：</label>
            <div class="layui-input-inline">
                <input type="text" name="channelName" lay-verify="required|channelName" placeholder="请输入渠道名称"
                       autocomplete="off"
                       class="layui-input" value="${channelInfo.channelName}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">省市地区：</label>
            <div class="layui-inline" style="width: 150px;">
                <select class="layui-input" name="provinceCode" id="provinceCode" lay-verify="provinceCode" lay-filter="provinceCode">
                    <option value="-999">请选择</option>
                    <c:forEach var="item" items="${provinceList}">
                        <option value="${item.id}">${item.addressName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-inline" class="layui-inline" style="width: 150px;">
                <select class="layui-input" name="cityCode" id="cityCode" lay-verify="cityCode" lay-filter="cityCode" autocomplete="off">
                    <option value="-999">请选择</option>
                </select>
            </div>
            <div class="layui-inline" class="layui-inline" style="width: 150px;">
                <select class="layui-input" name="townCode" id="townCode" lay-verify="townCode" lay-filter="townCode" autocomplete="off">
                    <option value="-999">请选择</option>
                </select>
            </div>
            <input type="hidden" name="provinceName" id="provinceName" value="${channelInfo.provinceName}">
            <input type="hidden" name="cityName" id="cityName" value="${channelInfo.cityName}">
            <input type="hidden" name="townName" id="townName" value="${channelInfo.townName}">
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">合作方式：</label>
            <div class="layui-input-inline" style="width: 30%">
                <select id="cooperationMode" class="layui-input" name="cooperationMode" lay-verify="required" lay-filter="cooperationMode" autocomplete="off">
                    <c:forEach var="item" items="${cooperationModeList}">
                        <option id="${item.cooperation_mode}" value="${item.cooperation_mode}" <c:if test="${item.cooperation_mode eq channelInfo.cooperationMode}">selected="selected"</c:if>>${item.cooperation_str}</option>
                    </c:forEach>
                </select>
                </div>
        </div>
        <div>
            <div id="channelSource" class="layui-form-item" style="display: none">
                <label class="layui-form-label">渠道来源：</label>
                <div class="layui-input-inline">
                    <input type="text" name="channelSource" placeholder="请输入渠道名称"
                           autocomplete="off"
                           class="layui-input" value="${channelInfo.channelSource}">
                </div>
            </div>
            <div id="description" class="layui-form-item" style="display: none">
                <label class="layui-form-label">渠道描述：</label>
                <div class="layui-input-block" style="width: 30%">
                    <textarea style="width: 500px;height: 50px;"
                              class="layui-input" autocomplete="off"  name="description"
                              placeholder="请输入渠道描述">${channelInfo.description}</textarea>
                </div>
            </div>
        </div>

       <%-- <div class="layui-form-item">
            <label class="layui-form-label">结算方式：</label>
            <div class="layui-input-block" style="width: 30%">
                <input type="text" name="settlementMode" lay-verify="required|settlementMode"
                       placeholder="请输入结算方式" autocomplete="off"
                       class="layui-input" value="${channelInfo.settlementMode}">
            </div>
        </div>--%>

        <div class="layui-form-item">
            <label class="layui-form-label">结算单价：</label>
            <div class="layui-input-inline" style="width: 30%">
                <input type="text" name="settlementPrice" lay-verify="required|settlementPrice"
                       placeholder="请输入结算单价" autocomplete="off" onkeyup="num(this)" size="10"
                       class="layui-input" value="${channelInfo.settlementPrice*1.0/100}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">开始时间：</label>
            <div class="layui-input-block" style="width: 30%">
                <input class="layui-input" id="startTime" name="startTime"  placeholder="请输入开始时间" lay-key="1" autocomplete="off"
                       value="${channelInfo.startTime}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">负责人：</label>
            <div class="layui-input-block" style="width: 100px;">
                <input lay-verify="required|personInCharge"
                       class="layui-input" autocomplete="off" id="personInCharge" name="personInCharge"
                       placeholder="请输入负责人" value="${channelInfo.personInCharge}">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">对接人：</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input lay-verify="required|pickUp"
                           class="layui-input" autocomplete="off" id="pickUp" name="pickUp"
                           placeholder="请输入对接人" value="${channelInfo.pickUp}">
                </div>
            </div>
            <div class="layui-inline" style="margin-left: -40px;">
                <label class="layui-form-label">联系方式：</label>
                <div class="layui-input-inline" style="width: 200px;">
                    <input lay-verify="required|phone"
                           class="layui-input" autocomplete="off" id="contactPhone" name="contactPhone"
                           placeholder="请输入联系方式" value="${channelInfo.contactPhone}">
                </div>
            </div>

            <div class="layui-inline" style="margin-left: -40px;">
                <label class="layui-form-label">联系邮箱：</label>
                <div class="layui-input-inline" style="width: 200px;">
                    <input lay-verify="required|email"
                           class="layui-input" autocomplete="off" id="contactEmail" name="contactEmail"
                           placeholder="请输入联系邮箱" value="${channelInfo.contactEmail}">
                </div>
            </div>
        </div>

        <div class="layui-form-item" style="width: 1600px">
            <label class="layui-form-label">推广链接：</label>
            <div class="layui-input-block" style="width: 30%">
                <input lay-verify="required|extensionUrl"
                       class="layui-input" autocomplete="off" id="extensionUrl" name="extensionUrl"
                       placeholder="请输入推广链接" value="${channelInfo.extensionUrl}" readonly="true">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">推广二维码：</label>
            <div class="layui-input-block" style="width: 30%">
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                    <div class="layui-upload-img"
                         style="width: 100px; height: 100px;" id="imgUrlSl" name="imgUrlSl">
                    </div>
                    <br/>
                   <%-- <a href="javascript:makeCode();" style="color: #01AAED">生成二维码</a>--%>
                    <input type="hidden" id="extensionQrc" name="extensionQrc" lay-verify="required" value="${channelInfo.extensionQrc}" readonly="true"/>
                </blockquote>
            </div>
        </div>
        <button style="display: none;" lay-filter="submitBtn" lay-submit="">
        </button>
    </fieldset>
</form>

<script>
    window.onload = function () {
        showDiv();
    };
    $.ajaxSetup({
        async: false
    });
    function submitForm() {
        if ('' != $("#extensionUrl").val()) {
            makeCode();
        }
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    layui.use(['form','upload','laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startTime'
            ,type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;
        layedit = layui.layedit;

        //自定义验证规则
        form.verify({
            channelName: function (value) {
                if (value.length > 11 ) {
                    return '渠道名称长度不能超过11位';
                }
            },
            provinceCode : function(value) {
                if (value == '-999') {
                    return '请选择所属省';
                } else {
                    //获取select元素的引用
                    var eduElement = document.getElementById("provinceCode");
                    //1 获取所有的option标签
                    var optionElements = eduElement.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements.length; i++){
                        var optionElement = optionElements[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#provinceCode").val()) {
                            $("#provinceName").val(text);
                        }
                    }
                }
            },
            cityCode : function(value) {
                if (value == '-999') {
                    return '请选择所属市';
                } else {
                    //获取select元素的引用
                    var eduElement2 = document.getElementById("cityCode");
                    //1 获取所有的option标签
                    var optionElements2 = eduElement2.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements2.length; i++){
                        var optionElement = optionElements2[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#cityCode").val()) {
                            $("#cityName").val(text);
                        }
                    }
                }
            },
            townCode : function(value) {
                if (value == '-999') {
                    return '请选择所属区县';
                } else {
                    //获取select元素的引用
                    var eduElement3 = document.getElementById("townCode");
                    //1 获取所有的option标签
                    var optionElements3 = eduElement3.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements3.length; i++){
                        var optionElement = optionElements3[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#townCode").val()) {
                            $("#townName").val(text);
                        }
                    }
                }
            },
            settlementMode : function(value) {
                if (value.length > 11) {
                    return '结算方式长度不能超过11位';
                }
            }, settlementPrice : function(value) {
                if (value.length  == null || value.length == NaN || value.length == "") {
                    return '结算单价不能为空';
                }else{
                    var settlementPrice = $("div input[name='settlementPrice']").val();
                    console.log("settlementPrice:"+settlementPrice);
                    settlementPrice=settlementPrice*100;
                    $("div input[name='settlementPrice']").val(settlementPrice);
                }
            },
            personInCharge : function(value) {
                if (value.length > 11) {
                    return '负责人长度不能超过11位';
                }
            },
            pickUp : function(value) {
                if (value.length > 11) {
                    return '对接人长度不能超过11位';
                }
            },
            contactPhone : function(value) {
                if (value.length > 11) {
                    return '联系电话长度不能超过11位';
                }
            },
            contactEmail : function(value) {
                if (value.length > 118) {
                    return '联系邮箱长度不能超过118位';
                }
            },
            extensionUrl : function(value) {
                if (value.length > 500) {
                    return '推广链接长度不能超过500位';
                }
            },
            extensionQrc : function(value) {
                if (value.length == 0) {
                    return '请生成二维码';
                }

                if (value.length > 500) {
                    return '二维码链接URL长度不能超过500位';
                }
            }
        });

        form.on('submit(submitBtn)', function (data) {

            $.post("updateChannel", data.field,
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

        form.on('select(provinceCode)', function(data){
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#provinceCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#provinceCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#cityCode").empty();
                    $("#townCode").empty();
                    $("#cityCode").append("<option value='-999'>请选择</option>");
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#cityCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                    form.render('select');
                });
            }
        });

        form.on('select(cityCode)', function(data){
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#cityCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#cityCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#townCode").empty();
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#townCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                    form.render('select');
                });
            }
        });

        form.on('select(cooperationMode)', function(data){
            showDiv();
            form.render('select');
        });
    });

    var qrcode = new QRCode(document.getElementById("imgUrlSl"), {
        width : 100,
        height : 100
    });

    function makeCode () {
        var elText = document.getElementById("extensionUrl");

        if (!elText.value) {
            layui.use(['table','form','laydate'], function() {
                var layer = layui.layer;
                layer.alert("请先输入推广链接！");
            });
            return;
        }

        var data = elText.value + "?channelId=" + document.getElementById("channelId").value;
        qrcode.makeCode(data);
        $("#extensionQrc").val(data);
    }

    qrcode.makeCode("${channelInfo.extensionQrc}");

    $(function () {
        $("#provinceCode").change(function () {
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#provinceCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#provinceCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#cityCode").empty();
                    $("#townCode").empty();
                    $("#cityCode").append("<option value='-999'>请选择</option>");
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#cityCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                });
            }
        });

        $("#cityCode").change(function () {
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#cityCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#cityCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#townCode").empty();
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#townCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                });
            }
        });

        $('#provinceCode').val("${channelInfo.provinceCode}");
        $('#provinceCode').change();
        $('#cityCode').val("${channelInfo.cityCode}");
        $('#cityCode').change();
        $('#townCode').val("${channelInfo.townCode}");
        layui.use(['form','upload'], function () {
            var form = layui.form;
            form.render('select');
        });
    });

    function resetForm() {
        qrcode.makeCode("${channelInfo.extensionQrc}");
        $("#extensionQrc").val("${channelInfo.extensionQrc}");

        document.getElementById("form").reset();

        $('#provinceCode').val("${channelInfo.provinceCode}");
        $('#provinceCode').change();
        $('#cityCode').val("${channelInfo.cityCode}");
        $('#cityCode').change();
        $('#townCode').val("${channelInfo.townCode}");
        layui.use(['form','upload'], function () {
            var form = layui.form;
            form.render('select');
        });
    }

    // 显示渠道方式和渠道描述Div
    function showDiv(){
        var channelSource,description,other;
        // 获取"其他"解析对应的字段值
        <c:forEach var="item" items="${cooperationModeList}">
        <c:if test="${item.cooperation_str == '其他'}">
        other=${item.cooperation_mode};
        </c:if>
        </c:forEach>
        // 获取当前合作方式的值
        var cooperationMode = $("#cooperationMode").val();
        // 判断cooperationMode是否等于其他
        if(cooperationMode == other){
            channelSource =  $("#channelSource").css("display","block");
            description =  $("#description").css("display","block");
        }else{
            channelSource =  $("#channelSource").css("display","none");
            description =  $("#description").css("display","none");
        }
    }
    //两位小数限制
    function num(obj){
        obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
    }
</script>
</body>
</html>
