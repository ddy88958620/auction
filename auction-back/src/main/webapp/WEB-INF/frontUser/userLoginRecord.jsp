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
<form class="layui-form" onsubmit="return false;">
    <input type="hidden" id="parentName" name="parentName" value="${parentName}">

    <table class="layui-hide" id="data" lay-filter="dataTable">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>数据列表</legend>
        </fieldset>
    </table>
    <script src="<%=common%>/system/btns.js" charset="utf-8"></script>
    <script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
    <script>
        var parentName = $("#parentName").val();
        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        layui.use(['table','form'], function() {
            findBtns(null, "${parentId}");
            table = layui.table;
            form = layui.form;
            table.render({
                id:"${id}",
                elem : '#data',
                even : true,
                url : '${path}/user/getLoginRecord?id='+"${id}",
                height : "full-70",
                method : 'post',
                page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout : [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],//自定义分页布局//,curr: 5 //设定初始在第 5 页
                    groups : 5,//只显示 1 个连续页码
                    limits : [ 10, 20, 50, 100, 200 ],
                    first : "首页",
                    theme : "#FF5722;",
                    limit : 10,
                    last : "尾页"

                },
                id : 'search',
                cols : [ [ {
                    field : 'userId',
                    align : 'center',
                    title : '用户id'
                },  {
                    field : 'loginTime',
                    title : '时间',
                    align : 'center',
                    templet : '<div>{{toDate(d.loginTime)}}</div>'
                },{
                    field : 'loginIp',
                    title : 'IP',
                    align : 'center'
                },{
                    field : 'address',
                    title : '地区',
                    align : 'center'
                },{
                    field: 'loginDevices',
                    title : '登陆设备',
                    align:'center'
                }] ]

            });


            // 表格重载
            var $ = layui.$, active = {
                reload : function() {
                    //执行重载
                    table.reload('search', {
                        page : {
                            //重新从第 1 页开始
                            page : 1
                        },
                        where : {
                            templateName : $('#templateName').val()
                        }
                    });
                }
            };
            refreshPage = function() {
                $('#btnSearch').trigger("click");
            }
        });
    </script>
</form>
</body>
</html>