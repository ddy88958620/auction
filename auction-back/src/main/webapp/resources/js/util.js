var wait = 60;
/**
 * 打开新窗口,内嵌iframe,区别于内嵌html元素的showAddWinow,适用于添加等操作
 */
var showAddIframe;
/**
 * 打开新窗口,内嵌iframe,区别于内嵌html元素的showEditWinow,内置必须选中一行记录逻辑,适用于修改等操作
 */
var showEditIframe;
/**
 * ajax请求,用于无界面的ajax操作,想选中的N行的field为id的记录提交到后台
 */
var toDelete;
/**
 * 打开新窗口,内嵌html元素,区别于内嵌ifame元素的showAddIframe
 */
var showWindow;
/**
 * toDelete操作之后要执行的刷新页面方法(或刷新表格如用户列表;或刷新整个页面如角色/菜单管理),如果没有重新赋值则默认执行
 * window.location.reload();
 */
var refreshPage;
/**
 * 新开窗口(非iframe)中动态组建按钮
 */
var createBtns;
/**
 * 倒计时1分钟，剩余0秒时绑定click事件，否则移除click事件
 * 
 * @param htmlId
 *            倒计时控件
 * @param click
 *            要绑定的时间
 * @return
 */
function time(htmlId, click) {
	if (wait == 0) {
		$("#" + htmlId).attr("onclick", click);
		$("#" + htmlId).text("获取验证码");
		wait = 60;
	} else {
		$("#" + htmlId).text(wait + "秒后重试");
		$("#" + htmlId).removeAttr("onclick");
		wait--;
		setTimeout(function() {
			time(htmlId, click);
		}, 1000);
	}
}
/**
 * ajax请求根据返回码执行不同的操作
 * 
 * @param sessionstatus
 */
function todo(sessionstatus) {
	var msg = null;
	if (sessionstatus == "301") {
		msg = "登录超时";
	} else if (sessionstatus == "303") {
		msg = "IP受限";
	}
	if (msg != null) {
		alert(msg);
		if (window.parent != window) {
			window.parent.location.reload(true);
		} else {
			window.location.reload();
		}
	}
}
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	dataType : "json",
	complete : function(XMLHttpRequest, textStatus) {
		if (layer != undefined) {
			layer.closeAll('loading');
		}
		var sessionstatus = XMLHttpRequest.getResponseHeader("statusCode");// 通过XMLHttpRequest取得响应头，sessionstatus，
		todo(sessionstatus);
	},
	error : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("statusCode");
		var msg = '服务器开小差了,请稍后重试';
		if(sessionstatus == '403'){
			msg = "权限不足";
		}
		if (layer != undefined) {
			layer.msg(msg);
		}else{
			alert(msg);
		}
	}
});
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
}
function isJson(str) {
	try {
		JSON.parse(str);
		return true;
	} catch (err) {
		return false;
	}
}
String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
}
/**
 * 日期格式化
 * 
 * @param obj
 *            要格式化的日期
 * @param format
 *            以某种格式输出，为空则按照yyyy-MM-dd HH:mm:ss
 * @return
 */
function toDate(obj, format) {
	if (obj != null && obj != '' && obj != undefined) {
		var date = new Date();
		date.setTime(obj.time);
		date.setHours(obj.hours);
		date.setMinutes(obj.minutes);
		date.setSeconds(obj.seconds);
		if (format == undefined || format == null) {
			format = "yyyy-MM-dd hh:mm:ss";
		}
		return date.format(format);
	}
	return '';
}
String.prototype.replaceAll = function(find, rep) {
	var exp = new RegExp(find, "g");
	return this.replace(exp, rep);
};
/**
 * 数字加逗号
 * 
 * @param nStr
 *            zeroFull 没有小数点是否以00填充,默认不填充
 * @returns
 */
function addCommas(nStr, zeroFull) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	var tmp = (x.length > 1 ? x[1] == "00" ? '' : x[1] : '');
	x2 = (tmp != '') ? '.' + tmp : zeroFull ? '.00' : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}


/**
 * 根据传入的formId获得表单内的键(参数名)值(参数值)对
 * @param formId
 */
function createWhere(formId){
    var where = {};
    var array = $("#"+formId).serializeArray();
    if (array != null && array.length > 0) {
        $.each(array, function () {
            if (where[this.name]) {
                if (!where[this.name].push) {
                    where[this.name] = [where[this.name]];
                }
                where[this.name].push(this.value || '');
            } else {
                where[this.name] = this.value || '';
            }
        });
    }
    return where;
}