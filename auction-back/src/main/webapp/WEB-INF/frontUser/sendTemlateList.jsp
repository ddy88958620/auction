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
            <input class="layui-input" id="templateName" name="templateName" autocomplete="off" placeholder="模板名称搜索">
        </div>
        创建时间:
        <div class="layui-inline">
            <input class="layui-input" id="createTime" lay-verify="date" placeholder="请选择时间"  autocomplete="off" value="${params.createTime}">
        </div>
        <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
            <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
        </button>
    </div>

    <table class="layui-hide" id="data" lay-filter="dataTable">
    </table>
    <script src="<%=common%>/system/btns.js" charset="utf-8"></script>
    <script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="see">查看</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="update">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    </script>
    <script src="<%=common%>/system/user/templateSendSms.js" charset="utf-8"></script>
    <script>
        var parentName = $("#parentName").val();
        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        var laydate;
        layui.use(['table','form','laydate'], function() {
            findBtns(null, "${parentId}");
            table = layui.table;
            form = layui.form;
            laydate = layui.laydate;
            laydate.render({
                elem: '#createTime'
            });
            table.render({
                elem : '#data',
                even : true,
                url : '${path}/sms/getTemplateList',
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
                cols : [ [ /*{
                    type : 'checkbox',
                    fixed : 'left'
                }, {
                    type : 'numbers',
                    align : 'center',
                    title : '序号',
                    fixed : "left",
                    width : 100
                },*/ {
                    field : 'id',
                    align : 'center',
                    title : '编号'
                },  {
                    field : 'templateName',
                    title : '模板名称',
                    align : 'center'
                },{
                    field : 'templateTitle',
                    title : '模板标题',
                    align : 'center'
                },{
                    field : 'createTime',
                    title : '创建时间',
                    align : 'center',
                    templet : '<div>{{toDate(d.createTime)}}</div>'
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
                if(obj.event === 'see'){
                    layer.open({
                        title:"短信模板信息查看",
                        content: 'seeSmsTemplate?id='+data.id,
                        type : 2,
                        shade : 0.3,
                        maxmin : true,
                        area : [ '100%', '100%' ],
                        anim : 1,
                        btnAlign : 'r',
                        btn : ['返回' ],
                        success : function(layero, index) {
                        }
                    });
                }else if(obj.event === 'update'){
                    layer.open({
                        title:"短信模板信息编辑",
                        content: 'updateSmsTemplate?id='+data.id,
                        type : 2,
                        shade : 0.3,
                        maxmin : true,
                        area : [ '100%', '100%' ],
                        anim : 1,
                        btnAlign : 'r'
                    });
                }else if(obj.event === 'delete'){
                    layer.confirm('确定要操作吗?', {
                        icon : 3,
                        title : '警告'
                    }, function() {
                        $.ajax({
                            type : "post",
                            url : "deleteTemplate",
                            data : {
                                id : data.id
                            },
                            dataType : "json",
                            success : function(result) {
                                layer.msg(result.msg, {
                                    time : 1000
                                }, function() {
                                    if (result.code == '0') {
                                        if (refreshPage != undefined && refreshPage != null && refreshPage != '') {
                                            refreshPage();
                                        } else {
                                            window.location.reload();
                                        }
                                    }
                                });
                            }
                        });
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
                            templateName : $('#templateName').val(),
                            createTime : $('#createTime').val()
                        }
                    });
                }
            };
            $('#btnSearch').on('click', function() {
                var templateName = $('#templateName').val();
                var createTime = $('#createTime').val();

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