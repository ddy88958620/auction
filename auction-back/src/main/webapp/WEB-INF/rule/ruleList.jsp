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
</head>
<body>
<div id="toolBar">
	规则ID:
	<div class="layui-inline">
		<input class="layui-input" id="id" onblur="checkNumber()" lay-verify="number" autocomplete="off" name="id">
	</div>
	规则名称:
	<div class="layui-inline">
		<input class="layui-input" id="ruleName" autocomplete="off">
	</div>
	规则状态:
	<div class="layui-inline">
		<select name="status" id="status" style="width: 168px;height: 33px;" lay-verify="">
			<option value="">请选择</option>
			<option value="1">已上架</option>
			<option value="2">未上架</option>
		</select>
	</div>
	上架时间:
	<div class="layui-inline">
		<input type="text" name="startTime" id="startTime" lay-verify="date"  autocomplete="off" class="layui-input">
	</div>
	至
	<div class="layui-inline">
		<input class="layui-input" name="endTime" id="endTime">
	</div>

	<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
		<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
	</button>
	<br />
</div>

<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script type="text/html" id="barOperate">
	<a class="layui-btn layui-btn-primary layui-btn-mini" style="align-content: center" lay-event="detail">查看</a>

	{{#  if(d.status == 1 ){ }}

	{{# } else if(d.status == 2) { }}
	<a class="layui-btn " style="text-align: center"  lay-event="enable">上架</a>
	{{# } else if(d.status == 3) { }}

	{{#  } else { }}

	{{#  } }}

</script>

<table class="layui-hide" id="data" lay-filter="batchOn" >
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    var saveUserRole;
    var saveRoleWindow;
    layui.use(['table','form','laydate'], function() {
        findBtns(null, "${parentId}");
        table = layui.table;
        form = layui.form;
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startTime',
            type: 'date'
        });
        laydate.render({
            elem: '#endTime',
            type: 'date'
        });

		//监听表格复选框选择
        table.on('checkbox(batchOn)', function(obj){
            console.log(obj)
        });
        //监听工具条
        table.on('tool(batchOn)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.open({
                    title:"查看",
                    type: 2,
                    area: ['100%', '100%'],
                    btn : ['取消' ],
                    content: 'previewAuctionRule/'+data.id
                });

            }else if(obj.event === 'edit'){

            }else if(obj.event === 'enable'){
                $.get("enable/" + data.id, function (data, status) {
                    if (data.code == '0') {
                        window.location.reload();
                    } else {
                        layer.msg("上架失败:"+data.msg);
                    }
                });

            }
        });


        table.render({
            elem : '#data',
            even : true,
            url : '${path}/rule/findListPage',
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
            },{
                field : 'id',
                title : '规则Id',
                align : 'center',
				width:'5%',
                fixed : 'left'

            }, {
                field : 'ruleName',
                title : '规则名称',
                align : 'center',
                width:'7%'
            }, {
                field : 'premiumAmount',
                title : '加价幅度',
                align : 'center',
                width:'7%'
            },{
                field : 'refundMoneyProportion',
                title : '退币比例',
                align : 'center',
                width:'7%'
            },{
                field : 'openingBid',
                title : '起拍价',
                align : 'center',
                width:'7%'
            },{
                field : 'differenceFlag',
                title : '差价购买',
                align : 'center',
                width:'10%',
                templet : '<div>{{toType(d.differenceFlag)}}</div>'
            },{
                field : 'poundage',
                align : 'center',
                title : '手续费',
                width:'7%'
            }, {
                field : 'status',
                title : '类型状态',
                align : 'center',
                width:'7%',
                templet : '<div>{{toStatus(d.status)}}</div>'
            }, {
                field : 'onShelfTime',
                title : '上架时间',
                align : 'center',
                width:'12%',
                templet : '<div> {{toDate(d.onShelfTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
            }, {
                field : 'productNum',
                title : '商品数量',
                align : 'center',
                width:'7%'
            },{	field:'right',
                title: '操作',
                align: "center",
                width: 280, toolbar: "#barOperate"
            }
            ] ]

        });
        var $ = layui.$, active = {
            reload : function() {
                if(checkNumber()) {
                    //执行重载
                    table.reload('search', {
                        page: {
                            //重新从第 1 页开始
                            page: 1
                        },
                        where: {
                            ruleName: $('#ruleName').val(),
                            id: $('#id').val(),
                            status: $("#status").val(),
                            beginTime: $("#startTime").val(),
                            endTime: $("#endTime").val()

                        }
                    });
                }
            }
        };
        $('#btnSearch').on('click', function() {
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
    function checkNumber() {
        var integer = new RegExp("^\\d+$");
        var id = $("#id").val();
        if(id != null && id != undefined && id != '') {
            if (!integer.test(id)) {
                layer.msg('规则ID只能是整数', function () {
                });
                return false;
            }
        }
        return true;
    }
  //差价标识  1.可以差价购买 2.不可以差价购买
    function toType(type){
        if(type =='1'){
            return "可以差价购买";
        }
        if(type =='2'){
            return "不可以差价购买";
        }
    }
//类型状态  1.启用 2.禁用
    function toStatus(status){
        if (status == '1') {
            return "已上架";
        }
        if (status == '2') {
            return "未上架";
        }
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>

<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>

</body>
</html>