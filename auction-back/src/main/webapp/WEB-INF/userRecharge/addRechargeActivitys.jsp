<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
	<style>
		.layui-form-label {
			width: 120px;
		}
		.layui-input, .layui-textarea{
			width: 160px;
		}
		table th,table th input{
			color: #ffffff;
			background-color: #009688;
			font-size: 14px;
		}
		.layui-form-item {
			width: 700px;
		}
		.moneyItem {
			width: 106px;
			display: inline-block;
		}
		.moneyItemDiv{
			width: 160px;
		}
		.remark{
			width: 361px;
		}
		.percent_detailType {
			display: inline;
		}
	</style>
</head>
<body>
<form class="layui-form " action="" id="form">
	<input type="hidden" name="id" value="${rule.id }">
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">规则ID:</label>
		<div class="layui-input-inline">
			<label  class="layui-form-label">${rule.id }</label>
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">规则名称:</label>
		<div class="layui-input-inline">
			<input type="text" name="ruleTitle" lay-verify="required|ruleTitle"
				   placeholder="请输入规则名称" autocomplete="off"
				   class="layui-input" value="${rule.ruleTitle }">
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">规则对象:</label>
		<div class="layui-input-inline" style="width: 160px;">
			<select class="layui-input" id="ruleUser" name="ruleUser" style="padding-left: 27px;padding-right: 27px;">
				<c:forEach var="ruleUser" items="${ruleUsers}">
					<option <c:if test="${ruleUser.key eq rule.ruleUser}">selected="selected"</c:if>
							value="${ruleUser.key}">${ruleUser.value}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">活动状态:</label>
		<div class="layui-input-inline" style="width: 160px;">
			<input id="hasOpenRul" value="${hasOpenRul}" style="display: none"></input>
			<input name="ruleStatus" value="${rule.ruleStatus}" style="display: none"></input>
			<input type="checkbox"
				   lay-filter="switchsub"
				   <c:if test="${rule.ruleStatus eq 1}">checked</c:if> lay-skin="switch">
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">活动时间设置:</label>
		<div class="layui-inline">
			<input class="layui-input" id="ruleStartTimeStr" name="ruleStartTimeStr"
				   lay-verify="required|ruleStartTimeStr"
				   autocomplete="off" placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value="${rule.ruleStartTime}" pattern="yyyy-MM-dd"/>">
		</div>
		&nbsp;至&nbsp;
		<div class="layui-inline">
			<input class="layui-input" id="ruleEndTimeStr" name="ruleEndTimeStr" lay-verify="required|ruleEndTimeStr"
				   autocomplete="off" placeholder="yyyy-MM-dd"
				   value="<fmt:formatDate value="${rule.ruleEndTime}" pattern="yyyy-MM-dd"/>">
		</div>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">备注:</label>
		<textarea name="remark" placeholder="请输入内容(最多150字符)" value="${rule.remark}"
				  class="layui-textarea remark" maxlength="150">${rule.remark}</textarea>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<label class="layui-form-label">充值金额设置:</label>
	</div>
	<div class="layui-form-item" style="margin-top: 20px;">
		<input type="hidden" lay-verify="required|checkDetail" class="layui-input" value="checkDetail">
		<table class="layui-table"  lay-size="sm" style="margin-left: 30px;margin-right: 30px;">
			<colgroup>
				<col width="80">
				<col width="150">
				<col width="150">
				<col width="150">
				<col width="150">
			</colgroup>
			<thead>
			<tr>
				<th><input type="checkbox"
						   lay-filter="checkboxall"
						   lay-skin="primary"> &nbsp;全选</th>
				<th>预设充值金额</th>
				<th>赠送方式</th>
				<th>赠币</th>
				<th>积分</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="detail" items="${rule.details}">
				<tr id="${detail.id}" class="detailTr">
					<td>
						<input type="checkbox" lay-filter="checkboxsub"
							   lay-skin="primary" name="checkbox_${detail.id}"
							   value="${detail.id}">
					</td>
					<td>
						<input type="number" min="0" max="999999" maxlength="6"
							   oninput="this.value=checkNum(this.value)"
							   onafterpaste="this.value=checkNum(this.value)"
							   name="rechargeMoney_${detail.id}"
							   placeholder="请输入金额" autocomplete="off"
							   class="layui-input moneyItem rechargeMoney" value="${detail.rechargeMoney*1.0/100 }">
					</td>
					<td class="moneyItemDiv">

						<c:forEach var="detailType" items="${detailTypes}">
							<c:if test="${detailType.key eq detail.detailType}">
								<input type="number" name="value_detailType_${detail.id}" value="${detail.detailType}"
									 style="display: none;"/>
							</c:if>
							<input type="radio" name="detailType_${detail.id}" class="detailType moneyItem"
								   value="${detailType.key}" title="${detailType.value}" lay-filter="radiosub"
								   <c:if test="${detailType.key eq detail.detailType}">checked</c:if>/>
						</c:forEach>
					</td>
					<td>
						<input type="number" min="0" max="999999" maxlength="6"
							   oninput="this.value=checkNum(this.value)"
							   onafterpaste="this.value=checkNum(this.value)"
							   name="presentCoin_${detail.id}"
							   placeholder="请输入赠币" autocomplete="off"
							   class="layui-input moneyItem presentCoin" value="${detail.presentCoin*1.0/100 }">
						<div class="percent_detailType" name="percent_detailType_${detail.id}"
							 <c:if test="${detail.detailType!=2}">style="display: none;"</c:if>>%</div>
					</td>
					<td>
						<input type="number" min="0" max="999999" maxlength="6"
							   oninput="this.value=checkNum(this.value)"
							   onafterpaste="this.value=checkNum(this.value)"
							   name="points_${detail.id}"
							   placeholder="请输入积分" autocomplete="off"
							   class="layui-input moneyItem points" value="${detail.points*1.0/100}">
						<div class="percent_detailType" name="percent_detailType_${detail.id}"
							 <c:if test="${detail.detailType!=2}">style="display: none;" </c:if>>%</div>
					</td>
				</tr>
			</c:forEach>
			<tr id="11111111" hidden>
				<td>
					<input type="checkbox" lay-filter="checkboxsub"
						   lay-skin="primary" name="checkbox_11111111"
						   value="11111111">
				</td>
				<td>
					<input type="number" min="0" max="999999" maxlength="6"
						   onkeyup="this.value=checkNum(this.value)"
						   onafterpaste="this.value=checkNum(this.value)"
						   name="rechargeMoney_11111111"
						   placeholder="请输入金额" autocomplete="off"
						   class="layui-input moneyItem rechargeMoney">
				</td>
				<td class="moneyItemDiv">
					<input type="number" name="value_detailType_11111111" value="1"
						   style="display: none;"/>
					<c:forEach var="detailType" items="${detailTypes}">
						<input type="radio" name="detailType_11111111"
							   class="detailType moneyItem" lay-filter="radiosub"
							   value="${detailType.key}" title="${detailType.value}"
							   <c:if test="${detailType.key eq 1}">checked</c:if>/>
					</c:forEach>
				</td>
				<td>
					<input type="number" min="0" max="999999" maxlength="6"
						   onkeyup="this.value=checkNum(this.value)"
						   onafterpaste="this.value=checkNum(this.value)"
						   name="presentCoin_11111111"
						   placeholder="请输入赠币" autocomplete="off"
						   class="layui-input moneyItem presentCoin">
					<div class="percent_detailType" name="percent_detailType_11111111"
						 style="display: none;">%</div>
				</td>
				<td>
					<input type="number" min="0" max="999999" maxlength="6"
						   onkeyup="this.value=checkNum(this.value)"
						   onafterpaste="this.value=checkNum(this.value)"
						   name="points_11111111"
						   placeholder="请输入积分" autocomplete="off"
						   class="layui-input moneyItem points" >
					<div class="percent_detailType" name="percent_detailType_11111111"
						 style="display: none;">%</div>
				</td>
			</tr>
			</tbody>
		</table>
		<a onclick="deleteDetail()" class="layui-btn layui-btn-xs" style="float: right; margin-left: 20px;">删除</a>
		<a onclick="addDetail()" class="layui-btn layui-btn-xs" style="float: right;">新增</a>
	</div>

	<button style="display: none;" lay-filter="submitBtn" lay-submit="" />
</form>

<script>

    var selectTrIds = [];

    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {});
    }
    layui.use(['table','form','laydate','layer'], function() {

        var form = layui.form;
        //自定义验证规则
        form.verify({
            ruleTitle : function(value, item) {
                if (!new RegExp("(.+){1,100}$").test(value)) {
                    return '请输入1~100个字符的规则名称！';
                } else if (!new RegExp("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$").test(value)) {
                    //return '规则名称格式错误';
                }
			},
            checkDetail:function () {
                var str = submitVerify();
                if (str != null && str != ""){
                    return str;
                }
            },
            ruleEndTimeStr : function(value, item) {
				var ruleStartTimeStr = $("#ruleStartTimeStr").val();
				var ruleEndTimeStr = $("#ruleEndTimeStr").val();
                var nowDateStr = getNowFormatDate();
				var ruleStatus = $("input[name='ruleStatus']").val();
				console.log(ruleStartTimeStr);
				console.log(ruleEndTimeStr);
                if (!new RegExp("(.+){1,100}$").test(value)) {
                    return '请填写活动结束时间！' ;
                } else if (ruleStartTimeStr>ruleEndTimeStr) {
                    return '活动结束时间不能小于开始时间！'
                } else if (ruleStatus == 1 && ruleEndTimeStr<nowDateStr){
                    return '活动开启状态下，结束时间不能小于当前时间！'
				}
            },
            ruleStartTimeStr : [ /(.+){1,100}$/, '请填写活动开始时间！' ]
        });

        function submitVerify() {
            var trs = $("tbody tr.detailTr");
            len = trs.length;
            for (var i = 0; i < len;i++) {
                var tr = $(trs[i]);
                var detailId = tr.attr("id");
                console.log("detailId:"+detailId);
                tr = document.getElementById(detailId);

                var rechargeMoney = $("tr input[name='rechargeMoney_"+detailId+"']").val();
                console.log("rechargeMoney:"+rechargeMoney);
                if (rechargeMoney == null || rechargeMoney == NaN || rechargeMoney == ""){
                    rechargeMoney = 0;
                }
                rechargeMoney = rechargeMoney *100;
                rechargeMoney = parseInt(rechargeMoney);

                var detailTypeName = "value_detailType_"+detailId;
                var detailTypeItem = $("tr input[name='"+detailTypeName+"']");
                var detailType = detailTypeItem.val();
                console.log("detailTypeItem:"+detailTypeItem);
                console.log("detailType:"+detailType);
                if (detailType == null || detailType == NaN || detailType == ""){
                    detailType = 1;
                }
                detailType = parseInt(detailType);

                var presentCoin = $("tr input[name='presentCoin_"+detailId+"']").val();
                console.log("presentCoin:"+presentCoin);
                if (presentCoin <= 0 || presentCoin == NaN || presentCoin == ""){
                    presentCoin = null;
                }else {
                    presentCoin = presentCoin * 100;
				}
                if (detailType == 2 && presentCoin>10000){
                    return "赠币"+presentCoin/100+"不能超过100%！";
                }
                if(detailType == 3 && presentCoin>rechargeMoney){
                    return "赠币"+presentCoin/100+"不能超过充值金额！";
                }
            }
            return null;
        }

        function addDetailOneByOne(data,callback) {
            $.ajax({
                type : "post",
                url : "${path }${urlDetail}",
                data : data,
                dataType : "json",
                async:false,
                beforeSend : function() {
                    /*layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });*/
                },
                success : function(result) {
                    if (result.code == '0') {
                        callback();
                    }
                }
            });
        }

        var hasAlert = false;
        function submitDetails() {
            var trs = $("tbody tr.detailTr");
            len = trs.length;
            for (var i = 0; i < len;i++) {
                var tr = $(trs[i]);
                var detailId = tr.attr("id");
                console.log("detailId:"+detailId);
                tr = document.getElementById(detailId);

                var rechargeMoney = $("tr input[name='rechargeMoney_"+detailId+"']").val();
                console.log("rechargeMoney:"+rechargeMoney);
                if (rechargeMoney == null || rechargeMoney == NaN || rechargeMoney == ""){
                    rechargeMoney = 0;
                }
                rechargeMoney = rechargeMoney *100;
                rechargeMoney = parseInt(rechargeMoney);

                var detailTypeName = "value_detailType_"+detailId;
                var detailTypeItem = $("tr input[name='"+detailTypeName+"']");
                var detailType = detailTypeItem.val();
                console.log("detailTypeItem:"+detailTypeItem);
                console.log("detailType:"+detailType);
                if (detailType == null || detailType == NaN || detailType == ""){
                    detailType = 1;
                }
                detailType = parseInt(detailType);

                var presentCoin = $("tr input[name='presentCoin_"+detailId+"']").val();
                console.log("presentCoin:"+presentCoin);
                if (presentCoin <= 0 || presentCoin == NaN || presentCoin == ""){
                    presentCoin = null;
                }
                if(presentCoin>rechargeMoney && detailType != 2){
                    presentCoin = rechargeMoney;
                    if(!hasAlert){
                        layui.layer.msg("赠币不能超过充值金额，已置为赠币所能填写的最大值！", {
                            time : 1000
                        });
                        hasAlert = true;
					}
				}
				if (detailType == 2 && presentCoin>100){
                    presentCoin = 100;
                    if(!hasAlert){
                        layui.layer.msg("赠币不能超过充值金额，已置为赠币所能填写的最大值！", {
                            time : 1000
                        });
                        hasAlert = true;
                    }
				}
                presentCoin = presentCoin * 100;
                presentCoin = parseInt(presentCoin);

                var points = $("tr input[name='points_"+detailId+"']").val();
                console.log("points:"+points);
                if (points <= 0 || points == NaN || points == ""){
                    points = null;
                }
                points = points * 100;
                points = parseInt(points);

                var ruleId = $("input[name='id'][type='hidden']").val();
                console.log("ruleId:"+ruleId);
                if (ruleId == null || ruleId == NaN || ruleId == ""){
                    ruleId = 0;
                }
                ruleId = parseInt(ruleId);

                addDetailOneByOne(
                    {"id":detailId,
                        "rechargeMoney":rechargeMoney,
                        "detailType":detailType,
                        "presentCoin":presentCoin,
                        "points":points,
                        "ruleId":ruleId},function () {
                        count=count+1;
                        console.log("xiixixiiii"+count);
                    });
            }
        }
        var count = 0;
        var len = 0;
		form.on('submit(submitBtn)', function(data) {

            $.ajax({
                type : "post",
                url : "${path }${url}",
                data : data.field,
                dataType : "json",
                async:false,
                beforeSend : function() {
                    layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });
                },
                success : function(result) {
                    if (result.code == '0') {
                        count = 1;
                        if(result.ruleId != null && result.ruleId != ""){
                            $("input[name='id'][type='hidden']").val(result.ruleId);
                        }
                        submitDetails();
                    }else{
                        layui.layer.msg("请求失败，请稍后重试！", {
                            time : 1000
                        });
					}
                }
            });

			var timer = setInterval(function(){
                if (count == len+1){
                    layui.layer.msg("提交成功", {time : 1000}, function(){
                        parent.layer.closeAll('iframe');
                        parent.window.refreshPage();
                        console.log("x===========");
                        clearInterval(timer);
					});
                }
			},1);

            console.log("xiixixiiii22222222222yyyyyyyyyyy");

            return false;
        });
        Array.prototype.indexOf = function(val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) return i;
            }
            return -1;
        };
        Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };

        var hastip = false;
        form.on('switch(switchsub)',function (data) {
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            var checked = data.elem.checked;
			$("input[name='ruleStatus']").val(checked?1:0);
			if (checked && !hastip){
				var hasOpenRul = $("#hasOpenRul").val();
				if(hasOpenRul == 1){
                    layui.layer.open({
                        title: '提示'
                        ,content: '已有正在开启的活动，开启当前活动则会将其关闭！'
                    });
                    hastip = true;
                }
			}
        });

        form.on('checkbox(checkboxall)', function(data){
            selectTrIds = [];
            if(data.elem.checked){
				$("tbody tr div.layui-form-checkbox").addClass('layui-form-checked');
				var trs = $("tbody tr");
                for (var i = 0; i < trs.length; i++) {
                    var id = trs[i].getAttribute('id');
                    if(id != null && id != '' && id != '11111111'){
                        selectTrIds.push(id);
					}
                }
            }else {
                $("tbody tr div.layui-form-checked").removeClass('layui-form-checked');
            }
            console.log(selectTrIds);
        });

        form.on('checkbox(checkboxsub)',function (data) {
            if(data.elem.checked){
                if(selectTrIds.indexOf(data.value)<0){
                    selectTrIds.push(data.value);
                }
            }else {
                selectTrIds.remove(data.value);
            }
            console.log(this);
            console.log(selectTrIds);
        });

        form.on('radio(radiosub)',function (data) {
            var name = this.getAttribute("name");
            console.log("name:"+name + "data.value:"+data.value);
            $("tr input[name='value_"+name+"']").val(data.value);
            if (data.value == 2){
                $("div[name='percent_"+name+"']").css('display','inline');
			}else{
                $("div[name='percent_"+name+"']").css('display','none');
            }
            /*var str = submitVerify();
            if (str != null && str != ""){
                layui.layer.msg(str, {
                    time : 1000
                });
            }*/
		});

        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
            return currentdate;
        }

        var minDate = getNowFormatDate();
        var maxDate = "2099-12-31";
        function renderBeginTime() {
            layui.laydate.render({
                elem: '#ruleStartTimeStr',
                format:'yyyy-MM-dd',
                min: getNowFormatDate(),
                max: maxDate,
                change: function(value, date, endDate){
                    minDate = value;
                    console.log(value); //得到日期生成的值，如：2017-08-18
                    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                    renderEndTime();
                }
            });
		};
        function renderEndTime() {
            layui.laydate.render({
                elem: '#ruleEndTimeStr',
                format:'yyyy-MM-dd',
                min: minDate,
                max: "2099-12-31",
                change: function(value, date, endDate){
                    maxDate = value;
                    console.log(value); //得到日期生成的值，如：2017-08-18
                    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                    renderBeginTime();
                }
            });
        }
        renderBeginTime();
        renderEndTime();

        if($("tr").length <= 2){
            addDetail();
            console.log("jquery----===");
        }
        console.log("jquery----===222222");

    });

    function addDetail() {
        var tr = $("tr#11111111").clone(true);
        var detailId = tr.attr("id");
        var random = parseInt(Math.random()*100)+"";
        tr.attr("id",detailId+random);
        tr.addClass("detailTr");
        tr.removeAttr("hidden");
        $("tbody").append(tr);
        $("tr input[name='checkbox_"+detailId+"']").last().attr("value",detailId+random);
        $("tr input[name='checkbox_"+detailId+"']").last().attr("name","checkbox_"+detailId+random);
        $("tr input[name='rechargeMoney_"+detailId+"']:last").attr("name","rechargeMoney_"+detailId+random);
        $("tr input[name='value_detailType_"+detailId+"']:last").attr("name","value_detailType_"+detailId+random);
        $("tr input[name='detailType_"+detailId+"']:last").attr("name","detailType_"+detailId+random);
        $("tr input[name='detailType_"+detailId+"']:last").attr("name","detailType_"+detailId+random);
        $("tr input[name='detailType_"+detailId+"']:last").attr("name","detailType_"+detailId+random);
        $("tr input[name='presentCoin_"+detailId+"']:last").attr("name","presentCoin_"+detailId+random);
        $("tr input[name='points_"+detailId+"']:last").attr("name","points_"+detailId+random);
        $("tr div[name='percent_detailType_"+detailId+"']:last").attr("name","percent_detailType_"+detailId+random);
        $("tr div[name='percent_detailType_"+detailId+"']:last").attr("name","percent_detailType_"+detailId+random);
        console.log(tr);
        layui.form.render('checkbox');
        layui.form.render('radio');

        var trs = $("tbody tr.detailTr");
        len = trs.length;
        console.log("len:"+len);
        for (var i = 0; i < len;i++) {
            var tr = $(trs[i]);
            var detailId = tr.attr("id");
            console.log("detailId:" + detailId);
            tr = document.getElementById(detailId);

            var detailTypeName = "value_detailType_" + detailId;
            var detailTypeItem = $("tr input[name='" + detailTypeName + "']");
            var detailType = detailTypeItem.val();
            console.log("detailTypeItem:" + detailTypeItem);
            console.log("detailType:" + detailType);
            if (detailType == null || detailType == NaN || detailType == "") {
                detailType = 1;
            }
            detailType = parseInt(detailType);
            if (detailType == 1){
                console.log("000000000000000000000")
                var styleDiv = detailTypeItem.next().next();
                styleDiv.attr("class","layui-unselect layui-form-radio layui-form-radioed");
                var styleI = styleDiv.children("i:first-child");
                styleI.text("");
                styleI.attr("class","layui-anim layui-icon layui-anim-scaleSpring");
                console.log(styleDiv);
                console.log(styleI);
			}
        }
    }
    function deleteDetail() {
        for (var i = 0; i < selectTrIds.length; i++) {
            $(("tbody tr#" + selectTrIds[i])).remove();
        }
    }

    function checkNum(value){
        var str = value;
        var len1 = str.substr(0,1);
        var len2 = str.substr(1,1);

        //如果第一位是0，第二位不是点，就用数字把点替换掉
        if(str.length > 1 && len1==0 && len2 != '.'){
            str = str.substr(1,1);
        }

        //第一位不能是.
        if(len1=='.'){
            str = '';
        }

        //限制只能输入一个小数点
        if(str.indexOf(".")!=-1){
            var str_=str.substr(str.indexOf(".")+1);
            //限制只能输入一个小数点
            if(str_.indexOf(".")!=-1){
                str=str.substr(0,str.indexOf(".")+str_.indexOf(".")+1);
            }
        }

        if (str<0){
            str = 0;
            layui.layer.msg("最小不能小于0！", {
                time : 1000
            });
		}
        if (str>9999999){
            str = 9999999;
            layui.layer.msg("最大不能超过999999！", {
                time : 1000
            });
		}


        return str;
    }
</script>
</body>
</html>