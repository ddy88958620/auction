<%@ page import="com.trump.auction.back.util.file.PropertiesUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath() + "";
    String common = path + "/resources/";
    String photoPathSuffix = PropertiesUtils.get("aliyun.oss.domain");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
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
<form name="addBanner" id="addBanner" class="layui-form  layui-form-pane1" method="POST">
    <h1 style="margin-left: 650px;">Banner信息
    </h1>
    <div class="layui-form-item">
        <div style="margin-left: 300px;" class="layui-inline">
            <h2 style="margin: 10px;">Banner 1:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType1" autocomplete="off">
                        <c:forEach var="item" items="${bannerJumpTypeList}">
                            <option value="${item.key}" <c:if test="${banner1.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType1" autocomplete="off">
                        <c:forEach var="item" items="${bannerDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${banner1.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin1" autocomplete="off">
                        <c:forEach var="item" items="${bannerHasLoginList}">
                            <option value="${item.key}" <c:if test="${banner1.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params1" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${banner1.jumpType eq 1}">
                           value="${params1}"
                    </c:when>
                    <c:otherwise>
                           value="${banner1.url}"
                    </c:otherwise>
                    </c:choose>
                    >
                </div>
                <span style="color: red;">示例：APP(参数名1:值1,参数名2:值2)<br/>H5(访问的URL?参数名1=值1&参数名2=值2)</span>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">ios标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyIos1" placeholder="请输入ios标识" autocomplete="off"
                           class="layui-input" value="${banner1.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" autocomplete="off">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid1" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${banner1.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upBannerPic" onclick="upClick(1);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${banner1.img}"
                                 style="width: 100px; height: 100px;" id="bannerl1" name="bannerl1"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">Banner 2:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType2" autocomplete="off">
                        <c:forEach var="item" items="${bannerJumpTypeList}">
                            <option value="${item.key}" <c:if test="${banner2.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType2" autocomplete="off">
                        <c:forEach var="item" items="${bannerDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${banner2.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin2" autocomplete="off">
                        <c:forEach var="item" items="${bannerHasLoginList}">
                            <option value="${item.key}" <c:if test="${banner2.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params2" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${banner2.jumpType eq 1}">
                           value="${params2}"
                    </c:when>
                    <c:otherwise>
                           value="${banner2.url}"
                    </c:otherwise>
                    </c:choose>
                    >
                </div>
                <span style="color: red;">示例：APP(参数名1:值1,参数名2:值2)<br/>H5(访问的URL?参数名1=值1&参数名2=值2)</span>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">ios标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyIos2" placeholder="请输入ios标识" autocomplete="off"
                           class="layui-input" value="${banner2.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid2" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${banner2.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upBannerPic" onclick="upClick(2);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${banner2.img}"
                                 style="width: 100px; height: 100px;" id="bannerl2" name="bannerl2"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">Banner 3:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType3" autocomplete="off">
                        <c:forEach var="item" items="${bannerJumpTypeList}">
                            <option value="${item.key}" <c:if test="${banner3.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType3" autocomplete="off">
                        <c:forEach var="item" items="${bannerDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${banner3.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin3" autocomplete="off">
                        <c:forEach var="item" items="${bannerHasLoginList}">
                            <option value="${item.key}" <c:if test="${banner3.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params3" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${banner3.jumpType eq 1}">
                           value="${params3}"
                    </c:when>
                    <c:otherwise>
                           value="${banner3.url}"
                    </c:otherwise>
                    </c:choose>
                    >
                </div>
                <span style="color: red;">示例：APP(参数名1:值1,参数名2:值2)<br/>H5(访问的URL?参数名1=值1&参数名2=值2)</span>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">ios标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyIos3" placeholder="请输入ios标识" autocomplete="off"
                           class="layui-input" value="${banner3.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid3" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${banner3.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upBannerPic" onclick="upClick(3);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${banner3.img}"
                                 style="width: 100px; height: 100px;" id="bannerl3" name="bannerl3"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div style="margin-left: 500px;" class="layui-inline">
            <h2 style="margin: 10px;">Banner 4:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType4" autocomplete="off">
                        <c:forEach var="item" items="${bannerJumpTypeList}">
                            <option value="${item.key}" <c:if test="${banner4.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType4" autocomplete="off">
                        <c:forEach var="item" items="${bannerDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${banner4.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin4" autocomplete="off">
                        <c:forEach var="item" items="${bannerHasLoginList}">
                            <option value="${item.key}" <c:if test="${banner4.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params4" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${banner4.jumpType eq 1}">
                           value="${params4}"
                    </c:when>
                    <c:otherwise>
                           value="${banner4.url}"
                    </c:otherwise>
                    </c:choose>
                    >
                </div>
                <span style="color: red;">示例：APP(参数名1:值1,参数名2:值2)<br/>H5(访问的URL?参数名1=值1&参数名2=值2)</span>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">ios标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyIos4" placeholder="请输入ios标识" autocomplete="off"
                           class="layui-input" value="${banner4.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid4" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${banner4.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upBannerPic" onclick="upClick(4);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${banner4.img}"
                                 style="width: 100px; height: 100px;" id="bannerl4" name="bannerl4"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">Banner 5:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType5" autocomplete="off">
                        <c:forEach var="item" items="${bannerJumpTypeList}">
                            <option value="${item.key}" <c:if test="${banner5.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType5" autocomplete="off">
                        <c:forEach var="item" items="${bannerDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${banner5.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin5" autocomplete="off">
                        <c:forEach var="item" items="${bannerHasLoginList}">
                            <option value="${item.key}" <c:if test="${banner5.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params5" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${banner5.jumpType eq 1}">
                           value="${params5}"
                    </c:when>
                    <c:otherwise>
                           value="${banner5.url}"
                    </c:otherwise>
                    </c:choose>
                    >
                </div>
                <span style="color: red;">示例：APP(参数名1:值1,参数名2:值2)<br/>H5(访问的URL?参数名1=值1&参数名2=值2)</span>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">ios标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyIos5" placeholder="请输入ios标识" autocomplete="off"
                           class="layui-input" value="${banner5.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid5" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${banner5.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upBannerPic" onclick="upClick(5);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${banner5.img}"
                                 style="width: 100px; height: 100px;" id="bannerl5" name="bannerl5"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="margin-left: 660px;">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="addBanner">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
        </div>
    </div>

    <input type="hidden" name="img1" id="banner1" value="${banner1.img}"/>
    <input type="hidden" name="img2" id="banner2" value="${banner2.img}"/>
    <input type="hidden" name="img3" id="banner3" value="${banner3.img}"/>
    <input type="hidden" name="img4" id="banner4" value="${banner4.img}"/>
    <input type="hidden" name="img5" id="banner5" value="${banner5.img}"/>
</form>
</body>
</html>
<script typ="text/javascript">

    var sl;
    var img;
    var err;

    function upClick(selected) {
        if (selected == 1) {
            sl = $("#bannerl1");
            img = $("#banner1");
            err = $("#banner1Err");
        } else if (selected == 2) {
            sl = $("#bannerl2");
            img = $("#banner2");
            err = $("#banner2Err");
        } else if (selected == 3) {
            sl = $("#bannerl3");
            img = $("#banner3");
            err = $("#banner3Err");
        } else if (selected == 4) {
            sl = $("#bannerl4");
            img = $("#banner4");
            err = $("#banner4Err");
        } else if (selected == 5) {
            sl = $("#bannerl5");
            img = $("#banner5");
            err = $("#banner5Err");
        }
    }

    $(document).ready(function(){
        $("button[lay-filter='reset']").click(function () {
            $("#bannerl1").attr("src","<%=photoPathSuffix%>/${banner1.img}");
            $("#banner1").val("${banner1.img}");
            $("#bannerl2").attr("src","<%=photoPathSuffix%>/${banner2.img}");
            $("#banner2").val("${banner2.img}");
            $("#bannerl3").attr("src","<%=photoPathSuffix%>/${banner3.img}");
            $("#banner3").val("${banner3.img}");
            $("#bannerl4").attr("src","<%=photoPathSuffix%>/${banner4.img}");
            $("#banner4").val("${banner4.img}");
            $("#bannerl5").attr("src","<%=photoPathSuffix%>/${banner5.img}");
            $("#banner5").val("${banner5.img}");
        })
    });

    layui.use(['form','upload'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        form.on('submit(addBanner)', function (data) {

            $.post("addBanner", data.field,
                function (data) {
                    if (data.code == '0') {
                        layer.msg("操作成功！");
                        setTimeout(function () {
                            location.reload();
                        },1000);
                    } else {
                        layer.msg(data.msg);
                    }
                }, "json");
            return false;
        });

        //普通图片上传
        upload.render({
            elem: '.upBannerPic'
            , url: '${path}/upload/uploadFiles'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    sl.attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code == 0) {
                    img.attr('value',res.data.src); //图片链接（base64）
                } else {
                    return layer.msg('上传失败');
                }
                //上传成功
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = err;
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    });
</script>


