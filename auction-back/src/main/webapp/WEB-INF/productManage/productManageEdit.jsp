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
	<link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
	<link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
	<link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
	<link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
	<link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title">
	<legend>商品修改</legend>
</fieldset>
<form class="layui-form layui-form-pane" id="product_manage_edit_form"  method="post">
	<div class="layui-form-item">
		<label class="layui-form-label">商品ID</label>
		<div class="layui-input-inline">
			<input type="text" name="productId" id="productId" value="${productVo.productId}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品名称</label>
		<div class="layui-input-inline">
			<input type="text" name="productName" value="${productVo.productName}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品标题</label>
		<div class="layui-input-inline">
			<input type="text" name="productTitle" value="${productVo.productTitle}" autocomplete="off" class="layui-input">
		</div>
	</div>




	<div class="layui-inline">
		<label class="layui-form-label">渠道</label>
		<div class="layui-input-inline">
			<select name="merchantId" lay-verify="required" lay-search="">
				<c:forEach items="${merchants}" var="item">
					<c:choose>
						<c:when test="${productVo.merchantId == item.id}">
							<option value="${item.id}" selected>${item.merchantName}</option>
						</c:when>
						<c:otherwise>
							<option value="${item.id}" >${item.merchantName}</option>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</select>
		</div>
	</div>
	</div>





	<div class="layui-form-item">
		<label class="layui-form-label">商品库存</label>
		<div class="layui-input-inline">
			<input type="text" name="productNum" lay-verify="integer" value="${inventoryVo.productNum}"  autocomplete="off"
				   class="layui-input">
		</div>
	</div>


	<div class="layui-form-item">
		<label class="layui-form-label">预览图</label>
		<div class="layui-input-block">

			<div class="layui-upload">
				<button type="button" class="layui-btn layui-btn-normal" id="previewBtn">选择一张图片</button>
				<div class="layui-upload-list">
					<table class="layui-table">
						<thead>
						<tr><th>文件名</th>
							<th>状态</th>
							<th>操作</th>
						</tr></thead>
						<tbody id="previewList"></tbody>
					</table>
				</div>
				<button type="button" class="layui-btn" id="previewAction">开始上传</button>
			</div>
		</div>
	</div>


	<div class="layui-form-item">
		<label class="layui-form-label">商品图</label>
		<div class="layui-input-block">

			<div class="layui-upload">
				<button type="button" class="layui-btn layui-btn-normal" id="prodBtn">选择多张图片</button>
				<div class="layui-upload-list">
					<table class="layui-table">
						<thead>
						<tr><th>文件名</th>
							<th>状态</th>
							<th>操作</th>
						</tr></thead>
						<tbody id="prodList"></tbody>
					</table>
				</div>
				<button type="button" class="layui-btn" id="prodAction">开始上传</button>
			</div>
		</div>
	</div>


	<div class="layui-form-item">
		<label class="layui-form-label">详情图</label>
		<div class="layui-input-block">

			<div class="layui-upload">
				<button type="button" class="layui-btn layui-btn-normal" id="testList">选择多张图片</button>
				<div class="layui-upload-list">
					<table class="layui-table">
						<thead>
						<tr><th>文件名</th>
							<th>状态</th>
							<th>操作</th>
						</tr></thead>
						<tbody id="demoList"></tbody>
					</table>
				</div>
				<button type="button" class="layui-btn" id="testListAction">开始上传</button>
			</div>
		</div>
	</div>


	<div class="layui-form-item">
		<label class="layui-form-label">渠道价格</label>
		<div class="layui-input-inline">
			<input type="text" name="productAmount" value="${productVo.productPrice}" lay-verify="integer" placeholder="请输入渠道价格" autocomplete="off"
				   class="layui-input">
		</div>

		<label class="layui-form-label">市场价格</label>
		<div class="layui-input-inline">
			<input type="text" name="salesAmount" value="${productVo.salesAmount}" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">竞拍规则</label>
		<div class="layui-input-block">
			<textarea  name="remarks" class="layui-textarea">${productVo.rules}</textarea>
		</div>
	</div>



	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" type="submit" lay-submit lay-filter="productUpdate">立即提交</button>
		</div>
	</div>
	<input type="hidden" id="picUrl" name="picUrl"/>
	<input type="hidden" id="prod" name="prod"/>
	<input type="hidden" id="detail" name="detail"/>
</form>
	<script src="<%=common%>/system/btns.js" charset="utf-8"></script>
	<script src="<%=common%>/system/productManage/productManageList.js" charset="utf-8"></script>
	<script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
	<script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
	<script src="<%=common%>/js/util.js" charset="utf-8"></script>
<script type="text/javascript">

    layui.use('form',function () {
        var form = layui.form;
        var layer = layui.layer;
        form.render();
        form.on('submit(productUpdate)', function (data) {

            var previewTd = "";
            var prodTd = "";
            var detailTd = "";
         	 var flag = true;
            $('#previewList tr').each(function(i){                   // 遍历 tr
				//alert("==="+JSON.stringify($(this).children('td')));
			   $(this).children('td').each(function(j){  // 遍历 tr 的各个 td
				   if(i==0 && j==0){
                       previewTd = $(this).text();
				   }
				   if($(this).text() == "等待上传"){
						layer.msg("请上传图片");
						flag = false;
						return false;
				   }
			   });
			});

            $('#prodList tr').each(function(i){                   // 遍历 tr
                $(this).children('td').each(function(j){  // 遍历 tr 的各个 td
                    if(j==0){
                        prodTd += $(this).text()+",";
                    }
                    if($(this).text() == "等待上传"){
                        layer.msg("请上传图片");
                        flag = false;
                        return false;
                    }
                });
            });

            $('#demoList tr').each(function(i){                   // 遍历 tr
                $(this).children('td').each(function(j){  // 遍历 tr 的各个 td
                    if(j==0){
                        detailTd += $(this).text()+",";
                    }
                    if($(this).text() == "等待上传"){
                        layer.msg("请上传图片");
                        flag = false;
                        return false;
                    }
                });
            });

            if (previewTd == "" || previewTd == undefined ) {
                layer.msg("请上传预览图");
                return false;
            }else {
                $("#picUrl").val(previewTd);
			}

            if (prodTd == "" || prodTd == undefined) {
                layer.msg("请上传商品图");
                return false;
            }else{
                $("#prod").val(prodTd);
			}

            if (detailTd == "" || detailTd == undefined) {
                layer.msg("请上传详情图");
                return false;
            }else{
                $("#detail").val(detailTd);
			}

            var picss=[];
            var detailList = {};
            var picArray = new Array();
            var picObj = {};
			picObj.picType = 2;
			picObj.picUrl =previewTd;
			picObj.productId = $("#productId").val();
            picArray.push(picObj);

            var st = prodTd.split(",");
			for (var i=0;i<st.length;i++){
			    if(st[i] != "") {
                    picObj = {}
                    picObj.picType = 1;
                    picObj.picUrl = st[i];
                    picObj.productId = $("#productId").val();
                    picArray.push(picObj);
                }

			}


            var stDetail = detailTd.split(",");
            for (var i=0;i<stDetail.length;i++){
                if(stDetail[i] != "") {
                    picObj = {}
                    picObj.picType = 0;
                    picObj.picUrl = stDetail[i];
                    picObj.productId = $("#productId").val();
                    picArray.push(picObj);
                }
            }
            var newData =  $("form").serialize()+'&allPic='+JSON.stringify(picArray);
			if(flag) {
                $.post("/product/editProduct", newData,
                    function (data) {
                        if (data.code == '0') {
                            layer.msg("修改成功!", {
                                icon: 1,
                                time: 1000,
                                end: function () {
                                    var index = parent.layer.getFrameIndex(window.name);
                                    parent.layer.close(index);
//                                    parent.location.reload();
                                    parent.window.refreshPage();
                                }
                            });
                        }
                    }, "json");
            }
            return false;

        });
    });


	$().ready(function () {
	   var previewPicPre = '${productVo.previewPic}';
        var previewListView = $('#previewList');
        var id = "upload_pre1";
	   if(previewPicPre!=''){
           var tr = getDeleteTr(id,previewPicPre) ;
           previewListView.append(tr);
	   }
        var prodView = $('#prodList');
	    var prodViewPre = '${picList}';
        var arrayProd = new Array();
        var arrayDetail = new Array();

        <c:forEach items="${picList}" var="item" varStatus="index"  >
			<c:if test="${item.picType == 0}">
        		arrayDetail.push("${item.picUrl}");
			</c:if>
        <c:if test="${item.picType == 1}">
        		arrayProd.push("${item.picUrl}");
        </c:if>

        </c:forEach>
        for(var i=0;i<arrayProd.length;i++){
			var id ="prod_pic_pre_"+i;
			var tr = getDeleteTr(id,arrayProd[i]);
            prodView.append(tr);
        }

        var demoListView = $('#demoList');
        for(var i=0;i<arrayDetail.length;i++){
            var id ="detail_pic_pre_"+i;
            var tr = getDeleteTr(id,arrayDetail[i]);
            demoListView.append(tr);
        }

    });






    layui.use('form',function () {
        var form = layui.form;
        var layer = layui.layer;
        form.render();
    });


    layui.use('upload', function () {
        var upload = layui.upload;
        var picArray = '';
        //预览图
        var previewListView = $('#previewList')
            ,uploadListIns = upload.render({
            elem: '#previewBtn'
            ,url: '/upload/uploadFiles'
            ,accept: 'images'
            ,multiple: false
            ,auto: false
            ,bindAction: '#previewAction'
            ,choose: function(obj){
                if($('#previewList').children().length>=1){
                    layer.msg("只能上传一张预览图");
                    return;
                }
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列

                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    previewListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var tr = previewListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(0).html(res.data.src);
                    tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(2).html(''); //清空操作
                    //$('#previewList').append('<input  name="preview" value="' + res.data.src + '" type="hidden">')
                    //$('input[name="picUrl').attr('value', res.data.src);
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = previewListView.find('tr#upload-'+ index)
                    ,tds = tr.children();

                tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(2).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

        var prodView = $('#prodList')
            ,uploadListIns = upload.render({
            elem: '#prodBtn'
            ,url: '/upload/uploadFiles'
            ,accept: 'images'
            ,multiple: true
            ,auto: false
            ,bindAction: '#prodAction'
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列

                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    prodView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var tr = prodView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(0).html(res.data.src);
                    tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(2).html(''); //清空操作
                    $('#prodList').append('<input  name="prodrt" value="' + res.data.src + '" type="hidden">')
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = prodView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(2).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

        //详情图多文件列表示例
        var demoListView = $('#demoList')
            ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: '/upload/uploadFiles'
            ,accept: 'images'
            ,multiple: true
            ,auto: false
            ,bindAction: '#testListAction'
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列

                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(0).html(res.data.src);
                    tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(2).html(''); //清空操作
                    $('#demoList').append('<input  name="detailrt" value="' + res.data.src + '" type="hidden">')
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

    });




</script>
</body>
</html>