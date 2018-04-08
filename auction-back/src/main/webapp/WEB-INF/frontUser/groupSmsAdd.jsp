<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
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
    <input type="hidden" id="ids" name="ids" value="${ids}"/>
    <input type="hidden" id="templateId" name="templateId" value="${templateId}">
    <input type="hidden" id="count" name="count" value="${count}"/>
<div class="layui-form-item">
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">发送对象：</label>
        <div class="layui-input-inline" style="width: 30%;">
        <input type="text" unselectable="on" class="layui-input" disabled="disabled"  value="${count}个用户" />
        </div>
    </div>
</div>
<div class="layui-form-item">
<div class="layui-form-inline" style="margin-left: 95px;margin-top: 30px">
    <label class="layui-form-label">模板名称： </label>
    <div class="layui-input-inline" style="width: 30%;">
        <button id="setTop" name="setTop" placeholder="请选择模板" type="button" unselectable="on" class="layui-input"  lay-verify="setTop"  style="text-align: left ">请选择模板</button>
    </div>
</div>
</div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">标题： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input" disabled="disabled" id="title" name="title" value="${title }" />
        </div>
    </div>
<div class="layui-form-item layui-form-text" style="margin-left: 95px;margin-top: 20px">
    <label class="layui-form-label">短信内容：</label>
    <div class="layui-input-block" style="width: 30%">
        <input name="content"  id="content" disabled="disabled"   lay-verify="content" class="layui-textarea">${content }</input>
    </div>
</div>
    <p style="margin-left: 205px">发送时系统会自动在开头追加【开心拍卖】，请勿重复添加。</p>
    <p style="margin-left: 205px">内容上限不能超过500字，当前已输入<span id="num">0</span>字，将作为<span id="numCount">1</span>条发送。</p>
    <br/>
    <p style="margin-left: 205px">单条短信在70字以内按一条计费，超过70个字的长短信按67个字一条。</p>
    <p style="margin-left: 205px">计费，字数统计包含短信签名。</p>
    <div class="layui-form-item">
        <p style="margin-left: 205px">发送统计：发送条数：<span id="sendNum">${count}</span>条 </p>
    </div>

    <div class="layui-form-item" style="margin-top: 50px;margin-left: 195px">
        <div class="layui-input-block">
            <button type="submit" lay-submit class="layui-btn" lay-filter="close">取消</button>
            <button type="submit" lay-submit class="layui-btn" lay-filter="confirm">确定</button>
        </div>
    </div>
</form>


<script>

    layui.use('form', function(){ //独立版的layer无需执行这一句
        // 获取layer控件
        var layer = layui.layer;
        var form = layui.form;
        // 获取当前窗口名称
        var parentName = window.name;
        // 传递参数到后台
        var url = "choose";
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        url =  url + "parentName=" + parentName;
        $("#setTop").click(function(){
            layer.open({
                    type: 2 //此处以iframe举例
                    ,title: '选择短信模板'
                    ,area: ['1110px', '650px']
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
        });

        //自定义验证规则
        form.verify({
            setTop: function (value) {
                if (value=="请选择模板" ) {
                    return '只有选择一个模板后,才能发送短信,';
                }
            }
        });

        var ids = $("#ids").val();
        form.on('submit(confirm)', function(data) {
            var templateId = $("#templateId").val();
            var setTop = $("#setTop").html();
            if(setTop=="请选择模板" ){
                layer.msg('只有选择一个模板后,才能发送短信', {
                    time : 3000
                }, function() {
                });
                return;
            }
            var count = $("#sendNum").text();
            $.ajax({
                type : "post",
                url : "${path }/sms/send",
                data :{ "ids":ids,"templateId":templateId,"count":count},
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
            parent.window.refreshPage();
            return false;
        });
    });
</script>
</body>
</html>
