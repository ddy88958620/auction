var $,tab,skyconsWeather;
var bindTabClk;
layui.config({
	base : "./resources/layui/lay/modules/"
}).use(['bodyTab','form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element;
		$ = layui.jquery;
		tab = layui.bodyTab();
	// 添加新窗口
		bindTabClk = function(){
		$(".layui-nav .layui-nav-item a").on("click",function(){
		addTab($(this));
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
	})};

	
//打开新窗口
	function addTab(_this){
		tab.tabAdd(_this);
		addRightMenu();
	}
	function addRightMenu(){
		var tabs = $(".layui-tab-title.top_tab li");
		tabs.on('contextmenu', function (e) {
			var $target = e.target.nodeName === 'LI' ? e.target : e.target.parentElement;
			//判断，如果存在右键菜单的div，则移除，保存页面上只存在一个
			if ($(document).find('div.admin-contextmenu').length > 0) {
				$(document).find('div.admin-contextmenu').remove();
			}
			//获取当前点击选项卡的id值
			var id = $($target).find('i.layui-tab-close').data('id');
			//创建一个div
			var div = document.createElement('div');
			//设置一些属性
			div.className = 'admin-contextmenu';
			div.style.width = '120px';
			div.style.backgroundColor = 'white';
			var ul = '<ul>';
			ul += '<li data-target="refresh" title="刷新当前选项卡"><i class="fa fa-refresh layui-icon  layui-anim layui-anim-rotate layui-anim-loop" aria-hidden="true">&#x1002;</i>&nbsp;刷新当前页</li>';
			if(id != undefined){
				ul += '<li data-target="closeCurrent" title="关闭当前选项卡"><i class="fa fa-close fa fa-refresh layui-icon  layui-anim layui-anim-scaleSpring" aria-hidden="true">&#x1006;</i> 关闭当前页</li>';
			}
			ul += '<li data-target="closeOther" title="关闭其他选项卡"><i class="fa fa-window-close-o  layui-icon  layui-anim layui-anim-scale" aria-hidden="true">&#x1007;</i> 关闭其他页</li>';
			ul += '<li data-target="closeAll" title="关闭全部选项卡"><i class="fa fa-window-close-o  layui-icon  layui-anim layui-anim-up" aria-hidden="true" style=" color: #f9081e;">&#xe69c;</i> 全部关闭</li>';
			ul += '</ul>';
			div.innerHTML = ul;
			div.style.top = e.pageY + 'px';
			div.style.left = e.pageX + 'px';
			//将dom添加到body的末尾
			document.getElementsByTagName('body')[0].appendChild(div);
			//获取当前点击选项卡的索引值
			var clickIndex = $($target).attr('lay-id');
			var $context = $(document).find('div.admin-contextmenu');
			$context.mouseleave(function(){
				$context.remove();
			});
			if ($context.length > 0) {
				$context.eq(0).children('ul').children('li').each(function () {
					var $that = $(this);
					//绑定菜单的点击事件
					//绑定菜单的点击事件
					$that.on('click', function () {
						//获取点击的target值
						var target = $that.data('target');
						switch (target) {
						case 'refresh': //刷新当前
							 var iframe = $("div.layui-tab-content").find('iframe[data-id=' + id + ']')[0];
                             var src = iframe.src;
                             iframe.src = src;
							break;
						case 'closeCurrent': //关闭当前
							if (clickIndex !== 0) {
								element.tabDelete("bodyTab", clickIndex);
							}
							break;
						case 'closeOther': //关闭其他
							tabs.each(function () {
                                 var $t = $(this);
                                 var id1 = $t.find('i.layui-tab-close').data('id');
                                 if (id1 != id && id1 !== undefined) {
                                     element.tabDelete("bodyTab", $t.attr('lay-id'));
                                 }
                             });
							break;
						case 'closeAll': //全部关闭
							tabs.each(function () {
                                 var $t = $(this);
                                 if ($t.index() !== 0) {
                                     element.tabDelete("bodyTab", $t.attr('lay-id'));
                                 }
                             });
							break;
						}
						//处理完后移除右键菜单的dom
						$context.remove();
					});
				});
				$(document).on('click', function () {
					$context.remove();
				});
			}
			return false;
		});
	}
	addRightMenu();
})
