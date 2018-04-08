<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
	<div id="toolBar">
		分类:
		<div class="layui-inline">
			<select name="classifyId" id="classifyId" style="width: 168px;height: 33px;" lay-verify="">
				<option value="">请选择</option>
				<c:forEach items="${classifyList}" var="st">
					<option value="${st.id}">${st.name}</option>
				</c:forEach>
			</select>
		</div>
		拍品名称:
		<div class="layui-inline">
			<input class="layui-input" id="productName" name="productName" autocomplete="off">
		</div>
		拍品ID:
		<div class="layui-inline">
			<input class="layui-input" id="productId" name="productId" onblur="checkNumber()" lay-verify="number" autocomplete="off">
		</div>
		拍品状态:
		<div class="layui-inline">
			<select name="productStatus" id="productStatus" style="width: 168px;height: 33px;" lay-verify="">
				<option value="">请选择</option>
				<option value="1">在售中</option>
				<option value="2">即将开始</option>
			</select>
		</div>
	<%--	成交数量:
		<div class="layui-inline">
			<select name="remainNum" id="remainNum" style="width: 168px;height: 33px;" >
				<option value="">全部</option>
				<option value="2">由高到低</option>
				<option value="1">由低到高</option>
			</select>
		</div>
		成交价:
		<div class="layui-inline">
			<input type="text" name="beginPrice" id="beginPrice" lay-verify="number"  autocomplete="off" class="layui-input">
		</div>
		至
		<div class="layui-inline">
			<input class="layui-input" lay-verify="number" name="endPrice" id="endPrice">
		</div><br />--%>
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
		<br />
	</div>


	<table class="layui-hide" id="manage-data" lay-filter="batchOn">
	</table>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/auctionRule/auctionProductOnList.js" charset="utf-8"></script>

	<script type="text/html" id="barOperate">
		<a class="layui-btn layui-btn-primary layui-btn-mini" style="align-content: center" lay-event="detail">查看</a>
	</script>
	<script type="text/javascript">
		var myId = "${parentId}";
		var path = "${path}";
		var table;
		var form;
		layui.use(['table','form','laydate'], function() {
			findBtns(null, "${parentId}");

			table = layui.table;
			form = layui.form;
			laydate = layui.laydate;

            laydate.render({
                elem: '#beginTime',
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
                        title:"上架拍品查看",
                        type: 2,
                        area: ['100%', '100%'],
                        btn : ['取消' ],
                        content: '/auctionProduct/on/preview/'+data.id
                    });

                } else if(obj.event === 'edit'){
                    layer.open({
                        title:"上架拍品编辑",
                        type: 2,
                        area: ['550px', '450px'],
                        btn : ['取消' ],
                        content: '/auctionProduct/on/editPre/'+data.id
                    });
                }
            });
			table.render({
				elem : '#manage-data',
				even : true,
				url : '${path}/auctionProduct/on/getManagePage',
				height : "full-78",
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
                    field : 'id',
                    title : '拍品ID',
                    align : 'center',
                    sort : true,
                    fixed : "left",
                    width: '6%'
                }, {
					field : 'productId',
					title : '商品ID',
					align : 'center',
					sort : true,
					fixed : "left",
					width: '7%'
				}, {
					field : 'productName',
					align : 'center',
                    width : '13%',
					title : '商品名称'
				}, {
                    field : 'classifyName',
                    align : 'center',
                    width : '6%',
                    title : '分类'
                }

                    , {
                    field : 'ruleName',
                    align : 'center',
                    width : '7%',
                    title : '当前适用规则'
                },  {
					field : 'productStatus',
					title : '拍品状态',
					align : 'center',
					width : '6%',
					sort : false,
					templet:"#productStatusScript"

				}, {
					field : 'beginTime',
					title : '上架时间',
					align : 'center',
					sort : true,
                    width : '9%',
                    templet : '<div>{{toDate(d.onShelfTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
				}, {
                    field : 'auctionStartTime',
                    align : 'center',
                    width : '9%',
                    title : '开拍时间',
                    templet : '<div>{{toDate(d.auctionStartTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
                }, {
                    field : 'openingBid',
                    title : '起拍价',
                    align : 'center',
                    width : '7%',
                }, {
                    field : 'productPrice',
                    title : '市场价',
                    align : 'center',
                    width : '7%',
                }, {
                    field : 'premiumAmount',
                    title : '加价幅度(元)',
                    align : 'center',
                    width : '7%',
                },{
                    field : 'sort',
                    title : '热区排序',
                    align : 'center',
                    width : '5%',
                },/* {
                    field : 'bargain',
                    title : '总成交数量',
                    align : 'center',
                    width : '7%',
                }, {
                    field : 'bargainPrice',
                    title : '总成交价',
                    align : 'center',
                    width : '7%',
                }*/
                    {field:'right',
						title: '操作',
						style: 'height:120',
						width:200,toolbar:"#barOperate"}
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
                                productId: $('#productId').val(),
                                productName: $('#productName').val(),
                                productStatus: $('#productStatus').val(),
                                beginTime: $('#beginTime').val(),
                                endTime: $('#endTime').val(),
                                classifyId: $('#classifyId').val(),
                                beginPrice: $("#beginPrice").val(),
                                endPrice: $("#endPrice").val(),
                                bargain: $("#bargain").val()
                            }
                        });
                    }
				},
                getCheckData: function(){
				    //获取选中数据
                    var checkStatus = table.checkStatus('manage-data');
					var  data = checkStatus.data;
                }
			};

			$("#getDelData").on("click",function(){
                var checkStatus = table.checkStatus('search');
                var  data = checkStatus.data;
                var arr = [];
                $.each(data,function(i,item){
                    arr[i] = item.productId;
                });
                if(arr.length == 0) {
                    layer.msg('未选中要操作的行', function () {
                    });
                }else {
                    layer.confirm('确定要操作吗?', {
                        icon : 3,
                        title : '警告'
                    }, function(index) {
                        //批量删除
                        $.get("batchOff?ids=" + arr, function (data, status) {
                            if (data.code == '0') {
                                //alert("删除成功");
                                window.location.reload();
                            } else {
                               layer.msg("失败:"+data.msg);
                            }
                        });
					});
                }
			});
            $('#getCheckData').on('click', function() {
                //获取选中数据
                var checkStatus = table.checkStatus('search');
                var  data = checkStatus.data;
                var arr = [];
                $.each(data,function(i,item){
                    arr[i] = item.productId;
				});

				//批量上架
				if(arr.length >=1) {
                    $.get("batchOn?ids=" + arr, function (data, status) {
                        if (data.code == '0') {
                            //alert("上架成功");
                            window.location.reload();
                        } else {
                            layer.msg('失败', function() {
                            });
                        }
                    });
                }else{
                    layer.msg('未选中要操作的行', function() {
                    });
				}
            });
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
    var productId = $("#productId").val();
    if(productId != null && productId != undefined && productId != '') {
        if (!integer.test(productId)) {
            layer.msg('拍品ID只能是整数', function () {
            });
            return false;
        }
    }
    return true;
}


</script>
<script type="text/html" id="productStatusScript">
	{{#  if(d.status == 1 ){ }}
	  	在售中
	{{# } else if(d.status == 2) { }}
		即将开始
	{{# } else if(d.status == 3) { }}
		上架
	{{# }else { }}
		未上架
	{{#  } }}
</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
	<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
	<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
	<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
</body>
</html>