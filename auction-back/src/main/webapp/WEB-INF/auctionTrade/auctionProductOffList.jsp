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
				<option value="">全部</option>
				<c:forEach items="${classifyList}" var="st">
					<option value="${st.id}">${st.name}</option>
				</c:forEach>
			</select>
		</div>
		商品名称:
		<div class="layui-inline">
			<input class="layui-input" id="productName" name="productName" autocomplete="off">
		</div>
		拍品ID:
		<div class="layui-inline">
			<input class="layui-input" id="productId" name="productId" onblur="checkNumber()" lay-verify="number" autocomplete="off">
		</div>
		<%--拍品数量:
		<div class="layui-inline">
			<select name="bargain" id="bargain" style="width: 168px;height: 33px;" >
				<option value="">全部</option>
				<option value="2">由高到低</option>
				<option value="1">由低到高</option>
			</select>
		</div>--%>
		下架时间:
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
	<script src="<%=common%>/system/auctionRule/auctionProductOffList.js" charset="utf-8"></script>

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
                        title:"下架拍品查看",
                        type: 2,
                        area: ['100%', '100%'],
                        btn : ['取消' ],
                        content: '/auctionProduct/on/preview/'+data.id
                    });

                }
            });
			table.render({
				elem : '#manage-data',
				even : true,
				url : '${path}/auctionProduct/off/getManagePage',
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
                    fixed : "left",
                    width: '7%'
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
                    width : '14%',
					title : '商品名称'
				}, {
                    field : 'classifyName',
                    align : 'center',
                    width : '6%',
                    title : '分类'
                }, {
                    field : 'productNum',
                    align : 'center',
                    width : '17%',
                    title : '剩余总库存'
                }, {
                    field : 'productPrice',
                    title : '商品价格',
                    align : 'center',
                    width : '7%',
                }, {
					field : 'underShelfTime',
					title : '下架时间',
					align : 'center',
					sort : true,
                    width : '10%',
                    templet : '<div>{{toDate(d.underShelfTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
				}, {
                    field : 'createTime',
                    title : '创建时间',
                    align : 'center',
                    sort : true,
                    width : '15%',
                    templet : '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
                },/* {
                    field : 'bargain',
                    title : '成交数量',
                    align : 'center',
                    sort : true,
                    width : '10%'
                },*/
                    {field:'right',
						title: '操作',
						style: 'height:220',
						width:300,toolbar:"#barOperate"}
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
                                classify1: $('#classify1').val(),
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
                        $.get("batchDel?ids=" + arr, function (data, status) {
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
	  	上架
	{{# } else if(d.status == 2) { }}
		上架
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