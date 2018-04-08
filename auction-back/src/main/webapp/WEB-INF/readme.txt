1.表格加载http://www.layui.com/doc/modules/table.html#autoRender
官方推荐方法渲染,userList.jsp中是方法渲染写法;缺点如果某个页面不需要默认查询，此时该页面连表头都不显示；为解决此问题参考roleList.jsp和moduleList.jsp页面是自动渲染,
table标签中不指定url,在reload方法中指定url,此时即可实现打开页面不查询，但仍然显示表头
2.权限按钮<script src="<%=common%>/system/btns.js" charset="utf-8"></script>页面引入此脚本,设置var myId = 当前页面主键ID;var path = "${path}";
 在页面合适的地方执行findBtns();函数即可实现查询某个页面的按钮
3.表单的提交按钮不设置lay-submit="" 的时候错误信息是在具体的错误控件上；加上则统一在中间报错