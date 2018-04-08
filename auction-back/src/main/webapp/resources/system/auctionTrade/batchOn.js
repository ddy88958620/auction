/**
 * 通用上架方法,需要页面的table中id要固定为search,如果未定义refreshPage方法则调用window.location.reload();否则执行window.location.reload();
 *
 * @param obj
 */
batchOn = function (obj) {
    document.activeElement.blur();
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    if (len == 0) {
        layer.msg('未选中要操作的行', function () {
        });
    } else {
        layer.confirm('确定要操作吗?', {
            icon: 3,
            title: '警告'
        }, function (index) {
            var url = $(obj).attr("url");
            var ids = new Array();
            for (var i = 0; i < len; i++) {
                ids.push(checkStatus.data[i].id);
            }
            $.ajax({
                type: "post",
                url: path + url,
                data: {
                    ids: ids
                },
                dataType: "json",
                beforeSend: function () {
                    layer.msg('正在上架' + len + "条数据", {
                        icon: 16
                    });
                },
                success: function (result) {
                    layer.msg(result.msg, {
                        time: 1000
                    }, function () {
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

var htmlView = {
    type : 2,
     shade : 0.3,
     maxmin : true,
     area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [],
    success : function(layero, index) {
     }
 };

showAuctionTimingIframe = function (obj) {
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
    } else {
        if (checkStatus.data[0].status != 1) {
            htmlView.title = title;
            if (url.indexOf("?") > 0) {
                url += "&";
            } else {
                url += "?";
            }

            var ids = "";
            for (var i = 0; i < len; i++) {
                ids += checkStatus.data[i].id + ",";
            }
            htmlView['area'] = ['50%', '50%'];
            htmlView['content'] = url + "id=" + ids;


            layer.open(htmlView);



        } else {
            layer.msg('该拍品已上架或者在定时中,请重新选择!', function () {
            });
        }
    }
};
showAuctionLookupIframe = function (obj) {
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
    } else if (len > 1) {
        layer.msg('一次仅能操作一行', function () {
        });
    } else {
        html.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        html['content'] = url + "id=" + checkStatus.data[0].id + "&auctionProdId=" + checkStatus.data[0].auctionProdId;
        layer.open(html);
    }
};