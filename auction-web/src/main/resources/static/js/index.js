//选择箱子
$(".choice li").each(function (i) {
    $(this).click(function () {
        $(".choice li").each(function () {
            $(this).removeClass("selected")
            $('.receive').fadeIn(300);
        });
        $(this).addClass("selected")
    })
});

//获取验证码
$(document).ready(function(){
	$(".hqcode").click(function () {
        var userPhone = $('#mytext').val();
        $.ajax({
            type: "post",
            url:"/sendShareCode",
            dataType: "json",
            data: {userPhone:userPhone},
            success: function(data){
                if(data.code == "4"){
                    alert(data.msg);
                    return;
                }
            },
            error: function(data){
                alert("error");
                return;
            } ,
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
	$('body,html').animate({scrollTop:150},1000)	
});

//输入框获取焦点和离开
$(document).ready(function(){
	$("#dialog input").on("input propertychange",function(){  
	  	if ($("#dialog input").val() !== '') {
	  		$("#dialog input").css({"font-size":"1rem"});
	  	}else{
	  		$("#dialog input").css({"font-size":"0.8rem"});
	  	}
	});
	if ($("#dialog input").val() !== '') {
  		$("#dialog input").css({"font-size":"1rem"});
  	}else{
  		$("#dialog input").css({"font-size":"0.8rem"});
  	}
  	$("#dialogtwo #mycode").on("input propertychange",function(){  
	  	if ($("#dialogtwo #mycode").val() !== '') {
	  		$("#dialogtwo #mycode").css({"font-size":"1rem"});
	  	}else{
	  		$("#dialogtwo #mycode").css({"font-size":"0.8rem"});
	  	}
	});
	if ($("#dialogtwo #mycode").val() !== '') {
  		$("#dialogtwo #mycode").css({"font-size":"1rem"});
  	}else{
  		$("#dialogtwo #mycode").css({"font-size":"0.8rem"});
  	}
  	$("#dialogtwo #mypwd").on("input propertychange",function(){  
	  	if ($("#dialogtwo #mypwd").val() !== '') {
	  		$("#dialogtwo #mypwd").css({"font-size":"1rem"});
	  	}else{
	  		$("#dialogtwo #mypwd").css({"font-size":"0.8rem"});
	  	}
	});
	if ($("#dialogtwo #mypwd").val() !== '') {
  		$("#dialogtwo #mypwd").css({"font-size":"1rem"});
  	}else{
  		$("#dialogtwo #mypwd").css({"font-size":"0.8rem"});
  	}
});


// //小手抖动
// var css = {top:'0.7rem'};
// setInterval(function(){
//     $('.hand').animate(css,500,rowBack);
// },300);
// function rowBack(){
//     if(css.top==='0.7rem')
//         css.top='1.1rem';
//     else if(css.top==='1.1rem')
//         css.top='0.7rem';
// }

//倒计时
$(document).ready(function() {
    var times = 5 * 100; // 5秒
    countTime = setInterval(function() {
        times = --times < 0 ? 0 : times;
        var ms = Math.floor(times / 100).toString();
        if(ms.length <= 1) {
            ms = "0" + ms;
        }
        var hm = Math.floor(times % 100).toString();
        if(hm.length <= 1) {
            hm = "0" + hm;
        }
        if(times == 0) {
            // alert("游戏结束");
            times = 500;
        }
        if(times == 100){
            $(".time").css("display","none");
            $(".timewc").css("display","block");
            $(".mask").fadeIn("slow");
        }
        // 获取分钟、毫秒数
        $("#timeout").html(ms);
        $("#timeoutm").html(hm);
    }, 10);
    $('.offer').click(function(){
        times = 500;
        setTimeout(function(){//三秒后重置时间
            times = 500;
        },3000);
    });
    $('.mask').click(function(){
        times = 1000;
        $(this).fadeOut("slow");
    });
});

//弹框
var w,h,className;
function getSrceenWH(){
	w = $(window).width();
	h = $(window).height();
	$('#dialogBg').width(w).height(h);
}
window.onresize = function(){  
	getSrceenWH();
}  
$(window).resize();  
$(function(){
	getSrceenWH();
	//显示弹框
	$('.offer').click(function(){
		var n = $('.num').html();
		var num = parseInt(n)-1;
		if(num < 0){
			className = $(this).attr('class');
			$('#dialogBg').fadeIn(300);
			$('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn(); 
			return
		}
		$('.num').html(num);
		$(".record .title").after('<ul><li style="width: 18%;"><img src="../images/user-me.png"></li><li>我</li><li class="state">领先</li><li>浙江杭州</li><li id="mybids">¥20.00</li></ul>');
		$(".record ul").last().remove();
		$(".state").eq(1).html('出局');
		$(this).css("display","none");
		$(".being").css("display","block");
		$(".time").css("display","block");
		$(".timewc").css("display","none");
		if(num==3){
			$("#mybids").html('¥20.00')
			$("#current").html('¥20.00')
			$(".bids").html('若无人出价，我将以20.00拍得本商品')
		}else if(num == 2){
			$("#mybids").html('¥20.10')
			$("#current").html('¥20.10')
			$(".bids").html('若无人出价，我将以20.10拍得本商品')
		}else if(num == 1){
			$("#mybids").html('¥20.30')
			$("#current").html('¥20.30')
			$(".bids").html('若无人出价，我将以20.30拍得本商品')
		}else if(num == 0){
			$("#mybids").html('¥20.50')
			$("#current").html('¥20.50')
			$(".bids").html('若无人出价，我将以20.50拍得本商品')
			var times = 5 * 100; // 5秒
			countTime = setInterval(function() {
				times = --times < 0 ? 0 : times;
				var ms = Math.floor(times / 100).toString();
				if(ms.length <= 1) {
					ms = "0" + ms;
				}
				var hm = Math.floor(times % 100).toString();
				if(hm.length <= 1) {
					hm = "0" + hm;
				}
				if(times == 0) {
					// alert("游戏结束");
					// clearInterval(countTime);
                    $(".timewc").css("display","none");
                    $(".mask").css("display","none");
				}
				// 获取分钟、毫秒数
				$("#timeoutqq").html(ms);
				$("#timeoutmqq").html(hm);
			}, 10);
			$(".time").css("display","none");
			$(".timesuc").css("display","block");
			setTimeout(function(){//五秒后弹框
				className = $(this).attr('class');
				$('#dialogBg').fadeIn(300);
				$('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn(); 
			},5000); 
		}
		setTimeout(function(){//两秒后添加
	        $(".offer").css("display","block");
	        if(num==3){
				$("#mybidsjq").html('¥20.00')
				$("#current").html('¥20.00')
				$(".bids").html('若无人出价，糊糊将以20.00拍得本商品')
				$(".record .title").after('<ul><li style="width: 18%;"><img src="../images/user-one.png"></li><li>糊糊</li><li class="state">领先</li><li>上海浦东</li><li id="mybidsjq">¥20.00</li></ul>')
				$(".state").eq(1).html('出局');
				$(".record ul").last().remove()
				$(".being").css("display","none");
			}else if(num == 2){
				$("#mybidsjq").html('¥20.20')
				$("#current").html('¥20.20')
				$(".bids").html('若无人出价，糊糊将以20.20拍得本商品')
				$(".record .title").after('<ul><li style="width: 18%;"><img src="../images/user-one.png"></li><li>糊糊</li><li class="state">领先</li><li>上海浦东</li><li id="mybidsjq">¥20.20</li></ul>')
				$(".state").eq(1).html('出局');
				$(".record ul").last().remove();
				$(".being").css("display","none");
			}else if(num == 1){
				$("#mybidsjq").html('¥20.40')
				$("#current").html('¥20.40')
				$(".bids").html('若无人出价，糊糊将以20.40拍得本商品')
				$(".record .title").after('<ul><li style="width: 18%;"><img src="../images/user-one.png"></li><li>糊糊</li><li class="state">领先</li><li>上海浦东</li><li id="mybidsjq">¥20.40</li></ul>')
				$(".state").eq(1).html('出局');
				$(".record ul").last().remove();
				$(".being").css("display","none");
			}else if(num == 0){
				$(".record .title").after('');
				setTimeout(function(){//五秒后添加
					$(".being").css("display","none");
			    },2000); 
			}
	    },3000); 
	});
	$('.hdgz').click(function(){
		className = $(this).attr('class');
		$('#dialogBg').fadeIn(300);
		$('#dialogrule').removeAttr('class').addClass('animated '+className+'').fadeIn();
	});
	//关闭弹窗
	$('.claseDialogBtn').click(function(){
		$('#dialogBg').fadeOut(300,function(){
			$('#dialog').addClass('bounceOutUp').fadeOut();
			$('#dialogtwo').addClass('bounceOutUp').fadeOut();
			$('#dialogrule').addClass('bounceOutUp').fadeOut();
		});
	});
});

//注册判断手机号
$(document).ready(function () {
    $("#btnSenderMessage").click(function(){
	    var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
	    var mobile = $("#mytext").val();
	    if( mobile == null || mobile == "" ){
	        alert("手机号不能为空！");
	    }else if(!reg.exec(mobile)){
	        alert("手机号格式不正确");
	    }
	    // else if(checkPhoneIsExist()){
	    //     alert("该手机号码已经被绑定！");
	    // }
	    else{
	    	//校验手机号是否注册
            $.ajax({
                type: "post",
                url:"/checkSmsCode",
                dataType: "json",
                data: {userPhone:mobile},
                success: function(data){
                    if(data.code == "7"){
						alert(data.msg);
						window.location.href="lead.html";
						return;
                    }else{
                        className = $(this).attr('class');
                        $('#dialogBg').fadeIn(300);
                        $('#dialogtwo').removeAttr('class').addClass('animated '+className+'').fadeIn();
					}
                },
                error: function(data){
                    alert("error");
                    return;
                } ,
            });

	    }
	})
});

//判断验证码和密码
$(document).ready(function () {
    $("#btnSenderMessagetwo").click(function(){
	    var reg=/^\d{6}$/;
	    var regpwd=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
	    var code = $("#mycode").val();
	    var pwd = $("#mypwd").val();
	    if( !code ){
	        alert("验证码不能为空！");
	    }else if(!reg.exec(code)){
	        alert("验证码格式不正确");
	    }else if( !pwd){
	        alert("密码不能为空！");
	    }else if(!regpwd.exec(pwd)){
	        alert("密码格式不正确");
	    }else{
            var Request = getData();
            var channelId = Request['channelId'];
            var userPhone = $('#mytext').val();
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
                    	if (data.code == "7"){
                            alert(data.msg);
                            window.location.href="lead.html";
                            return;
						}
                        alert(data.msg);
                        return;
                    }else {
                        window.location.href="receive.html?coin="+data.data.coin;
					}
                },
                error: function(data){
                    alert("error");
                    return;
                } ,
            });
	    	// alert("成功");
	    	// window.location.href="receive.html";
	    	// window.location.href="lead.html";

	    }
	})
});

//判断手机号是否存在
// function checkPhoneIsExist(){
// 	var mobile = $("#mytext").val();
//     var flag = true;
//     jQuery.ajax(
//       	{
//       		url: "",
//         	data:{
//         		mobile:mobile
//         	},
//         	dataType:"json",
//            	type:"GET",
//            	async:false,
//            	success:function(data) {
//            	var status = data.status;
//            	if(status == "0"){
//              	flag = false;
//            	}
//         }
//     });
//     return flag;
// }

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