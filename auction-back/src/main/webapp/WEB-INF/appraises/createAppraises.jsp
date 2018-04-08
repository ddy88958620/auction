<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
%>
<head>
<meta charset="utf-8">
<title>layui</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<c:set var="path" value="<%=path%>"></c:set>
<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico" />
<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="<%=common%>/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=common%>/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=common%>/ztree/js/jquery.ztree.core.js"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>



 
<form class="layui-form  layui-form-pane1 pzjzsj" action="" id="form">


<div class="layui-form-item">
    </div>
	<input type="hidden" id="orderId" name="orderId" value="${orderId}">
	<input type="hidden" id="orderTime" name="orderTime" value="${orderTime}">
	
    <div class="layui-inline">
      <label class="layui-form-label">选择时间：</label>
      <div class="layui-input-inline">
        <input type="text" name="createTime" class="layui-input" lay-verify="createTime" id="test5" placeholder="yyyy-MM-dd HH:mm:ss">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">感受:</label>
        <div class="layui-input-block" style="width: 30%">
            <textarea name="content" placeholder="请输入内容"  lay-verify="content" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">订单时间:</label>
        <div class="layui-input-inline">
           <label  class="layui-form-label" lay-verify="orderTime"  style="width: 140px;text-align: left;">${orderTime}</label>
       </div>
    </div>
    
<div class="layui-upload">
  <button type="button" class="layui-btn" id="test2">多图片上传</button> 
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
    预览图：
    <div id="mutiPic"></div>
    <input type="hidden" id="appraisesPic" name="appraisesPic"/>
    <p id="upMutilHeadTxt"></p>
 </blockquote>
</div>
<button style="display: none;" lay-filter="submitBtn" lay-submit="" />
</form>
 <script>
 var picArray ="";
 layui.use('upload', function () {
     var upload = layui.upload;
     
     //普通图片上传
     var uploadInst = upload.render({
         elem: '#upHeadPage'
         , url: '${path}/upload/uploadFiles'
         , before: function (obj) {
             //预读本地文件示例，不支持ie8
             obj.preview(function (index, file, result) {
                 $('#headImg').attr('src', result); //图片链接（base64）
             });
         }
         , done: function (res) {
             //如果上传失败
             if (res.code == 0) {
                 $('input[name="buyPic').attr('value',res.data.src); //图片链接（base64）
             } else {
                 return layer.msg('上传失败');
             }
             //上传成功
         }
         , error: function () {
             //演示失败状态，并实现重传
             var demoText = $('#upHeadTxt');
             demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
             demoText.find('.demo-reload').on('click', function () {
                 uploadInst.upload();
             });
         }
     });
     
   //多图片上传
     upload.render({
       elem: '#test2'
       ,url: '${path}/upload/uploadFiles'
       ,multiple: true
       ,before: function(obj){
         //预读本地文件示例，不支持ie8
         obj.preview(function(index, file, result){
           $('#mutiPic').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width:50px;height:50px">')
         });
       }
       ,done: function(res){
         //上传完毕
         //如果上传失败
         if (res.code == 0) {
        	 picArray = picArray+res.data.src+",";
             $('input[name="appraisesPic').attr('value',picArray);
             
         } else {
             return layer.msg('上传失败');
         }
       }, error: function () {
           //演示失败状态，并实现重传
           var demoText = $('#upMutilHeadTxt');
           demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
           demoText.find('.demo-reload').on('click', function () {
               uploadInst.upload();
           });
       }
     });
 });
 
</script>

<script>
function resetForm() {
	document.getElementById("form").reset();
}
function submitForm() {
	$("button[lay-filter='submitBtn']").trigger('click');
}
function select(){
	alert("w3wew");
}
function getBuyNickName(){
	  var productId = $("#productId").val();
	  $.ajax({
			type : "post",
			url : "${path}/appraises/getBuyNickName",
			data : {'productId':productId},
			dataType : "json",
			success : function(result) {
				layer.msg(result.msg, {
				}, function() {
					if (result.code == '0') {
					 $("#buyNickId").empty();
					    var buyNickNameList = result.buyNickNameList;
					    var options = '<option value="">--请选择--</option>';
					    $.each(buyNickNameList, function(idx, obj) {
					     options = options + '<option value="' + idx + '">' + obj + '</option>';
					    });
					    $("#buyNickId").append(options);
					    form.render();
					}  
				});
			}
		});
	}	
	
function createAppraises() {
	$("button[lay-filter='submitBtn']").trigger('click');
}	
	
var form;
layui.use(['form', 'layedit', 'laydate'], function(){
	
	<c:if test="${not empty error}">
    parent.layer.msg("${error}");
    parent.layer.closeAll('iframe');
    parent.window.refreshPage();
    </c:if>
	
  form = layui.form;
  var layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
//时间选择器
  laydate.render({
    elem: '#test5'
    ,type: 'datetime'
  });
  
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
 
  //自定义验证规则
  form.verify({
	  buyNickName : function(value) {
	if (value.length < 1) {
		return '请选择用户名';
		}
	},
productId:function(value){
  	if(value.length < 1){
        return '请输入商品ID';
      }
  	if(!new RegExp("^[0-9]+.?[0-9]*$").test(value)){
  		return '商品ID只能是数字';
  		}
  	}
  ,createTime:function(value){
  	if(value.length < 1){
        return '请选择时间';
      }
  	}
    ,content: function(value){
    if(value.length < 1){
        return '请输入内容';
     }
    if(value.length >200){
        return '最多只能输入200字！';
     }
    }
  });
  
  form.on('submit(submitBtn)', function(data) {
	  
    var orderTime=$("#orderTime").val();  
    var start=new Date(orderTime.replace("-", "/").replace("-", "/"));  
    var endTime=$("#test5").val();  
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
	  
    if(end<start){  
    	layer.msg('晒单时间不能小于订单时间！');
    }
    else if($("#mutiPic").children("img").length > 3){
   		layer.msg('最多只能上传3张图片！');
      }else{
    	  $.ajax({
  			type : "post",
  			url : "${path }/appraises/saveOrderAppraises",
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
  						parent.layer.closeAll('iframe');
  						parent.window.refreshPage();
  					}
  				});
  			}
  		});
      }
		return false;
	});
  

});
</script>
