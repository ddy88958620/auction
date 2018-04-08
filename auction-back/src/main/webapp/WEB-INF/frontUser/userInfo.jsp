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
		用户Id:
		<div class="layui-inline">
			<input class="layui-input" id="id" autocomplete="off">
		</div>
		手机号:
		<div class="layui-inline">
			<input class="layui-input" id="userPhone" autocomplete="off">
		</div>
		用户状态:
		<div class="layui-inline"  >
			<select class="layui-input" id="status" style="padding-left: 27px;padding-right: 27px;">
				<option selected="selected" value="">全部</option>
				<c:forEach var="item" items="${status}">
					<option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</div>
		是否充值:
		<div class="layui-inline"  >
			<select class="layui-input" id="rechargeType" style="padding-left: 27px;padding-right: 27px;">
				<option selected="selected" value="">全部</option>
				<c:forEach var="item" items="${rechargeTypeList}">
					<option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</div>
		注册时间:
		<div class="layui-inline">
			<input class="layui-input" id="addTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd"  autocomplete="off" value="${params.addTimeBegin}">
		</div>
		&nbsp;至&nbsp;
		<div class="layui-inline">
			<input class="layui-input" id="addTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd"  autocomplete="off" value="${params.addTimeEnd}">
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
		<a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		{{#  if(d.status == 1){ }}
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="disable">禁用</a>
		{{#  } }}
	</script>
	<script src="<%=common%>/system/user/groupSendSms.js" charset="utf-8"></script>
	<script src="<%=common%>/system/user/excelExport.js" charset="utf-8"></script>
	<script>
        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        layui.use(['table','form','laydate'], function() {
            findBtns(null, "${parentId}");
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
                elem : '#data',
                even : true,
                url : '${path}/user/getUserInfo',
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
                    type : 'checkbox',
                    fixed : 'left'
                }, {
                    type : 'numbers',
                    align : 'center',
                    title : '序号',
                    fixed : "left",
                    width : 100
                }, {
                    field : 'id',
                    align : 'center',
                    title : '用户Id',
                    width : 120
                }, {
					field : 'channelSource',
					title : '渠道来源',
					align : 'center',
                    width : 120
				}, {
                    field : 'userPhone',
                    title : '手机号',
                    align : 'center',
                    width : 120
                },{
                    field : 'wxNickName',
                    title : '关联微信',
                    align : 'center',
                    width : 120
                },{
                    field : 'qqNickName',
                    title : '关联QQ',
                    align : 'center',
                    width : 120
                },{
                    field : 'rechargeMoney',
                    title : '首充金额',
                    align : 'center',
                    width : 180,
                    templet : '<div>{{toMoney(d.rechargeMoney)}}</div>'
                }, {
                    field : 'coin1',
                    title : '拍币余额',
                    align : 'center',
                    width : 120,
                    templet : '<div>{{toMoney(d.coin1)}}</div>'
                }, {
                    field : 'coin2',
                    align : 'center',
                    title : '赠币余额',
                    width : 120,
                    templet : '<div>{{toMoney(d.coin2)}}</div>'
                }, {
                    field : 'coin4',
                    align : 'center',
                    title : '购物币余额',
                    width : 120,
                    templet : '<div>{{toMoney(d.coin4)}}</div>'
                }, {
                    field : 'coin3',
                    align : 'center',
                    title : '积分余额',
                    width : 120,
                    templet : '<div>{{toMoney(d.coin3)}}</div>'
                }, {
                    field : 'status',
                    title : '状态',
                    align : 'center',
                    width : 120,
                    templet : '<div>{{toStatus(d.status)}}</div>'
                },{
                    field : 'rechargeType',
                    title : '充值状态',
                    align : 'center',
                    width : 120,
                    templet : '<div>{{status(d.rechargeType)}}</div>'
                },{
                    field : 'provinceName',
                    title : '收货地址',
                    align : 'center',
                    width : 120,
                    templet : '<div>{{toAddress(d.provinceName,d.cityName)}}</div>'
                },{
                    field : 'addTime',
                    title : '注册时间',
                    align : 'center',
                    sort : true,
                    width : 180,
                    templet : '<div>{{toDate(d.addTime)}}</div>'
                },{
                    field : 'terminalSign',
                    title : '终端标识',
                    align : 'center',
                    sort : true,
                    width : 180
                },{
                    fixed: 'right',
                    title : '操作',
                    width:150,
                    align:'center',
                    toolbar: '#barDemo'
                }] ]

            });

            //监听工具条
            table.on('tool(dataTable)', function(obj){
                var data = obj.data;
                if(obj.event === 'edit'){
                    layer.open({
                        title:"用户信息编辑",
                        content: 'edit?id='+data.id,
                        type : 2,
                        shade : 0.3,
                        maxmin : true,
                        area : [ '100%', '100%' ],
                        anim : 1,
                        btnAlign : 'r',
                        btn : [ ],
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
                }else if(obj.event === 'disable'){
                    layer.confirm('确定要操作吗?', {
                        icon : 3,
                        title : '警告'
                    }, function() {
                        $.ajax({
                            type : "post",
                            url : "editUpdate",
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
                }else if(obj.event === 'view'){
                    layer.open({
                        title:"",
                        content: 'view?id='+data.id ,
                        type : 2,
                        shade : 0.3,
                        maxmin : true,
                        area : [ '100%', '100%' ],
                        anim : 1,
                        btnAlign : 'r',
                        btn : [  '返回' ],
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
                            id : $('#id').val(),
                            userPhone : $('#userPhone').val(),
                            status : $('#status').val(),
                            addTimeBegin : $('#addTimeBegin').val(),
                            addTimeEnd : $('#addTimeEnd').val(),
							rechargeType : $('#rechargeType').val()
						}
					});
				}
			};
            $('#btnSearch').on('click', function() {
                var id = $('#id').val();
                var userPhone = $('#userPhone').val();
                var status = $('#status').val();
                var addTimeBegin = $('#addTimeBegin').val();
                var addTimeEnd = $('#addTimeEnd').val();
                var rechargeType = $('#rechargeType').val();

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
        function toStatus(status) {
            if(status =='1'){
                return "启用";
            }
            if(status =='2'){
                return "注销";
            }
        }
        function status(status) {
            if(status =='1'){
                return "未充值";
            }else if(status =='2'){
                return "首充";
            }else if(status =='3'){
                return "多次";
            }else if(status =='4'){
                return "首冲反币等待中"
            }else if(status =='5'){
                return "首冲反币成功";
            }else if(status =='6'){
                return "首冲拍品成功";
            }else {
                return "其他";
            }
        }
        function toAddress(provinceName,cityName) {
            return provinceName+cityName;
        }
        function toMoney(money) {
            return Math.floor(money) / 100  ;
        }
	</script>
</form>
</body>
</html>