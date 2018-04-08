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
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	分类:
	<div class="layui-inline layui-form" style="width: 100px;">
		<select id="channelType" class="layui-input" id="channelType" autocomplete="off">
			<option value="-999">全部</option>
			<c:forEach var="item" items="${messageTypeList}">
				<option id="${item.help_type}" value="${item.help_type}" <c:if test="${item.help_type eq params.channelType}">selected="selected"</c:if>>${item.help_str}</option>
			</c:forEach>
		</select>
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	标题:
	<div class="layui-inline">
		<input class="layui-input" id="contentTitle" autocomplete="off" value="${params.contentTitle}">
	</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	摘要:
	<div class="layui-inline">
		<input class="layui-input" id="contentSummary" autocomplete="off" value="${params.contentSummary}">
	</div>
	<button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch" style="margin-left: 20px;">
		<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 查询
	</button>
</div>

<table class="layui-hide" id="data">
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    layui.use(['table','form','laydate'], function() {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#createTimeBegin'
        });
        laydate.render({
            elem: '#createTimeEnd'
        });

        findBtns(null, "${parentId}");

        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/messageCenter/findList',
            height : "full-75",
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
            }, {
                field : 'id',
                title : '编号',
                align : 'center',
                fixed : "left",
                sort : true
            }, {
                field : 'contentTitle',
                title : '标题',
                align : 'center',
                fixed : "left"
            }, {
                field : 'contentSummary',
                align : 'center',
                title : '摘要'
            }, {
                field : 'addUserId',
                title : '发布人',
                align : 'center'
            }, {
                field : 'addTime',
                title : '发布时间',
                align : 'center',
                templet : '<div>{{toDate(d.addTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                sort : true
            }, {
                field : 'addIp',
                title : '发布人IP',
                align : 'center'
            }, {
                field : 'updateUserId',
                title : '修改人',
                align : 'center',
                templet : '<div>{{toUpdateUserId(d.updateUserId)}}</div>'
            }, {
                field : 'channelType',
                title : '分类',
                align : 'center',
                templet : '<div>{{toType(d.channelType)}}</div>'
            }, {
                field : 'updateTime',
                title : '最后更新时间',
                align : 'center',
                templet : '<div>{{toDate(d.updateTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                sort : true
            }, {
                field : 'orderNum',
                title : '排序',
                align : 'center',
                sort : true
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
                        channelType : $('#channelType').val(),
                        contentTitle : $('#contentTitle').val(),
                        contentSummary : $('#contentSummary').val()
                    }
                });
            }
        };
        $('#btnSearch').on('click', function() {
            var channelType = $('#channelType').val();
            var contentTitle = $('#contentTitle').val();
            var contentSummary = $('#contentSummary').val();
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

    function toType(type){
        //获取select元素的引用
        var eduElement = document.getElementById("channelType");
        //1 获取所有的option标签
        var optionElements = eduElement.getElementsByTagName("option");
        //2 遍历option
        for(var i = 0; i<optionElements.length; i++){
            var optionElement = optionElements[i];
            var text = optionElement.text;
            var value = optionElement.value;
			if (value == type) {
			    type = text;
			}
        }
        return type;
    }

    function toUpdateUserId(type){
        if (null == type || type == '') {
            type = "";
        }
        return type;
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/messagecenter/messageUtils.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
</body>
</html>