// 导出Excel
excelExport = function(obj) {
    document.activeElement.blur();
    var url = $(obj).attr("url");
    var urlHelp = $(obj).attr("urlHelp");
    if (urlHelp != undefined && urlHelp != null && urlHelp != '') {
        url += urlHelp;
    }
    var title = $(obj).attr("title");
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    if (len == 0) {
        layer.msg('未选中要操作的行', function () {
        });
        return;
    }
    var ids = new Array();
    for (var i = 0; i < len; i++) {
        ids.push(checkStatus.data[i].id);
    }
    var url = "/user/excelExport";
    if (url.indexOf("?") > 0) {
        url += "&";
    } else {
        url += "?";
    }
    window.location.href=path + url + "ids=" + ids;
    }