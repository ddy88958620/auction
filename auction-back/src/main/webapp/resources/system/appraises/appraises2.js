var html3 = {
	    type : 2,
	    shade : 0.3,
	    maxmin : true,
	    area : [ '100%', '100%' ],
	    btnAlign : 'r',
	    btn : [ '提交审核','取消' ],
	    success : function(layero, index) {
	    },
	    yes : function(index, layero) {
	        document.getElementById("layui-layer-iframe" + index).contentWindow.createAppraises();
	    }
	};

	createAppraiseInfoIframe = function(obj) {
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
	    	html3.title = title;
	        if (url.indexOf("?") > 0) {
	            url += "&";
	        } else {
	            url += "?";
	        }
	        html3['content'] = path + url + "orderId=" + checkStatus.data[0].orderId;
	        layer.open(html3);
	    }
	};

