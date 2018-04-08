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
    <c:set var="path" value="<%=path%>"/>
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

<form id="form" class="layui-form  layui-form-pane1 pzjzsj" method="POST">
    <div class="layui-form-item" style="margin-top: 10px;">
        <div class="layui-inline">
            <div class="layui-form-item">
                <label class="layui-form-label">方案1:</label>
                <div class="layui-input-inline">
                    <input type="button" class="layui-btn" id="checkPlan1Prize" value="验证奖品"
                           onclick="checkPrize('plan1PrizeNo','plan1PrizeName')"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称1：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo1" name="plan1PrizeNo1" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[0].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName1')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName1" value="${plan1List[0].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称2：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo2" name="plan1PrizeNo2" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[1].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName2')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName2" value="${plan1List[1].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称3：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo3" name="plan1PrizeNo3" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[2].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName3')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName3" value="${plan1List[2].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称4：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo4" name="plan1PrizeNo4" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[3].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName4')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName4" value="${plan1List[3].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称5：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo5" name="plan1PrizeNo5" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[4].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName5')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName5" value="${plan1List[4].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称6：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo6" name="plan1PrizeNo6" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[5].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName6')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName6" value="${plan1List[5].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称7：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo7" name="plan1PrizeNo7" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[6].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName7')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName7" value="${plan1List[6].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称8：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeNo8" name="plan1PrizeNo8" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan1List[7].prizeNo}"
                           lay-verify="required" onchange="clearName('plan1PrizeName8')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan1PrizeName8" value="${plan1List[7].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
        </div>

        <div class="layui-inline" style="margin-top: 10px;">
            <div class="layui-form-item">
                <label class="layui-form-label">方案2:</label>
                <div class="layui-input-inline">
                    <input type="button" class="layui-btn" id="checkPlan2Prize" value="验证奖品"
                           onclick="checkPrize('plan2PrizeNo','plan2PrizeName')"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称1：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo1" name="plan2PrizeNo1" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[0].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName1')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName1" value="${plan2List[0].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称2：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo2" name="plan2PrizeNo2" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[1].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName2')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName2" value="${plan2List[1].prizeName}" class="layui-input"
                           readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称3：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo3" name="plan2PrizeNo3" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[2].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName3')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName3" value="${plan2List[2].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称4：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo4" name="plan2PrizeNo4" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[3].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName4')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName4" value="${plan2List[3].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称5：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo5" name="plan2PrizeNo5" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[4].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName5')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName5" value="${plan2List[4].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称6：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo6" name="plan2PrizeNo6" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[5].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName6')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName6" value="${plan2List[5].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称7：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo7" name="plan2PrizeNo7" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[6].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName7')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName7" value="${plan2List[6].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">奖品名称8：</label>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeNo8" name="plan2PrizeNo8" placeholder="请输入奖品编号"
                           autocomplete="off" class="layui-input" value="${plan2List[7].prizeNo}"
                           lay-verify="required" onchange="clearName('plan2PrizeName8')">
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="plan2PrizeName8" value="${plan2List[7].prizeName}"
                           class="layui-input" readonly lay-verify="required"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">方案状态：</label>
            <div class="layui-input-inline">
                <input type="radio" name="validPlan" value="1" title="方案1" <c:if test="${validPlan == '1'}">checked</c:if>>
                <input type="radio" name="validPlan" value="2" title="方案2" <c:if test="${validPlan == '2'}">checked</c:if>>
            </div>
        </div>
    </div>
    <button style="display: none;" lay-filter="submitBtn" lay-submit="">
    </button>
</form>

<script>
    function clearName(id) {
        $("#"+id).val("");
    }

    function resetForm() {
        document.getElementById("form").reset();
    }
    function submitForm() {
        $("button[lay-filter='submitBtn']").trigger('click');
    }

    layui.use(['form','layer'], function () {
        var form = layui.form;
        var layer = layui.layer;
        form.on('submit(submitBtn)', function (data) {
            $.post("online", data.field,
                function (data) {
                    if (data.code == '0') {
                        parent.layer.msg("修改成功！");
                        parent.layer.closeAll('iframe');
                        parent.table.reload('search');
                    } else {
                        layer.error("修改失败！");
                    }
                }, "json");
            return false;
        });
    });

    function checkPrize(noPre,namePre) {
        var prizeNo1 = $("#"+noPre+'1').val();
        var prizeNo2 = $("#"+noPre+'2').val();
        var prizeNo3 = $("#"+noPre+'3').val();
        var prizeNo4 = $("#"+noPre+'4').val();
        var prizeNo5 = $("#"+noPre+'5').val();
        var prizeNo6 = $("#"+noPre+'6').val();
        var prizeNo7 = $("#"+noPre+'7').val();
        var prizeNo8 = $("#"+noPre+'8').val();

        $.ajax({
            url: '${path}/lotteryPrize/checkPrize',
            type: 'POST',
            data: {prizeNo1:prizeNo1,prizeNo2:prizeNo2,prizeNo3:prizeNo3,prizeNo4:prizeNo4,
                prizeNo5:prizeNo5,prizeNo6:prizeNo6,prizeNo7:prizeNo7,prizeNo8:prizeNo8},
            dataType:"json",
            success: function (data) {
                if (data.code=='0') {
                    for (var i=1; i<9; i++) {
                        var prizeName = data.prizeNameList[i-1];
                        var prizeNo = data.prizeNoList[i-1];
                        $("#"+namePre+i).val(prizeName);
                        $("#"+noPre+i).val(prizeNo);
                    }
                } else {
                    layer.msg(data.message);
                    for (var i=1; i<9; i++) {
                        var prizeName = data.prizeNameList[i-1];
                        var prizeNo = data.prizeNoList[i-1];
                        $("#"+namePre+i).val(prizeName);
                        $("#"+noPre+i).val(prizeNo);
                    }
                }
            }
        });
    }
</script>
</body>
</html>
