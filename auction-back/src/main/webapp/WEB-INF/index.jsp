<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<%
    String path = request.getContextPath() + "";
    String common = path + "/resources/";
%>
<head>
    <c:set var="path" value="<%=path%>"/>
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
<body class="main_body">

<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <div class="admin-login-box">
                <a class="logo" style="left: 0;" href="javascript:hide();"> <span
                        style="font-size: 22px;"> </span>
                </a>
                <div class="admin-side-toggle">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </div>
                <div class="admin-side-full">
                    <i class="fa fa-life-bouy" aria-hidden="true"></i>
                </div>
            </div>
            <!-- 头部区域（可配合layui已有的水平导航） -->
            <ul class="layui-nav layui-layout-left admin-header-item">
                <c:forEach items="${menuModuleList}" var="item" varStatus="count">
                    <li class="layui-nav-item"><a id="flag${count.count }" href="javascript:;"
                                                  onclick="showSecondPage('${item.moduleUrl }',${item.id});"><i
                            class="layui-icon">${item.iconShow }</i>${item.moduleName}</a></li>
                </c:forEach>
            </ul>
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item"><a href="javascript:;"> <img src="http://t.cn/RCzsdCq"
                                                                        class="layui-nav-img"> ${loginUser.userAccount}
                </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a    onclick="showUpdateUserPwdWindow(this)">修改密码</a>
                        </dd>
                        <c:if test="${isSuper }">
                            <dd>
                                <a href="javascript:updateCache();">刷新缓存</a>
                            </dd>
                        </c:if>
                        <dd>
                            <a href="javascript:toOut();">退出</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black" id="admin-side">
        <div class="navBar layui-side-scroll"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form" id="admin-body">
        <div class="layui-tab marg0" lay-filter="bodyTab">
            <ul class="layui-tab-title top_tab">
                <li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>首页</cite></li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="http://www.baidu.com"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <div class="layui-footer" id="admin-footer" style="text-align: center;">
        <!-- 底部固定区域 -->
        Copyright &copy; 2017 技术支持：${website.site_signature }<a href="#" target="_blank"></a>
    </div>
</div>
<script type="text/javascript" src="<%=common%>/system/leftNav.js"></script>
<script type="text/javascript" src="<%=common%>/system/index.js"></script>


<script>
    function hide() {
        var sideWidth = $('#admin-side').width();
        if (sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            }); //admin-footer
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    }
    var lastParentId = null;
    var moduleId = "${moduleId}";
    var updateUserPwdUrl="${updateUserPwdUrl}";
    var path = "${path}";
    var showSecondPage;
    var toOut;
    var updateCache;
    var showUpdateUserPwdWindow;
    var rpd = "${loginUser.defaultPwd}";
    layui.use(['element', 'layer'], function () {
        var element = layui.element;
        showSecondPage = function (url, parendId) {
            if (lastParentId != parendId) {
                lastParentId = parendId;
                $.ajax({
                    type: "post",
                    url: path + url,
                    data: {'myId': parendId},
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            //显示左侧菜单
                            $(".navBar").html(navBar(path,result.data)).height($(window).height() - 230);
                            element.init();  //初始化页面元素
                            $(window).resize(function () {
                                $(".navBar").height($(window).height() - 230);
                            });
                            bindTabClk();
                        } else {
                            layer.msg(result.msg);
                        }
                    }
                });
            }

        }
        <c:if test="${isSuper }">
        updateCache = function () {
            $.ajax({
                type: "post",
                url: path + "/updateCache",
                data: {},
                dataType: "json",
                error: function () {
                    layer.msg('服务器开小差了,请稍后重试');
                },
                success: function (result) {
                    layer.msg("刷新成功");
                }
            });
        }
        </c:if>
        $("#flag1").trigger('click');
        toOut = function () {
            //询问框
            layer.confirm('您确定要离开吗？', {
                icon: 5,
                anim: 6,
                title: '警告',
                btn: ['乘风归去', '点错了']
            }, function () {
                window.location.href = "logout";
            }, function () {
                layer.msg('知错能改善莫大焉', {
                    time: 1000
                });
            });
        };


    });


</script>
<script type="text/javascript" src="<%=common%>/system/user/updateUserPwd.js"></script>


</body>
</html>