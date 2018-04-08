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
        标题:
        <div class="layui-inline">
            <input class="layui-input" id="templateTitle" autocomplete="off" />
        </div>
        发布时间:
        <div class="layui-inline">
            <input class="layui-input" id="releaseTime" lay-verify="date" placeholder="yyyy-MM-dd"  autocomplete="off" />
        </div>
        模板类型:
        <div class="layui-inline"  >
            <select class="layui-input" id="sendType" style="padding-left: 27px;padding-right: 27px;">
                <option selected="selected" value="">全部</option>
                <c:forEach var="item" items="${sendTypeList}">
                    <option value="${item.key}" <c:if test="${item.key eq params.sendType}">selected="selected"</c:if>>${item.value}</option>
                </c:forEach>
            </select>
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
    </script>
    <script>
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
                elem: '#releaseTime'
            });
            table.render({
                elem : '#data',
                even : true,
                url : '${path}/sms/sendSmsRecordList',
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
                    field : 'id',
                    align : 'center',
                    title : '编号'
                },{
                    field : 'templateName',
                    title : '模板名称',
                    align : 'center'
                },{
                    field : 'templateTitle',
                    title : '标题',
                    align : 'center'
                },{
                    field : 'count',
                    title : '接受对象',
                    align : 'center'
                },{
                    field : 'releaseTime',
                    title : '发布时间',
                    align : 'center',
                    templet : '<div>{{toDate(d.releaseTime)}}</div>'
                }, {
                    field : 'publisher',
                    title : '发布人',
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
                if(obj.event === 'see'){
                    layer.open({
                        title:"短信记录信息查看",
                        content: 'seeSmsRecord?id='+data.id,
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
                }
            });

            var $ = layui.$, active = {
                reload : function() {
                    //执行重载
                    table.reload('search', {
                        page : {
                            //重新从第 1 页开始
                            page : 1
                        },
                        where : {
                            templateTitle : $('#templateTitle').val(),
                            releaseTime : $('#releaseTime').val(),
                            sendType : $('#sendType').val()
                        }
                    });
                }
            };
            $('#btnSearch').on('click', function() {
                var templateTitle = $('#templateTitle').val();
                var releaseTime = $('#releaseTime').val();
                var sendType = $('#sendType').val();

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