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
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
    <script src="<%=common%>/system/qrcode.min.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道ID:
    <div class="layui-inline">
        <input class="layui-input" id="channelId" autocomplete="off" value="${params.channelId}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道名称:
    <div class="layui-inline">
        <input class="layui-input" id="channelName" autocomplete="off" value="${params.channelName}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道来源:
    <div class="layui-inline">
        <input class="layui-input" id="channelSource" autocomplete="off" value="${params.channelSource}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道负责人姓名:
    <div class="layui-inline">
        <input class="layui-input" id="personInCharge" autocomplete="off" value="${params.personInCharge}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道对接人姓名:
    <div class="layui-inline">
        <input class="layui-input" id="pickUp" autocomplete="off" value="${params.pickUp}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    渠道方联系电话:
    <div class="layui-inline">
        <input class="layui-input" id="contactPhone" autocomplete="off" value="${params.contactPhone}">
    </div>
    <!--<br/><br/>-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    创建时间:
    <div class="layui-inline">
        <input class="layui-input" id="createTimeBegin" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="1" autocomplete="off" value="${params.createTimeBegin}">
    </div>
    &nbsp;&nbsp;至&nbsp;&nbsp;
    <div class="layui-inline">
        <input class="layui-input" id="createTimeEnd" lay-verify="date" placeholder="yyyy-MM-dd" lay-key="2" autocomplete="off" value="${params.createTimeEnd}">
    </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <div class="layui-inline" style="display: none;">
        <select id="cooperationMode" autocomplete="off">
            <c:forEach var="item" items="${cooperationModeList}">
                <option id="${item.cooperation_mode}" value="${item.cooperation_mode}">${item.cooperation_str}</option>
            </c:forEach>
        </select>
    </div>
    <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
        <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 查询
    </button>
</div>

<table class="layui-hide" id="data">
</table>

<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/channel/channelViewUtils.js" charset="utf-8"></script>

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
            url : '${path}/channel/findList',
            height : "full-130",
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
                field : 'channelId',
                title : '渠道ID',
                align : 'center',
                fixed : "left",
                sort : true,
                width : 180
            }, {
                field : 'channelName',
                title : '渠道名称',
                align : 'center',
                fixed : "left",
                width : 90
            }, {
                field : 'channelSource',
                title : '渠道来源',
                align : 'center',
                fixed : "left",
                width : 90
            },{
                field : 'provinceName',
                align : 'center',
                title : '省市地区',
                templet : '<div>{{toAddress(d.provinceName, d.cityName, d.townName)}}</div>',
                width : 200
            }, {
                field : 'cooperationMode',
                title : '合作方式',
                align : 'center',
                width : 90,
                templet : '<div>{{toCooperationMode(d.cooperationMode)}}</div>',
            },
                /*{
                field : 'settlementMode',
                title : '结算方式',
                align : 'center',
                width : 90
            },*/
                {
                field : 'settlementPrice',
                title : '结算单价',
                align : 'center',
                templet : '<div>{{toMoney(d.settlementPrice)}}</div>',
                width : 90
            },{
                field : 'startTime',
                title : '开始时间',
                //templet : '<div>{{toDate(d.startTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                align : 'center',
                width : 90
            }, {
                field : 'personInCharge',
                title : '负责人',
                align : 'center',
                width : 90
            }, {
                field : 'pickUp',
                title : '对接人',
                align : 'center',
                width : 90
            }, {
                field : 'contactPhone',
                title : '联系方式',
                align : 'center',
                width : 120
            }, {
                field : 'contactEmail',
                title : '联系邮箱',
                align : 'center',
                width : 190
            }, {
                field : 'extensionUrl',
                title : '推广链接',
                align : 'center',
                width : 210
            }, {
                field : 'extensionQrc',
                title : '推广二维码',
                templet : '<div>{{toQrc(d.extensionQrc)}}</div>',
                align : 'center',
                width : 90
            }, {
                field : 'createTime',
                title : '创建时间',
                templet : '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>',
                align : 'center',
                sort : true,
                width : 170
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
                        channelId : $('#channelId').val(),
                        channelName : $('#channelName').val(),
                        channelSource : $('#channelSource').val(),
                        personInCharge : $('#personInCharge').val(),
                        pickUp : $('#pickUp').val(),
                        contactPhone : $('#contactPhone').val(),
                        createTimeBegin : $('#createTimeBegin').val(),
                        createTimeEnd : $('#createTimeEnd').val()
                    }
                });
            }
        };
        $('#btnSearch').on('click', function() {
            var channelId = $('#channelId').val();
            var channelName = $('#channelName').val();
            var personInCharge = $('#personInCharge').val();
            var pickUp = $('#pickUp').val();
            var contactPhone = $('#contactPhone').val();
            var createTimeBegin = $('#createTimeBegin').val();
            var createTimeEnd = $('#createTimeEnd').val();
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

    function toQrc(url) {
        return "<a btn='${parentId}' href='#' onclick=\"showWindowToChannel(this,\'showWindowToChannel2\');\" url='/channel/getQrc' param='"+ url +"'>查看</a>";
    }

    showWindowToChannel = function(obj, callBack) {
        var url = $(obj).attr("url");
        var param = $(obj).attr("param");
        // 打开窗口的时候按钮的id就是下一个页面的父id
        var pId = $(obj).attr("btn");
        document.activeElement.blur();
        htmlWindow.title = '查看二维码';
        $.ajax({
            type : "post",
            url : path + url,
            data : {
                qrcUrl : param
            },
            dataType : "json",
            beforeSend : function() {
                layer.load();
            },
            success : function(result) {
                if (result.code == '0') {
                    var data = result.data;
                    var id = result.id;
                    eval(callBack + '(' + JSON.stringify(data) + ')');
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    }

    showWindowToChannel2 = function(data) {
        var content = "";
        content += '<div class="layui-input-block" style="width: 30%;margin-left: 220px;">'
        content += '<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">'
        content += '<div class="layui-upload-img"'
        content += 'style="width: 100px; height: 100px;" id="imgUrlSl" name="imgUrlSl">'
        content += '</div>'
        content += '</blockquote>'
        content += '</div>'
        htmlWindow['content'] = content;
        layer.open(htmlWindow);
        makeCode(data);
    }

    function makeCode (data) {
        var qrcode = new QRCode(document.getElementById("imgUrlSl"), {
            width : 100,
            height : 100
        });
        qrcode.makeCode(data);
    }

    function toAddress(proName, cityName, townName) {
        return proName + cityName +townName;
    }

    function toCooperationMode(cooperationMode){
        //获取select元素的引用
        var eduElement = document.getElementById("cooperationMode");
        //1 获取所有的option标签
        var optionElements = eduElement.getElementsByTagName("option");
        //2 遍历option
        for(var i = 0; i<optionElements.length; i++){
            var optionElement = optionElements[i];
            var text = optionElement.text;
            var value = optionElement.value;
            if (value == cooperationMode) {
                cooperationMode = text;
            }
        }
        return cooperationMode;
    }

    function toMoney(money) {
        return Math.floor(money) / 100  ;
    }
</script>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/channel/channelUtils.js" charset="utf-8"></script>
<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
</body>
</html>