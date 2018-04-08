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
		<input type="Hidden" name="parentId" value="${params.parentId}"/>
		<input type="Hidden" name="orderId" value="${orderInfo.logistics.orderId}"/>
		<input type="Hidden" name="id" value="${orderInfo.logistics.id}"/>
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;width: 1000px;">
				<legend><span style="font-size: 30px;">基本信息</span></legend>
				<div class="layui-form-item" style="margin-top: 10px;">
					<div class="layui-inline">
						<label class="layui-form-label" style="font-weight: bolder;font-size: 16px;">订单编号:</label>
						<div class="layui-input-inline">
							<label class="layui-form-label" style="width: 140px;text-align: left;">${orderInfo.orderId}</label>
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
							<input type="text" name="sendName" unselectable="on" readonly="readonly" id="sendName" value="${orderInfo.logistics.sendName}" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*发货人手机:</label>
						<div class="layui-input-inline">
							<input type="tel" unselectable="on" readonly="readonly" name="sendPhone" id="sendPhone" value="${orderInfo.logistics.sendPhone}" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*发货人邮编:</label>
						<div class="layui-input-inline">
							<input type="text" unselectable="on" readonly="readonly" name="receiverName" id="receiverName" value="${orderInfo.logistics.receiverName}" class="layui-input"/>
						</div>
					</div>
				</div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">*发货地址:</label>
					<div class="layui-input-block">
						<textarea style="width: 840px;" unselectable="on" readonly="readonly" class="layui-textarea" name="sendAddress" id="sendAddress">${orderInfo.logistics.sendAddress}</textarea>
					</div>
				</div>
                <hr class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 950px;margin-left: 20px;color: red;"/>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">*收货人:</label>
						<div class="layui-input-inline">
							<input type="text" unselectable="on" readonly="readonly" name="transName" id="transName" value="${orderInfo.logistics.transName}" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*收货人电话:</label>
						<div class="layui-input-inline">
							<input type="text" unselectable="on" readonly="readonly" name="transTelphone" id="transTelphone" value="${orderInfo.logistics.transTelphone}" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*收货人手机:</label>
						<div class="layui-input-inline">
							<input type="text" unselectable="on" readonly="readonly" name="transPhone" id="transPhone" value="${orderInfo.logistics.transPhone}" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*收货人邮编:</label>
						<div class="layui-input-inline">
							<input type="text" unselectable="on" readonly="readonly" name="receiverCode" id="receiverCode" value="${orderInfo.logistics.receiverCode}" class="layui-input"/>
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
						<textarea style="width: 840px;" unselectable="on" readonly="readonly" class="layui-textarea" name="logisticsInfo" id="logisticsInfo">${orderInfo.logistics.logisticsInfo}</textarea>
					</div>
				</div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">备注:</label>
					<div class="layui-input-block">
						<textarea style="width: 840px;" unselectable="on" readonly="readonly" class="layui-textarea" name="remark" id="remark">${orderInfo.logistics.remark}</textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">*快递单号:</label>
						<div class="layui-input-inline">
							<input type="text" name="logisticsId" unselectable="on" readonly="readonly" class="layui-input" value="${orderInfo.logistics.logisticsId}"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*快递公司:</label>
						<div class="layui-input-inline">
							<input type="text" name="logisticsName" unselectable="on" readonly="readonly" id="logisticsName" class="layui-input" value="${orderInfo.logistics.logisticsName}"/>
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
    function resetForm() {
        document.getElementById("form").reset();
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }
    var layer;
    layui.use(['form','layer'], function() {
        <c:if test="${not empty error}">
        parent.layer.msg("${error}");
        parent.layer.closeAll('iframe');
        parent.window.refreshPage();
        </c:if>
    });
</script>
</body>
</html>