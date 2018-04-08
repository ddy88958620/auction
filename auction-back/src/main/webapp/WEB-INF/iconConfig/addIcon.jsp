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
<form name="addicon" id="addicon" class="layui-form  layui-form-pane1" method="POST">
    <h1 style="margin-left: 650px;">icon信息
    </h1>
    <div class="layui-form-item">
        <div style="margin-left: 300px;" class="layui-inline">
            <h2 style="margin: 10px;">Icon 1:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType1" autocomplete="off">
                        <c:forEach var="item" items="${iconJumpTypeList}">
                            <option value="${item.key}" <c:if test="${icon1.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType1" autocomplete="off">
                        <c:forEach var="item" items="${iconDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${icon1.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin1" autocomplete="off">
                        <c:forEach var="item" items="${iconHasLoginList}">
                            <option value="${item.key}" <c:if test="${icon1.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">按钮名称：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="name1" placeholder="请输入按钮名称" autocomplete="off"
                           class="layui-input" value="${icon1.name}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params1" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${icon1.jumpType eq 1}">
                           value="${params1}"
                    </c:when>
                    <c:otherwise>
                           value="${icon1.url}"
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
                           class="layui-input" value="${icon1.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" autocomplete="off">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid1" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${icon1.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upiconPic" onclick="upClick(1);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${icon1.img}"
                                 style="width: 100px; height: 100px;" id="iconl1" name="iconl1"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">Icon 2:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType2" autocomplete="off">
                        <c:forEach var="item" items="${iconJumpTypeList}">
                            <option value="${item.key}" <c:if test="${icon2.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType2" autocomplete="off">
                        <c:forEach var="item" items="${iconDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${icon2.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin2" autocomplete="off">
                        <c:forEach var="item" items="${iconHasLoginList}">
                            <option value="${item.key}" <c:if test="${icon2.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">按钮名称：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="name2" placeholder="请输入按钮名称" autocomplete="off"
                           class="layui-input" value="${icon2.name}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params2" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${icon2.jumpType eq 1}">
                           value="${params2}"
                    </c:when>
                    <c:otherwise>
                           value="${icon2.url}"
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
                           class="layui-input" value="${icon2.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid2" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${icon2.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upiconPic" onclick="upClick(2);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${icon2.img}"
                                 style="width: 100px; height: 100px;" id="iconl2" name="iconl2"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">Icon 3:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType3" autocomplete="off">
                        <c:forEach var="item" items="${iconJumpTypeList}">
                            <option value="${item.key}" <c:if test="${icon3.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType3" autocomplete="off">
                        <c:forEach var="item" items="${iconDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${icon3.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin3" autocomplete="off">
                        <c:forEach var="item" items="${iconHasLoginList}">
                            <option value="${item.key}" <c:if test="${icon3.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">按钮名称：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="name3" placeholder="请输入按钮名称" autocomplete="off"
                           class="layui-input" value="${icon3.name}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params3" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${icon3.jumpType eq 1}">
                           value="${params3}"
                    </c:when>
                    <c:otherwise>
                           value="${icon3.url}"
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
                           class="layui-input" value="${icon3.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid3" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${icon3.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upiconPic" onclick="upClick(3);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${icon3.img}"
                                 style="width: 100px; height: 100px;" id="iconl3" name="iconl3"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div style="margin-left: 500px;" class="layui-inline">
            <h2 style="margin: 10px;">Icon 4:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType4" autocomplete="off">
                        <c:forEach var="item" items="${iconJumpTypeList}">
                            <option value="${item.key}" <c:if test="${icon4.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType4" autocomplete="off">
                        <c:forEach var="item" items="${iconDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${icon4.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin4" autocomplete="off">
                        <c:forEach var="item" items="${iconHasLoginList}">
                            <option value="${item.key}" <c:if test="${icon4.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">按钮名称：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="name4" placeholder="请输入按钮名称" autocomplete="off"
                           class="layui-input" value="${icon4.name}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params4" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${icon4.jumpType eq 1}">
                           value="${params4}"
                    </c:when>
                    <c:otherwise>
                           value="${icon4.url}"
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
                           class="layui-input" value="${icon4.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid4" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${icon4.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upiconPic" onclick="upClick(4);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${icon4.img}"
                                 style="width: 100px; height: 100px;" id="iconl4" name="iconl4"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 20px;" class="layui-inline">
            <h2 style="margin: 10px;">icon 5:</h2>
            <div class="layui-form-item">
                <label class="layui-form-label">跳转方式：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="jumpType5" autocomplete="off">
                        <c:forEach var="item" items="${iconJumpTypeList}">
                            <option value="${item.key}" <c:if test="${icon5.jumpType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">显示状态：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="displayType5" autocomplete="off">
                        <c:forEach var="item" items="${iconDisplayStatusList}">
                            <option value="${item.key}" <c:if test="${icon5.displayType eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否需要登陆：</label>
                <div class="layui-inline" style="width: 100px;">
                    <select class="layui-input" name="hasLogin5" autocomplete="off">
                        <c:forEach var="item" items="${iconHasLoginList}">
                            <option value="${item.key}" <c:if test="${icon5.hasLogin eq item.key}">selected</c:if>>${item.value}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">按钮名称：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="name5" placeholder="请输入按钮名称" autocomplete="off"
                           class="layui-input" value="${icon5.name}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参数：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="params5" placeholder="请输入参数" autocomplete="off"
                           class="layui-input"
                    <c:choose>
                    <c:when test="${icon5.jumpType eq 1}">
                           value="${params5}"
                    </c:when>
                    <c:otherwise>
                           value="${icon5.url}"
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
                           class="layui-input" value="${icon5.identifyIos}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">安卓标识：</label>
                <div class="layui-input-block" style="width: 195px;">
                    <input type="text" name="identifyAndroid5" placeholder="请输入安卓标识" autocomplete="off"
                           class="layui-input" value="${icon5.identifyAndroid}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图片：</label>
                <div class="layui-input-block">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn upiconPic" onclick="upClick(5);">图片上传</button>
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 105px;">
                            预览：
                            <img class="layui-upload-img" src="<%=photoPathSuffix%>/${icon5.img}"
                                 style="width: 100px; height: 100px;" id="iconl5" name="iconl5"/>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="margin-left: 660px;">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="addIcon">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" lay-filter="reset">重置</button>
        </div>
    </div>

    <input type="hidden" name="img1" id="icon1" value="${icon1.img}"/>
    <input type="hidden" name="img2" id="icon2" value="${icon2.img}"/>
    <input type="hidden" name="img3" id="icon3" value="${icon3.img}"/>
    <input type="hidden" name="img4" id="icon4" value="${icon4.img}"/>
    <input type="hidden" name="img5" id="icon5" value="${icon5.img}"/>
</form>
</body>
</html>
<script typ="text/javascript">

    var sl;
    var img;
    var err;

    function upClick(selected) {
        if (selected == 1) {
            sl = $("#iconl1");
            img = $("#icon1");
            err = $("#icon1Err");
        } else if (selected == 2) {
            sl = $("#iconl2");
            img = $("#icon2");
            err = $("#icon2Err");
        } else if (selected == 3) {
            sl = $("#iconl3");
            img = $("#icon3");
            err = $("#icon3Err");
        } else if (selected == 4) {
            sl = $("#iconl4");
            img = $("#icon4");
            err = $("#icon4Err");
        } else if (selected == 5) {
            sl = $("#iconl5");
            img = $("#icon5");
            err = $("#icon5Err");
        }
    }

    $(document).ready(function(){
        $("button[lay-filter='reset']").click(function () {
            $("#iconl1").attr("src","<%=photoPathSuffix%>/${icon1.img}");
            $("#icon1").val("${icon1.img}");
            $("#iconl2").attr("src","<%=photoPathSuffix%>/${icon2.img}");
            $("#icon2").val("${icon2.img}");
            $("#iconl3").attr("src","<%=photoPathSuffix%>/${icon3.img}");
            $("#icon3").val("${icon3.img}");
            $("#iconl4").attr("src","<%=photoPathSuffix%>/${icon4.img}");
            $("#icon4").val("${icon4.img}");
            $("#iconl5").attr("src","<%=photoPathSuffix%>/${icon5.img}");
            $("#icon5").val("${icon5.img}");
        })
    });

    layui.use(['form','upload'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        form.on('submit(addIcon)', function (data) {

            $.post("addIcon", data.field,
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
            elem: '.upiconPic'
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


