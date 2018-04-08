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
    var windowheight = $(window).height();
    var ruleboxheight = $('.rulebox').outerHeight();
    var rulebox1height = $('.rulebox1').outerHeight();
    var ruleboxtop = (windowheight - ruleboxheight)/2
    var rulebox1top = (windowheight - rulebox1height)/2
    $('.rulebox').css({"top":ruleboxtop})
    $('.rulebox1').css({"top":rulebox1top})
    //遮罩层禁止滚动
    $('.Mask1,.rulebox1').bind("touchmove", function (e) {
        e.preventDefault();
    });
    //准备好了
    $(".rulebox_btn1").click(function(){
        $('.Mask1,.rulebox1').fadeOut(300);
    })
    $(".hqcode").click(function () {
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
    $('body,html').animate({scrollTop:0},1000)
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
    $("#dialogtwo #mypicture").on("input propertychange",function(){
        if ($("#dialogtwo #mypicture").val() !== '') {
            $("#dialogtwo #mypicture").css({"font-size":"1rem"});
        }else{
            $("#dialogtwo #mypicture").css({"font-size":"0.8rem"});
        }
    });
    if ($("#dialogtwo #mypicture").val() !== '') {
        $("#dialogtwo #mypicture").css({"font-size":"1rem"});
    }else{
        $("#dialogtwo #mypicture").css({"font-size":"0.8rem"});
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


//小手抖动
// var css = {top:'0.7rem'};  
// setInterval(function(){  
//     $('.mask').animate(css,500,rowBack);  
// },300);  
// function rowBack(){  
//     if(css.top==='0.7rem')  
//         css.top='1.1rem';  
//     else if(css.top==='1.1rem')  
//         css.top='0.7rem';  
// }  

//倒计时
$(document).ready(function() {
    var times = 10 * 100; // 10秒
    //遮罩层禁止滚动
    $('.Mask,.rulebox').bind("touchmove", function (e) {
        e.preventDefault();
    });
    //准备好了
    $(".rulebox_btn").click(function(){
        $('.Mask,.rulebox').fadeOut(300);
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
                times = 1000;
            }
            if(times == 100){
                $(".time").css("display","none");
                $(".timewc").css("display","block");
                $(".mask").fadeIn("slow");
                setTimeout(function(){//三秒后重置时间
                    $(".mask").fadeOut("slow");
                },2000);
            }
            // 获取分钟、毫秒数
            $("#timeout").html(ms);
            $("#timeoutm").html(hm);
        }, 10);
    })
    $('.offer').click(function(){
        times = 1000;
        setTimeout(function(){//三秒后重置时间
            times = 1000;
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
        if(num < 2){
            className = $(this).attr('class');
            $('#dialogBg').fadeIn(300);
            $('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
            return
        }
        $('.num').html(num);
        $(".record .title").after('<ul><li style="width: 18%;"><img src="../images/user-me.png"></li><li>我</li><li class="state"><img src="../images/leadlx.png"></li><li>浙江杭州</li><li id="mybids">¥20.00</li></ul>');
        $(".record ul").last().remove();
        $(".state").eq(1).html('出局');
        $(this).css("display","none");
        $(".being").css("display","block");
        $(".time").css("display","block");
        $(".timewc").css("display","none");
        if(num==3){
            $("#mybids").html('¥20.00')
            $("#current").html('20.00')
            $(".bids").html('若无人出价，我将以20.00拍得本商品')
        }else if(num == 2){
            $('.bidsuccess').fadeIn("slow");
            setTimeout(function(){//两秒后消失
                $('.bidsuccess').fadeOut("slow");
            },1000);
            $("#mybids").html('¥20.10')
            $("#current").html('20.10')
            $(".bids").html('若无人出价，我将以20.10拍得本商品')
            var times = 10 * 100; // 10秒
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
            },10000);
        }else if(num == 1){
            $("#mybids").html('¥20.30')
            $("#current").html('20.30')
            $(".bids").html('若无人出价，我将以20.30拍得本商品')
        }else if(num == 0){
            $("#mybids").html('¥20.50')
            $("#current").html('20.50')
            $(".bids").html('若无人出价，我将以20.50拍得本商品')
        }
        setTimeout(function(){//两秒后添加
            $(".offer").css("display","block");
            $(".being").css("display","none");
        },10000);
    });
    //关闭弹窗
    $('.claseDialogBtn').click(function(){
        $('#dialogBg').fadeOut(300,function(){
            $('#dialog').addClass('bounceOutUp').fadeOut();
            $('#dialogtwo').addClass('bounceOutUp').fadeOut();
            $('#dialogrule').addClass('bounceOutUp').fadeOut();
        });
    });
    $('.hdgz').click(function(){
        className = $(this).attr('class');
        $('#dialogBg').fadeIn(300);
        $('#dialogrule').removeAttr('class').addClass('animated '+className+'').fadeIn();
    });
});

//注册判断手机号
$(document).ready(function () {
    $("#btnSenderMessage").click(function(){
        var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
        var mobile = $("#mytext").val();
        if( mobile == null || mobile == "" ){
            // alert("手机号不能为空！");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('手机号不能为空！');
        }else if(!reg.exec(mobile)){
            // alert("手机号格式不正确");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('手机号格式不正确');
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
                    //用户已注册
                    if(data.code == "7"){
                        $('.Mask1,.rulebox1').fadeIn(300);
                        $('.rulebox_title1').html(data.msg);
                        $(".rulebox_btn1").click(function(){
                            $('.Mask1,.rulebox1').fadeOut(300,function(){
                                window.location.href="share-third.html";
                                return;
                            });
                        })
                    }else{
                        var Request = getData();
                        var activityId = Request['activityId'];
                        var sid = Request['sid'];
                        //短信登录
                        var userPhone = $('#mytext').val();
                        $.ajax({
                            type: "post",
                            url:"/share/doRegister?activityId="+activityId+'&sid='+sid,
                            dataType: "json",
                            data: {userPhone:userPhone},
                            success: function(data){
                                if(data.code != "0"){
                                    $('.Mask1,.rulebox1').fadeIn(300);
                                    $('.rulebox_title1').html(data.msg);
                                    return;
                                }

                                var coin = 0;
                                if (data.data.coin){
                                    coin = data.data.coin;
                                }
                                $('.Mask1,.rulebox1').fadeIn(300);
                                $('.rulebox_title1').html("恭喜你注册成功");
                                $(".rulebox_btn1").click(function(){
                                    $('.Mask1,.rulebox1').fadeOut(300,function(){
                                        window.location.href="receive.html?coin="+coin;
                                    });
                                })


                                $('.hqcode').css("display","none");
                                $(".send").css("display","block");
                                //获取验证码倒计时
                                var setTime;
                                var time = 60;
                                var neo = $("#time").text();
                                setTime = setInterval(function(){
                                    if(time <= 1){
                                        clearInterval(setTime);
                                        $("#time").text(60);
                                        $(".hqcode").css("display","block");
                                        $(".send").css("display","none");
                                        return;
                                    }
                                    if(neo < 60){
                                        $("#time").text();
                                        return;
                                    }
                                    time--;
                                    $("#time").text(time);
                                },1000);
                            },
                            error: function(data){
                                alert("error");
                                return;
                            } ,
                        });


                    }
                },
                error: function(data){
                    alert("error");
                    return;
                } ,
            });
            // alert($("#time").text());
            // window.location.href="code.html";

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
        if( code == null || code == "" ){
            // alert("验证码不能为空！");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('验证码不能为空！');
        }else if(!reg.exec(code)){
            // alert("验证码格式不正确");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('验证码格式不正确');
        }else if( pwd == null || pwd == "" ){
            // alert("密码不能为空！");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('密码不能为空！');
        }else if(!regpwd.exec(pwd)){
            // alert("密码格式不正确");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('密码格式不正确');
        }else{
            // alert("成功");
            $('.Mask1,.rulebox1').fadeIn(300);
            $('.rulebox_title1').html('注册成功');
            $(".rulebox_btn1").click(function(){
                $('.Mask1,.rulebox1').fadeOut(300,function(){
                    window.location.href="receive.html";
                    // window.location.href="lead.html";
                });
            })
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