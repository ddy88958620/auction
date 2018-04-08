<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>

<form class="layui-form  layui-form-pane1 pzjzsj" method="POST">
    <div class="layui-form-item">
    </div>
    <input type="hidden" lay-verify="required|id" name="id" class="layui-input"
           autocomplete="off" value="${auctionRule.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">规则名称</label>
            <div class="layui-input-inline" style="width:30%">
                 <textarea rows="1" id="ruleName" lay-verify="required" name="ruleName" class="layui-input"
                           autocomplete="off" placeholder="规则1">${auctionRule.ruleName}</textarea>
                </textarea>
            </div>
        <span id="info"></span>
    </div>
    <%--<div class="layui-form-item">
        <label class="layui-form-label">手续费名称</label>
        <div class="layui-input-inline" style="width:30%">
            <input type="text" lay-verify="required" name="poundageName" value="${auctionRule.poundageName}" class="layui-input"
                   autocomplete="off" />
        </div>
    </div>--%>
    <div class="layui-form-item">

        <label class="layui-form-label">手续费/次</label>
            <div class="layui-input-inline" style="width:30%">

                <select name="poundage" lay-verify="required">
                    <c:forEach var="item" items="${poundageList}">
                        <option value="${item.key}"
                            <c:if test="${item.key eq poundage}">selected="selected"</c:if>>${item.value}</option>
                    </c:forEach>
                </select>

            </div>
        拍币
    </div>
    <%--<div class="layui-form-item">
        <label class="layui-form-label">差价购买名称</label>
        <div class="layui-input-inline" style="width:30%">
            <input type="text" lay-verify="required" name="differenceName" value="${auctionRule.differenceName}" class="layui-input"
                   autocomplete="off" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">差价购买</label>
            <div class="layui-input-inline" style="width:30%">
                <select name="differenceFlag" lay-verify="required">
                    <c:forEach var="item" items="${differenceFlag}">
                        <option value="${item.key}"
                                <c:if test="${item.key eq auctionRule.differenceFlag}">selected="selected"</c:if>>${item.value}</option>
                    </c:forEach>
                </select>
            </div>
        拍币
    </div>
 <%--   <div class="layui-form-item">
        <label class="layui-form-label">每次加价名称</label>
        <div class="layui-input-inline" style="width:30%">
            <input type="text" lay-verify="required" name="increaseBidName" value="${auctionRule.increaseBidName}" class="layui-input"
                   autocomplete="off" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">加价幅度</label>
            <div class="layui-input-inline" style="width:30%">
                <input type="text" lay-verify="required|number" name="premiumAmount" class="layui-input"
                       autocomplete="off" placeholder="0" value="${auctionRule.premiumAmount}"/>
            </div>
        元
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">延时周期</label>
            <div class="layui-input-inline" style="width:30%">
                <select name="countdown" lay-verify="required">
                    <c:forEach var="item" items="${countdown}">
                        <option value="${item.key}"
                                <c:if test="${item.key eq auctionRule.countdown}">selected="selected"</c:if>>${item.value}</option>
                    </c:forEach>
                </select>
                <%--<input type="text" lay-verify="required" name="countdown" class="layui-input"--%>
                       <%--autocomplete="off" placeholder="0" value="${auctionRule.countdown}"/>--%>
            </div>
        秒
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">退币比例名称</label>
        <div class="layui-input-inline" style="width:30%">
            <input type="text" lay-verify="required" name="proportionName" value="${auctionRule.proportionName}" class="layui-input"
                   autocomplete="off" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">退币比例</label>
            <div class="layui-input-inline" style="width:30%">
                <input type="text" lay-verify="required|integer" name="refundMoneyProportion" class="layui-input"
                       autocomplete="off" placeholder="0" value="${refundMoneyProportion}"/>
            </div>
        %
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">起拍价名称</label>
        <div class="layui-input-inline" style="width:30%">
            <input type="text" lay-verify="required" name="startBidName" value="${auctionRule.startBidName}" class="layui-input"
                   autocomplete="off" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">起拍价</label>
            <div class="layui-input-inline" style="width:30%">
                <input type="text" lay-verify="required|number" name="openingBid" class="layui-input"
                       autocomplete="off" placeholder="0" value="${auctionRule.openingBid}"/>
            </div>
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">规则状态</label>
            <div class="layui-input-inline" style="width:30%">
                <select name="status" lay-verify="required">
                    <c:forEach var="item" items="${status}">
                        <option value="${item.key}"
                                <c:if test="${item.key eq auctionRule.status}">selected="selected"</c:if>>${item.value}</option>
                    </c:forEach>
                </select>
            </div>
    </div>--%>

   <%-- <div class="layui-form-item">
        <label class="layui-form-label">上架延迟开拍时间</label>
            <div class="layui-input-inline" style="width:30%">
                <input type="text" lay-verify="required" name="shelvesDelayTime" class="layui-input"
                       autocomplete="off" placeholder="0" value="${auctionRule.shelvesDelayTime}"/>
            </div>
    </div>--%>
    </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
</form>
<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
<script src="<%=common%>/system/auctionRule/auctionRule.js" charset="utf-8"></script>
<script>
    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }


    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

//时间选择器
        laydate.render({
            elem: '#test5'
            , type: 'datetime'
        });
        form.verify({
            integer: function (value) {
                if (value != null && value != '') {
                    var integer = new RegExp("^[-+]{0,1}\\d+$");
                    if (!integer.test(value)) {
                        return "只能是整数!";
                    }
                }

            }
        });
        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                type: "post",
                url: "${path }/rule/updateAuctionRule",
                data: data.field,
                dataType: "json",
                beforeSend: function () {
                    layer.load(1, {
                        shade: [0.1, '#fff']
                    });
                },
                success: function (result) {
                    layer.msg(result.msg, {
                        time: 1000
                    }, function () {
                        if (result.code == '0') {
                            parent.layer.closeAll('iframe');
                            parent.window.refreshPage();
                        }
                    });
                }
            });
            return false;
        });
    });
</script>
<script>
    (function ($) {
        $.fn.limitTextarea = function (opts) {
            var defaults = {
                maxNumber: 50, //允许输入的最大字数
                onOk: function () {
                }, //输入后，字数未超出时调用的函数
                onOver: function () {
                } //输入后，字数超出时调用的函数
            }
            var option = $.extend(defaults, opts);
            this.each(function () {
                var _this = $(this);
                var fn = function () {
                    var $info = $('#info');
                    var extraNumber = option.maxNumber - _this.val().length;
                    if (extraNumber >= 0) {
                        $info.html('*<b>' + extraNumber + '</b>/50');
                        option.onOk();
                    } else {
                        $info.html('已经超出<b style="color:red;">' + (-extraNumber) + '</b>个字');
                        option.onOver();
                    }
                };
                //绑定输入事件监听器
                if (window.addEventListener) { //先执行W3C
                    _this.get(0).addEventListener("input", fn, false);
                } else {
                    _this.get(0).attachEvent("onpropertychange", fn);
                }
                if (window.VBArray && window.addEventListener) { //IE9
                    _this.get(0).attachEvent("onkeydown", function () {
                        var key = window.event.keyCode;
                        (key == 8 || key == 46) && fn(); //处理回退与删除
                    });
                    _this.get(0).attachEvent("oncut", fn); //处理粘贴
                }
            });
        }
    })(jQuery)
    //插件调用；
    $(function () {
        $('#ruleName').limitTextarea({
            maxNumber: 50, //最大字数
            onOk: function () {
                $('#ruleName').css('background-color', 'white');
            }, //输入后，字数未超出时调用的函数
            onOver: function () {
                //输入后，字数超出时调用的函数，这里把文本区域的背景变为粉红色
                $('#ruleName').css('background-color', 'lightpink');
            }
        });

        $("#info").html('*<b>' + (50-$("#ruleName").val().length) + '</b>/50');
    });
</script>
