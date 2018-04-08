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
<body>
<form class="layui-form "  class="layui-form  layui-form-pane1 pzjzsj"  style="margin-left: 500px;" action="" id="form">
    <fieldset  class="layui-elem-field layui-field-title"  style="margin-top: 20px;width: 1000px;">
        <legend style="margin-bottom: 20px;"><strong>新增晒单评级规则</strong></legend>
    <div class="layui-form-item" style="margin-top: 20px;">
        <label class="layui-form-label"  style="width: 120px">评论级别:</label>
        <div class="layui-input-inline"  style="width: 100px;">
        <select  id="appraisesLevel" class="layui-input" name="appraisesLevel" lay-verify="required" lay-filter="appraisesLevel"  autocomplete="off" onselect="params()">
                  <c:forEach var="item" items="${appraisesLevelList}">
                      <option id="${item.appraisesLevel}" value="${item.appraisesLevel}" <c:if test="${item.appraisesLevel eq level}">selected="selected"</c:if>>${item.appraisesLevel}</option>
                  </c:forEach>
        </select>
        </div>
    </div>

     <div class="layui-form-item" >
            <label class="layui-form-label"  style="width: 120px">评论字数范围(R):</label>
            <div class="layui-input-inline"  style="width: 100px;">
                <input name="minAppraisesWords"  id="minAppraisesWords" placeholder="最小值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text" readonly="readonly">
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline" style="width: 100px;">
                <input name="maxAppraisesWords" id="maxAppraisesWords" placeholder="最大值" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text" readonly="readonly">
            </div>
            <div class="layui-form-mid">
             <%--<span>提示:目前仅支持范围:0-200</span>--%>
            </div>
     </div>

     <div class="layui-form-item" >
            <label class="layui-form-label"  style="width: 120px">图片数量范围:</label>
            <div class="layui-input-inline"  style="width: 100px;" >
                <input name="minPicNum" placeholder="最小值" id="minPicNum" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text" readonly="readonly">
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline" style="width: 100px;">
                <input name="maxPicNum" placeholder="最大值"  id="maxPicNum" autocomplete="off" lay-verify="required|integer" class="layui-input" type="text" readonly="readonly">
            </div>
     </div>

     <div class="layui-form-item">
        <label class="layui-form-label" style="width: 120px">基础奖励(积分):</label>
        <div class="layui-input-inline">
            <input type="text" name="baseRewords" id="baseRewords" lay-verify="required|integer" placeholder="请输入基础奖励" autocomplete="off" class="layui-input" readonly="readonly"/>
        </div>
     </div>

     <div class="layui-form-item">
        <label class="layui-form-label" style="width: 120px">出镜奖励(赠币):</label>
        <div class="layui-input-inline">
            <input type="text" name="showRewords" id="showRewords" lay-verify="integer|show" placeholder="请输入出镜奖励" autocomplete="off" class="layui-input" value="0" />
        </div>
     </div>

      <div class="layui-form-item" style="margin-top: 40px;">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn"  lay-submit lay-filter="check1">提交</button>
                <button type="text"  class="layui-btn"   onclick="resetForm()">重置</button>
                <button type="text"  class="layui-btn"  onclick="closeForm()">返回</button>
            </div>
      </div>
      <button style="display: none;" lay-filter="submitBtn" lay-submit=""/>
    </fieldset>
</form>

<script>
    function resetForm() {
        document.getElementById("showRewords").reset();
    }
    function closeForm() {
        parent.layer.closeAll('iframe');
        return false;
    }
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function() {
        });
    }

    layui.use('form', function() {
        var form = layui.form;
        form.verify({
            appraisesLevel:function (value) {
                var reg=/^[a-zA-Z]*$/;
                if(!reg.test(value)) {
                    return "只能输入字母！";
                }
                $("#appraisesLevel")[0].value=value.toUpperCase();
            },
            integer: function (value) {
                if(value != null && value != '' ) {
                    var integer = new RegExp("^([1-9]\\d*|[0]{1,1})$");
                    if (!integer.test(value)) {
                        $("#showRewords").val("");
                        return "只能是整数!";
                    }
                }
            },
            show:function(value){
                var show=parseInt($("#showRewords").val());
                if (show>5){
                    $("#showRewords").val("");
                    return "出镜奖励积分不大于5，请重新输入";
                }
            }
        });
        form.on('submit(check1)', function(data) {
            $.ajax({
                type : "post",
                url : "${path }${url}",
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
            return false;
        });

        form.on('select(appraisesLevel)', function(data) {
            var appraisesLevel =   $("#appraisesLevel").val();
            $.ajax({
                type : "post",
                url : "/appraisesRules/getMsg",
                data:{ "appraisesLevel":appraisesLevel},
                dataType : "json",
                beforeSend : function() {
                    layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });
                },
                success : function(result) {
                    $("#baseRewords").val(result.baseRewords);
                    //$("#showRewords").val(result.showRewords);
                    $("#minAppraisesWords").val(result.minAppraisesWords);
                    $("#maxAppraisesWords").val(result.maxAppraisesWords);
                    $("#minPicNum").val(result.minPicNum);
                    $("#maxPicNum").val(result.maxPicNum);
                }
            });
        });
        $(function(){
            var appraisesLevel =   $("#appraisesLevel").val();
            $.ajax({
                type : "post",
                url : "/appraisesRules/getMsg",
                data:{ "appraisesLevel":appraisesLevel},
                dataType : "json",
                beforeSend : function() {
                    layer.load(1, {
                        shade : [ 0.1, '#fff' ]
                    });
                },
                success : function(result) {
                    $("#baseRewords").val(result.baseRewords);
                    //$("#showRewords").val(result.showRewords);
                    $("#minAppraisesWords").val(result.minAppraisesWords);
                    $("#maxAppraisesWords").val(result.maxAppraisesWords);
                    $("#minPicNum").val(result.minPicNum);
                    $("#maxPicNum").val(result.maxPicNum);
                }
            });
        })
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
</body>
</html>