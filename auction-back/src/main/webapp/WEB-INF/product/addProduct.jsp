<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/22 0022
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=common%>layui/css2/font_eolqem241z66flxr.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/main.css" media="all"/>
    <link rel="stylesheet" href="<%=common%>layui/css2/global.css" media="all"/>
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<body>

<form class="layui-form  layui-form-pane1 pzjzsj" method="POST">

    <div class="layui-form-item">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-block" style="width: 30%">
            <input type="text" name="productName" lay-verify="required" placeholder="请输入商品名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-block" style="width: 30%">
            <input type="text" name="productTitle" required lay-verify="required" placeholder="请输入商品标题"
                   autocomplete="off"
                   class="layui-input">
        </div>
        <%--<div class="layui-form-mid layui-word-aux">辅助文字</div>--%>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">渠道</label>
        <div class="layui-input-block" style="width: 30%">
            <select name="merchantId" lay-verify="required">
                <c:forEach var="merchant" items="${merchants}">
                    <option value="${merchant.id}">
                            ${merchant.merchantName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品库存</label>
        <div class="layui-input-inline">
            <input type="text" name="productNum" lay-verify="integer" placeholder="请输入库存" autocomplete="off"
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
                            <th>大小</th>
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
                            <th>大小</th>
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
                            <th>大小</th>
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
            <input type="text" name="productAmount" lay-verify="integer" placeholder="请输入渠道价格" autocomplete="off"
                   class="layui-input">
        </div>

        <label class="layui-form-label">市场价格</label>
        <div class="layui-input-inline">
            <input type="text" name="salesAmount" lay-verify="integer" placeholder="请输入销售价格" autocomplete="off"
                   class="layui-input">
        </div>

        <label class="layui-form-label">商品运费</label>
        <div class="layui-input-inline">
            <input type="text" name="freight" id="freight" lay-verify="number"
                   placeholder="请输入商品运费" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <%--<div class="layui-form-item layui-form-text">--%>
        <%--<label class="layui-form-label">拍品参数</label>--%>
        <%--<div class="layui-input-block" style="width: 30%">--%>
            <%--<textarea name="skuInfo" placeholder="请输入内容" class="layui-textarea"></textarea>--%>
        <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">竞拍规则</label>
        <div class="layui-input-block" style="width: 30%">
            <textarea name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="addProduct">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
        </div>
    </div>
    <input type="hidden" id="picUrl" name="picUrl"/>
</form>

<script>
    $(document).ready(function(){
        $("button[lay-filter='reset']").click(function () {
            $('#prodList').empty();
            $('#previewList').empty();
            $('#demoList').empty();
            $('#picUrl').val('');
        })

    });

    layui.use('form', function () {
        var form = layui.form;
        var layer = layui.layer;

        //自定义验证规则
        form.verify({
            integer: function (value) {
                var integer = new RegExp("^\\d+$");
                if (!integer.test(value)) {
                    return "只能是整数!";
                }
            }
        });

        form.on('submit(addProduct)', function (data) {
            var previewLen = $('input[name="preview"]');
            var prodLen = $('input[name="prod"]');
            var detailLen = $('input[name="detail"]');

            if (!previewLen || previewLen.length == 0) {
                layer.msg("请上传预览图");
                return false;
            }

            if (!prodLen || prodLen.length == 0) {
                layer.msg("请上传商品图");
                return false;
            }

            if (!detailLen || detailLen.length == 0) {
                layer.msg("请上传详情图");
                return false;
            }

            var picss=[];
            var detailList = {};
            detailList = getValue(picss,'preview',detailList,'2');
            detailList= getValue(picss,'prod',detailList,'1');
            detailList = getValue(picss,'detail',detailList,'0');
            var newData =  $("form").serialize()+'&'+$.param(detailList);
            $.post("saveProduct",newData,
                function (data) {
                    layer.msg(data.msg);
                    if (data.code == '200') {
                        $("button[lay-filter='reset']").trigger('click');
                    }
                }, "json");
            return false;

        });
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
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
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
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    $('#previewList').append('<input  name="preview" value="' + res.data.src + '" type="hidden">')
                    $('input[name="picUrl').attr('value', res.data.src);
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = previewListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
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
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
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
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    $('#prodList').append('<input  name="prod" value="' + res.data.src + '" type="hidden">')
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = prodView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
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
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
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
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    $('#demoList').append('<input  name="detail" value="' + res.data.src + '" type="hidden">')
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

    function getValue(arr,inputName,detailList,picType){
        var length = arr.length;
        var newArr = [];
        var value = $("input[name="+inputName+"]");

        for(var x=0; x<value.length;x++){
            newArr.push($(value[x]).val());
            arr.push($(value[x]).val());
        }

//        var detailList = {};
        //商品图
        for (var i = 0; i < newArr.length; i++) {
            var picIndex = parseInt(length)+i;
            detailList["pics[" + picIndex + "].picUrl"] =  newArr[i];
            detailList["pics[" + picIndex + "].picType"] =  picType;
        }

        return detailList;
    }

</script>
</body>
</html>
