<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath() + "";
	String common = path + "/resources/";
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
<script src="<%=common%>/system/appraises/appraisesRules.js" charset="utf-8"></script>
<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
<script src="<%=common%>/js/util.js" charset="utf-8"></script>
<script src="<%=common%>/system/qrcode.min.js" charset="utf-8"></script>
</head>
<body>
<div id="toolBar">
	<br>&nbsp;&nbsp;
	<button class="layui-btn layui-btn-radius" data-type="reload" style="display: none;" id="btnSearch">
	<i class="layui-icon layui-anim layui-anim-scale">&#xe615;</i>
</button>
</div>
	<table class="layui-hide" id="data" lay-filter="test3">
	</table>
	<script>
		var myId = "${parentId}";
		var path = "${path}";
		var table;
		var form;
		var saveUserRole;
		var saveRoleWindow;
		layui.use(['table','form'], function() {
			findBtns(null, "${parentId}");
			table = layui.table;
			form = layui.form;
			table.render({
				id:"id",
				elem : '#data',
				even : true,
				url : '${path}/appraisesRules/getAppraisesRulesPage',
				height : "full-58",
				method : 'post',
				page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
					layout : [ 'prev', 'page', 'next', 'skip', 'count', 'limit' ],//自定义分页布局//,curr: 5 //设定初始在第 5 页
					groups : 5,//只显示 1 个连续页码
					limits : [ 10, 20, 50, 100, 200 ],
					first : "首页",
					theme : "#FF5722;",
					limit : 10,
					last : "尾页"

				},
                id : 'search',
				cols : [ [ {
					type : 'checkbox',
					fixed : 'left',
                    name : '全选',
					title:"全选",
                    width : 100
				}, {
					type : 'numbers',
					align : 'center',
					title : '序号',
					fixed : "left"
				}, {
					field : 'appraisesLevel',
					title : '评论级别',
					align : 'center',
					sort : true,
					fixed : "left",
                    width : 150
				}, {
					field : 'appraisesWords',
					align : 'center',
					title : '评论字数(R)',
                    templet : '<div>{{toWords(d.appraisesWords)}}</div>',
				}, {
					field : 'picNumber',
					title : '图片数量(张)',
					align : 'center',
                    templet : '<div>{{toNum(d.picNumber)}}</div>',
				}, {
					field : 'baseRewords',
					title : '基础奖励(积分)',
                    edit : 'text',
					align : 'center'
				}, {
					field : 'showRewords',
					title : '出镜奖励(赠币)',
					edit : 'text',
					align : 'center'
				}] ]

			});
			var $ = layui.$, active = {
				reload : function() {
					//执行重载
					table.reload('search', {
						page : {
							//重新从第 1 页开始
							page : 1
						},
						where : {
							status : $('#status').val(),
							buyId : $('#buyId').val(),
							type : $('#type').val(),
							createTimeStart : $('#createTimeStart').val(),
							createTimeEnd : $('#createTimeEnd').val(),
							productName : $('#productName').val()
							
						}
					});
				}
			};
			$('#btnSearch').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			$("#toolBar").keydown(function(event) {
				if (event.keyCode == 13) {
					refreshPage();
				}
			});
			refreshPage = function() {
				$('#btnSearch').trigger("click");
			}
		});
	</script>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/iframe.known.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/window.unknown.btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/toDelete.js" charset="utf-8"></script>

<script>
layui.use(['form', 'layedit'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit;
});

</script>
<script type="text/javascript">
    layui.use('table', function(){
        var table = layui.table;

        //监听单元格编辑
        table.on('edit(test3)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段
			if("showRewords"==field){
                if( !new RegExp("^([1-9]\\d*|[0]{1,1})$").test(value)){
                    layer.msg( "出镜奖励积分只能是正整数");
                    refreshPage();
                    return false;
                }else  if (value>5){
                    layer.msg( "出镜奖励积分不大于5，请重新输入");
                    refreshPage();
                    return false;
                }
			}else  if("baseRewords"==field){
                if( !new RegExp("^([1-9]\\d*|[0]{1,1})$").test(value)){
                    layer.msg( "基础奖励积分只能是正整数");
                    refreshPage();
                    return false;
                }else  if (value>500){
                    layer.msg( "基础奖励最高500积分,请重新输入");
                    refreshPage();
                    return false;
                }
            }
			$.ajax({
				type: 'POST',
				url: 'updateAppraisesRules',
				data: obj.data,
				beforeSend:function(){
					 index_wx = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
				},
				success: function(data){
					if(data.code=="0"){
						layer.close(index_wx);
						var word="";
						if("showRewords"==field){
						    word="出镜奖励";
						}else {
						    word="基础奖励";
						}
						layer.msg( word + ' 字段更改为：'+ value);
					}else{
						console.log(data);
						layer.msg(data.msg); }
				},
				dataType:"json"});
			});
    });

    function toWords(words){
        var list=words.split("-");
        var list0=list[0];
        var list1=list[1];
        var result="";
        //只有最小值
		if(list1==null ||list1==""){
            result= "R>"+list0;
		}else if(list0==null ||list0==""){
            //只有最大值
            result= "R<="+list1;
        }else {
            //左右都有值
            result=list0+"&lt;R&lt;="+list1;
		}
        return result;
    }

    function toNum(num){
        var list=num.split("-");
        var list0=list[0];
        var list1=list[1];
        var result="";
        //只有最小值
        if(list1==null ||list1==""){
            result= ""+list0;
        }else if(list0==null ||list0==""){
            //只有最大值
            result= ""+list1;
        }else {
            if(list0 ==list1){
                result=list0;
			}else{
                //左右都有值 不相等
                result=num;
			}

        }
        return result;
    }
</script>
</body>
</html>