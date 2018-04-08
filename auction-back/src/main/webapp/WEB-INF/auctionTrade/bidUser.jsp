<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
	String photoPathSuffix = PropertiesUtils.get("aliyun.oos.domain");
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
	<link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
	<link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
	<link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
	出价类型:
	<div class="layui-inline">
		<select name="bidType" id="bidType" style="width: 168px;height: 33px;" lay-verify="">
			<option value="">请选择</option>
			<option value="1">有效</option>
			<option value="2">赠币</option>
			<option value="3">VIP用户</option>
		</select>
	</div>
	用户类型:
	<div class="layui-inline">
		<select name="bidSubType" id="bidSubType" style="width: 168px;height: 33px;" lay-verify="">
			<option value="">请选择</option>
			<option value="1">单一</option>
			<option value="2">委托</option>
			<option value="3">VIP用户</option>
		</select>
	</div>
	<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
		<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
	</button>
</div>
<table class="layui-hide" id="data" lay-filter="batchOn">
</table>
<script type="text/javascript">
    var mId = "${parentId}";
    var userId = '${userId}';
    var auctionProdId = '${auctionProdId}';
    var auctionNo='${auctionId}';
    var subUserId='${subUserId}';
    var path = "";
    var table;
    var form;
    layui.use(['table','form','laydate'], function() {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startTime',
            type: 'datetime'
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime'
        });

        findBtns(null, mId);

        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/auctionInfo/bidUserList?'+'userId='+userId+'&auctionProdId='+auctionProdId+'&auctionNo='+auctionNo
            +"&subUserId="+subUserId,
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
                fixed : "left"
            },{
                field : 'userName',
                title : '用户名称',
                align : 'center',
                sort : true,
                width : '10%'
            }, {
                field : 'bidId',
                title : '出价表ID',
                align : 'center',
                width : '10%'
            },{
                field : 'bidType',
                title : '出价类型',
                align : 'center',
                templet:'<div>{{toBidType(d.bidType)}}</div>',
                width : '10%'
            },{
                field : 'userIp',
                title : '用户IP',
                align : 'center',
                width : '10%'
            },{
                field : 'bidPrice',
                title : '出价金额',
                align : 'center',
                width : '10%'
            },{
                field : 'bidSubType',
                title : '用户类型',
                align : 'center',
                width : '10%',
                sort : false,
                templet:'<div>{{toBidSubType(d.bidSubType)}}</div>'

            }, {
                field: 'address',
                title: '地址',
                align: 'center',
                width : '10%'
            }, {
                field : 'createTime',
                title : '创建时间',
                align : 'center',
                sort : true,
                width : '10%',
                templet :'<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
           },{
                field : 'updateTime',
                title : '修改时间',
                align : 'center',
                sort : true,
                width : '10%',
                templet : '<div>{{toDate(d.updateTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
            }
            ] ]

        });

        if(userId == undefined || auctionProdId == undefined){
            //执行重载
            table.reload('search', {
                page : {
                    //重新从第 1 页开始
                    page : 1
                },
                where : {

                }
            });
		}else {
            //执行重载
            table.reload('search', {
                page: {
                    //重新从第 1 页开始
                    page: 1
                },
                where: {
                    userId: userId,
                    auctionProdId: auctionProdId,
					auctionNo:auctionNo,
					subUser:subUserId
                }
            });
        }



        var $ = layui.$, active = {
            reload : function() {
                //执行重载
                table.reload('search', {
                    page : {
                        //重新从第 1 页开始
                        page : 1
                    },
                    where : {
                        userName : $('#userName').val(),
                        auctionProdId : $('#auctionProdId').val(),
                        userType : $('#userType').val(),
                        userId : $('#userId').val(),
                        status : $('#status').val(),
                        bidType : $('#bidType').val(),
                        bidSubType : $('#bidSubType').val(),
                        auctionNo:$('#auctionNo').val()
                    }
                });
            },
            getCheckData: function(){
                //获取选中数据
                var checkStatus = table.checkStatus('data');
                var  data = checkStatus.data;
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
    //用户类型  1.单一 2.委托 3.机器人
    function toBidSubType(type){
        if (!type) {
            return "";
        }
        if (type == 1) {
            return "单一";
        }
        if (type == 2) {
            return "委托";
        }
        if (type == 3) {
            return "VIP用户";
        }
    }
    //出价类型  1.有效 2.赠币 3.机器人
    function toBidType(type){
        if(!type){
            return "";
        }

        if (type == 1) {
            return "有效";
        }
        if (type == 2) {
            return "赠币";
        }
        if (type == 3) {
            return "VIP用户";
        }
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionTrade/auctionView.js" charset="utf-8"></script>
</body>
</html>