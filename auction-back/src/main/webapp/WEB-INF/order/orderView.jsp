<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<form class="layui-form " action="" id="form" style="margin-left: 350px;">
    <div class="layui-layer-content">
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 1000px;">
                <legend><span style="font-size: 30px;">基本信息</span></legend>
                <div class="layui-form-item" style="margin-top: 10px;">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">订单编号:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.orderId}</label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">订单状态:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">
                                <c:forEach var="item" items="${orderStatusList}">
                                    <c:if test="${orderInfo.orderStatus eq item.key}">
                                        ${item.value}
                                    </c:if>
                                </c:forEach>
                            </label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">订单类型:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">
                                <c:forEach var="item" items="${orderTypeList}">
                                    <c:if test="${orderInfo.orderType eq item.key}">
                                        ${item.value}
                                    </c:if>
                                </c:forEach>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">下单时间:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;"><fmt:formatDate value="${orderInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">发货时间:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;"><fmt:formatDate value="${orderInfo.logistics.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">订单总额:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.orderAmount}</label>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">出价次数:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.bidTimes}</label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">尾款总额:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.paidMoney}</label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">物流状态:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">
                                <c:forEach var="item" items="${logisticsStatusList}">
                                    <c:if test="${orderInfo.logistics.logisticsStatus eq item.key}">
                                        ${item.value}
                                    </c:if>
                                </c:forEach>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">用户ID:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.buyId}</label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">购买账号:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.userPhone}</label>
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">用户姓名:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="width: 140px;text-align: left;">
                                ${orderInfo.userName}
                            </label>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="margin-top: 30px;font-weight: bolder;font-size: 16px;">关联微信:</label>
                        <div class="layui-input-inline">
                            ${orderInfo.userInfo.wxNickName}&nbsp;&nbsp;<img src="${orderInfo.userInfo.wxHeadImg}">
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="margin-top: 30px;font-weight: bolder;font-size: 16px;">关联QQ:</label>
                        <div class="layui-input-inline">
                            ${orderInfo.userInfo.qqNickName}&nbsp;&nbsp;<img src="${orderInfo.userInfo.qqHeadImg}">
                        </div>
                    </div>
                    <div class="layui-inline" style="margin-left: 15px;">
                        <label class="layui-form-label" style="margin-top: 30px;font-weight: bolder;font-size: 16px;">修改时间:</label>
                        <div class="layui-input-inline">
                            <label  class="layui-form-label" style="margin-top: 30px;width: 140px;text-align: left;">
                                <fmt:formatDate value="${orderInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 1000px;">
                <legend><span style="font-size: 30px;">商品信息</span></legend>
                <table style="margin-left: 20px; width:950px;" cellspacing="0" cellpadding="0" border="0" class="layui-table">
                    <thead>
                    <tr>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">商品ID</th>
                        <th style="text-align: center;">商品价格</th>
                        <th style="text-align: center;">货源</th>
                        <th style="text-align: center;">购买数量</th>
                        <th style="text-align: center;">小计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="text-align: center;width: 30%;">${orderInfo.productRecord.productName}</td>
                        <td style="text-align: center;width: 15%;">${orderInfo.productRecord.productId}</td>
                        <td style="text-align: center;width: 15%;">${orderInfo.productPrice}</td>
                        <td style="text-align: center;width: 15%;">
                            ${orderInfo.productRecord.merchantName}
                        </td>
                        <td style="text-align: center;width: 10%;">${orderInfo.productNum}</td>
                        <td style="text-align: center;width: 15%;">${orderInfo.productPrice}</td>
                    </tr>
                    </tbody>
                </table>
            </fieldset>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 1000px;">
                <legend><span style="font-size: 30px;">物流信息</span></legend>
                <div class="layui-form-item" style="margin-top: 10px;">
                    <div class="layui-inline">
                        <label class="layui-form-label">*发货人:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sendName" lay-verify="required|deliverName" id="sendName" value="${orderInfo.logistics.sendName}" class="layui-input" unselectable="on" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*发货人手机:</label>
                        <div class="layui-input-inline">
                            <input type="tel" lay-verify="required|phone" name="sendPhone" id="sendPhone" value="${orderInfo.logistics.sendPhone}" class="layui-input" unselectable="on" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*发货人邮编:</label>
                        <div class="layui-input-inline">
                            <input type="text" lay-verify="required|deliverZip" name="receiverName" id="receiverName" value="${orderInfo.logistics.receiverName}" class="layui-input" unselectable="on" readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">*发货地址:</label>
                    <div class="layui-input-block">
                        <textarea style="width: 840px;" lay-verify="required|deliverAddress" class="layui-textarea" name="sendAddress" id="sendAddress" unselectable="on" readonly="readonly">${orderInfo.logistics.sendAddress}</textarea>
                    </div>
                </div>
                <hr class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 950px;margin-left: 20px;"/>
                </hr>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">*收货人:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="transName" unselectable="on" readonly="readonly" id="transName" value="${orderInfo.logistics.transName}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*收货人电话:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="transTelphone" unselectable="on" readonly="readonly" id="transTelphone" value="${orderInfo.logistics.transTelphone}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*收货人手机:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="transPhone" unselectable="on" readonly="readonly" id="transPhone" value="${orderInfo.logistics.transPhone}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*收货人邮编:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="receiverCode" lay-verify="required|receiptZip" id="receiverCode" unselectable="on" readonly="readonly" value="${orderInfo.logistics.receiverCode}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">*收货地址:</label>
                    <div class="layui-input-block">
                        <textarea style="width: 840px;" class="layui-textarea" name="address" id="address" unselectable="on" readonly="readonly">${orderInfo.logistics.provinceName}${orderInfo.logistics.cityName}${orderInfo.logistics.districtName}${orderInfo.logistics.townName} ${orderInfo.logistics.address}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">物流信息:</label>
                    <div class="layui-input-block">
                        <textarea style="width: 840px;" lay-verify="logisticsInfo" class="layui-textarea" unselectable="on" readonly="readonly" name="logisticsInfo" id="logisticsInfo">${orderInfo.logistics.logisticsInfo}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注:</label>
                    <div class="layui-input-block">
                        <textarea style="width: 840px;" lay-verify="remark" class="layui-textarea" name="remark" unselectable="on" readonly="readonly" id="remark">${orderInfo.logistics.remark}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">*快递单号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="logisticsId" unselectable="on" readonly="readonly" lay-verify="required|logisticsId" id="logisticsId" value="${orderInfo.logistics.logisticsId}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">*快递公司:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="logisticsName" unselectable="on" readonly="readonly" lay-verify="required|logisticsName" id="logisticsName" value="${orderInfo.logistics.logisticsName}" class="layui-input"/>
                        </div>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }
</script>
</body>
</html>