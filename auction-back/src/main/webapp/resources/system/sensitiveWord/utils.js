
var html = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [  ],
    success : function(layero, index) {
    }
};

showUpdateSensitiveWordIframe = function(obj) {
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
        html['content'] = path + url + "id=" + checkStatus.data[0].id;
        layer.open(html);
    }
};
deleteSensitiveWord = function(obj) {
    document.activeElement.blur();
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    if (len == 0) {
        layer.msg('未选中要操作的行', function() {
        });
    } else if (len >1){
        layer.msg('一次仅能操作一行', function() {
        });
    } else if (len == 1){
        layer.confirm('确定要操作吗?', {
            icon : 3,
            title : '警告'
        }, function(index) {
            var url = $(obj).attr("url");
            $.ajax({
                type : "post",
                url : path + url,
                data : {
                    id : checkStatus.data[0].id
                },
                dataType : "json",
                beforeSend : function() {
                    layer.msg('正在删除' + len + "条数据", {
                        icon : 16
                    });
                },
                success : function(result) {
                    layer.msg(result.msg, {
                        time : 1000
                    }, function() {
                        if (result.code == '0') {
                            if (refreshPage != undefined && refreshPage != null && refreshPage != '') {
                                refreshPage();
                            } else {
                                window.location.reload();
                            }
                        }
                    });
                }
            });
        });

    }
};

var htmlChannelView = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [ ],
    success : function(layero, index) {
    }
};

showViewSensitiveWordIframe = function(obj){
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
        htmlChannelView.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        htmlChannelView['content'] = path + url + "id=" + checkStatus.data[0].id;
        layer.open(htmlChannelView);
    }
};