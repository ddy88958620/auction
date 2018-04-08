
var dtd = $.Deferred();
var checkCode = function(dtd){
    var userPhone = $('#mytext').val();
    $.ajax({
        type: "post",
        url:"/checkSmsCode",
        dataType: "json",
        data: {userPhone:userPhone},
        success: function(data){
            //已注册
            if(data.code != "0"){
                dtd.resolve("1");
            }else{
                dtd.resolve();
            }
        },
        error: function(data){
            dtd.resolve("发送验证码失败");
        } ,
    });

    return dtd;

};

var d2 = $.Deferred();
var sendCode = function(d2){
    var userPhone = $('#mytext').val();
    $.ajax({
        type: "post",
        url:"/sendShareCode",
        dataType: "json",
        data: {userPhone:userPhone},
        success: function(data){
            //发送失败
            if(data.code != "0"){
                d2.resolve(data.msg);
            }else{
                d2.resolve();
			}
        },
        error: function(data){
            d2.resolve('发送验证码失败');
        } ,
    });

    return d2;

};

var d3 = $.Deferred();
var doRegister = function(d3){
    var userPhone = $('#mytext').val();
    var code = $("#mycode").val();
    var pwd = $("#mypwd").val();
    var Request = getData();
    var channelId = Request['channelId'];
    var data = {
        userPhone:userPhone,
        smsCode:code,
        password:pwd,
        province:'',
        city:''
    }
    $.ajax({
        type: "post",
        url:"/doRegister?channelId="+channelId,
        dataType: "json",
        data: data,
        success: function(data){
            if(data.code != "0"){
                d3.resolve(data.msg);
            }else {//注册成功
                d3.resolve('0');
            }
        },
        error: function(data){
            d3.resolve('注册失败');
        } ,
    });

    return d3;

};

//获取验证码
$(document).ready(function(){
	$(".hqcode").unbind().click(function () {
		//发送验证码1.验证手机号是否注册 2.发送
        $.when(checkCode(dtd)).done(function ( v1) {
        	if(v1){
                if (v1=='1'){
                    alert( '该手机号已注册');
                    window.location.href="http://sj.qq.com/myapp/detail.htm?apkName=com.canfu.auction";
                    return;
                }
                alert( v1 );
                return;
			}else {
                $.when(sendCode(d2)).done(function ( v2) {
                    if(v2){
                        alert( v2 );
                        return;
                    }
                });
			}
        });


	  	$(this).css("display","none");
	  	$(".send").css("display","block");
	  	//获取验证码倒计时
	  	var setTime;
	  	var time = 60;
	    setTime = setInterval(function(){
	        if(time <= 1){
	            clearInterval(setTime);
	            $("#time").text(60);
	            $(".hqcode").css("display","block");
		  		$(".send").css("display","none");
	            return;
	        }
	        time--;
	        $("#time").text(time);
	    },1000);
	});
});

//注册判断手机号，验证码和密码
$(document).ready(function () {
	$("body").css("height",$(window).height());
	$("input").focus(function(){
    	$(this).css("border-bottom","1px solid #F5863A");
  	});
  	$("input").blur(function(){
    	$(this).css("border-bottom","1px solid #dad9d9");
  	});
    $("#btnSenderMessage").unbind().click(function(){
    	var regphone=/^[1][3,4,5,7,8][0-9]{9}$/;
	    var regcode=/^\d{6}$/;
	    var regpwd=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
	    var mobile = $("#mytext").val();
	    var code = $("#mycode").val();
	    var pwd = $("#mypwd").val();
	    if( mobile == null || mobile == "" ){
	        alert("手机号不能为空！");
	    }else if(!regphone.exec(mobile)){
	        alert("手机号格式不正确");
	    }else if( code == null || code == "" ){
	        alert("验证码不能为空！");
	    }else if(!regcode.exec(code)){
	        alert("验证码格式不正确");
	    }else if( pwd == null || pwd == "" ){
	        alert("密码不能为空！");
	    }else if(!regpwd.exec(pwd)){
	        alert("密码格式不正确");
	    }else{
            var userPhone = $('#mytext').val();
            var code = $("#mycode").val();
            var pwd = $("#mypwd").val();
            var Request = getData();
            var channelId = Request['channelId'];
            var data = {
                userPhone:userPhone,
                smsCode:code,
                password:pwd,
                province:'',
                city:''
            };
            $.when(

                $.ajax({
                    type: "post",
                    url:"/doRegister?channelId="+channelId,
                    dataType: "json",
                    data: data,
                    success: function(data){
                        if(data.code != "0"){
                            d3.resolve(data.msg);
                        }else {//注册成功
                            d3.resolve('0');
                        }
                    },
                    error: function(data){
                        d3.resolve('注册失败');
                    } ,
                })
			).done(function ( v1) {
                if(v1&&v1.code!='0'){
                    alert( v1.msg );
                    return;
                }

                //登录成功
                if (v1.code=='0'){
                    alert("注册成功");
                    window.location.href="http://sj.qq.com/myapp/detail.htm?apkName=com.canfu.auction";
				}

            });
	    }
	})
});

function getData() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}