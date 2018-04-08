
showAddIframe2 = function (obj) {
    nowPageId += "";
    if (nowPageId != null && nowPageId != '' && nowPageId != undefined) {
        $(obj).attr("urlHelp", '?myId=' + nowPageId + "&parentName=" + parentName);
        showAddIframe(obj);
    } else {
        layer.msg('请选择父节点', function () {
        });
    }
};
showEditIframe2 = function (obj) {
    $(obj).attr("urlHelp", '?myId=' + nowPageId + "&parentName=" + parentName);
    showEditIframe(obj);
};
