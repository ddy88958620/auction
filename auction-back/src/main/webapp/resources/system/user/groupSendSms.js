var html = {
    type : 2,
    title:'群发短信',
    shade : 0.3,
    maxmin : true,
    area: ['100%', '100%'],
    anim : 1,
    btnAlign : 'r'
};

// 群发短信(营销)
showAddSmsIframe = function(obj) {
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
        layer.msg('未选中要操作的行', function() {
        });
    } else {
        var ids = new Array();
        for (var i = 0; i < len; i++) {
            if(checkStatus.data[i].userPhone == null || checkStatus.data[i].userPhone == ""){
                layer.msg('选中的用户,有无手机号码的,不能发送短信', function() {
                });
                return ;
            }
            ids.push(checkStatus.data[i].userPhone);
        }
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        html['content'] = path + url + "ids=" + ids;
        layer.open(html);

    }
};

