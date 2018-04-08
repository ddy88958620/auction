<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
    分类:
    <div class="layui-inline">
        <select name="classifyName" id="classifyName" style="width: 168px;height: 33px;" lay-verify="">
            <option value="">请选择</option>
            <c:forEach items="${classifyList}" var="st">
                <option value="${st.id}">${st.name}</option>
            </c:forEach>
        </select>
    </div>
    拍品ID:
    <div class="layui-inline">
        <input class="layui-input" id="id" name="id" onblur="checkNumber()" lay-verify="number"autocomplete="off">
    </div>
    拍品名称:
    <div class="layui-inline">
        <input class="layui-input" id="productName" name="productName" autocomplete="off">
    </div>
    拍品状态:
    <div class="layui-inline">
        <select name="status" id="status" style="width: 168px;height: 33px;" lay-verify="">
            <option value="">请选择</option>
            <option value="1">已上架</option>
            <option value="2">未上架</option>
            <option value="4">已下架</option>
        </select>
    </div>
    <div class="layui-form-item">
    </div>
    上架时间:
    <div class="layui-inline">
        <input type="text" name="onShelfTime" id="onShelfTime" lay-verify="date"  autocomplete="off" class="layui-input">
    </div>
    至
    <div class="layui-inline">
        <input class="layui-input" name="underShelfTime" id="underShelfTime">
    </div>
    <div class="layui-inline">
        <button class="layui-btn layui-btn-radius" data-type="reload" id="btnSearch">
            <i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i> 搜索
        </button>
    </div>
</div>
<script type="text/html" id="barOperate">
    <a class="layui-btn layui-btn-primary layui-btn-mini" style="align-content: center" lay-event="detail">查看</a>

</script>
<table class="layui-hide" id="manage-data" lay-filter="batchOn" ></table>
    <script type="text/javascript">

        var myId = "${parentId}";
        var path = "${path}";
        var table;
        var form;
        var saveUserRole;
        var saveRoleWindow;


        layui.use(['table', 'form', 'laydate'], function () {
            findBtns(null, "${parentId}");

            table = layui.table;
            form = layui.form;
            laydate = layui.laydate;

            laydate.render({
                elem: '#onShelfTime',
                type: 'datetime'
            });
            laydate.render({
                elem: '#underShelfTime',
                type: 'datetime'
            });
            //监听表格复选框选择
            table.on('checkbox(batchOn)', function (obj) {
            });
            //监听工具条
            table.on('tool(batchOn)', function (obj) {
                var data = obj.data;
                if(obj.event === 'detail'){
                    layer.open({
                        title:"拍品查看",
                        type: 2,
                        area: ['100%', '100%'],
                        btn : ['取消' ],
                        content: '/auctionProduct/on/preview/'+data.id
                    });

                }
            });
            table.render({
                scrollbar: false,
                elem: '#manage-data',
                even: true,
                url: '${path}/auctionProduct/auctionlist',
                height: "full-78",
                method: 'post',
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],//自定义分页布局//,curr: 5 //设定初始在第 5 页
                    groups: 5,//只显示 1 个连续页码
                    limits: [10, 20, 50, 100, 200],
                    first: "首页",
                    theme: "#FF5722;",
                    limit: 10,
                    last: "尾页"

                },
                id: 'search',
                cols: [[{
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                    type: 'numbers',
                    align: 'center',
                    title: '序号',
                    fixed: "left"
                },
                    {
                        field: 'id',
                        title: '拍品ID',
                        align:'center',
                        width: '6%',
                        sort: true
                    }
                    , {
                        field: 'productId',
                        title: '商品ID',
                        align: 'center',
                        width: '6%',
                        sort: true
                    }, {
                        field: 'productName',
                        align: 'center',
                        width: '15%',
                        title: '商品名称'
                    }, {
                        field: 'productNum',
                        title: '库存',
                        align: 'center',
                        width: '6%',
                        sort: false
                    },{
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        width: '6%',
                        sort: false,
                        templet: '<div>{{toStatus(d.status)}}</div>'

                    },{
                        field: 'classifyName',
                        title: '分类',
                        width: '6%',
                        align: 'center'
                    },{
                        field: 'onShelfTime',
                        title: '上架时间',
                        align: 'center',
                        width: '10%',
                        templet: '<div>{{toDate(d.onShelfTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
                    },{
                        field: 'auctionStartTime',
                        title: '拍卖开始时间',
                        align: 'center',
                        width: '10%',
                        templet: '<div>{{toDate(d.auctionStartTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
                    },  {
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center',
                        sort: true,
                        width: '10%',
                        templet: '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
                    }, {
                        field: 'underShelfTime',
                        title: '下架时间',
                        align: 'center',
                        sort: true,
                        width: '10%',
                        templet: '<div>{{toDate(d.underShelfTime,\'yyyy-MM-dd  hh:mm:ss\')}}</div>'
                    },{field:'right',
                        title: '操作',
                        style: 'height:120',
                        width: '10%',toolbar:"#barOperate"}
                ]]

            });
            var $ = layui.$, active = {
                reload: function () {
                    if(checkNumber()) {
                        //执行重载
                        table.reload('search', {
                            page: {
                                //重新从第 1 页开始
                                page: 1
                            },
                            where: {
                                id: $('#id').val(),
                                productName: $('#productName').val(),
                                classifyId: $('#classifyName').val(),
                                status: $('#status').val(),
                                onShelfTime: $('#onShelfTime').val(),
                                underShelfTime: $('#underShelfTime').val()
                            }
                        });
                    }
                },
            };

            $('#btnSearch').on('click', function () {
                var type = $(this).data('type');
                var id = $('#id').val();
                var status=$('#status').val();
                active[type] ? active[type].call(this) : '';

            });
            $("#toolBar").keydown(function (event) {
                if (event.keyCode == 13) {
                    refreshPage();
                }
            });
            refreshPage = function () {
                $('#btnSearch').trigger("click");
            }


        });
        function checkNumber() {
            var integer = new RegExp("^\\d+$");
            var id = $("#id").val();
            if (id != null && id != undefined && id != '') {
                if (!integer.test(id)) {
                    layer.msg('拍品ID只能是整数', function () {
                    });
                    return false;
                }
            }
            return true;
        }
        //类型状态  1.开拍中  2.准备中 3.定时 4.下架
        function toStatus(status) {
            if (status == '1') {
                return "已上架";
            }
            if (status == '2') {
                return "未上架";
            }
            if (status == '4') {
                return "已下架";
            }
        }
    </script>
    <script src="<%=common%>/system/btns.js" charset="utf-8"></script>
    <script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
    <script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>
    <script src="<%=common%>/system/auctionTrade/batchOn.js" charset="utf-8"></script>
    <script src="<%=common%>/system/user/saveUserRole.js" charset="utf-8"></script>
    <script src="<%=common%>/system/auctionTrade/auctionView.js" charset="utf-8"></script>
    <script src="<%=common%>/system/auctionRule/auctionProductOnList.js" charset="utf-8"></script>
</body>
</html>