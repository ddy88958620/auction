function navBar(path,data) {
	var ulHtml = '<ul class="layui-nav layui-nav-tree">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].spread) {
			ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
		} else {
			ulHtml += '<li class="layui-nav-item">';
		}
		var children = data[i].moduleList;
		if (children != undefined && children.length > 0) {
			ulHtml += '<a href="javascript:;">';
			if (data[i].iconShow != undefined && data[i].iconShow != '' && data[i].iconShow.indexOf("icon-") != -1) {
				ulHtml += '<i class="iconfont ' + data[i].iconShow + '" data-icon="' + data[i].iconShow + '"></i>';
			} else {
				ulHtml += '<i class="layui-icon" data-icon="' + data[i].iconShow + '">' + data[i].iconShow + '</i>';
			}
			ulHtml += '<cite>' + data[i].moduleName + '</cite>';
			ulHtml += '<span class="layui-nav-more"></span>';
			ulHtml += '</a>';
			ulHtml += '<dl class="layui-nav-child">';
			for (var j = 0; j < children.length; j++) {
				ulHtml += '<dd><a href="javascript:;" tabId="' + children[j].id + '" data-url="' +path+  children[j].moduleUrl + "?myId=" + children[j].id + '">';
				if (children[j].iconShow != undefined && children[j].iconShow != '' && children[j].iconShow.indexOf("icon-") != -1) {
					ulHtml += '<i class="iconfont ' + children[j].iconShow + '" data-icon="' + children[j].iconShow + '"></i>';
				} else {
					ulHtml += '<i class="layui-icon" data-icon="' + children[j].iconShow + '">' + children[j].iconShow + '</i>';
				}
				ulHtml += '<cite>' + children[j].moduleName + '</cite></a></dd>';
			}
			ulHtml += "</dl>"
		} else {
			ulHtml += '<a href="javascript:;" tabId="' + data[i].id + '"  data-url="' +path+  data[i].moduleUrl + "?myId=" + data[i].id + '">';
			if (data[i].iconShow != undefined && data[i].iconShow != '') {
				if (data[i].iconShow.indexOf("icon-") != -1) {
					ulHtml += '<i class="iconfont ' + data[i].iconShow + '" data-icon="' + data[i].iconShow + '"></i>';
				} else {
					ulHtml += '<i class="layui-icon" data-icon="' + data[i].iconShow + '">' + data[i].iconShow + '</i>';
				}
			}
			ulHtml += '<cite>' + data[i].moduleName + '</cite></a>';
		}
		ulHtml += '</li>'
	}
	ulHtml += '</ul>';
	return ulHtml;
}
