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
    <div id="toolBar" style="margin: 10px;">
        <div class="layui-inline">
            <input class="layui-input" id="condition" name="condition"  autocomplete="off" placeholder="拍品名称/拍品ID">
        </div>
        <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
            <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
        </button>
    </div>
    <input type="hidden" id="id" name="id" value="${id}">
    <table class="layui-hide" id="data" lay-filter="dataTable">
    </table>
    <script src="<%=common%>/system/btns.js" charset="utf-8"></script>
    <script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="select">选择</a>
    </script>
    <script>
        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        layui.use(['table','form'], function() {
            findBtns(null, "${parentId}");
            table = layui.table;
            form = layui.form;
            table.render({
                elem : '#data',
                even : true,
                url : '${path}/labelManager/auctionProductSelect',
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
                cols : [ [{
                    field : 'auctionProductName',
                    align : 'center',
                    title : '拍品名称'
                },  {
                    field : 'auctionProductId',
                    title : 'ID',
                    align : 'center'
                },{
                    field : 'auctionProductPrice',
                    title : '拍品价格',
                    align : 'center'
                },{
                    fixed: 'right',
                    title : '操作',
                    align:'center',
                    toolbar: '#barDemo'
                }] ]

            });

            //监听工具条
            table.on('tool(dataTable)', function(obj){
                var data = obj.data;
                var labelId = $("#id").val();
                if(obj.event === 'select'){
                    $.ajax({
                        type : "post",
                        url : "labelBindAuctionProduct",
                        data : {
                            auctionProductId : data.auctionProductId,
                            labelId:labelId
                        },
                        dataType : "json",
                        success : function(result) {
                            layer.msg(result.msg, {
                                time : 1000
                            }, function() {
                                if (result.code == '0') {
                                    parent.layer.closeAll();
                                    parent.window.refreshPage();
                                }
                            });
                        }
                    });
                }
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
                            condition : $('#condition').val()
                        }
                    });
                }
            };
            $('#btnSearch').on('click', function() {
                var condition = $('#condition').val();

                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            $("#toolBar").keydown(function(event) {
                if (event.keyCode == 13) {
                    refreshPage();
                }
            });
            refreshPage = function() {
                $('#btnSearch').trigger("click");
            }
        });
    </script>
</form>
</body>
</html>