<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8" %>
<!DOCTYPE>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>${website.site_name }</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="icon" href="<%=common%>admin-favicon.ico" type="image/x-ico"/>
	<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
	<style>
		html, body { width: 100%; height: 100%;}
		body, p, div, ul, li, span, img, button, a, ol, input, b { margin: 0; padding: 0;-webkit-tap-highlight-color: transparent; }
		ul, li { list-style: none; }
		input,button,a,textarea{ outline: none; }
		.box{width: 100%;min-width:1200px;height: 100%;min-height:700px;background-image: url(<%=common%>img/bd.png);background-size: 100%;background-repeat: no-repeat;}
		.box .left{width: 52%;padding-top: 310px;overflow: hidden;box-sizing: border-box;float: left;}
		.box .left img{width: 80px;margin-left: 200px;float: left;display: inline-block;margin-right: 15px;}
		.box .left .content{float: left;display: inline-block;color: #fff;}
		.box .left .content p{font-size: 34px;letter-spacing: 3px;margin-top: 10px}
		.box .left .content ._content{font-size: 20px;letter-spacing: 2px;margin-top: 8px;}
		.box .right{width: 413px;height: 456px;background: rgba(255,255,255,0.2);float: left;margin-top: 138px;color: #fff;}
		.box .right .top_title{width: 100%;height: 60px;font-size: 20px;line-height: 60px;text-align: center;border-bottom: 1px solid #fff;}
		.box .right .bottom{width: 100%;}
		.box .right .bottom form{width: 100%;padding-top: 38px;}
		.box .right .bottom .item{width: 100%;height: 36px;margin-bottom: 24px;font-size: 14px;}
		.box .right .bottom .item span{text-align:right;width: 124px;height:36px;line-height:36px;display: inline-block;display: inline-block;float: left;}
		.box .right .bottom .item input{padding:0 10px;box-sizing:border-box;color:#fff;width: 244px;height: 36px;border: 1px solid #fff;border-radius: 3px;background: transparent;float: left;}
		.box .right .bottom .item .short{width: 127px;}
		.box .right .bottom .item .yzm_box{width: 104px;height: 37px;float: left;margin-left: 12px;}
		.box .right .bottom .item a{width: 104px;height: 37px;line-height:37px;text-align:center;float: left;margin-left: 12px;background: #ff8400;border:none;border-radius: 4px;color: #fff;}
		.box .right .bottom form .login_btn{display:block;width: 350px;height: 50px;background: #ff8400;line-height: 50px;text-align: center;margin:0 auto;border: none;border-radius: 4px;margin-top: 37px;font-size: 18px;color: #fff;}
	</style>
	<script type="text/javascript">
        $(document).ready(function () {
            if (window.parent != window) {
                window.top.location.href = location.href;
            }
        });
	</script>
</head>
<body>
<div class="box">

	<div class="left">
		<img src="<%=common%>img/logo.png" />
		<div class="content">
			<p>${website.site_name }</p>
			<%--<div class="_content">专业的二手物品回收平台</div>--%>
		</div>
	</div>
	<div class="right  layui-anim layui-anim-scaleSpring">
		<div class="top_title">${website.site_name }后台管理系统</div>
		<div class="bottom">
			<form id="jvForm" name="jvForm" action="login" method="post" onsubmit="return login();">
				<div class="item">
					<span>手机号码：</span>
					<input type="text" id="userMobile" name="userMobile"  maxlength="11"/>
				</div>
				<div class="item">
					<span>密码：</span>
					<input name="userPassword" type="password" id="userPassword" maxlength="32"/>
				</div>
				<div class="item">
					<span>验证码：</span>
					<input class="short" type="text" name="captcha" id="captcha"  value="1111"  maxlength="4" />

					<!--验证码-->
					<div class="yzm_box">
						<img id="imgCap" style="margin-top: 3px; width: 73px; height: 30px;" src="<%=path %>/captcha.svl" onclick="this.src='<%=path %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
					</div>
				</div>
				<div class="item">
					<span>短信验证码：</span>
					<input class="short" type="text"  name="smsCode"  id="smsCode" maxlength="6"  value="0000"/>
					<a href="javascript:;" id="sendCode" onclick="sendCode();">获取验证码</a>
				</div>
				<input class="login_btn" type="submit" value="登录"/>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
    function sendCode(){
        if (jvForm.userMobile.value==""){
            alert("手机号码不能为空！");
            return false;
        }else if (jvForm.userPassword.value==""){
            alert("密码不能为空！");
            return false;
        }else if (jvForm.captcha.value==""){
            alert("验证码不能为空！");
            return false;
        }else{
            $.ajax({
                type : "post",
                data:{
                    "userMobile":$("[name='userMobile']").val(),
                    "userPassword":$("[name='userPassword']").val(),
                    "captcha":$("[name='captcha']").val()
                },
                url : "sendSmsBack",
                success : function(ret) {
                    if(ret.code == '0'){
                        time('sendCode','sendCode()');
                        alert(ret.msg);
                    }else{
                        $("#imgCap").trigger("click");
                        alert(ret.msg);
                    }
                },
                error:function(ret){
                    $("#imgCap").trigger("click");
                }
            });
        }
    }
    function login(){
        if (jvForm.userMobile.value==""){
            alert("手机号码不能为空！");
            return false;
        }else if (jvForm.userPassword.value==""){
            alert("密码不能为空！");
            return false;
        }else if (jvForm.captcha.value==""){
            alert("验证码不能为空！");
            return false;
        }else if (jvForm.smsCode.value==""){
            alert("短信验证码不能为空！");
            return false;
        }
    }
    var msg="${message}";
    if(msg){
        alert(msg);
    }
</script>
</body>
</html>
