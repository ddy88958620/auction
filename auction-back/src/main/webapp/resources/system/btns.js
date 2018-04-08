/**
 * 根据父菜单查询子菜单
 */

var findBtns = function(callBack, parentId) {
	if (parentId != null && parentId != '' && parentId != undefined) {
		$.ajax({
			type : "post",
			url : path + "/rightSubList",
			data : {
				'parentId' : parentId
			},
			dataType : "json",
			error : function() {
				layer.msg('服务器开小差了,请稍后重试');
			},
			success : function(result) {
				if (result.code == "0") {
               
					if (callBack != null && callBack != '' && callBack != undefined) {
						eval(callBack + "(" + JSON.stringify(result.data) + ")");
					} else {

						showFirstBtns(result.data);

					}
				} else {
					layer.msg(result.msg);
				}
			}
		});
	}
};

/**
 * 查找一级页面的子菜单(按钮)
 */
function showFirstBtns(btns) {
	if (btns != null && btns != '' && btns != undefined) {
		for (var i = 0; i < btns.length; i++) {
			var tmp = btns[i];
			var html = tmp.moduleStyle;
			var btnClk = "";
			if ((html == null || html == '')) {
				btnClk = "javascript:layer.msg('菜单读取异常', function() {});";
			} else {
				btnClk = "" + html + "";
			}
			$("#toolBar").append(
					"<button class=\"layui-btn\" btn=\"" + tmp.id + "\" title=\"" + tmp.moduleName + "\" pId=\"" + tmp.moduleParentId
							+ "\" url=\"" + tmp.moduleUrl + "\" onclick=\"" + btnClk + "\"><i class=\"layui-icon\">" + tmp.iconShow + "</i>" + tmp.moduleName + "</button>");
		}
	}
};
function showError() {
	layer.msg('菜单读取异常', function() {
	});
}

