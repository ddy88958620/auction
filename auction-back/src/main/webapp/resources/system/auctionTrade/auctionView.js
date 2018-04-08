
var html = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [ '返回' ],
    success : function(layero, index) {
    }
};

showAuctionViewIframe = function(obj) {
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
    } else if (len > 1) {
        layer.msg('一次仅能操作一行', function() {
        });
    } else {
        html.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        // html['area'] = ['50%','40%'];
        html['content'] = path + url + "id=" + checkStatus.data[0].id;
        layer.open(html);
    }
};
