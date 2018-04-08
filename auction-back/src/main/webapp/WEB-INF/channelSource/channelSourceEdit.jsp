<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form" action=" " id="form">
    <input type="hidden" name="id" value="${channelInfo.id }">
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label">渠道ID:</label>
        <div class="layui-input-inline">
            <label  class="layui-form-label" style="width: 190px;text-align: left;">${channelInfo.id }</label>
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label">渠道名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="channelName" lay-verify="required|realName"
                   placeholder="请输入渠道名" autocomplete="off"
                   class="layui-input" value="${channelInfo.channelName }">
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label">渠道key:</label>
        <div class="layui-input-inline">
            <input type="text" name="channelKey" lay-verify="required|channelKey"
                   placeholder="请输入渠道key" autocomplete="off"
                   class="layui-input" value="${channelInfo.channelKey }">
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 40px;">
        <label class="layui-form-label">创建时间:</label>
        <div class="layui-input-inline">
            <label  class="layui-form-label" style="width: 190px;text-align: left;">
                <fmt:formatDate value="${channelInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </label>
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 20px;"  >
        <label class="layui-form-label">状态:</label>
        <div class="layui-input-inline">
        <select class="layui-input" id="status" name="status" style="padding-left: 27px;padding-right: 27px;">
            <option selected="selected" value="">全部</option>
            <c:forEach var="item" items="${status}">
                <option value="${item.key}" <c:if test="${item.key eq channelInfo.status}">selected="selected"
                </c:if>>${item.value}
                </option>
            </c:forEach>
        </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="submitBtn">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
        </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="" />
</form>

<script>
    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }
    layui.use('form', function() {
        var form = layui.form;
        form.on('submit(submitBtn)', function(data) {
            $.ajax({
                type : "post",
                url : "${path }${url}",
                data : data.field,
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
    });

</script>
</body>
</html>