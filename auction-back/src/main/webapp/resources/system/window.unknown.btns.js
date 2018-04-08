/**
 * 弹出html元素窗口非iframe,参考用户列表
 */
var htmlWindow = {
	type : 1,
	shade : 0.3,
	maxmin : true,
	area : '600px',
	anim : 4,
	btn : [ '取消' ]
};
/**
 * 弹出html元素非iframe窗口添加按钮,参考用户授权
 * 
 * @param btns
 */
createBtns = function(btns) {
	var btnArray = new Array();
	if (btns != null && btns != '' && btns != undefined && btns.length > 0) {
		for (var i = 0; i < btns.length; i++) {
			var tmp = btns[i];
			if (i == 0) {
				htmlWindow.yes = function(index, layero) {
					eval(tmp.moduleStyle + "(" + JSON.stringify(tmp) + ")");

				};
			} else {
				htmlWindow["btn" + (i + 1)] = function(index, layero) {
					eval(tmp.moduleStyle);
				};
			}
			btnArray.push(tmp.moduleName);
		}
		btnArray.push("取消");
		htmlWindow.btn = btnArray;
	}
};
/**
 * 打开非iframe窗口时查询并创建按钮,然后回调数据库配置的方法,如用户授权/用户列表
 * 
 * @param obj
 * @param callBack
 */
showWindow = function(obj, callBack) {
	var url = $(obj).attr("url");
	var title = $(obj).attr("title");
	// 打开窗口的时候按钮的id就是下一个页面的父id
	var pId = $(obj).attr("btn");
	document.activeElement.blur();
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
		htmlWindow.title = title;
		$.ajax({
			type : "post",
			url : path + url,
			data : {
				id : checkStatus.data[0].id
			},
			dataType : "json",
			beforeSend : function() {
				layer.load();
			},
			success : function(result) {
				if (result.code == '0') {
					var data = result.data;
					var id = result.id;
					if (pId != null && pId != null && pId != '') {
						$.ajax({
							type : "post",
							url : path + "/rightSubList",
							data : {
								'parentId' : pId
							},
							dataType : "json",
							error : function() {
								layer.msg('服务器开小差了,请稍后重试');
							},
							success : function(result) {
								if (result.code == "0") {
									createBtns(result.data);
									if (data != null && data != '' && data.length > 0) {
										eval(callBack + '(' + JSON.stringify(data) + ',' + id + ')');
									}
								} else {
									layer.msg(result.msg);
								}
							}
						});
					} else {
						eval(callBack + '(' + JSON.stringify(data) + ')');
					}
				} else {
					layer.msg(result.msg);
				}
			}
		});
	}
};