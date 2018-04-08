<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
    用户ID:
    <div class="layui-inline">
        <input class="layui-input" id="userId" name="userId" onblur="checkNumber()" lay-verify="number" autocomplete="off">
    </div>
    用户名称:
    <div class="layui-inline">
        <input class="layui-input" id="userName" name="userName" lay-verify="number" autocomplete="off">
    </div>
    状态:
    <div class="layui-inline">
        <select name="auctionStatus" id="auctionStatus" style="width: 168px;height: 33px;" lay-verify="">
            <option value="">请选择</option>
            <option value="1">正在拍</option>
            <option value="2">已拍中</option>
            <option value="3">未拍中</option>
            <option value="4">不能差价购</option>
        </select>
    </div>
    用户类型:
    <div class="layui-inline">
        <select name="userType" id="userType" style="width: 168px;height: 33px;" lay-verify="">
            <option value="">请选择</option>
            <option value="1">真实用户</option>
            <option value="2">VIP用户</option>
        </select>
    </div>
    <div class="layui-form-item">
    </div>
    上架时间:
    <div class="layui-inline">
        <input type="text" name="beginTime" id="beginTime" lay-verify="date"  autocomplete="off" class="layui-input">
    </div>
    至
    <div class="layui-inline">
        <input class="layui-input" name="endTime" id="endTime">
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
    </button>
</div>
<table class="layui-hide" id="data" lay-filter="batchOn">
</table>
<script type="text/html" id="barOperate">
    <a class="layui-btn" lay-event="detail">出价用户</a>
</script>
<script type="text/javascript">
    var mId = "${parentId}";
    var auctionId = '${auctionId}';
    var auctionProdId = '${auctionProdId}';
    var path = "${path}";
    layui.use(['table', 'form', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#beginTime',
            type: 'date'
        });
        laydate.render({
            elem: '#endTime',
            type: 'date'
        });

        findBtns(null, mId);

        table = layui.table;
        form = layui.form;
        //监听表格复选框选择
        table.on('checkbox(batchOn)', function (obj) {
            //console.log(obj)
        });
        //监听工具条
        table.on('tool(batchOn)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.open({
                    type: 2,
                    area: ['100%', '100%'],
                    content: 'bidUser?' + "userId=" + data.userId + "&auctionProdId=" + data.auctionProdId + "&auctionId=" + data.auctionId
                    + "&subUserId=" + data.subUserId
                });

            }
        });

        table.render({
            elem: '#data',
            even: true,
            url: '${path}/auctionInfo/viewAuctionInfoList?' + '&auctionProdId=' + auctionProdId + '&auctionId=' + auctionId,
            height: "full-58",
            method: 'post',

            done: function (res, curr, count) {

                $("[data-field='subUserId']").css('display', 'none');
            },

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
                type: 'checkbox',
                fixed: 'left'
            }, {
                type: 'numbers',
                align: 'center',
                title: '序号',
                fixed: "left"
            }, {
                field: 'userId',
                title: '用户ID',
                align: 'center',
                sort: true,
                width: '5%'
            }, {
                field: 'subUserId',
                title: '机器人',
                align: 'center',
                sort: true,
                width: '5%'
            }, {
                field: 'userName',
                title: '用户名称',
                align: 'center',
                sort: true,
                width: '12%'
            }, {
                field: 'auctionProdId',
                align: 'center',
                width: '5%',
                title: '拍品ID'
            }, {
                field: 'auctionId',
                align: 'center',
                width: '7%',
                title: '拍品期数ID'
            }, {
                field: 'bidCount',
                align: 'center',
                width: '7%',
                title: '出价次数'
            }, {
                field: 'returnPrice',
                align: 'center',
                width: '7%',
                title: '返还购物币'
            }, {
                field: 'nickName',
                title: '昵称',
                align: 'center',
                width: '12%'
            }, {
                field: 'headImg',
                title: '头像',
                align: 'center',
                width: '10%',
                sort: false,
                templet: '<div><img  src="{{ d.headImg}}"></div>'

            }, {
                field: 'auctionStatus',
                title: '状态',
                align: 'center',
                width: '5%',
                sort: false,
                templet: '<div>{{toStatus(d.auctionStatus)}}</div>'

            }, {
                field: 'userType',
                title: '用户类型',
                align: 'center',
                width: '7%',
                sort: false,
                templet: '<div>{{toUserType(d.userType)}}</div>'

            }, {
             field: 'createTime',
             title: '上架时间',
             align: 'center',
             sort: true,
             width: '12%',
             templet: '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
             }, {
                field: 'right',
                title: '操作',
                style: 'height:120',
                width: '10%', toolbar: "#barOperate"
            }
            ]]

        });

        //执行重载
        table.reload('search', {
            page: {
                //重新从第 1 页开始
                page: 1
            },
            where: {
                auctionId: auctionId,
                auctionProdId: auctionProdId
            }
        });

        var $ = layui.$, active = {
            reload: function () {

                if(checkNumber()) {
                    //执行重载
                    table.reload('search', {
                        page: {
                            //重新从第 1 页开始
                            page: 1
                        },
                        where: {
                            id: auctionId,
                            auctionProdId: auctionProdId,
                            userId: $('#userId').val(),
                            userName: $('#userName').val(),
                            userType: $('#userType').val(),
                            beginTime: $('#beginTime').val(),
                            endTime: $('#endTime').val(),
                            status: $('#auctionStatus').val()
                        }
                    });
                }
            }
        };

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
    function checkNumber() {
        var integer = new RegExp("^\\d+$");
        var userId = $("#userId").val();
        if(userId != null && userId != undefined && userId != '') {
            if (!integer.test(userId)) {
                layer.msg('用户ID只能是整数', function () {
                });
                $("#userId").focus();
                return false;
            }
        }
        return true;
    }


    //类型状态  1.正在拍 2.已拍中 3.未拍中
    function toStatus(status) {
        if (status == '1') {
            return "正在拍";
        }
        if (status == '2') {
            return "已拍中";
        }
        if (status == '3') {
            return "未拍中";
        }
        if (status = '4') {
            return "不能差价购";
        }
    }
    //类型状态  1.机器人 2.真实用户
    function toUserType(type) {
        if (!type) {
            return "";
        }

        if (type == '1') {
            return "真实用户";
        }
        if (type == '2') {
            return "VIP用户";
        }
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionTrade/batchOn.js" charset="utf-8"></script>
</body>
</html>