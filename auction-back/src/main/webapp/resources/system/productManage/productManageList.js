/**
 * 适用于普通的添加和编辑窗口,注意iframe页面中要定义submitForm和resetForm方法
 */
var html = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : ['取消' ],
    success : function(layero, index) {
    }
};

var htmlProductView = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : ['取消' ],
    success : function(layero, index) {
    }
};

showProductViewIframe = function(obj){
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
        htmlProductView.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        htmlProductView['content'] = url + "id=" + checkStatus.data[0].productId;
        layer.open(htmlProductView);
    }
}

var htmlEdit = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r', btn : [  '取消' ],
    success : function(layero, index) {
    }
};
var htmlAuction = {
    type : 2,
    shade : 0.3,
    maxmin : true,
    area : [ '100%', '100%' ],
    anim : 1,
    btnAlign : 'r',
    btn : [ '取消' ],
    success : function(layero, index) {
    }
};
deleteProduct = function (obj) {
    var checkStatus = table.checkStatus('search');
    var  data = checkStatus.data;
    var arr = [];
    $.each(data,function(i,item){
        arr[i] = item.productId;
    });
    if(arr.length == 0) {
        layer.msg('未选中要操作的行', function () {
        });
    }else {
        layer.confirm('确定要操作吗?', {
            icon : 3,
            title : '警告'
        }, function(index) {
            //批量删除
            $.get("batchDel?ids=" + arr, function (data, status) {
                if (data.code == '0') {
                    //alert("删除成功");
                    window.location.reload();
                } else {
                    layer.msg("失败:"+data.msg);
                }
            });
        });
    }
}

showProductEditIframe = function(obj){
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
        htmlEdit.title=title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        htmlEdit['content'] = url + "id=" + checkStatus.data[0].productId;
        layer.open(htmlEdit);
    }
}
showAuctionAddIframe = function(obj) {

    // 去除打开此页面的按钮的焦点
    document.activeElement.blur();
    var url = $(obj).attr("url");
    // 去除打开此页面的按钮的焦点
    var checkStatus = table.checkStatus("search");
    var len = checkStatus.data.length;
    var title = $(obj).attr("title");
    if (len == 0) {
        layer.msg('未选中要操作的行', function() {
        });
    } else if (len > 1) {
        layer.msg('一次仅能操作一行', function() {
        });
    } else {
        htmlAuction.title = title;
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        htmlAuction['content'] = url + "productId=" + checkStatus.data[0].productId;
        layer.msg(checkStatus.data[0].productId, function() {
        });
        layer.open(htmlAuction);
    }
};
submitForm = function() {
    $("button[lay-filter='submitBtn']").trigger('click');
}

function deleteTrById(id){
    $("#"+id).remove();
}

function getDeleteTr(id,picUrl){
    var trId = id;
    id = "'"+id+"'";
    var td = '<td><input type="button" class="layui-btn layui-btn-mini layui-btn-danger demo-delete" onclick="deleteTrById('+id+')" value="删除" />';
    var tr = "<tr id='"+trId+"'>" +
        "<td>"+picUrl+"</td>" +
        "<td>已上传</td>" +
        td+
        "</tr>";
    return tr;
}

function getValue(arr,inputName,detailList,picType){
    var length = arr.length;
    var newArr = [];
    var value = $("input[name="+inputName+"]");

    for(var x=0; x<value.length;x++){
        newArr.push($(value[x]).val());
        arr.push($(value[x]).val());
    }

    //商品图
    for (var i = 0; i < newArr.length; i++) {
        var picIndex = parseInt(length)+i;
        detailList["pics[" + picIndex + "].picUrl"] =  newArr[i];
        detailList["pics[" + picIndex + "].picType"] =  picType;
    }

    return detailList;
}
