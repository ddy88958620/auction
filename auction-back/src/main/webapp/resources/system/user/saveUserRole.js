var html2 = {
    type: 2,
    shade: 0.3,
    maxmin: true,
    area: ['100%', '100%'],
    anim: 1,
    btnAlign: 'r',
    btn: ['取消'],
    success: function (layero, index) {
    }
};
/**
 * 获得用户授权页面,通过showWindow方法回调本方法,前者是弹出窗口后者是指定弹出的内容
 *
 * @param data
 */
showWindow2 = function (data, id) {
    var content = "<form class=\"layui-form\" action=\"\" id=\"userRoleForm\" style=\"margin-top:20px;\"><input type='hidden' name='id' value=" + id + ">";
    for (var i = 0; i < data.length; i++) {
        if (i % 4 == 0) {
            if (i != 0) {
                content += '</div></div>';
            }
            content += '<div class="layui-form-item"> <div class="layui-input-block">';
        }
        var ck = "";
        if (data[i].checked) {
            ck = "checked";
        }
        content += '<input type="checkbox" name="roleId" tip="'+data[i].tip+'" value="' + data[i].id + '" title="' + data[i].name + '" ' + ck + '>';
        if (i == (data.length - 1)) {
            content += '</div></div>';
        }
    }
    content += "</form>";
    htmlWindow['content'] = content;
    saveRoleWindow = layer.open(htmlWindow);
    form.render('checkbox');
    $("div.layui-form-item").find("div.layui-form-checkbox").hover(function () {
        layer.tips($(this).prev().attr("tip"), $(this), {
            tips: [1, '#3595CC'],
            tipsMore: true,
            time: 3000
        });
    });
};
saveUserRole = function (obj) {
    var roleIds = new Array();
    $("#userRoleForm").find("div.layui-form-checked").each(function (index, obj) {
        roleIds.push($(obj).prev("input[name='roleId']").val());
    });
    $.ajax({
        type: "post",
        url: path + obj.moduleUrl,
        data: {
            roleIds: roleIds,
            id: $("#userRoleForm").find("input[name='id']").val()
        },
        dataType: "json",
        error: function () {
            layer.msg('服务器开小差了,请稍后重试');
        },
        success: function (result) {
            layer.msg(result.msg);
            if (result.code == "0") {
                layer.close(saveRoleWindow);
            }
        }
    });
}
