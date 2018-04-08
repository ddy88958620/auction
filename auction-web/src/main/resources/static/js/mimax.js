function geneDialog(e) {
    var t = {
        lj: {
            id: "lj",
            name: "大熊猫",
            avatar: "../images/passerby1.png"
        },
        lwq: {
            id: "lwq",
            name: "朱磊",
            avatar: "../images/passerby2.png"
        },
        lb: {
            id: "lb",
            name: "Wendy",
            avatar: "../images/passerby3.png"
        }
    };
    _members = $.extend(_members, t),
        _dialog.d0 = [{
            type: "plain",
            author: _members.lwq,
            content: "想买一个iphoneX，谁知道哪里能便宜点么？",
        },
            {
                type: "plain",
                author: _members.lj,
                content: "狗东搞活动时候只要7800元，我吃了两个月的土，已经买下来了！",
            },
            {
                type: "plain",
                author: _members.lb,
                content: "这算神马！我京东120块钱就买到了！"
            }],
        _dialog.d1 = [{
            type: "plain",
            author: _members.me,
            content: "怎么买到的？求个链接~"
        },
            {
                type: "plain",
                author: _members.lb,
                content: "竞拍到的呀~10秒内没人竞拍就中啦，京东自营发货的，刚到手呢~"
            },
            {
                type: "plain",
                author: _members.lb,
                content: "@所有人 正好他们现在搞活动，竞拍成功送神秘礼品呢！点链接：<a href='#' id='gotoNext' onclick='toNextStep()'>http://www.zbswzn.com</a><img src='../images/finger.png' style='width: 2.125rem;height: auto;transform:rotate(-45deg);margin-left:10px;'>"
                // content: "@所有人 你们拍到要请我吃饭啊！链接 <a href='../html/homenew.html'>http://www.zbswzn.com</a>"
            }]
} !

    function(e) {
        function t(e) {
            e = e || {},
                this.maxFlake = e.maxFlake || 200,
                this.flakeSize = e.flakeSize || 10,
                this.fallSpeed = e.fallSpeed || 2,
                this.emojiImage = e.emojiImage
        }
        function a() {
            var e = document.getElementById("emoji");
            this.canvas = e,
                this.ctx = e.getContext("2d")
        }
        function n() {
            for (var e = this.maxFlake,
                     t = this.flakes = [], a = this.canvas, n = 0; e > n; n++) t.push(new r(a.width, a.height, this.flakeSize, this.fallSpeed, this.emojiImage))
        }
        function o() {
            var e = this.maxFlake,
                t = this.flakes;
            ctx = this.ctx,
                canvas = this.canvas,
                that = this,
                ctx.clearRect(0, 0, canvas.width, canvas.height);
            for (var a = 0; e > a; a++) t[a].update(),
                t[a].render(ctx);
            this.loop = l(function() {
                o.apply(that)
            })
        }
        function i() {
            var e = this.ctx,
                t = this.canvas;
            e && e.clearRect(0, 0, t.width, t.height);
            $(".box_ft").find(".input-wrapper").removeClass("J_inputWrapper")
        }
        function r(e, t, a, n, o) {
            this.x = Math.floor(Math.random() * e),
                this.y = Math.floor(Math.random() * t * 1.5) - 1.5 * t,
                this.size = Math.random() * a + 2,
                this.maxSize = a,
                this.speed = 1 * Math.random() + n,
                this.fallSpeed = n,
                this.velY = this.speed,
                this.velX = 0,
                this.stepSize = Math.random() / 30,
                this.step = 0,
                this.emojiImage = o
        }
        e.emojiFall = t;
        var l = e.requestAnimationFrame || e.mozRequestAnimationFrame || e.webkitRequestAnimationFrame || e.msRequestAnimationFrame || e.oRequestAnimationFrame ||
                function(e) {
                    setTimeout(e, 1e3 / 60)
                },
            m = e.cancelAnimationFrame || e.mozCancelAnimationFrame || e.webkitCancelAnimationFrame || e.msCancelAnimationFrame || e.oCancelAnimationFrame;
        t.prototype.start = function() {
            a.apply(this),
                n.apply(this),
                o.apply(this)
        },
            r.prototype.update = function() {
                this.x,
                    this.y;
                this.velX *= .98,
                this.velY <= this.speed && (this.velY = this.speed),
                    this.velX += Math.cos(this.step += .05) * this.stepSize * 5,
                    this.y += this.velY,
                    this.x += this.velX
            },
            r.prototype.reset = function(e, t) {
                this.x = Math.floor(Math.random() * e),
                    this.y = 0,
                    this.size = Math.random() * this.maxSize + 2,
                    this.speed = 1 * Math.random() + this.fallSpeed,
                    this.velY = this.speed,
                    this.velX = 0
            },
            t.prototype.stop = function() {
                this.pause(),
                    // i()
                    $(".box_ft").find(".input-wrapper").removeClass("J_inputWrapper")
            },
            t.prototype.pause = function() {
                m(this.loop)
            },
            t.prototype.resume = function() {
                this.loop = l(function() {
                    o.apply(that)
                })
            },
            r.prototype.render = function(e) {
                var t = new Image;
                t.src = this.emojiImage,
                    t.complete ? e.drawImage(t, this.x, this.y, 84, 84) : (t.onload = function() {
                        e.drawImage(t, this.x, this.y, 84, 84)
                    },
                        t.onerror = function() {})
            }
    } (window);
var _imgUrl = "http://c1.mifile.cn/f/i/hd/2016051101/",
    _userName = me.name,
    _dialog = {},
    _members = {},
    _emoji = new emojiFall({
        maxFlake: 20,
        fallSpeed: 5,
        emojiImage: _imgUrl + "cry.gif"
    }),
    gif = {
        welcome: '<img src="' + _imgUrl + 'welcome.gif">',
        lol: '<img src="' + _imgUrl + 'lol.gif">',
        cry: '<img src="' + _imgUrl + 'cry.gif">'
    },
    _animation = {
        tour: $(".J_map").html()
    },
    _translate = '<span class="trans J_trans">鐐瑰嚮缈昏瘧</span><span class="hide J_actualContent">%placehold%</span>';
$.fn.loadingMask = function() {
    var e = this,
        t = e.css("position").toLowerCase();
    "relative" != t && "absolute" != t && "fixed" != t && e.css("position", "relative"),
        e.css("minHeight", 100);
    var a = $('<div class="loading"><div class="loader loader-white"></div></div>').appendTo(e);
    return function() {
        a.remove(),
            e.css("position", t)
    }
},
    $.fn.scrollSmooth = function(e, t) {
        function a() {
            var e = Math.min(1, (Date.now() - l) / t);
            o.scrollTop = r * e + i,
            1 > e && setTimeout(a, 10)
        }
        var n = this,
            o = n[0],
            i = o.scrollTop,
            r = e - i,
            l = Date.now();
        a()
    },
    $.fn.goSmooth = function(e, t) {
        function a() {
            var e = Math.min(1, (Date.now() - r) / t);
            n.css("margin-bottom", i * e + o),
            1 > e && setTimeout(a, 10)
        }
        var n = this,
            o = 1 * n.css("margin-bottom").replace("px", ""),
            i = e - o,
            r = Date.now();
        a()
    },
    $(document).ready(function() {
        function e() {}
        function t(t) {
            for (var a = new e,
                     n = 0; n < t.length; n++) a.add(t[n]);
            return a
        }
        function a(e) {
            h.html(""),
                unmask = h.loadingMask();
            var t = new Image;
            t.onload = function() {
                var a = t.width,
                    n = t.height,
                    o = $(window).width(),
                    i = $(window).height(); (n > i || a > o) && (n / a > i / o ? (a = a * i / n, n = i) : (n = n * o / a, a = o)),
                    h.css({
                        width: a,
                        height: n,
                        marginLeft: -a / 2,
                        marginTop: -n / 2
                    }),
                unmask && unmask(),
                    unmask = null,
                    h.append('<img src="' + e + '">')
            },
                t.src = e
        }
        function n() {
            _members.me = me
        }
        function o() {
            $(".J_mapWrapper").addClass("animate");
            var e = 0,
                t = setInterval(function() {
                        e >= 9 ? ($(".J_tourtime").html(8), clearInterval(t)) : (e++, $(".J_tourtime").html(e % 9))
                    },
                    5e3 / 9)
        }
        function i() {
            $(".box_ft").find(".input-wrapper").addClass("J_inputWrapper")
            $('body,html').animate({scrollTop:288},1000)
        }
        function r() {
            $(".box_ft").find(".input-wrapper").removeClass("J_inputWrapper")
        }
        function l(e, a) {
            function n(e) {
                void 0 == e && (e = 1e3 * Math.random() + 2000);
                var t = setTimeout(function() {
                        if (l) {
                            var e = m([l.el]);
                            c.append(e),
                                $("#message-push-music")[0].play();
                            var r = $(".J_scrollContent").height(),
                                s = c.height();
                            if (s > r && $(".J_scrollContent").scrollSmooth(s - r + 16, 300), l.el.flag) {
                                var p = l.el.flag;
                                switch (p) {
                                    case "emoji":
                                        _emoji.stop(),
                                            _emoji.start();
                                        break;
                                    case "emoji-welcome":
                                        var h = new emojiFall({
                                            maxFlake: 20,
                                            fallSpeed: 9,
                                            emojiImage: _imgUrl + "welcome.gif"
                                        });
                                        h.start();
                                        break;
                                    case "playVideo":
                                        break;
                                    case "animate-tour":
                                        o();
                                        break;
                                    case "animate-train":
                                        setTimeout(function() {
                                                var e = $(".J_train").find("img"),
                                                    t = e.attr("data-origin");
                                                e.attr("src", t),
                                                    $(".J_train").addClass("animate"),
                                                    $("#train-run-music")[0].play()
                                            },
                                            800),
                                            setTimeout(function() {
                                                    $(".J_train").hide(),
                                                        $("#train-run-music")[0].pause()
                                                },
                                                5e3)
                                }
                            }
                            void 0 != l.el.pause ? n(l.el.pause) : n(),
                                l = l._next
                        } else i(),
                            $(".box_ft").find(".input-wrapper").removeClass("J_inputWrapper"),
                            clearTimeout(t),
                        a && a()
                    },
                    e)
            }
            r();
            var l = t(e)._first,
                m = doT.template($("#messageTpl").html());
            n(0)
        }
        function m(e, t) {
            $(".J_noticeInput").hide(),
            null === t && (t = 100),
            e || (e = "0"),
                setTimeout(function() {
                        $(".J_choiceWrapper").addClass("opened").find(".J_choice").removeClass("choosen").hide(),
                            $(".J_inputWrapper").addClass("opened");
                        var t = $(".J_choiceWrapper").find(".J_choice").filter('[data-choice="' + e + '"]'),
                            a = t.addClass("choosen").show().height(),
                            n = $(".box_ft").height(),
                            o = $("#chatContent").height();
                        $(".box_bd").goSmooth(a, 100),
                            $(".J_scrollContent").scrollSmooth(n + o, 300)
                    },
                    t)
        }
        function s() {
            $(".box_bd").goSmooth(0, 100),
                $(".J_inputWrapper").removeClass("opened"),
                $(".J_choiceWrapper").removeClass("opened")
        }
        function p() {
            n(),
                geneDialog(_userName),
                l(_dialog.d0,
                    function() {
                        $(".J_noticeInput").show()
                    }),
                $(".box_ft").on("click", ".J_choice .J_liNext",
                    function(e) {
                        e.preventDefault();
                        var t = $(this),
                            a = t.attr("data-target-dialog"),
                            n = t.attr("data-target-choice"),
                            o = "false" !== t.attr("data-continue");
                        $(".J_mainChoice").find('.J_liNext[data-target-dialog="' + a + '"]').addClass("disabled"),
                        n || (n = "0"),
                            s(),
                            clearTimeout(window.waitUser),
                            l(_dialog["d" + a],
                                function() {
                                    o && (m(n, 500), window.waitUser = setTimeout(function() {
                                            var e = Math.floor(3 * Math.random() + 1);
                                            l(_dialog["dr_" + e])
                                        },
                                        3e4))
                                }),
                        $(".J_mainChoice").find(".J_liNext").not(".disabled") || clearTimeout(window.waitUser)
                    }),
                $(document).on("click", ".J_inputWrapper",
                    function() {
                        var e = $(".J_choice").filter(".choosen").attr("data-choice");
                        $(".J_choiceWrapper").hasClass("opened") ? s() : m(e, 0)
                    }),
                $(document).on("click", ".J_img",
                    function(e) {
                        var t = $(this),
                            n = t.attr("src").replace(/\.(jpg|jpeg|png|gif)/, "-hd.$1");
                        n && (a(n), $("#J_fullPics").show())
                    }),
                $(document).on("click", "#J_fullPics",
                    function(e) {
                        h.html("").attr("style", ""),
                            $("#J_fullPics").hide()
                    }),
                $(document).on("click", ".J_trans",
                    function(e) {
                        "true" != $(this).attr("translated") && $(this).html($(this).siblings(".J_actualContent").html()).attr("translated", "true")
                    }),
                $(document).on("click", ".J_register",
                    function(e) {
                        var t = $(this);
                        "true" != $(this).attr("registered") && $.ajax({
                            url: "http://xmt.www.mi.com/index.php?id=338&do=book",
                            type: "POST",
                            dataType: "json",
                            success: function(e) {
                                e && 1e3 === e.header.code ? (t.html("鎮ㄥ凡鎴愬姛棰勭害").attr("registered", "true"), l(_dialog.dreg_1)) : l(_dialog.dreg_2)
                            }
                        })
                    }),
                $(document).on("click", ".J_fpVideo",
                    function(e) {
                        var t = $(this).attr("data-src"),
                            a = $(".J_videoOverlay");
                        if (t) {
                            var n = a.loadingMask(),
                                o = document.createElement("iframe");
                            o.onload = function() {
                                n && n(),
                                    n = null
                            },
                                o.src = t,
                                $(o).attr({
                                    frameborder: 0,
                                    allowfullscreen: !0
                                }),
                                $(".J_videoOverlay").show().find(".J_videoWrapper").append(o)
                        }
                    }),
                $(document).on("click", ".J_videoClose",
                    function(e) {
                        $(".J_videoOverlay").hide().find(".J_videoWrapper").html("")
                    }),
                $(document).on("click", ".J_galleryShow",
                    function(e) {
                        $(".J_galleryOverlay").filter('[data-gallery="1"]').show(),
                            $(".J_gallerySlide").css({
                                "line-height": $(window).height() + "px"
                            }),
                            $(".J_gallerySlide").find("img").each(function(e, t) {
                                var a = $(t).attr("data-origin");
                                a && $(t).attr("src", a)
                            }),
                            XIAOMI.app.slide({
                                slideObj: ".J_gallerySlide",
                                edgeType: "rollback",
                                autoPlay: !1
                            });
                        var t = $(".J_galleryClose"),
                            a = $(".J_galleryOverlay");
                        t.on("click",
                            function() {
                                a.hide()
                            })
                    })
        }
        var c = $("#chatContent"),
            h = $("#J_fullPics .pic");
        e.prototype = {
            add: function(e) {
                return this._last ? this._last = this._last._next = {
                    el: e,
                    _next: null
                }: this._last = this._first = {
                    el: e,
                    _next: null
                },
                    this
            }
        },
            p()
    });

function toNextStep() {
    var activityId = $('#activityId').val();
    var sid = $('#sid').val();
    if (!activityId){
        alert('活动id为空，请联系管理员');
        return
    }
    if (!sid){
        alert('未获取分享者id，请联系管理员');
        return
    }
    var url = '/h5/share-second.html?activityId='+activityId+'&sid='+sid;
    $('#gotoNext').attr("href",url);

}