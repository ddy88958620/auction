<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<%
    String path = request.getContextPath() + "";
    String common = path + "/resources/";
%>
<head>
    <c:set var="path" value="<%=path%>"></c:set>
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
<form class="layui-form  layui-form-pane1 pzjzsj"  action="" id="form"  style="margin-left: 400px;">
    <input type="hidden" name="id" value="${userInfo.id }">
    <fieldset class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 1000px;">
        <legend style="margin-bottom: 10px;">
        <a class="layui-btn layui-btn-primary layui-btn-sm" style="vertical-align: middle" onclick="refreshPage()">用户详情</a>
        <a class="layui-btn layui-btn-primary layui-btn-sm" style="vertical-align: middle" onclick="edit()">编辑资料</a>
        </legend>
        <table class="layui-table" >
            <colgroup>
                <col width="150">
                <col width="200">
                <col>
            </colgroup>
            <tbody>
            <tr> <td rowspan="6"><img src="${userInfo.qqHeadImg}"></td></tr>
            <tr>
                <th width="5px" >用户ID</th>
                <td width="">${userInfo.id }</td>
                <th>终端标识</th>
                <td>${userInfo.terminalSign}</td>
            </tr>
            <tr>
                <th>昵称</th>
                <td width="">${userInfo.nickName}</td>
                <th>充值状态</th>
                <td>
                    <c:if test="${userInfo.rechargeType==1}">未充值</c:if>
                    <c:if test="${userInfo.rechargeType==2}">首充</c:if>
                    <c:if test="${userInfo.rechargeType==3}">多次</c:if>
                    <c:if test="${userInfo.rechargeType==4}">首冲反币等待中</c:if>
                    <c:if test="${userInfo.rechargeType==5}">首冲反币成功</c:if>
                    <c:if test="${userInfo.rechargeType==6}">首冲拍品成功</c:if>
                </td>
            </tr>
            <tr>
                <th>手机号</th>
                    <td width="">
                        <c:if test="${userInfo.userPhone!=null}">
                             ${userInfo.userPhone}<a style="color: #00B83F"  onclick="bind()" href="#" > &nbsp; &nbsp; &nbsp; &nbsp;换绑记录</a>
                        </c:if>
                    </td>
                <th>所在地区</th>
                <td>${userInfo.provinceName}${userInfo.cityName}</td>
            </tr>
            <tr>
                <th>微信号</th>
                <td width="">${userInfo.wxNickName }</td>
                <th>注册时间</th>
                <td><fmt:formatDate value="${userInfo.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>QQ名</th>
                <td width="">${userInfo.qqNickName}</td>
                <th>渠道来源</th>
                <td>${channelSource}</td>
            </tr>
            </tbody>
        </table><br>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>统计信息</legend>
        </fieldset>
        <table class="layui-table"  id="data" lay-filter="dataTable1">
            <colgroup>
                <col width="150">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr align="center">
                <th style="text-align:center;">拍币余额</th>
                <th style="text-align:center;">赠币余额</th>
                <th style="text-align:center;">开心币余额</th>
                <th style="text-align:center;">积分余额</th>
                <th style="text-align:center;">充值次数</th>
                <th style="text-align:center;">订单数量</th>
                <th style="text-align:center;">登录次数</th>
                <th style="text-align:center;">邀请好友</th>
            </tr>
            </thead>
            <tbody>
            <tr align="center" style="width:10px;">

                <td><fmt:parseNumber integerOnly="true" value="${accountDto.auctionCoin/100}" /></td>
                <td><fmt:parseNumber integerOnly="true" value="${accountDto.presentCoin/100}" /></td>
                <td><fmt:parseNumber integerOnly="true" value="${accountDto.shoppingCoin/100}" /></td>
                <td><fmt:parseNumber integerOnly="true" value="${accountDto.points/100}" /></td>
                <td>${rechargeCount}</td>
                <td>${orderCount}</td>
                <td>${loginCount}</td>
                <td>0</td>
            </tr>
            <tr align="center">
                <td  align="center" valign="middle" width="10%">
                    <%--<button class="layui-btn"  lay-filter="check1" >拍币明细</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle" onclick="showRecordDetail(1)">拍币明细</a>
                </td>
                <td  align="center" valign="middle" width="10%">
                    <%--<button class="layui-btn"  lay-filter="check1" >赠币明细</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle" onclick="showRecordDetail(2)">赠币明细</a>
                </td>
                <td align="center" valign="middle" width="10%">
                    <%--<button class="layui-btn"  lay-filter="check1" >开心币明细</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle"  onclick="showRecordDetail(4)">开心币明细</a>
                </td>
                <td align="center" valign="middle" width="10%">
                  <%--  <button class="layui-btn"  lay-filter="check1" >积分明细</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle" lay-event="integralDetail" href="#" onclick="showRecordDetail(3)">积分明细</a>
                </td>
                <td  align="center" valign="middle" width="10%">
                    <%--<button class="layui-btn"  lay-filter="check1" >充值明细</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle"  onclick="rechargeOrder()">充值明细</a>
                </td>
                <td  align="center" valign="middle" width="10%"></td>
                <td  align="center" valign="middle" width="10%">
                    <%--<button class="layui-btn"  lay-filter="check1" >登陆日志</button>--%>
                    <a class="layui-btn layui-btn-mini" style="vertical-align: middle"  onclick="loginDetail()">登陆日志</a>
                </td>
                <td  align="center" valign="middle" width="10%"></td>
            </tr>
            </tbody>
        </table>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>收货地址</legend>
        </fieldset><br>
        <table class="layui-table"  id="data2" align="center" valign="center">
            <colgroup>
                <col width="150">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr >
                <th style="text-align:center;">姓名</th>
                <th style="text-align:center;">手机号码</th>
                <th style="text-align:center;">详细地址</th>
                <th style="text-align:center;">邮政编码</th>
                <th style="text-align:center;">默认地址</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="addressInfo" items="${addressList}">
                <tr align="center" style="width:10px;">
                    <td>${addressInfo.userName}</td>
                    <td>${addressInfo.userPhone}</td>
                    <td>${addressInfo.address}</td>
                    <td>${addressInfo.postCode}</td>
                    <td>
                        <input type="checkbox" name="" lay-skin="primary" disabled="true"
                               <c:if test="${addressInfo.addressType=='0'}">checked="checked"</c:if> />
                    </td>
                </tr>
            </c:forEach>

        </table>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>订单记录</legend>
        </fieldset><br>
        <script type="text/html" id="barOperate">
            <%--<a class="layui-btn layui-btn-primary layui-btn-mini" style="align-content: center" lay-event="detail">查看</a>--%>
            <a style="color: #00B83F"  lay-event="detail" href="#">查看订单</a>
        </script>
        <table class="layui-table"  id="data3" align="center" valign="center" lay-filter="dataTable2">
        </table>
    </fieldset>
</form>

<script>
    var myId = "${parentId}";
    var path = "${path}";
    var table;
    var form;
    layui.use(['table','form','laydate'], function() {
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
            elem : '#data3',
            even : true,
            url : '${path}/user/viewOrder',
            height : "full-70",
            method : 'post',
            id:${userInfo.id},
            where: { id: "${userInfo.id}" },
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
            cols : [ [  {
                field : 'orderId',
                align : 'center',
                title : '订单编号'
            },{
                field : 'createTime',
                title : '提交时间',
                align : 'center',
                templet: '<div>{{toDate(d.createTime,\'yyyy-MM-dd hh:mm:ss\')}}</div>'
            },{
                field : 'userPhone',
                title : '用户账号',
                align : 'center'
            },{
                field : 'orderAmount',
                title : '订单金额',
                align : 'center',
                templet: '<div>{{toMoney(d.orderAmount)}}</div>'
            },{
                field : 'orderStatus',
                title : '交易状态',
                align : 'center',
                templet: '<div>{{toTradeStatus(d.orderStatus)}}</div>'
            }, {
                field : 'orderType',
                title : '订单类型',
                align : 'center',
                templet: '<div>{{toOrderStatus(d.orderStatus)}}</div>'
            }, {
                field : 'coin2',
                align : 'center',
                title : '操作',
                toolbar:"#barOperate"
            }] ]

        });

        //监听工具条
        table.on('tool(dataTable2)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                window.parent.layer.open({
                    title:"查看订单",
                    type: 2,
                    area: ['100%', '100%'],
                    btn : ['返回' ],
                    shade : 0.3,
                    content: '/order/orderView?id='+data.orderId
                });

            }
        });
    });
</script>

<script>
    var html = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [  '返回' ],
    success : function(layero, index) {}
    };
    var path = "${path}";
    //换绑记录
    var bind = function() {
        document.activeElement.blur();
        var id = $("form input[name='id']").val();
        var url = "/user/bindRecord?id="+id;
        html['content'] = path + url;
        html.title="换绑记录";
        html.btn="返回";
        window.parent.layer.open(html);
    };

    //资料编辑
    var edit = function() {
        document.activeElement.blur();
        var id = $("form input[name='id']").val();
        var url = "/user/edit?id="+id;
        html['content'] = path + url;
        html['btn'] = "";
        html.title="资料编辑";
        var  frameindex=window.parent.layer.open(html);
    };

       //收支明细
       /*类型：1：拍币；2：赠币；3：积分；4：购物币 */
    var showRecordDetail = function(accountType) {
        document.activeElement.blur();
        var id = $("form input[name='id']").val();
        var url = "/user/recordDetail?userId="+id+"&accountType="+accountType;
        html['content'] = path + url;
        html.title="收支明细";
        html['btn'] = "返回";
        window.parent.layer.open(html);
    };
        //充值明细
    var rechargeOrder = function() {
        document.activeElement.blur();
        var id = $("form input[name='id']").val();
        var url = "/user/getInfoRecord?userId="+id;
        html['content'] = path + url;
        html.title="充值明细";
        window.parent.layer.open(html);
    };
    function loginDetail() {
        window.parent.layer.open({
            title:"登陆日志",
            type : 2,
            shade : 0.3,
            maxmin : true,
            area : [ '100%', '100%' ],
            anim : 1,
            btnAlign : 'r',
            btn : [  '返回' ],
            content: 'getLoginRecord/?id='+"${userInfo.id}"
        });
}


    //交易状态   1,待支付尾款 2,已支付尾款 3,已流拍(内部拍回) 4,待配货 5,已发货 6,确认收货 7,已完成 8,已关闭',
    function toTradeStatus(status) {
        if (status == '1') {
            return "待支付尾款";
        }
        if (status == '2') {
            return "已支付尾款";
        }
        if (status == 3) {
            return "已流拍(内部拍回)";
        }
        if (status == '4') {
            return "待配货";
        }
        if (status == '5') {
            return "已发货)";
        }
        if (status == '6') {
            return "确认收货";
        }
        if (status == '7') {
            return "已完成";
        }
        if (status == '8') {
            return "已关闭";
        }else {
            return "其他";
        }
    }


    //订单状态  1. 拍卖 2.差价购'    
    function toOrderStatus(status) {
        if (status == '1') {
            return "拍卖";
        }
        if (status == '2') {
            return "差价购";
        }else {
            return "其他";
        }
    }

    function toMoney(money) {
        return "¥"+money;
    }
    function refreshPage() {
        location.reload();
    }
function toInt(value) {
    return parseInt(value);
}
</script>
</body>
</html>