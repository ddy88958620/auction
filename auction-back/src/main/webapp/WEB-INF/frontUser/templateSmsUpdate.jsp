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
    <input type="hidden" id="id" name="id" value="${sendSmsTemplate.id}">
    <div class="layui-form-item" >
        <div class="layui-form-inline" style="margin-left: 95px;margin-top: 30px;height: 50px">
            <label class="layui-form-label">短信类型： </label>
            <div class="layui-input-inline" style="height: 50px">
                <select id="sendType" class="layui-input"  style="overflow: visible;">
                    <c:forEach items="${sendTypeList}" var="item" >
                        <option id="${item.key}" value="${item.key}" <c:if test="${item.key eq sendSmsTemplate.sendType}">selected="selected"</c:if>>${item.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-inline" style="margin-left: 95px;margin-top: 30px">
            <label class="layui-form-label">模板名称： </label>
            <div class="layui-input-inline" style="width: 30%;">
                <input id="templateName" name="templateName"  type="text" unselectable="on" class="layui-input"  value="${sendSmsTemplate.templateName}" />
            </div>
        </div>
    </div>
    <div class="layui-form-inline" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">标题： </label>
        <div class="layui-input-inline" style="width: 30%;">
            <input type="text" unselectable="on" class="layui-input"  id="templateTitle" name="templateTitle" value="${sendSmsTemplate.templateTitle}" />
        </div>
    </div>
    <div class="layui-form-item layui-form-text" style="margin-left: 95px;margin-top: 20px">
        <label class="layui-form-label">短信内容：</label>
        <div class="layui-input-block" style="width: 30%">
            <input name="content"  id="content"  placeholder="请输入内容"  lay-verify="content" class="layui-textarea" value="${sendSmsTemplate.content}"/>
        </div>
    </div>
    <p style="margin-left: 205px">发送时系统会自动在开头追加【开心拍卖】，请勿重复添加。</p>
    <p style="margin-left: 205px">内容上限不能超过500字，当前已输入3字，将作为1条发送。</p>
    <br/>
    <p style="margin-left: 205px">单条短信在70字以内按一条计费，超过70个字的长短信按67个字一条。</p>
    <p style="margin-left: 205px">计费，字数统计包含短信签名。</p>
    <div class="layui-form-item" style="margin-top: 200px;margin-left: 300px">
        <div class="layui-input-block">
    <button class="layui-btn" type="submit" lay-submit lay-filter="cancle">取消</button>
    <button class="layui-btn" type="submit" lay-submit lay-filter="save">保存</button>
        </div>
    </div>
</form>
<script>
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }
    layui.use(['form'], function () {
        var form = layui.form;
        var layer = layui.layer;
        form.on('submit(save)', function(data) {
            $.post("updateTemplate", data.field,
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

        form.on('submit(cancle)', function(data) {
            parent.layer.closeAll('iframe');
            parent.window.refreshPage();
            return false;
        });
    });
</script>
</body>
</html>
