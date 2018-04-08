<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/22 0022
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
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

<form class="layui-form  layui-form-pane1 pzjzsj" method="POST">

    <input type="hidden" name="productId" value="${inventoryVo.productId}" />
    <div class="layui-form-item">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-block" style="width: 50%">
            <input type="text" name="productName" lay-verify="required"  readonly="readonly" autocomplete="off"
                  value="${inventoryVo.productName}"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">库存数量</label>
        <div class="layui-input-inline">
            <input type="text" name="productNum" lay-verify="integer" autocomplete="off"
                  value="${inventoryVo.productNum}"
                   class="layui-input">
        </div>
    </div>




    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="inventoryUpdate">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
        </div>
    </div>

</form>

<script>
    $(document).ready(function(){
        $("button[lay-filter='reset']").click(function () {
            $('#demo2').empty();
        })

    });

    layui.use('form', function () {
        var form = layui.form;
        var layer = layui.layer;

        //自定义验证规则
        form.verify({
            integer: function (value) {
                var integer = new RegExp("^\\d+$");
                if (!integer.test(value)) {
                    return "只能是整数!";
                }
            }
        });




        form.on('submit(inventoryUpdate)', function (data) {
            $.post("/inventory/edit", data.field,
                function (data) {
                    if (data.code == '0') {
                        layer.msg("修改成功!", {
                            icon: 1,
                            time: 1000,
                            end: function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                                parent.location.reload();
                            }
                        });
                    }

                }, "json");
            return false;

        });
    });



</script>
</body>
</html>
