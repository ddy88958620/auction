<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<%
    String path = request.getContextPath() + "";
    String common = path + "/resources/";
    String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");
%>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>${website.site_name }</title>
    <c:set var="path" value="<%=path%>"></c:set>
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">

    活动id:
    <div class="layui-inline" style="margin-top:15px;">
        <input class="layui-input" id="activityId" name="activityId" autocomplete="off">
    </div>
    活动名称:
    <div class="layui-inline">
        <input class="layui-input" id="activityName" name="activityName" lay-verify="number" autocomplete="off">
    </div>

    活动时间:
    <div class="layui-inline">
        <input type="text" name="startTime" id="startTime"
               lay-verify="date" autocomplete="off" class="layui-input">
    </div>
    至
    <div class="layui-inline">
        <input class="layui-input" name="endTime" id="endTime">
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
    </button>
</div>

<%--<div class="layui-btn-group demoTable">
    <button class="layui-btn" data-type="getDelData" id="getDelData">删除</button>
</div>--%>
<%--<input type="hidden" value="${entrances}" id="entrances">--%>
<table class="layui-hide" id="manage-data" lay-filter="batchOn">
</table>

<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
</script>
<script type="text/javascript">

    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    var saveUserRole;
    var saveRoleWindow;


    layui.use(['table', 'form', 'laydate'], function () {
        findBtns(null, "${parentId}");

        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;

        laydate.render({
            elem: '#startTime',
            type: 'date'
        });
        laydate.render({
            elem: '#endTime',
            type: 'date'
        });
        //监听表格复选框选择
//            table.on('checkbox(batchOn)', function(obj){
//                console.log(obj)
//            });
        //监听工具条
        table.on('tool(batchOn)', function (obj) {
            var data = obj.data;
//            if (obj.event === 'detail') {
//                layer.msg('ID：' + data.productId + ' 的查看操作');
//            } else if (obj.event === 'del') {
//                layer.confirm('真的删除行么', function (index) {
//                });
//            } else if (obj.event === 'edit') {
//                layer.msg('编辑ID：' + data.productId + ' 的查看操作');
//            }

            var data = obj.data;
            if(obj.event === 'view'){
                layer.open({
                    title:"分享活动查看",
                    content: 'shareActivityView?activityId='+data.id,
                    type : 2,
                    shade : 0.3,
                    maxmin : true,
                    area : [ '100%', '100%' ],
                    anim : 1,
                    btnAlign : 'r',
                    btn : [ '取消' ],
                    success : function(layero, index) {
                    },
//                    yes : function(index, layero) {
//                        document.getElementById("layui-layer-iframe" + index).contentWindow.submitForm();
//                    },
//                    btn2 : function(index, layero) {
//                        document.getElementById("layui-layer-iframe" + index).contentWindow.resetForm();
//                        return false;
//                    }
                });
            }

            if(obj.event === 'edit'){
                layer.open({
                    title:"分享活动编辑",
                    content: 'shareActivityEdit?activityId='+data.id,
                    type : 2,
                    shade : 0.3,
                    maxmin : true,
                    area : [ '100%', '100%' ],
                    anim : 1,
                    btnAlign : 'r',
                    btn : [ '提交', '重置', '取消' ],
                    success : function(layero, index) {
                    },
                    yes : function(index, layero) {
                        document.getElementById("layui-layer-iframe" + index).contentWindow.submitForm();
                    },
                    btn2 : function(index, layero) {
                        document.getElementById("layui-layer-iframe" + index).contentWindow.resetForm();
                        return false;
                    }
                });
            }
        });
        table.render({
            elem: '#manage-data',
            even: true,
            url: '${path}/shareActivity/getActivityList',
            height: "full-78",
            method: 'post',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],//自定义分页布局//,curr: 5 //设定初始在第 5 页
                groups: 5,//只显示 1 个连续页码
                limits: [10, 20, 50, 100, 200],
                first: "首页",
                theme: "#FF5722;",
                limit: 10,
                last: "尾页"

            },
            id: 'search',
            cols: [[{
                field: 'id',
                title: '活动ID',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '5%'
            }, {
                field: 'entrance',
                title: '分享入口',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'activityName',
                title: '活动名称',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'sharerPoints',
                title: '分享者奖励(积分)',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'sharerCoin',
                title: '分享者奖励(赠币)',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'registerPoints',
                title: '新注册奖励(积分)',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'registerCoin',
                title: '新注册奖励(赠币)',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '10%'
            }, {
                field: 'startTime',
                title: '活动时间',
                align: 'left',
                sort: true,
                fixed: "left",
                width: '20%',
                templet: '<div>{{toDate(d.startTime,\'yyyy-MM-dd hh:mm:ss\')}} - {{toDate(d.endTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
            }, {
                field: 'status',
                title: '状态',
                align: 'center',
                sort: true,
                fixed: "left",
                width: '5%',
                templet: '<div>{{toStatus(toDate(d.startTime,\'yyyy-MM-dd hh:mm:ss\'),toDate(d.endTime,\'yyyy-MM-dd hh:mm:ss\'))}}</div>'
            },{
                fixed: 'right',
                title : '操作',
                width:'10%',
                align:'center',
                toolbar: '#barDemo'
            }

            ]]

        });
        var $ = layui.$, active = {
            reload: function () {
                //执行重载
                table.reload('search', {
                    page: {
                        //重新从第 1 页开始
                        page: 1
                    },
                    where: {
                        id: $('#activityId').val(),
                        activityName: $('#activityName').val(),
                        startTime: $('#startTime').val(),
                        endTime: $('#endTime').val()
                    }
                });
            },
            getCheckData: function () {
                //获取选中数据
                var checkStatus = table.checkStatus('manage-data');
                var data = checkStatus.data;
            }
        };


        $('#getCheckData').on('click', function () {
            //获取选中数据
            var checkStatus = table.checkStatus('search');
            var data = checkStatus.data;
            var arr = [];
            $.each(data, function (i, item) {
                arr[i] = item.productId;
            });
            //alert(JSON.stringify(arr));
            //批量下架
            /* $.get("batchOff?ids="+arr,function(data,status){
             if(data.code == '0'){
             alert("下架成功");
             window.location.reload();
             }else {
             alert("失败");
             }
             });*/
            //批量上架
            if (arr.length >= 1) {
                $.get("batchOn?ids=" + arr, function (data, status) {
                    if (data.code == '0') {
                        //alert("上架成功");
                        window.location.reload();
                    } else {
                        layer.msg('失败', function () {
                        });
                    }
                });
            } else {
                layer.msg('未选中要操作的行', function () {
                });
            }
        });
        $('#btnSearch').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';

        });
        $("#toolBar").keydown(function (event) {
            if (event.keyCode == 13) {
                refreshPage();
            }
        });
        refreshPage = function () {
            $('#btnSearch').trigger("click");
        }

    });
    function toStatus(date1,date2){
        if (compareDate(new Date(),date1,date2)) {
            return "开启";
        }else {
            return "未开启";
        }

    }
    function compareDate(curTime,startTime,endTime) {
        var startDate =  new Date(startTime);
        var endDate =  new Date(endTime);
        return (curTime>=startDate && curTime<=endDate);
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
</body>
</html>