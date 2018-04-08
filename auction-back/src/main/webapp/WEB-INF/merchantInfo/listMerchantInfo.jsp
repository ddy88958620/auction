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
    <title>商家</title>
    <c:set var="path" value="<%=path%>"></c:set>
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<form class="layui-form" onsubmit="return false;">
<div id="toolBar">

    手机:
    <div class="layui-inline">
        <input class="layui-input" id="phone" autocomplete="off">
    </div>
    商家名称:
    <div class="layui-inline">
        <input class="layui-input" id="merchantName" autocomplete="off">
    </div>

    商家类型:
    <div class="layui-inline">
        <select class="layui-input" id="merchantType" name="merchantType">
             <option value=" ">全部</option>
            <c:forEach var="item" items="${merchantType}">
                <option value="${item.key}" <c:if test="${item.key eq params.merchantType}">selected="selected"</c:if>>${item.value}</option>
            </c:forEach>
        </select>
    </div>
    商家状态:
    <div class="layui-inline">
        <select class="layui-input" id="status" name="status">
            <option value=" ">全部</option>
            <c:forEach var="item" items="${merchantStatus}">
                <option value="${item.key}" <c:if test="${item.key eq params.status}">selected="selected"</c:if>>${item.value}</option>
            </c:forEach>
        </select>
    </div>

    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
    </button>


</div>
</form>
<table class="layui-hide" id="data">
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    var saveUserRole;
    var saveRoleWindow;
    layui.use(['table','form'], function() {
        findBtns(null, "${parentId}");
        table = layui.table;
        form = layui.form;
        table.render({
            elem : '#data',
            even : true,
            url : '${path}/merchantInfo/listMerchantInfo',
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
                fixed : 'left',
                width: 150
            }/*, {
                type : 'numbers',
                align : 'center',
                title : '序号',
                fixed : "left",
                width: 100
            }*/,{
                field : 'id',
                align : 'center',
                title : '商家Id',
                fixed : "left",
                width: 150
            },{
                field : 'merchantName',
                title : '商家名称',
                align : 'center',
                sort : true,
                fixed : "left",
                width: 150
            }, {
                field : 'merchantType',
                align : 'center',
                title : '商家类型',
                width: 150,
                templet : '<div>{{toMerchantType(d.merchantType)}}</div>'
            }, {
                field : 'phone',
                title : '联系方式',
                align : 'center',
                width: 200
            }, {
                field : 'status',
                title : '状态',
                align : 'center',
                width : 150,
                templet : '<div>{{toStatus(d.status)}}</div>'
            }, {
                field : 'userIp',
                title : '操作人Ip',
                align : 'center',
                width : 150,
                sort : true
            }, {
                field : 'userId',
                title : '操作人Id',
                align : 'center',
                sort : true,
                width: 150
            }, {
                field : 'createTime',
                title : '添加时间',
                align : 'center',
                width : 220,
                templet : '<div>{{toDate(d.createTime)}}</div>'
            }, {
                field : 'updateTime',
                title : '更新时间',
                align : 'center',
                width : 220,
                templet : '<div>{{toDate(d.updateTime)}}</div>'
            } ] ]

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
                        merchantName : $('#merchantName').val(),
                        phone : $('#phone').val(),
                        status: $('#status').val(),
                        merchantType:$('#merchantType').val()
                    }
                });
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

    function toStatus(status){
        if (status == '0') {
            status = "启用";
        } else if (status == '1') {
            status = "停用";
        }
        return status;
    }
    function toMerchantType(merchantType){
        if(merchantType == '0'){
            merchantType = "第三方";
        }else if(merchantType == '1'){
            merchantType = "渠道";
        }else if(merchantType == '2'){
            merchantType = "京东";
        }
        return merchantType;
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
<script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>

</body>
</html>