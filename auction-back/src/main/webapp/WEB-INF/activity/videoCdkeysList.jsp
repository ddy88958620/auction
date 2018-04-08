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
    <c:set var="path" value="<%=path%>"/>
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar" class="layui-form-item" style="margin: 10px;">
    <div class="layui-inline">
        <div class="layui-form-label">兑换码：</div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="cdkey" name="cdkey"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-form-label" style="width: 100px;">兑换码类型：</div>
        <div class="layui-input-inline">
            <select class="layui-input" name="cdkeyType" id="cdkeyType" autocomplete="off">
                <option value="">请选择</option>
                <c:forEach var="item" items="${allVideoCdkeysType}">
                    <option value="${item.key}">${item.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-form-label">是否使用：</div>
        <div class="layui-input-inline">
            <select class="layui-input" name="isUsed" id="isUsed" autocomplete="off">
                <option value="">请选择</option>
                <option value="1">未使用</option>
                <option value="2">已使用</option>
            </select>
        </div>
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 查询
    </button>
    <button type="button" class="layui-btn" id="importExcel">
        <i class="layui-icon">&#xe67c;</i>上传数据
    </button>
</div>

<table class="layui-hide" id="data">
</table>
<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    var upload;
    layui.use(['table','form','upload'], function() {
        findBtns(null, "${parentId}");
        table = layui.table;
        form = layui.form;
        upload = layui.upload;

        table.render({
            elem : '#data',
            even : true,
            url : '${path}/videoCdkeys/findList',
            height : "full-90",
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
            },/*{
                field : 'id',
                title : '兑换码id',
                align : 'center',
                sort : true,
                fixed : "left"
            },*/ {
                field : 'cdkey',
                title : '兑换码',
                align : 'center',
                sort : true,
                fixed : "left"
            }, {
                field : 'cdkeyType',
                title : '兑换码类型',
                align : 'center',
                templet : '<div>{{toType(d.cdkeyType)}}</div>',
                sort : true,
                fixed : "left"
            },{
                field : 'cdkeyName',
                title : '兑换码名称',
                align : 'center',
                sort : true,
                fixed : "left"
            },{
                field : 'usefulLife',
                title : '有效期',
                align : 'center',
                templet : '<div>{{toDate(d.usefulLife,\'yyyy-MM-dd\')}}</div>',
                sort : true
            },{
                field : 'activateUrl',
                title : '激活地址',
                align : 'center',
                sort : true
            },{
                field : 'isUsed',
                align : 'center',
                title : '是否使用',
                templet : '<div>{{toUsed(d.isUsed)}}</div>',
                sort : true
            }, {
                field : 'addTime',
                title : '导入时间',
                align : 'center',
                templet : '<div>{{toDate(d.addTime)}}</div>',
                sort : true
            }] ]
        });

        upload.render({
            elem: '#importExcel'
            , url: '${path}/videoCdkeys/toImplExcel'
            , accept: 'file'
            , exts: 'xls|xlsx'
            , done: function (res) {
                layer.msg(res.msg);
                table.reload('search');
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
                        cdkey : $('#cdkey').val(),
                        cdkeyType : $('#cdkeyType').val(),
                        isUsed : $('#isUsed').val()
                    }
                });
            }
        };

        $('#btnSearch').on('click', function() {
            var cdkey = $('#cdkey').val();
            var cdkeyType = $('#cdkeyType').val();
            var isUsed = $('#isUsed').val();

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
        if (type == '301') {
            return "爱奇艺月卡";
        }
    }
    function toUsed(isUsed){
        if (isUsed == '1') {
            return "未使用";
        }
        if (isUsed == '2') {
            return "已使用";
        }
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
</body>
</html>