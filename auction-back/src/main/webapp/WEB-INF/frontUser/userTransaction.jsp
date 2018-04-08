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
<form class="layui-form" onsubmit="return false;">
<div id="toolBar" style="margin: 10px;">
    用户Id:
    <div class="layui-inline">
        <input class="layui-input" id="userId" autocomplete="off">
    </div>
    手机号:
    <div class="layui-inline">
        <input class="layui-input" id="userPhone" autocomplete="off">
    </div>
    交易类型:
    <div class="layui-inline">
        <select class="layui-input" id="transactionType" style="padding-left: 40px;padding-right: 40px;">
            <option selected="selected" value=""></option>
            <c:forEach var="item" items="${transactionType}">
                <option value="${item.key}" <c:if test="${item.key eq params.transactionType}">selected="selected"</c:if>>${item.value}</option>
            </c:forEach>
        </select>
    </div>
    账户类型:
    <div class="layui-inline">
        <select class="layui-input" id="accountType" style="padding-left: 27px;padding-right: 27px;">
            <option selected="selected" value=""></option>
            <c:forEach var="item" items="${accountType}">
                <option value="${item.key}" <c:if test="${item.key eq params.accountType}">selected="selected"</c:if>>${item.value}</option>
            </c:forEach>
        </select>
    </div>

    时间：
    <div class="layui-inline">
        <input class="layui-input" id="addTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.addTimeBegin}">
    </div>
    &nbsp;至&nbsp;
    <div class="layui-inline">
        <input class="layui-input" id="addTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.addTimeEnd}">
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
    </button>
</div>

<table class="layui-hide" id="data">
</table>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    layui.use(['table', 'form', 'laydate'], function () {
        findBtns(null, "${parentId}");
        var laydate = layui.laydate;

        var laydate = layui.laydate;

        laydate.render({
            elem: '#addTimeBegin'
        });
        laydate.render({
            elem: '#addTimeEnd'
        });

        table = layui.table;
        form = layui.form;
        table.render({
            elem: '#data',
            even: true,
            url: '${path}/user/getTransactionInfo',
            height: "full-70",
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
                type: 'checkbox',
                fixed: 'left'
            }, {
                type: 'numbers',
                align: 'center',
                title: '序号',
                fixed: "left",
                width: 120
            }, {
                field: 'userId',
                align: 'center',
                title: '用户Id',
                width: 120
            }, {
                field: 'userPhone',
                title: '手机号',
                align: 'center',
                width: 200
            }, {
                field: 'orderNo',
                title: '订单号',
                align: 'center',
                width: 280
            }, {
                field: 'transactionCoin',
                title: '本次交易金额',
                align: 'center',
                width: 160 ,
                templet: '<div>{{toMoney(d.transactionCoin)}}</div>'
            }, {
                field: 'transactionType',
                title: '交易类型',
                align: 'center',
                width: 180,
                templet: '<div>{{toTransaction(d.transactionType)}}</div>'
            }, {
                field: 'balanceType',
                title: '收支类型',
                align: 'center',
                width: 160,
                templet: '<div>{{toBalance(d.balanceType)}}</div>'
            }, {
                field: 'accountType',
                title: '账户类型',
                align: 'center',
                width: 160,
                templet: '<div>{{toAccount(d.accountType)}}</div>'
            }, {
                field: 'createTime',
                title: '交易时间',
                align: 'center',
                sort: true,
                width: 280,
                templet: '<div>{{toDate(d.createTime)}}</div>'
            }]]

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
                        userId: $('#userId').val(),
                        userPhone: $('#userPhone').val(),
                        accountType: $('#accountType').val(),
                        transactionType: $('#transactionType').val(),
                        addTimeBegin: $('#addTimeBegin').val(),
                        addTimeEnd: $('#addTimeEnd').val(),
                    }
                });
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
    function toAccount(accountType) {
        if (accountType == '1') {
            return "拍币";
        }
        if (accountType == '2') {
            return "赠币";
        }
        if (accountType == '3') {
            return "积分";
        }
        if (accountType == '4') {
            return "开心币";
        }
    }

    var transactionTypes = eval('${transactionTypeArray}');
    function toTransaction(transactionType) {
        for (var obj in transactionTypes) {
            if (transactionTypes[obj].key == transactionType) {
                return transactionTypes[obj].value;
            }
        }
    }
    function toBalance(balanceType) {
        if (balanceType == '1') {
            return "收入";
        }
        if (balanceType == '2') {
            return "支出";
        }
    }
    function toMoney(transactionCoin) {
        return Math.floor(transactionCoin) / 100  ;
    }
</script>

</form>
</body>
</html>
