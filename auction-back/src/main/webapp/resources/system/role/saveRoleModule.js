
function showPage(btns) {
    addBtns(btns);
    saveRoleTreeLayer = layer.open(htmlDynamicBtn);
}
showRoleModuleTree = function(obj) {
    var url = $(obj).attr("url");
    var title = $(obj).attr("title");
    var pId = $(obj).attr("btn");
    // 去除打开此页面的按钮的焦点
    document.activeElement.blur();
    htmlDynamicBtn.title = title;
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    if (len == 0) {
        layer.msg('未选中要操作的行', function() {
        });
    } else if (len > 1) {
        layer.msg('一次仅能操作一行', function() {
        });
    } else {
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        htmlDynamicBtn['content'] = path + url + "roleId=" + checkStatus.data[0].id;
        findBtns('showPage', pId);
    }
};
/**
 * 获得用户列表,通过showWindow方法回调本方法,前者是弹出窗口后者是指定弹出的内容
 *
 * @param data
 */
showWindow2 = function(data) {
    var content = "";
    for (var i = 0; i < data.length; i++) {
        if (i % 4 == 0) {
            if (i != 0) {
                content += '</div></div>';
            }
            content += '<div class="layui-form-item"> <div class="layui-input-block">';
        }

        content += '<label class="layui-form-label layui-icon">&#xe612;' + data[i] + '</label>';
        if (i == (data.length - 1)) {
            content += '</div></div>';
        }
    }
    htmlWindow['content'] = content;
    layer.open(htmlWindow);
};
