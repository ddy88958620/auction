<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
%>
<meta charset="utf-8">
	<title>404</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
</head>

<body>
	<body class="childrenBody">
		<div style="text-align: center; padding: 11% 0;">
			<i class="layui-icon" style="line-height: 20rem; font-size: 20rem; color: #393D50;"  class="layui-anim layui-anim-rotate">&#xe61c;</i>
			<p style="font-size: 20px; font-weight: 300; color: #999;">页面走丢了!</p>
		</div>
	</body>
</html>
