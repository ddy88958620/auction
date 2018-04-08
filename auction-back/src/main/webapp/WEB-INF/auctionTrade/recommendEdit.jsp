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
<body style="margin-top: 20px;">
<form class="layui-form layui-form-pane" id="form" action="" method="post">
    <div  style="margin-left:13%;margin-right: 13%">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;margin-right:13%;" >
            <legend >修改排序</legend>
        </fieldset>

        <input type="hidden" id="hotSize" name="hotSize" value="${hotSize}" />

        <input type="hidden" id="auctionProdId" name="auctionProdId" value="${auctionProdId}" />

        <input type="hidden" id="oldSort" name="oldSort" value="${oldSort}" />

        <div class="layui-form-item">
            <label class="layui-form-label">排序</label>
            <div class="layui-input-block" style="width:80%">
                <input name="sort" lay-verify="integer" id="sort"
                       autocomplete="off"  class="layui-input"
                       type="text" value="${oldSort}">
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" type="submit" lay-submit lay-filter="updateSort">立即提交</button>
            </div>
        </div>
    </div>
</form>
<script>
    layui.use(['form','laydate'], function() {
        var form = layui.form;
        var laydate= layui.laydate;
        var layer = layui.layer;


        form.verify({
            integer: function (value) {
                if(value != null && value != '' ) {
                    var integer = new RegExp("^[-+]{0,1}\\d+$");
                    if (!integer.test(value)) {
                        return "只能是整数!";
                    } else {
                        if (value <= 0) {
                            return "排序必须大于0";
                        }
                    }
                }

            }
        });

        form.on('submit(updateSort)', function(data) {
                var hostSize = parseInt(data.field.hotSize);
                var sort = parseInt(data.field.sort);
                if(sort > hostSize){
                    layer.msg("排序不能大于最大数量");
                    return false;
                }
                $.ajax({
                    type: "post",
                    url: "/auctionRecommend/edit",
                    data: data.field,
                    dataType: "json",
                    beforeSend: function () {
                        layer.load(1, {
                            shade: [0.1, '#fff']
                        });
                    },
                    success: function (result) {
                        layer.msg(result.msg, {
                            time: 1000
                        }, function () {
                            if (result.code == '0') {
                                parent.layer.closeAll('iframe');
                                parent.window.location.reload();
                            }else {
                                layer.msg("修改排序失败");
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