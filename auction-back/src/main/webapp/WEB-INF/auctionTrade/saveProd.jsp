<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body style="margin-top: 20px;">
<form class="layui-form layui-form-pane" id="form" action="" method="post">
    <div  style="margin-left:13%;margin-right: 13%">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;margin-right:13%;" >
            <legend >拍品新建</legend>
        </fieldset>
        <div class="layui-form-item">
            <label class="layui-form-label">分类</label>
            <div class="layui-input-inline" style="width: 30%">
                <select name="classifyId"  id="classifyId"  lay-verify="required">
                    <c:forEach items="${productClassifyList}" var="classify">
                        <option value="${classify.id}">${classify.name}</option>
                    </c:forEach>
                </select>
            </div>
            <label class="layui-form-label">拍卖规则</label>
            <div class="layui-input-inline" style="width: 30%" >
                <select name="ruleId" id="ruleId" lay-verify="required" >
                    <option value="">请选择</option>
                    <c:forEach items="${auctionRuleList}" var="rule" >
                        <option value="${rule.id}">${rule.ruleName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品编号</label>
            <div class="layui-input-block" style="width:80%">
                <input name="productId" readonly="readonly" lay-verify="title"  autocomplete="off" class="layui-input" type="text" value="${productInfo.id}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称</label>
            <div class="layui-input-block" style="width:80%">
                <input name="productName" readonly="readonly" lay-verify="title"  autocomplete="off" placeholder="请输入" class="layui-input" type="text" value="${productInfo.productName}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"  >商品标题</label>
            <div class="layui-input-block" style="width:80%">
                <input name="productitle" readonly="readonly" lay-verify="title" autocomplete="off" placeholder="请输入" class="layui-input" type="text" value="${productInfo.productTitle}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">市场价</label>
            <div class="layui-input-inline"  style="width:25%">
                <input  name="productPrice" readonly="readonly" lay-verify="title" autocomplete="off" placeholder="请输入" class="layui-input" type="text" value="${productInfo.salesAmount}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >上架数量</label>
            <div class="layui-input-inline" style="width:25%">
                <input  name="productNum" lay-verify="required" autocomplete="off" placeholder="请输入" class="layui-input" type="text">
            </div>
            <label class="layui-form-label" style="width:150px" >当前库存数量</label>
            <div class="layui-input-inline" style="width:25%">
                <input  name="productNumNow"  lay-verify="required" readonly="readonly" value="${inventoryVo.productNum}" autocomplete="off" class="layui-input" type="text">
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;margin-right:13%;">
            <legend>保底价格浮动规则</legend>
        </fieldset>
	    <div class="layui-form-item" id="ruleDiv">
	      	<c:forEach items="${priceRules}" var="priceRule">
                <div class="layui-inline">
	                <label class="layui-form-label">范围段(%)</label>
	                <div class="layui-input-inline"  style="width: 100px;">
	                    <input name="minFloatRate" value="${priceRule.minFloatRate}" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">
	                </div>
	                <div class="layui-form-mid">-</div>
	                <div class="layui-input-inline" style="width: 100px;">
	                    <input name="maxFloatRate" value="${priceRule.maxFloatRate}" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">
	                </div>
	                <label class="layui-form-label">权重(%)</label>
	                <div class="layui-input-inline" style="width: 100px;">
	                    <input name="randomRate" value="${priceRule.randomRate}" placeholder="%" autocomplete="off" lay-verify="required|integer2" class="layui-input" type="text">
	                </div>
	                <button class="delRule layui-btn layui-btn-danger" ><i class="layui-icon"></i> 删除</button>
	        	</div>
            </c:forEach>
		</div>
		<div style="width:100%;text-align: center;" id="ruleFootDiv">
			<button  class="addRule layui-btn layui-btn-primary">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<i class="layui-icon"></i>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</button>
			<button  class="reback layui-btn layui-btn-primary"  style="display: none;">
				撤销本次删除
			</button>
		</div>
        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;margin-right:13%;">
                <legend>上架时间设置</legend>
            </fieldset>
            <label class="layui-form-label">请选择</label>
            <div class="layui-input-block">
                <input name="status" value="1" title="立即上架" onclick="times(1)"  lay-filter="status" checked=""  type="radio">
                <input name="status" value="2" title="暂不上架" onclick="times(2)" lay-filter="status" type="radio">
            </div>

        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label" style="width:150px">下一期开拍倒计时</label>
            <div class="layui-input-inline" style="width: 30%" >
                <select name="shelvesDelayTime" id="shelvesDelayTime" lay-verify="required" >
                    <option value="60">60s</option>
                    <option value="80">80s</option>
                    <option value="100">100s</option>
                    <option value="120">120s</option>
                    <option value="140">140s</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">竞拍规则</label>
            <div class="layui-input-block" style="width: 30%">
                <textarea name="auctionRule" id="auctionRule" placeholder="请输入内容" class="layui-textarea">${productInfo.remarks}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" type="submit" lay-submit lay-filter="addProduct">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
            </div>
        </div>

    </div>
    <input type="hidden" name="classifyName" id="classifyName" />
</form>
<script>
    layui.use(['form','laydate'], function() {
        var form = layui.form;
        var laydate= layui.laydate;
        laydate.render({
            elem: '#date'
        });
        laydate.render({
            elem: '#time',
            type:'time'
        });
        form.verify({
            integer: function (value) {
                if(value != null && value != '' ) {
                    var integer = new RegExp("^[-+]{0,1}\\d+$");
                    if (!integer.test(value)) {
                        return "只能是整数!";
                    } else {
                        if (value < -30) {
                            //return "浮动下限负30%";
                        }
                    }
                }

            },
            integer2: function (value) {
                if(value != null && value != '' ) {
                    var integer = new RegExp("^[-+]{0,1}\\d+$");
                    if (!integer.test(value)) {
                        return "只能是整数!";
                    } else {
                        if (value <= 0) {
                            return "权重必须不小于0";
                        }
                    }
                }

            }
        });

        form.on('submit(addProduct)', function(data) {
            var floorPrice = parseInt(data.field.floorPrice);
            var productPrice = parseInt(data.field.productPrice);
            var productNum  = parseInt(data.field.productNum);
            var productNumNow =  parseInt(data.field.productNumNow);
            var ruleId = parseInt(data.field.ruleId);

            if (ruleId == "" || ruleId == undefined || ruleId == null  ) {
                layer.msg("规则不能为空");
                return false;
            }
			
            var minFloatRates=$("#ruleDiv").find("input[name='minFloatRate']");
            var maxFloatRates=$("#ruleDiv").find("input[name='maxFloatRate']");
            var randomRates=$("#ruleDiv").find("input[name='randomRate']");
            if(!minFloatRates||minFloatRates.length<=0){
            	layer.msg("请设置保底价浮动规则");
                return false;
            }
            var randomRateSum=0;
            for(var i=0;i<minFloatRates.length;i++){
            	var minFloat=parseInt($(minFloatRates[i]).val());
            	var maxFloat=parseInt($(maxFloatRates[i]).val());
            	var rate=parseInt($(randomRates[i]).val());
            	if(maxFloat <= minFloat){
                    layer.msg("最大浮动比例必须大于最小浮动比例 错误范围:("+minFloat+"-->"+maxFloat+")");
                    return false;
                } 
            	if(maxFloat>400||minFloat <-100){
                    layer.msg("1.	浮动范围限定为：-100%--400% 错误范围:("+minFloat+"-->"+maxFloat+")");
                    return false;
                }
            	if(rate<0){
            		layer.msg("随机权重不可小于0");
                    return false; 
            	}
            	randomRateSum+=rate;
            }
            if(randomRateSum!=100){
            	layer.msg("随机权重必须为100");
                return false;
            }
            if(productNum <= 0 ){
                layer.msg("上架数量必须大于0");
                return false;
            }
            var integer = new RegExp("^[-+]{0,1}\\d+$");
            if(productNumNow <= 0  ){
                layer.msg("库存数量必须大于0");
                return false;
            }
            if(productNum > productNumNow){
                layer.msg("上架数量必须小于库存数量");
                return false;
            }else{
            	$("#classifyName").val($("#classifyId option:selected").text());
                $.ajax({
                    type: "post",
                    url: "/auctionProduct/save",
                    data: $(data.form).serialize(),
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
                            $(".layui-btn").removeClass("layui-btn-disabled");
                            if (result.code == '0000') {
                                parent.layer.closeAll('iframe');
                                parent.window.location.reload();
                            }
                       });
                    }
                });
            }
            return false;
        });
    });
    $(function() {
        var minFloatRate=0,maxFloatRate=0,randomRate=0;
        $("#ruleFootDiv").delegate(".addRule","click",function(e){
        	e.preventDefault();
        	var ihtml='<div class="layui-inline">';
			ihtml+='<label class="layui-form-label">范围段(%)</label>';
			ihtml+='<div class="layui-input-inline"  style="width: 100px;">';
			ihtml+='    <input name="minFloatRate" value="'+0+'" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<div class="layui-form-mid">-</div>';
			ihtml+='<div class="layui-input-inline" style="width: 100px;">';
			ihtml+='    <input name="maxFloatRate" value="'+0+'" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<label class="layui-form-label">权重(%)</label>';
			ihtml+='<div class="layui-input-inline" style="width: 100px;">';
			ihtml+='    <input name="randomRate" value="'+0+'" placeholder="%" autocomplete="off" lay-verify="required|integer2" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<button class="delRule layui-btn layui-btn-danger" ><i class="layui-icon"></i> 删除</button>';
			ihtml+='</div>';
			$("#ruleDiv").append(ihtml);
        });
        $("#ruleDiv").delegate(".delRule","click",function(e){
        	e.preventDefault();
			minFloatRate=$(this).parent().find("input[name='minFloatRate']").val();
			maxFloatRate=$(this).parent().find("input[name='maxFloatRate']").val();
			randomRate=$(this).parent().find("input[name='randomRate']").val();
			$(this).parent().remove();
			$(".reback").show();
        });
        $("#ruleFootDiv").delegate(".reback","click",function(e){
        	e.preventDefault();
			$(".reback").hide();
			var ihtml='<div class="layui-inline">';
			ihtml+='<label class="layui-form-label">范围段(%)</label>';
			ihtml+='<div class="layui-input-inline"  style="width: 100px;">';
			ihtml+='    <input name="minFloatRate" value="'+minFloatRate+'" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<div class="layui-form-mid">-</div>';
			ihtml+='<div class="layui-input-inline" style="width: 100px;">';
			ihtml+='    <input name="maxFloatRate" value="'+maxFloatRate+'" placeholder="%" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<label class="layui-form-label">权重(%)</label>';
			ihtml+='<div class="layui-input-inline" style="width: 100px;">';
			ihtml+='    <input name="randomRate" value="'+randomRate+'" placeholder="%" autocomplete="off" lay-verify="required|integer2" class="layui-input" type="text">';
			ihtml+='</div>';
			ihtml+='<button class="delRule layui-btn layui-btn-danger" ><i class="layui-icon"></i> 删除</button>';
			ihtml+='</div>';
			$("#ruleDiv").append(ihtml);
        });
    });
</script>
</body>
</html>