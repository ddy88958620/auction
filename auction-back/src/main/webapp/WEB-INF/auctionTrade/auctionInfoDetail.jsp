<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel='icon' href="<%=common%>/admin-favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" href="<%=common%>layui/css/layui.css" media="all">
    <script src="<%=common%>/layui/layui.js" charset="utf-8"></script>
    <script src="<%=common%>/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script src="<%=common%>/js/util.js" charset="utf-8"></script>
</head>
<form class="layui-form  layui-form-pane1 pzjzsj">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>基本信息</legend>
    </fieldset>
    <input type="hidden" name="id" id="id" class="layui-input" value="${auctionInfo.id}"/>
    <input type="hidden" name="productId" id="productId" class="layui-input"
           value="${auctionInfo.productId}"/>
    <input type="hidden" name="ruleId" id="ruleId" class="layui-input" value="${auctionInfo.ruleId}"/>
    <input type="hidden" name="classifyId" id="classifyId" class="layui-input"
           value="${auctionInfo.classifyId}"/>
    <div class="layui-form-item">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">期数ID:</label>
                <div class="layui-input-block">
                    <input type="text" name="id" value="${auctionInfo.id}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">拍卖期数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="auctionNo" value="${auctionInfo.auctionNo}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">分类名称:</label>
                <div class="layui-input-inline">
                    <input type="text" name="winUserId" value="${classifyList.name}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">拍品围观人数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="pageView" value="${auctionInfo.pageView}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">拍品收藏人数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="collectCount" value="${auctionInfo.collectCount}" autocomplete="off"
                           class="layui-input"/>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">赠币出价次数:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="freeBidCount" value="${auctionInfo.freeBidCount}" autocomplete="off"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">拍品总出价次数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="totalBidCount" value="${auctionInfo.totalBidCount}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">拍品有效出价次数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="validBidCount" value="${auctionInfo.validBidCount}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">机器人出价次数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="robotBidCount" value="${auctionInfo.robotBidCount}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">最终成交人ID:</label>
                <div class="layui-input-inline">
                    <input type="text" name="winUserId" value="${auctionInfo.winUserId}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">最终成交人:</label>
                <div class="layui-input-inline">
                    <input type="text" name="winUserDesc" value="${auctionInfo.winUserDesc}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">成交金额:</label>
                <div class="layui-input-inline">
                    <input type="text" name="finalPrice" value="${auctionInfo.finalPrice}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">拍品出价人数:</label>
                <div class="layui-input-inline">
                    <input type="text" name="personCount" value="${auctionInfo.personCount}" autocomplete="off"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">拍卖开始时间:</label>
                <div class="layui-input-inline">
                    <input type="text" name="startTime" value="<fmt:formatDate value="${auctionInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                            autocomplete="off" class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">拍卖结束时间:</label>
                <div class="layui-input-inline">
                    <input type="text" name="endTime" value="<fmt:formatDate value="${auctionInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           autocomplete="off"  class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">预览图：</label>
            <div class="layui-input-inline">
                <div class="layui-upload">
                    <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                        <div class="layui-upload-list" id="demo1">
                            <img src="${imgUrl}${auctionInfo.previewPic}" class="layui-upload-img"
                                 style="width: 50px;height: 100px;">
                        </div>
                        <input type="hidden" id="previewPic" name="previewPic"/>
                        <p id="demoText"></p>
                    </blockquote>
                </div>
            </div>
                <label class="layui-form-label">拍品状态:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label">
                        <input type="text" name="status" id="status" value="${auctionInfo.status}"
                               autocomplete="off"  class="layui-input"/>
                    </label>
            </div>

        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>竞拍规则信息</legend>
        </fieldset>
        <div class="layui-form">
            <table class="layui-table">
                <colgroup>
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>竞拍规则ID</th>
                    <th>竞拍规则名称</th>
                    <th>差价购买</th>
                    <th>每次可加价金额</th>
                    <th>退币比例</th>
                    <th>起拍价</th>
                    <th>倒计时</th>
                    <th>机器人是否必中</th>
                    <th>保留价</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${auctionInfo.ruleId}</td>
                    <td>${auctionInfo.ruleName}</td>
                    <td>
                        <c:if test="${auctionInfo.buyFlag != null}">
                            <c:if test="${auctionInfo.buyFlag =='1'}">
                                可以差价购买
                            </c:if>
                            <c:if test="${auctionInfo.buyFlag =='2'}">
                                不可以差价购买
                            </c:if>
                        </c:if>
                    </td>
                    <td>${auctionRule.premiumAmount}</td>
                    <td>${auctionRule.refundMoneyProportion}</td>
                    <td>${auctionRule.openingBid}</td>
                    <td>${auctionRule.countdown}</td>
                    <td>${auctionRule.robotTakenIn}</td>
                    <td>${auctionInfo.floorPrice}</td>
                    <td>
                        <fmt:formatDate value="${auctionInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>商品信息</legend>
        </fieldset>
        <div class="layui-form">
            <table class="layui-table">
                <colgroup>
                    <col width="200">
                    <col width="200">
                    <col width="200">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>商品ID</th>
                    <th>商品价格</th>
                    <th>创建时间</th>
                    <th>商品名称</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${auctionInfo.productId}</td>
                    <td>${auctionInfo.productPrice}</td>
                    <td>
                        <fmt:formatDate value="${auctionInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>${auctionInfo.productName}</td>
                </tr>
                </tbody>
            </table>
        </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
    </div>
</form>


<script>
    if ("${message}") {
        parent.layer.closeAll('iframe');
        parent.layer.msg('${message}', function () {
        });
    }

    if ("${auctionInfo.status != null}"){
        var status ='${auctionInfo.status}';
        if(status == 1){
            $("#status").val("正在拍");
        }else if(status == 2){
            $("#status").val("已完结");
        }else if(status == 3){
            $("#status").val("未完成");
        }
    }
</script>
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        //图片预览
        var uploadInst = upload.render({
            url: '${path}/upload/uploadFiles'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）

                });
            }
//            , done: function (res) {
//                //如果上传失败
//                if (res.code == 0) {
//                    $('input[name="previewPic').attr('value', res.data.src); //图片链接（base64）
//                } else {
//                    return layer.msg('上传失败');
//                }
//                //上传成功
//            }
//            , error: function () {
//                //演示失败状态，并实现重传
//                var demoText = $('#demoText');
//                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
//                demoText.find('.demo-reload').on('click', function () {
//                    uploadInst.upload();
//                });
//            }
        });
    });

</script>
</body>
</html>