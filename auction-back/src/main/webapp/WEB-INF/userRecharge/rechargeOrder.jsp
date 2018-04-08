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
<script src="<%=common%>/system/appraises/appraises2.js" charset="utf-8"></script>
</head>
<body>
	<div id="toolBar">
		用户Id:
		<div class="layui-inline">
			<input class="layui-input" id="userId"  autocomplete="off">
		</div>
		订单Id:
		<div class="layui-inline" >
			<input class="layui-input" id="orderNo" autocomplete="off" >
		</div>
		手机号:
		<div class="layui-inline">
			<input class="layui-input" id="userPhone" autocomplete="off">
		</div>
		&nbsp;&nbsp;&nbsp;&nbsp;充值渠道:
		<div class="layui-inline layui-form" style="width: 120px;height: 33px;" >
			<select id="rechargeType" style="width: 120px;height: 33px;" >
				<option value="">全部</option>
				<option value="2">微信充值</option>
				<option value="3">支付宝充值</option>
			</select>
		</div>
		&nbsp;&nbsp;&nbsp;&nbsp;充值金额:
		<div class="layui-inline">
			<input class="layui-input" id="outMoneyBegin" placeholder="￥" autocomplete="off" size="10" value="${params.outMoneyBegin}">
		</div>
		元&nbsp;到&nbsp;
		<div class="layui-inline">
			<input class="layui-input" id="outMoneyEnd" placeholder="￥" autocomplete="off" size="10" value="${params.outMoneyEnd}">
		</div>
		元
		<div class="divider" style="margin-top: 14px;"></div>
		充值时间:
		<div class="layui-inline" style="width: 160px;height: 33px;">
			<input class="layui-input" id="createTimeBegin" lay-verify="date"  placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.createTimeBegin}">
		</div>
		&nbsp;至&nbsp;
		<div class="layui-inline" style="width: 160px;height: 33px;">
			<input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.createTimeEnd}">
		</div>
		<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
			<i class="layui-icon layui-anim layui-anim-scale" >&#xe615;</i> 搜索
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
        layui.use(['table','form','laydate'], function() {
            findBtns(null, "${parentId}");

            var laydate = layui.laydate;

            laydate.render({
                elem: '#createTimeBegin'
            });
            laydate.render({
                elem: '#createTimeEnd'
            });

            table = layui.table;
            form = layui.form;
			table.render({
				elem : '#data',
				even : true,
				url : '${path}/order/getRechargeOrder',
				height : "full-58",
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
					type : 'checkbox',
					fixed : 'left'
				}, {
					type : 'numbers',
					align : 'center',
					title : '序号',
					fixed : "left",
                    width : 130
				}, {
					field : 'orderNo',
					align : 'center',
					title : '订单Id',
                    width : 240
				}, {
					field : 'userId',
					title : '用户ID',
					align : 'center',
                    width : 180
				}, {
					field : 'userPhone',
					title : '用户手机号',
					align : 'center',
					width : 140
				},{
                    field : 'outMoney',
                    title : '充值金额',
                    align : 'center',
                    width : 150,
                    templet : '<div>{{toMoney(d.outMoney)}}</div>'
                },{
                    field : '',
                    title : '手续费',
                    align : 'center',
                    width : 150,
                }, {
                    field : 'rechargeType',
                    title : '充值渠道',
                    align : 'center',
                    width : 150,
                    templet : '<div>{{toStatus(d.rechargeType)}}</div>'
                },{
                    field : 'tradeStatus',
                    title : '交易状态',
                    align : 'center',
                    width : 150,
                    templet : '<div>{{status(d.tradeStatus)}}</div>'
                },{
                    field : 'payStatus',
                    title : '支付状态',
                    align : 'center',
                    width : 150,
                    templet : '<div>{{payStatus(d.payStatus)}}</div>'
                },{
                    field : 'createTime',
                    title : '充值时间',
                    align : 'center',
                    sort : true,
                    width : 240,
                    templet : '<div>{{toDate(d.createTime)}}</div>'
                }] ]

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
                            userId : $('#userId').val(),
                            orderNo : $('#orderNo').val(),
                            realName : $('#realName').val(),
                            userPhone : $('#userPhone').val(),
                            status : $('#status').val(),
                            tradeStatus : $('#tradeStatus').val(),
                            rechargeType : $('#rechargeType').val(),
                            outMoney : $('#outMoney').val(),
                            createTimeBegin : $('#createTimeBegin').val(),
                            createTimeEnd : $('#createTimeEnd').val(),
                            outMoneyBegin : $('#outMoneyBegin').val(),
                            outMoneyEnd : $('#outMoneyEnd').val(),
						}
					});
				}
			};
			$('#btnSearch').on('click', function() {
                var userId = $('#userId').val();
                var orderNo = $('#orderNo').val();
                var realName = $('#realName').val();
                var userPhone = $('#userPhone').val();
                var status = $('#status').val();
                var tradeStatus = $('#tradeStatus').val();
                var rechargeType = $('#rechargeType').val();
                var outMoney = $('#outMoney').val();
                var createTimeBegin = $('#createTimeBegin').val();
                var createTimeEnd = $('#createTimeEnd').val();
                var outMoneyBegin = $('#outMoneyBegin').val();
                var outMoneyEnd = $('#outMoneyEnd').val();

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
		function toStatus(rechargeType) {
            if (rechargeType != '2' && rechargeType != '3'){
                return rechargeType;
            }
		    if(rechargeType =='2'){
				return "微信充值";
			}
            if(rechargeType =='3'){
                return "支付宝充值";
            }
        }
		function status(tradeStatus) {
            if (tradeStatus != '1' && tradeStatus != '2' && tradeStatus != '3'){
                return tradeStatus;
            }
		    if(tradeStatus =='1'){
				return "充值中";
			}
			if(tradeStatus =='2'){
                return "充值成功";
            }
			if(tradeStatus =='3'){
                return "充值失败";
            }
        }
		function payStatus(payStatus) {
            if (payStatus != '1' && payStatus != '2' && payStatus != '3'){
                return "未完成";
            }else if(payStatus =='1'){
                return "充值中";
            }else if(payStatus =='2'){
				return "支付成功";
			}else if(payStatus =='3'){
                return "支付失败";
            }
        }
        function toAddress(provinceName,cityName) {
			return provinceName+cityName;
        }
        function toMoney(money) {
            return Math.floor(money) / 100  ;
        }
	</script>
</body>
</html>