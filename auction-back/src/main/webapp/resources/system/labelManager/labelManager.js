var html1 = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    success : function(layero, index) {
    }
};

showAddLabelIframe = function(obj) {
    // 去除打开此页面的按钮的焦点
    document.activeElement.blur();
    var url = $(obj).attr("url");
    var urlHelp = $(obj).attr("urlHelp");
    if (urlHelp != undefined && urlHelp != null && urlHelp != '') {
        url += urlHelp;
    }
    var title = $(obj).attr("title");
    html1.title = title;
    html1['content'] = path + url;
    layer.open(html1);
};