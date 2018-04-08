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
							<input type="text" name="sendName" lay-verify="required|deliverName" id="sendName" value="" placeholder="请输入发货人" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*发货人手机:</label>
						<div class="layui-input-inline">
							<input type="tel" lay-verify="required|phone" name="sendPhone" id="sendPhone" value="" placeholder="请输入手机号" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*发货人邮编:</label>
						<div class="layui-input-inline">
							<input type="text" lay-verify="required|deliverZip" name="receiverName" id="receiverName" value="" placeholder="请输入邮编" class="layui-input"/>
						</div>
					</div>
				</div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">*发货地址:</label>
					<div class="layui-input-block">
						<textarea style="width: 840px;" lay-verify="required|deliverAddress" placeholder="请输入发货地址" class="layui-textarea" name="sendAddress" id="sendAddress"></textarea>
					</div>
				</div>
                <hr class="layui-elem-field layui-field-title" style="margin-top: 20px;width: 950px;margin-left: 20px;color: red;"/>
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
							<input type="text" name="receiverCode" unselectable="on" readonly="readonly" id="receiverCode" value="${orderInfo.logistics.receiverCode}" class="layui-input"/>
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
						<textarea style="width: 840px;" lay-verify="logisticsInfo" class="layui-textarea" name="logisticsInfo" id="logisticsInfo"></textarea>
					</div>
				</div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">备注:</label>
					<div class="layui-input-block">
						<textarea style="width: 840px;" lay-verify="remark" class="layui-textarea" name="remark" id="remark"></textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">*快递单号:</label>
						<div class="layui-input-inline">
							<input type="text" name="logisticsId" lay-verify="required|logisticsId" id="logisticsId" placeholder="请输入快递单号" class="layui-input"/>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">*快递公司:</label>
						<div class="layui-input-inline">
							<input type="text" name="logisticsName" lay-verify="required|logisticsName" id="logisticsName" placeholder="请输入快递公司名称" class="layui-input"/>
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
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
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
        var form = layui.form;
        //自定义验证规则
        form.verify({
            deliverName : function(value, item) {
                if (value.length == 0) {
                    return '发货人不能为空';
                } else if (value.length > 16) {
                    return '发货人长度不能超过16位';
                }
            },
            deliverAddress : function(value, item) {
                if (value.length == 0) {
                    return '发货人地址不能为空';
                } else if (value.length > 488) {
                    return '发货人地址长度不能超过488位';
                }
            },
            deliverZip : [ /^[1-9]\d{5}$/, '发货人邮编错误' ],
            logisticsInfo : function(value, item) {
                if (value.length > 180) {
                    return '物流信息长度不能超过180位';
                }
            },
            remark : function(value, item) {
                if (value.length > 488) {
                    return '备注长度不能超过488位';
                }
            },
            logisticsId : function(value, item) {
                if (value.length == 0) {
                    return '快递单号不能为空';
                } else if (value.length > 118) {
                    return '快递单号长度不能超过18位';
                } else {
                    for (var i = 0; i < value.length; i++) {
                        if (value.charCodeAt(i) > 255) {
                            return '快递单号不能包含中文';
                        }
                    }
                }
            },
            logisticsName : function(value, item) {
                if (value.length == 0) {
                    return '快递公司不能为空';
                } else if (value.length > 118) {
                    return '快递公司长度不能超过118位';
                }
            }

        });
        form.on('submit(submitBtn)', function(data) {
            $.ajax({
                type : "post",
                url : "${path }/logistics/deliverGoods",
                data : data.field,
                dataType : "json",
                beforeSend : function() {
                    layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });
                },
                success : function(result) {
                    layer.msg(result.msg, {
                        time : 1000
                    }, function() {
                        if (result.code == '0') {
                            parent.layer.msg("发货成功！");
                            parent.layer.closeAll('iframe');
                            parent.window.refreshPage();
                        } else {
                            layer.error("发货失败！");
						}
                    });
                }
            });
            return false;
        });
    });
</script>
</body>
</html>