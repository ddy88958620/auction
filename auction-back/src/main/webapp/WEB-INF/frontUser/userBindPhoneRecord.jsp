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
    <script src="<%=common%>/system/appraises/appraises2.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar" style="margin: 10px;">
    <input type="hidden" id="userId" value="${userId}"/>
    <button style="display: none" class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
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
    layui.use(['table', 'form'], function () {
        findBtns(null, "${parentId}");

        var laydate = layui.laydate;

        table = layui.table;
        form = layui.form;
        table.render({
            elem: '#data',
            even: true,
            url: '${path}/user/bindRecord',
            where:{
                id:${id}
            },
            height: "full-58",
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
                fixed: "left"
            }, {
                field: 'userId',
                title: '用户Id',
                align: 'center'
            }, {
                field: 'userPhone',
                title: '手机号',
                align: 'center'
            }, {
                field: 'userLastPhone',
                title: '上次绑定手机号',
                align: 'center',
            }, {
                field: 'addTime',
                title: '添加时间',
                align: 'center',
                templet: '<div>{{toDate(d.addTime)}}</div>'
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
        refreshPage();
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
            return "购物币";
        }
    }
    function toTransaction(transactionType) {
        if (transactionType == '1') {
            return "注册赠送";
        }
        if (transactionType == '2') {
            return "微信充值";
        }
        if (transactionType == '3') {
            return "支付宝充值";
        }
        if (transactionType == '4') {
            return "充值赠送";
        }
        if (transactionType == '5') {
            return "竞拍消费";
        }
        if (transactionType == '6') {
            return "订单拍币返回";
        }
        if (transactionType == '7') {
            return "取消差价购买";
        }
        if (transactionType == '8') {
            return "签到获取积分";
        }
        if (transactionType == '9') {
            return "积分兑换赠币";
        }
        if (transactionType == '10') {
            return "签到送积分";
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

    function toTransactionCoin(transactionCoin) {
        return "拍币+"+transactionCoin;
    }
</script>
</body>
</html>