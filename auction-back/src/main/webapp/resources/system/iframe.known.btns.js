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
	btn : [ '提交', '重置', '取消' ],
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
/**
 * 弹出iframe窗口适用于添加操作
 * 
 * @param obj
 */
showAddIframe = function(obj) {
	// 去除打开此页面的按钮的焦点
	document.activeElement.blur();
	var url = $(obj).attr("url");
	var urlHelp = $(obj).attr("urlHelp");
	if (urlHelp != undefined && urlHelp != null && urlHelp != '') {
		url += urlHelp;
	}
	var title = $(obj).attr("title");
	html.title = title;
	html['content'] = path + url;
	layer.open(html);
};
/**
 * 弹出iframe窗口适用于修改操作
 * 
 * @param obj
 */
showEditIframe = function(obj) {
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
