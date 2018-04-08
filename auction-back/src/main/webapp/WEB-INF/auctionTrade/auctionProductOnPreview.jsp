<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
	String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");
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
<form class="layui-form  layui-form-pane1 pzjzsj" method="POST">
<fieldset class="layui-elem-field layui-field-title" style="">
	<legend>商品信息</legend>
</fieldset>

	<div class="layui-row">
		<div class="layui-col-md8 layui-col-md-offset2">
			<table class="layui-table">
				<thead>
				<tr>
					<th>商品ID</th>
					<th>商品名称</th>
					<th>商品标题</th>
					<th>商品库存</th>
					<th>市场价格</th>
					<th>渠道价格</th>
					<th>商品运费</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${product.id}</td>
					<td>${product.productName}</td>
					<td>${product.productTitle}</td>
					<td>${inventory.productNum}</td>
					<td>${product.salesAmount} 元</td>
					<td>${product.productAmount} 元</td>
					<td>${product.freight} 元</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
<%--	<div class="layui-form-item">
		<label class="layui-form-label">商品ID</label>
		<div class="layui-form-mid layui-word-aux">${product.id}</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品名称</label>
		<div class="layui-form-mid layui-word-aux">${product.productName}</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品标题</label>
		<div class="layui-form-mid layui-word-aux">${product.productTitle}</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品库存</label>
		<div class="layui-form-mid layui-word-aux">${inventory.productNum}</div>
	</div>

	<div class="layui-row">
		<div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
			<label class="layui-form-label">市场价格</label>
			<div class="layui-form-mid layui-word-aux">${product.salesAmount} 元</div>
		</div>
		<div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
			<label class="layui-form-label">渠道价格</label>
			<div class="layui-form-mid layui-word-aux">${product.productAmount} 元</div>
		</div>
		<div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
			<label class="layui-form-label">商品运费</label>
			<div class="layui-form-mid layui-word-aux">${product.freight} 元</div>
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品详情（750*750）:</label>
		<div class="layui-form-mid layui-word-aux" id="detail-pic">
		</div>
	</div>
--%>



	<div class="layui-form-item">
		<label class="layui-form-label">预览图</label>
		<div class="layui-input-inline">
			<table>
				<tr>
					<c:forEach var="picInfo" items="${pics}">
						<c:if test="${picInfo.picType==2}">
							<td>
								<img src="<%=photoPathSuffix%>/${picInfo.picUrl}" style="width: 100px;height: 60px;"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品图</label>
		<div class="layui-input-inline">
			<table>
				<tr>
					<c:forEach var="picInfo" items="${pics}">
						<c:if test="${picInfo.picType==1}">
							<td>
								<img src="<%=photoPathSuffix%>/${picInfo.picUrl}" style="width: 100px;height: 60px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">详情图</label>
		<div class="layui-input-inline">
			<table>
				<tr>
					<c:forEach var="picInfo" items="${pics}">
						<c:if test="${picInfo.picType==0}">
							<td>
								<img src="<%=photoPathSuffix%>/${picInfo.picUrl}" style="width: 120px;height: 120px;margin-left: 5px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">竞拍规则：</label>
		<div class="layui-input-block" style="width: 30%" readonly>
			<textarea readonly class="layui-textarea">${product.remarks}</textarea>
		</div>
	</div>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>拍品信息</legend>
	</fieldset>

	<div class="layui-row">
		<div class="layui-col-md8 layui-col-md-offset2">
			<table class="layui-table">
				<thead>
				<tr>
					<th>拍品id</th>
					<th>竞拍规则</th>
					<th>上架数量</th>
					<th>拍品保底价（有效价格）</th>
					<th>上架时间</th>
					<th>开拍时间</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${info.id}</td>
					<td>${auctionInfo.ruleName}</td>
					<td>${info.productNum}</td>
					<td>${auctionInfo.floorPrice}</td>
					<td><fmt:formatDate value="${info.onShelfTime}"   pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${info.auctionStartTime}"   pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
		<hr>
	<%--
	<div class="layui-row">
		<div class="layui-col-xs6">
			<label class="layui-form-label">拍品id:</label>
			<div class="layui-form-mid layui-word-aux">${info.id}</div>
		</div>
		<div class="layui-col-xs6">
			<label class="layui-form-label">竞拍规则:</label>
			<div class="layui-form-mid layui-word-aux">${auctionInfo.ruleName}</div>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-col-xs6">
			<label class="layui-form-label">上架数量:</label>
			<div class="layui-form-mid layui-word-aux">${info.productNum}</div>
		</div>
		<div class="layui-col-xs6">
			<label class="layui-form-label">库存:</label>
			<div class="layui-form-mid layui-word-aux">${info.productName}</div>
		</div>
	</div>

	<div class="layui-row">
		<div class="layui-col-xs6">
			<label class="layui-form-label">拍品保底价（有效价格）:</label>
			<div class="layui-form-mid layui-word-aux">${auctionInfo.floorPrice}</div>
		</div>
		<div class="layui-col-xs6">
			<label class="layui-form-label">浮动比例:</label>
			<div class="layui-form-mid layui-word-aux">${auctionInfo.floatPrice}</div>
		</div>
	</div>

	<div class="layui-row">
		<div class="layui-col-xs6">
			<label class="layui-form-label">上架时间:</label>
			<div class="layui-form-mid layui-word-aux"><fmt:formatDate value="${info.onShelfTime}"   pattern="yyyy-MM-dd HH:mm:ss" /></div>
		</div>
		<div class="layui-col-xs6">
			<label class="layui-form-label">开拍时间:</label>
			<div class="layui-form-mid layui-word-aux"><fmt:formatDate value="${auctionInfo.startTime}"   pattern="yyyy-MM-dd HH:mm:ss" /></div>
		</div>
	</div>
	--%>



	<%--<div class="layui-row">
		<div class="layui-col-xs6">
			<label class="layui-form-label">下一期开拍倒计时:</label>
			<div class="layui-form-mid layui-word-aux">${auctionInfo.countDown}</div>
		</div>
	</div>--%>

	<div class="layui-row">
		<div class="layui-col-md8 layui-col-md-offset2">
			<table class="layui-table">
				<thead>
				<tr>
					<th>下一期开拍倒计时</th>
					<th>起拍价（元）</th>
					<th>手续费/次（拍币）</th>
					<th>差价购买</th>
					<th>加价幅度（元））</th>
					<th>延时周期（秒）</th>
					<th>退币比例（%）</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${auctionInfo.countDown}</td>
					<td>${auctionInfo.startPrice}</td>
					<td>${auctionInfo.poundage}</td>
					<td>${auctionInfo.buyFlag}</td>
					<td>${auctionInfo.increasePrice}</td>
					<td>${info.shelvesDelayTime}</td>
					<td>${auctionInfo.returnPercent}</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>拍卖信息</legend>
	</fieldset>

	<div class="layui-row">
		<div class="layui-col-md8 layui-col-md-offset2">
			<table class="layui-table">
				<thead>
				<tr>
					<th>出价次数</th>
					<th>有效出价次数</th>
					<th>当前价</th>
					<th>成交价（元）</th>
					<th>围观（次）</th>
					<th>收藏数（次）</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${auctionInfo.totalBidCount}</td>
					<td>${auctionInfo.validBidCount}</td>
					<td>${auctionInfo.bidPrice}</td>
					<td>
						<c:choose>
						<c:when test="${empty auctionInfo.finalPrice||auctionInfo.finalPrice.unscaledValue()==0}">
							/
						</c:when>
						<c:otherwise>
							${auctionInfo.finalPrice}
						</c:otherwise>
					</c:choose>
					</td>
					<td>${auctionInfo.pageView}</td>
					<td>${auctionInfo.collectCount}</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>

<input type="hidden" value="${record.picUrls}" id="auction_record">

	</table>
</form>

<script type="text/javascript">

</script>

<%--<script src="<%=common%>/system/btns.js" charset="utf-8"></script>--%>

	<script type="text/html" id="barOperate">
		<a class="layui-btn layui-btn-primary layui-btn-mini" style="align-content: center" lay-event="detail">查看</a>
		<%--<a class="layui-btn layui-btn-mini" style="vertical-align: middle" lay-event="edit">编辑</a>--%>
	</script>

</body>
</html>