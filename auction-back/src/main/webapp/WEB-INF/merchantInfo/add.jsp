<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<form class="layui-form " action="" id="form">
    <input type="hidden" name="id" value="${merchantInfo.id }">
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label">商家名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="merchantName" lay-verify="required|merchantName" placeholder="请输入商家名称" autocomplete="off" class="layui-input" value="${merchantInfo.merchantName}"/>
        </div>

            <label class="layui-form-label">商家类型：</label>
            <div class="layui-input-inline" style="width: 100px;">
                <select name="merchantType" lay-verify="required">
                    <option value=""></option>
                    <option value="0">第三方</option>
                    <option value="1">渠道</option>
                    <option value="2">京东</option>
                </select>
            </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系方式:</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${merchantInfo.phone}"/>
        </div>
        <label class="layui-form-label">状态：</label>
        <div class="layui-input-inline" style="width: 100px;">
            <select name="status" lay-verify="required">
                <option value=""></option>
                <option value="0">启用</option>
                <option value="1">禁用</option>
            </select>
        </div>
    </div>

    <button style="display: none;" lay-filter="submitBtn" lay-submit="" />
</form>

<script>
   /* <c:if test="${not empty  sysUser}">
    $("input[name='userSex'][value='${sysUser.userSex}']").attr("checked", true);
    </c:if>*/
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