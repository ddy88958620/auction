/**
 * 通用删除方法,需要食欲页面的table中id要固定为search,如果未定义refreshPage方法则调用window.location.reload();否则执行window.location.reload();
 * 
 * @param obj
 */
toDelete = function(obj) {
	document.activeElement.blur();
	// 去除打开此页面的按钮的焦点
	var checkStatus = table.checkStatus("search");
	var len = checkStatus.data.length;
	if (len == 0) {
		layer.msg('未选中要操作的行', function() {
		});
	} else {
		layer.confirm('确定要操作吗?', {
			icon : 3,
			title : '警告'
		}, function(index) {
			var url = $(obj).attr("url");
			var ids = new Array();
			for (var i = 0; i < len; i++) {
				ids.push(checkStatus.data[i].id);
			}
			$.ajax({
				type : "post",
				url : path + url,
				data : {
					ids : ids
				},
				dataType : "json",
				beforeSend : function() {
					layer.msg('正在删除' + len + "条数据", {
						icon : 16
					});
				},
				success : function(result) {
					layer.msg(result.msg, {
						time : 1000
					}, function() {
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