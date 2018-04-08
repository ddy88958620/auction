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
		推送类型:
		<div class="layui-inline"  >
			<select class="layui-input" id="notiType" style="padding-left: 27px;padding-right: 27px;">
				<option selected="selected" value="">全部</option>
				<c:forEach var="item" items="${notiType}">
					<option value="${item.key}" <c:if test="${item.key eq params.notiType}">selected="selected"</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</div>
		推送标题:
		<div class="layui-inline">
			<input class="layui-input" id="title" autocomplete="off" value="${params.title}">
		</div>
		任务类型:
		<div class="layui-inline"  >
			<select class="layui-input" id="sendStatus" style="padding-left: 27px;padding-right: 27px;">
				<option selected="selected" value="">全部</option>
				<c:forEach var="item" items="${sendStatus}">
					<option value="${item.key}" <c:if test="${item.key eq params.sendStatus}">selected="selected"</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</div>
		<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
			<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
		</button>
	</div>

	<table class="layui-hide" id="data" lay-filter="dataTable">
	</table>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/push/record/utils.js" charset="utf-8"></script>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        {{#  if(d.status == 1){ }}
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="disable">禁用</a>
        {{#  } }}
    </script>
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
				url : '${path}/notiRecord/list',
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
				},{
                    field : 'notiType',
                    title : '推送类型',
                    align : 'center',
                    width : 120,
                    templet : '<div>{{toNotiType(d.notiType)}}</div>'
                },{
                    field : 'subject',
                    title : '标题',
                    align : 'center',
                    width : 120
                },{
                    field : 'title',
                    title : '推送标题',
                    align : 'center',
                    width : 120
                },{
                    field : 'createTime',
                    title : '创建时间',
                    align : 'center',
                    sort : true,
                    width : 180,
                    templet : '<div>{{toDate(d.createTime)}}</div>'
                },{
                    field : 'deviceCount',
                    title : '接受对象',
                    align : 'center',
                    width : 120
                },{
                    field : 'userName',
                    title : '发布人员',
                    align : 'center',
                    width : 120
                }/*,{
				    fixed: 'right',
                    title : '操作',
                    width:150,
                    align:'center',
                    toolbar: '#barDemo'
				}*/] ]

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
                            notiType : $('#notiType').val(),
                            title : $('#title').val(),
                            sendStatus : $('#sendStatus').val()
						}
					});
				}
			};
			$('#btnSearch').on('click', function() {
                var notiType = $('#notiType').val();
                var title = $('#title').val();
                var sendStatus = $('#sendStatus').val();

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
		function toNotiType(notiType) {
		    if(notiType =='1'){
				return "链接";
			}
			if(notiType =='2'){
                return "活动";
            }
            if(notiType =='3'){
                return "拍品";
            }
        }
	</script>
</form>
</body>
</html>