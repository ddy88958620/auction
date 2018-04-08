/**
 * 效果类似上边的html,但是按钮是动态读取的,默认在最后加上一个取消按钮
 */
var htmlDynamicBtn = {
	type : 2,
	shade : 0.3,
	maxmin : true,
	area : [ '100%', '100%' ],
	anim : 1,
	btnAlign : 'r',
	btn : [ '取消' ]
};
function addBtns(btns) {
	var btnArray = new Array();
	if (btns != null && btns != '' && btns != undefined && btns.length > 0) {
		for (var i = 0; i < btns.length; i++) {
			var tmp = btns[i];
			if (i == 0) {
				htmlDynamicBtn.yes = function(index, layero) {
					eval(tmp.moduleStyle);
				};
			} else {
				htmlDynamicBtn["btn" + (i + 1)] = function(index, layero) {
					eval(tmp.moduleStyle);
				};
			}
			btnArray.push(tmp.moduleName);
		}
		btnArray.push("取消");
		htmlDynamicBtn.btn = btnArray;
	}
}
