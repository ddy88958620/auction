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
<form class="layui-form  layui-form-pane1 pzjzsj"  action="" id="form"  style="margin-left: 500px;">
	<input type="hidden" name="id" value="${userInfo.id }">
	<fieldset class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 1000px;">
		<legend style="margin-bottom: 20px;"><strong>编辑资料</strong></legend>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">用户ID:</label>
			<div class="layui-input-inline">
				<input  type="text" class="layui-input" readonly="readonly"  value="${userInfo.id }">
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">用户名:</label>
			<div class="layui-input-inline">
				<input type="text" name="realName" lay-verify="required|realName"   placeholder="请输入用户名" autocomplete="off"  class="layui-input" value="${userInfo.realName}">
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">手机号码:</label>
			<div class="layui-input-inline">
				<input type="text" name="userPhone" lay-verify="required|userPhone"  autocomplete="off" placeholder="请输入手机号码"
					   class="layui-input"  value="${userInfo.userPhone}">
			</div>
		</div>
		<%--<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">性别:</label>
			<div class="layui-input-block">
				<input type="radio" name="sex" value="0" title="保密" checked>
				<input type="radio" name="sex" value="1" title="男">
				<input type="radio" name="sex" value="2" title="女" >
			</div>
		</div>--%>
		<%--<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">生日：</label>
			<div class="layui-input-block" style="width:  150px">
				<input class="layui-input" id="birthday" name="startTime"  placeholder="请输入生日" lay-key="1" autocomplete="off"  >
			</div>
		</div>--%>
		<div class="layui-form-item" >
			<label class="layui-form-label">城市：</label>
			<div class="layui-input-inline" >
				<input readonly="readonly" type="text" name="city" lay-verify="required|realName"
					    autocomplete="off"class="layui-input" value="${userInfo.provinceName}${userInfo.cityName}">
			</div>
		</div>

		<div class="layui-form-item" style="margin-top: 20px;" >
			<label class="layui-form-label">昵称:</label>
			<div class="layui-input-inline">
				<input type="text" name="nickName"  autocomplete="off" class="layui-input" value="${userInfo.nickName}" readonly="readonly">
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">新密码:</label>
			<div class="layui-input-inline">
				<input type="password" name="loginPassword" id="loginPassword1" lay-verify="required|loginPassword"
					   placeholder="请输入密码" autocomplete="off"
					   class="layui-input" >
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 20px;">
			<label class="layui-form-label">确认密码:</label>
			<div class="layui-input-inline">
				<input type="password" name="loginPassword2" id="loginPassword2" lay-verify="required|appraisesLevel"
					   placeholder="请再次输入密码" autocomplete="off"
					   class="layui-input" >
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">账户状态:</label>
			<div class="layui-input-block" aria-readonly="true">
				<div class="layui-input-inline" style="width: 160px;">
					<input name="status" id="status" value="${userInfo.status}" style="display: none" readonly="readonly"/>
					<input type="checkbox"
						   lay-filter="switchsub"
						   <c:if test="${userInfo.status eq 1}">checked</c:if> lay-skin="switch" readonly="readonly">
				</div>
			</div>
		</div>
		<div class="layui-form-item" style="margin-top: 40px;">
			<div class="layui-input-block">
				<button type="submit" class="layui-btn"  lay-submit lay-filter="check1" >提交</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="text"  class="layui-btn"  onclick="closeForm()">取消</button>
			</div>
		</div>
		<button style="display: none;" lay-filter="submitBtn" lay-submit="" />
	</fieldset>
</form>

<script>
    function closeForm() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        return false;
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }
    layui.use(['form','upload','laydate'],function() {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#birthday'
        });
        var form = layui.form;
        form.on('switch(switchsub)',function (data) {
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            var checked = data.elem.checked;
            $("input[name='status']").val(checked?1:2);
        });
        //自定义验证规则
        form.verify({
            realName : function(value, item) {
                if (!new RegExp("(.+){2,10}$").test(value)) {
                    return '长度必须在2到10之间';
                } else if (!new RegExp("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$").test(value)) {
                    return '姓名格式错误';
                }
            },
            userPhone : [ /^[1][3,4,7,5,8,9][0-9]{9}$/, '手机号码格式错误' ],
            loginPassword : [ /^$|(.+){6,16}$/, '长度必须在6到16之间' ],
            appraisesLevel:function (value) {
                var  loginPassword1=$("#loginPassword1").val();
                var  loginPassword2=$("#loginPassword2").val();
                if (loginPassword1 != loginPassword2){
                    $("#loginPassword1").val("");
                    $("#loginPassword2").val("");
                    return "两次输入的密码不一致!";
                }
            },
			provinceCode : function(value) {
                if (value == '-999') {
                    return '请选择所属省';
                } else {
                    //获取select元素的引用
                    var eduElement = document.getElementById("provinceCode");
                    //1 获取所有的option标签
                    var optionElements = eduElement.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements.length; i++){
                        var optionElement = optionElements[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#provinceCode").val()) {
                            $("#provinceName").val(text);
                        }
                    }
                }
            },
            cityCode : function(value) {
                if (value == '-999') {
                    return '请选择所属市';
                } else {
                    //获取select元素的引用
                    var eduElement2 = document.getElementById("cityCode");
                    //1 获取所有的option标签
                    var optionElements2 = eduElement2.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements2.length; i++){
                        var optionElement = optionElements2[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#cityCode").val()) {
                            $("#cityName").val(text);
                        }
                    }
                }
            },
            townCode : function(value) {
                if (value == '-999') {
                    return '请选择所属区县';
                } else {
                    //获取select元素的引用
                    var eduElement3 = document.getElementById("townCode");
                    //1 获取所有的option标签
                    var optionElements3 = eduElement3.getElementsByTagName("option");
                    //2 遍历option
                    for(var i = 0; i<optionElements3.length; i++){
                        var optionElement = optionElements3[i];
                        var text = optionElement.text;
                        var value = optionElement.value;
                        if (value == $("#townCode").val()) {
                            $("#townName").val(text);
                        }
                    }
                }
            }
        });
        form.on('submit(check1)', function (data) {
            $.post("${path }${url}", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("修改成功！");
                        parent.layer.closeAll('iframe');
                        parent.window.refreshPage();
                    } else {
                        parent.layer.msg("修改失败！"+data.msg);
                    }
                }, "json");
            return false;
        });

        form.on('select(provinceCode)', function(data){
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#provinceCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#provinceCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#cityCode").empty();
                    $("#townCode").empty();
                    $("#cityCode").append("<option value='-999'>请选择</option>");
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#cityCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                    form.render('select');
                });
            }
        });

        form.on('select(cityCode)', function(data){
            if (!$(this).find("option").first().attr("selected")) {
                var options = $(this).find("option:selected");
            }

            if($('#cityCode').val() != "") {
                $.post("/channel/getAddressInfo", {parentId: $('#cityCode').val()}, function (result) {
                    var jsonArr = eval(result);
                    $("#townCode").empty();
                    $("#townCode").append("<option value='-999'>请选择</option>");
                    $.each(jsonArr, function(i, item) {
                        $("#townCode").append("<option value='"+item.id+"'>"+item.addressName+"</option>");
                    });
                    form.render('select');
                });
            }
        });
    });
    var html = {
        type : 2,
        shade : 0.3,
        maxmin : true,
        area : [ '100%', '100%' ],
        anim : 1,
        btnAlign : 'r',
        btn : [  '取消' ],
        success : function(layero, index) {}
    };
    var path = "${path}";

    function str(wxNickName,qqNickName) {
        return wxNickName.replace(" ","+");
        return qqNickName.replace(" ","+");
    };
</script>
</body>
</html>