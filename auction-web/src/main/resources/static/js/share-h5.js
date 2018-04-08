//邀请得现金box
$(document).ready(function(){
	var windowwidth = $(window).width();
	var windowheight = $(window).height();
	var boxheight = $('.invitation_box').height();
	$(".invitation_bg").css({"width":windowwidth,"height":windowheight});
	$(".invitation_box").css({"top":(windowheight - boxheight) / 4});
 	$(".invitation").click(function(){
    	$(".invitation_bg , .invitation_box").fadeIn("slow");
  	});
  	$(".invitation_bg , .close").click(function(){
    	$(".invitation_bg , .invitation_box").fadeOut("slow");
  	});

  	$('.share-button').click(function () {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        var activityId = $('#activityId').val();

        if(isAndroid){
            native.jumpNative("{'type':'6','activityId':'"+activityId+"'}");
		}

		if (isIOS){
            var jsonObj={"type":"6","activityId":activityId};
            window.webkit.messageHandlers.Native.postMessage(jsonObj);

        }

    });

});