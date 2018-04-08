<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST" style="margin: 20px 0 0 20px;">
    <input type="hidden" value="${lotteryPrize.id}" name="id">
    <div class="layui-form-item">
        <label class="layui-form-label">奖品库存：</label>
        <div class="layui-input-inline">
            <input type="text" name="store" lay-verify="required|integer" value="${lotteryPrize.store}"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">中奖概率：</label>
        <div class="layui-input-inline">
            <input type="text" name="prizeRate" lay-verify="required|double" value="${lotteryPrize.prizeRate}"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid">%</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-block" style="width: 30%">
            <textarea style="width: 500px;height: 100px;" class="layui-input" autocomplete="off"
                      id="remark" name="remark">${lotteryPrize.remark}</textarea>
        </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    layui.use(['form'], function () {
        var form = layui.form;
        var layer = layui.layer;

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
            $.post("update", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("修改成功！");
                        parent.layer.closeAll('iframe');
                        parent.table.reload('search');
                    } else {
                        layer.error("修改失败！");
                    }
                }, "json");
            return false;
        });
    });
</script>
</body>
</html>
