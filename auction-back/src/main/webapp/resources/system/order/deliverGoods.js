
var html2 = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [ '发货','重置','返回' ],
    success : function(layero, index) {
    },
    yes : function(index, layero) {
        document.getElementById("layui-layer-iframe" + index).contentWindow.submitForm();
    },
    btn2 : function(index, layero) {
        document.getElementById("layui-layer-iframe" + index).contentWindow.resetForm();
        return false;
    }
};

showDeliverGoodsIframe = function(obj) {
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
        html2.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        html2['content'] = path + url + "id=" + checkStatus.data[0].orderId;
        layer.open(html2);
    }
};
