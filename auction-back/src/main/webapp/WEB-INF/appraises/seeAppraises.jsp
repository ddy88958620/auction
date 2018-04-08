<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<!DOCTYPE html>
<html>
    <%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
	String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");

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
    <!--<script src="<%=common%>/system/appraises/zoomify.min.js" ></script>-->
    <script src="<%=common%>/system/appraises/zoomify.js" ></script>
</head>
<body>

<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form" style="margin-left: 30px">

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend style="size: 24px;">晒单信息：</legend>
    </fieldset>
    <input type="hidden" id="id" value="${id}">

    <div class="layui-form-item">
        <div class="layui-input-inline" style="width: 300px;margin-left: 95px">
            <label class="layui-form-label">晒单商品：</label>
            <img class="layui-upload-img" style="width: 100px; height: 100px;"  id="productImg" name="productImg" src="<%=photoPathSuffix%>/${productImg}"/>
        </div>
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label">商品名称： </label>
            <div class="layui-input-inline" style="width: 30%;">
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="productName" name="productName" value="${productName }" style="border: 0px "/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >订单成交时间： </label>
            <div class="layui-input-inline" >
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="orderCreateTime" name="orderCreateTime" value="${orderCreateTime}" style="border: 0px "/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >订单Id： </label>
            <div class="layui-input-inline" >
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="orderId" name="orderId" value="${appraises.orderId}" style="border: 0px "/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >用户Id： </label>
            <div class="layui-input-inline" >
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="buyId" name="buyId" value="${appraises.buyId}" style="border: 0px "/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >用户名： </label>
            <div class="layui-input-inline" >
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="buyNickName" name="buyNickName" value="${appraises.buyNickName}" style="border: 0px "/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >选择晒单时间： </label>
            <div class="layui-input-inline" >
                <input type="text" unselectable="on" class="layui-input" readonly="readonly" id="createTime" name="createTime"  />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" >上传图片： </label>
            <div class="layui-input-inline" >
                <button type="button" class="layui-btn" id="upload">上传</button>
            </div>
        </div>
    </div>
    <input type="hidden" id="picArray" name="picArray" value=""/>
    <div class="layui-form-item layui-form-text" style="margin-left: 95px">
        <label class="layui-form-label">感受：</label>
        <div class="layui-input-block" style="width: 30%">
            <p name="content"  id="content" placeholder="请输入内容"  lay-verify="content" class="layui-textarea">${content }</p>
        </div>
    </div>

    <div class="layui-form-item" >
        <div class="layui-input-inline" style="width:120px;margin-left: 75px;">
            <label class="layui-form-label" style="width:100px">晒单图片：</label>
        </div>
        <div class="example" style="margin-top: 10px" id="mutiPic">
            <%--<table  class="example">--%>
            <%--<tr id="tr1">--%>
            <c:if test="${existence eq 'true'}">
                <c:forEach var="picInfo" items="${picList}">
                    <%--<td >--%>
                    <img src="<%=photoPathSuffix%>/${picInfo}"  class="img-rounded" style="width: 100px;height: 60px;cursor:pointer;"/>
                    <%--</td>--%>
                </c:forEach>
            </c:if>
            <c:if test="${existence eq 'false'}">
                <%--<td style="width: 100px;height: 60px;vertical-align: text-top;">--%>
                此晒单无图片
                <%--</td>--%>
            </c:if>
            <%--</tr>--%>
            <%--</table>--%>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px">
            <label class="layui-form-label" style="color:orangered;">晒单级别： </label>
            <div class="layui-input-inline" >
                <label class="layui-form-label" id="level" style="color:orangered;text-align: left">${level}</label>
            </div>
        </div>
    </div>

    <%--<div class="layui-form-item" style="margin-top: 200px;margin-left: 395px">--%>
        <%--<div class="layui-input-block">--%>
            <%--&lt;%&ndash;<button class="layui-btn" type="submit" lay-submit lay-filter="check1">审核并发布</button>&ndash;%&gt;--%>
            <%--<button type="submit" lay-submit class="layui-btn" lay-filter="check2">审核拒绝</button>--%>
            <%--<button type="submit" lay-submit class="layui-btn" lay-filter="close">取消</button>--%>
        <%--</div>--%>
    <%--</div>--%>
</form>


<%--<div class="site-demo-button" id="layerDemo" style="position: absolute;top: 976px;left: 415px">--%>
    <%--<button data-method="setTop" class="layui-btn">审核并发布</button>--%>
<%--</div>--%>


<script>
    var level = $("#level").text();
    var id =$("#id").val();
    var content = $("#content").text();
    var picArray = "";
    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var url = "gradeAppraises";
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        //触发事件
        var active = {
            setTop: function(){
                var that = this;
                picArray = $("#picArray").val();
                url =  url + "level=" + level+"&id="+id+"&content="+content+"&valueArray="+picArray;
                //多窗口模式，层叠置顶
                layer.open({
                    type: 2 //此处以iframe举例
                    ,title: '确认奖励级别'
                    ,area: ['650px', '375px']
                    ,shade: 0
                    ,maxmin: true
                    ,content: url
                    ,yes: function(){
                        $(that).click();
                    },btn2: function(){
                        layer.closeAll();
                    }
                    ,zIndex: layer.zIndex //重点1
                    ,success: function(layero){
                        layer.setTop(layero); //重点2
                    }
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });

    });


    layui.use(['form', 'layedit', 'laydate','upload'], function(){
        <c:if test="${not empty isAudited}">
        parent.layer.msg("${isAudited}");
        parent.layer.closeAll('iframe');
        parent.window.refreshPage();
        </c:if>
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate
            ,upload = layui.upload;

        //初始赋值
        laydate.render({
            elem: '#createTime'
            ,type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
            ,value: '${createTime}'
        });


        var valueArray = "";
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#upload'
            ,url: '${path}/upload/uploadFiles'
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    var vue =  "<%=photoPathSuffix%>"+"/"+result;
                    var content = $('#mutiPic').html();
                    if($.trim(content) == "此晒单无图片"){
                        $('#mutiPic').html("");
                    }
                    $('#mutiPic').append('<img src="'+ result +'" alt="'+ file.name +'" class="img-rounded zoomify" style="width: 100px;height: 60px;cursor:pointer;">')
                });
            }
            ,done: function(res){
                if (res.code == 0) {
                    if(valueArray==""){
                        $('input[name="picArray').attr('value',res.data.src);
                    }else{
                        $('input[name="picArray').attr('value',valueArray+","+res.data.src);
                    }
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

        var id=$("#id").val();
        var content=$("#content").text();
        form.on('submit(check1)', function(data) {
            $.ajax({
                type : "post",
                url : "${path }/appraises/orderAppraisesCheck",
                data :{ 'isShow':1 ,"id":id,"content":content,"valueArray":valueArray},
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

        form.on('submit(check2)', function(data) {
            $.ajax({
                type : "post",
                url : "${path }/appraises/orderAppraisesCheck",
                data :{ 'isShow':2 ,"id":id,"content":content},
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
    });
</script>
<script>
    $('.example img').zoomify();
</script>