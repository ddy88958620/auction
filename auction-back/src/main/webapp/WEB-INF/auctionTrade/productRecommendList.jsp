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
		商品ID:
		<div class="layui-inline">
			<input class="layui-input" id="productId" name="productId" onblur="checkNumber()" lay-verify="number" autocomplete="off">
		</div>
		商品名称:
		<div class="layui-inline">
			<input class="layui-input" id="productName" name="productName" autocomplete="off">
		</div>
		<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
			<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
		</button>
		<br />
	</div>

	<table class="layui-hide" id="manage-data" lay-filter="batchOn">
	</table>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/auctionTrade/productRecommendList.js" charset="utf-8"></script>
	<script type="text/html" id="barOperate">
		<a class="layui-btn layui-btn-mini" style="vertical-align: middle" lay-event="edit">修改排序</a>
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
			//监听表格复选框选择
            table.on('checkbox(batchOn)', function(obj){
                //console.log(obj)
            });
            //监听工具条
            table.on('tool(batchOn)', function(obj){
                var data = obj.data;
                if(obj.event === 'edit'){
                    layer.open({
                        title:"排序修改",
                        type: 2,
                        area: ['550px', '450px'],
                        btn : ['取消' ],
                        content: '/auctionRecommend/preview/'+data.auctionProdId
                    });
                }
            });
			table.render({
				elem : '#manage-data',
				even : true,
				url : '${path}/auctionRecommend/getManagePage',
				height : "full-200",
				method : 'post',
				id : 'search',
                page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout : [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],//自定义分页布局//,curr: 5 //设定初始在第 5 页
                    groups : 5,//只显示 1 个连续页码
                    limits : [ 10, 20, 50, 100, 200 ],
                    first : "首页",
                    theme : "#FF5722;",
                    limit : 10,
                    last : "尾页"

                },
				cols : [ [ {
					type : 'checkbox',
					fixed : 'left'
				}, {
					field : 'productId',
					title : '商品ID',
					align : 'center',
					sort : true,
					fixed : "left",
					width: 150
				}, {
					field : 'productName',
					align : 'center',
                    width : 300,
					title : '拍品名称'
				}, {
                    field : 'bidPrice',
                    align : 'center',
                    width : 200,
                    title : '拍品价格'
                }, {
                    field : 'previewPic',
                    align : 'center',
                    width : '25%',
                    title : '拍品图片',
                    templet:'<div><img  src="<%=photoPathSuffix%>/{{ d.previewPic}}"></div>'
                }, {
                    field : 'auctionId',
                    align : 'center',
                    width : 150,
                    title : '拍品期次ID'
                }
                , {
					field : 'status',
					title : '状态',
					align : 'center',
					width : 150,
					sort : false,
					templet:"#recommendStatusScript"

				}, {
                        field : 'sort',
                        title : '排序',
                        align : 'center',
                        width : 100,
                        sort : false

                    },{field:'right',
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
                                productId: $("#productId").val(),
                                productName: $("#productName").val()
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
                               // alert("删除成功");
                                window.location.reload();
                            } else {
                                layer.msg("失败:"+data.msg);
                            }
                        });
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
                    layer.msg('商品ID只能是整数', function () {
                    });
                    return false;
                }
            }
            return true;
        }

</script>
<script type="text/html" id="recommendStatusScript">
	{{#  if(d.status == 0 ){ }}
	启用
	{{# } else if(d.status == 1) { }}
	正在拍
	{{# } else if(d.status == 2) { }}
	正在拍
	{{# } else if(d.status == 3) { }}
	未开始
	{{#  } else if(d.status == 4) { }}
	下架
	{{#  } }}
</script>

</body>
</html>