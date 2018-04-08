/**
 * 适用于普通的添加和编辑窗口,注意iframe页面中要定义submitForm和resetForm方法
 */


var htmlAdd = {
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

showRecommendAdd = function (obj) {
    document.activeElement.blur();
    var url = $(obj).attr("url");
    var urlHelp = $(obj).attr("urlHelp");
    if (urlHelp != undefined && urlHelp != null && urlHelp != '') {
        url += urlHelp;
    }
    var title = $(obj).attr("title");
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    htmlAdd['content'] = url ;
    htmlAdd['area']=['80%','80%'];
    layer.open(htmlAdd);

}


recommendDeleteBatch = function (obj) {
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    if (len == 0) {
        layer.msg('未选中要操作的行', function() {
        });
    }
    if (len == 0) {
        layer.msg('未选中要操作的行', function() {
        });
    }else {
        layer.confirm('确定要操作吗?', {
            icon: 3,
            title: '警告'
        }, function (index) {

            var arr = [];
            $.each(checkStatus.data, function (i, item) {
                arr[i] = item.auctionProdId;
            });
            //批量删除
            $.get("batchDelRecommend?ids=" + arr, function (data, status) {
                if (data.code == '0') {
                    alert("删除成功");
                    window.location.reload();
                } else {
                    alert("失败");
                }
            });
        });
    }

}




submitForm = function() {
    $("button[lay-filter='submitBtn']").trigger('click');
}
