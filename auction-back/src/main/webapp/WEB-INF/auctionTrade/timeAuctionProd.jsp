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
</head>
<body style="margin-top: 20px;">
<form class="layui-form layui-form-pane" id="formTime" action="" method="post">
    <div  style="margin-left:13%;margin-right: 13%">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;margin-right:13%;" >
            <legend >定时上架</legend>
        </fieldset>

        <div class="layui-form-item" id="topTime">
            <label class="layui-form-label">上架时间</label>
            <div class="layui-input-inline">
                <input name="dateTime1" id="date"
                       placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input" type="text">
            </div>
            <div class="layui-input-inline">
                <input name="dateTime2" id="time"
                       placeholder="HH:mm:ss" autocomplete="off" class="layui-input" type="text">
            </div>
        </div>
        <input type="hidden"  name="id" class="layui-input"
               autocomplete="off" value="${auctionProductInfo.id}"/>
        <input type="hidden"  name="status" class="layui-input"
               autocomplete="off" value="${auctionProductInfo.status}"/>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="button" id="timeBtn" onclick="submitTime()" class="layui-btn"
                       value="立即提交" />
                <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
            </div>
        </div>
    </div>
</form>
<script>
    var id = '${id}';
    layui.use(['form','laydate','layer'], function() {
        var form = layui.form;
        var laydate= layui.laydate;
        var layer = layui.layer;
        laydate.render({
            elem: '#date',
            type:'date'
        });
        laydate.render({
            elem: '#time',
            type:'time'
        });




    });

    function submitTime() {
            var dateTime1 =$("#date").val();
            if(dateTime1 =="" || dateTime1 == null || dateTime1==undefined){
                layer.msg("时间不能为空");
                return false;
            }
            var dateTime2 = $("#time").val();
            if(dateTime2 =="" || dateTime2 == null || dateTime2==undefined){
                layer.msg("时间不能为空");
                return false;
            }
            var currentDate  = getServerDate();
            var timeDate = dateTime1+" "+dateTime2;
            var date2 = new Date(timeDate).getTime();
            if(date2<currentDate){
                layer.msg("上架时间必须大于当前时间");
                return false;
            }

        $.ajax({
            type : "post",
            url : "/auctionProduct/updateStatus?"+"id="+id,
            data : $('#formTime').serialize(),
            dataType : "json",
            success : function(result) {
                layer.msg(result.msg, {
                    time : 1000
                }, function() {
                    if (result.code == '0') {
                        window.parent.location.reload();
                    }
                });
            }
        });



    }

    /**
     * 获取服务器时间
     * @returns {Date}
     */
    function getServerDate(){
        var curDate = new Date($.ajax({async: false}).getResponseHeader("Date"));
        //curDate.getFullYear()+"-"+(curDate.getMonth()+1)+"-"+curDate.getDate()+" "+curDate.getHours()+":"+curDate.getMinutes()+":"+curDate.getSeconds()
        return curDate.getTime();
    }
</script>
</body>
</html>