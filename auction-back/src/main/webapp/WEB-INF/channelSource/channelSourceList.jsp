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
        <div id="toolBar">
            渠道名称:
            <div class="layui-inline">
                <input class="layui-input" id="channelName" autocomplete="off">
            </div>
            渠道key:
            <div class="layui-inline">
                <input class="layui-input" id="channelKey" autocomplete="off">
            </div>
            状态:
            <div  class="layui-inline layui-form" style="width: 100px;">
                <select class="layui-input" id="status" style="...">
                    <option selected="selected" value="">全部</option>
                    <c:forEach items="${status}" var="item">
                    <option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>  >${item.value}</option>
                    </c:forEach>
                </select>
            </div>
            时间:
            <%--<div class="layui-inline">
                <input class="layui-input" id="createTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" value="${params.addTimeBegin}">
            </div>--%>
            <div class="layui-inline">
                <input class="layui-input" id="createTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.createTimeBegin}">
            </div>
            &nbsp;至&nbsp;
           <%-- <div class="layui-inline">
                <input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" value="${params.addTimeEnd}">
            </div>--%>
            <div class="layui-inline">
                <input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.createTimeEnd}">
            </div>
            <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
                <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i>搜索
            </button>
        </div>
        <table class="layui-hide" id="data">
        </table>
        <script src="<%=common%>/system/btns.js" charset="utf-8"></script>
        <script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
        <script src="<%=common%>/system/channelSource/channelSourceUtils.js" charset="utf-8"></script>
        <script>
            var myId = "${parentId}";
            var path = "${path}";
            var table;
            var form;
            // layui依賴注入
            layui.use(['table','form','laydate'],function(){
                var laydate = layui.laydate;

                laydate.render({
                    elem: '#createTimeBegin'
                });
                laydate.render({
                    elem: '#createTimeEnd'
                });
                // 根据父菜单查询子菜单,查詢的是按鈕.
                findBtns(null,"${parentId}");
                // 获取laydate组件
                var laydate = layui.laydate;
                // 实例化开始时间控件
                laydate.render({
                    elem:'#addTimeBegin'
                });
                // 实例化结束时间控件
                laydate.render({
                    elem:'#addTimeEnd'
                })
                // 获取table组件
                table = layui.table;
                form = layui.form;
                // 实例化table
                table.render({
                    // 绑定table元素
                   elem:'#data',
                   even:true,
                   url:'${path}/channelSource/getChannelSourceList',
                   height:"full-58",
                   method:'post',
                   page:{
                       layout:['prev','page','next','skip','count','limit'],
                       groups:5,// 只显示1个连续页码,
                       limits:[10,20,50,100,200],
                       first:"首页",
                       theam:"#FF5722",
                       limit:10,
                       last:"尾页"
                   },
                    id : 'search',
                    cols:[[{
                       type:'checkbox',
                       fixed:'left'
                    },{
                        field:'id',
                       align:'center',
                       title:'渠道id',
                       fixed:'left'
                    },{
                       field:'channelName',
                       align:'center',
                       title:'渠道名称'
                    },{
                       field:'channelKey',
                       title:'渠道key',
                       align:'center'
                    },{
                        field : 'status',
                        title : '状态',
                        align : 'center',
                        templet : '<div>{{toStatus(d.status)}}</div>'
                    },{
                        field : 'createTime',
                        title : '创建时间',
                        align : 'center',
                        sort : true,
                        templet : '<div>{{toDate(d.createTime)}}</div>'
                    },{
                        field :'updateTime',
                        title : '更新时间',
                        align : 'center',
                        sort : true,
                        templet : '<div>{{toDate(d.updateTime)}}</div>'
                    },{
                        field:'remark',
                        title:'备注',
                        align:'center'
                    }]]
                });
                // 激活重载
                var $ = layui.$,active = {
                    reload:function(){
                        //执行重载
                        table.reload('search',{
                            page:{
                                //重新从第一页开始
                                page:1
                            },
                            where:{
                                channelName:$('#channelName').val(),
                                channelKey:$('#channelKey').val(),
                                status:$('#status').val(),
                                createTimeBegin:$('#createTimeBegin').val(),
                                createTimeEnd:$('#createTimeEnd').val(),
                            }
                        });
                    }
                };
                //  点击事件
                $('#btnSearch').on('click',function(){
                   var channelName = $("#channelName").val();
                   var channelKey = $('#channelKey').val();
                   var stauts = $("#stauts").val();
                   var createTimeBegin = $("#createTimeBegin").val();
                   var createTimeEnd = $("#createTimeEnd").val();

                   var type =$(this).data('type');
                   active[type] ? active[type].call(this):'';
                });

                $("#toolBar").keydown(function(event){
                    if(event.keyCode == 13){
                        refreshPage();
                    }
                });
                refreshPage = function(){
                    $('#btnSearch').trigger("click");
                }
            });
            function toStatus(status){
                if(status == '0'){
                    return "正常"
                }
                if(status == '1'){
                    return "删除"
                }
            }
        </script>
</body>
</html>
