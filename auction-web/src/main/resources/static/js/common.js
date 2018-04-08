function alert_nomask(alertElem){			
	var left=$(window).width();
	var top=$(window).height();	
	alertElem.css('display','block').css('top',(top-alertElem.height())/2+'px').css('left',(left-alertElem.width())/2+'px')
	
}
function alert_havemask(alertElem){
	//console.log()
	$('.mask').css('display','block');
	var left=$(window).width();
	var top=$(window).height();	
	alertElem.css('display','block').css('top',(top-alertElem.height())/2+'px').css('left',(left-alertElem.width())/2+'px')
	
}

function alertFade(elem){
	setTimeout(function(){
		elem.fadeOut()
	},2000)
}

function alertFade_havemask(elem){
	setTimeout(function(){
		$('.mask').fadeOut()
		elem.fadeOut()
	},2000)
}

function showMsgNoMask(msg) {
    var obj = $(".warning_alert");
    obj.text(msg);
    alert_nomask(obj);
    alertFade(obj);
}

function showMsgWithMask(msg) {
    var obj = $(".warning_alert");
    $(obj).text(msg);
    alert_havemask(obj);
    alertFade_havemask(obj);
}



(function (doc, win) {
    var docEl = doc.documentElement;
    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize';
    recalc = function () {
        var clientWidth = docEl.clientWidth;
        if (!clientWidth) return;
        docEl.style.fontSize=document.documentElement.clientWidth/3.75+'px'
    };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
}(document,window));

function addHead(content){
    var str='';
    str+="<div class='fixed_head'>"+
        content+
        "<img class='back' src='img/back.jpg'/>"
        +"</div>"
    $('body').prepend(str)
}

function blankTop(){
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        $('.fixed_head').css('padding-top','.08rem')
        $('.fixed_head .back').css('top','.18rem')
    } else if (/android/.test(ua)) {
        //  alert("android");
    }
}

// JavaScript Document
$(document).ready(function() {

});