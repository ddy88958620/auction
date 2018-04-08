var htmlWin;
showUpdateUserPwdWindow = function() {
	// 去除打开此页面的按钮的焦点
	htmlWin = layer
			.open({
				type : 1,
				title : "修改密码",
				shade : 0.3,
				maxmin : false,
				area : '400px',
				anim : 2,

				btnAlign : 'c',
				btn : [ '保存', '重置', '取消' ],
				success : function(layero, index) {
				},
				yes : function(index, layero) {
					submitForm();

				},
				btn2 : function(index, layero) {
					document.getElementById("form").reset();

					return false;
				},
				content : ' <form class="layui-form " id="form" action="">'
						+ '<div class="layui-form-item" style="margin-top: 20px;">'
						+ '<label class="layui-form-label" style="width:120px">旧密码:</label>'
						+ '<div class="layui-input-inline">'
						+ '<input type="password" name="userPassword" lay-verify="required|userPassword" placeholder="请输入旧密码" autocomplete="off" class="layui-input" >'
						+ '</div>'
						+ '</div>'
						+ '<div class="layui-form-item" style="margin-top: 20px;">'
						+ '<label class="layui-form-label" style="width:120px">新密码:</label>'
						+ '<div class="layui-input-inline">'
						+ '<input type="password" id="new1UserPassword" name="new1UserPassword" lay-verify="required|new1UserPassword|userPasswordeq" placeholder="请输入新密码" autocomplete="off" class="layui-input" >'
						+ '</div>'
						+ '</div>'
						+ '<div class="layui-form-item" style="margin-top: 20px;">'
						+ '<label class="layui-form-label" style="width:120px">确认新密码:</label>'
						+ '<div class="layui-input-inline">'
						+ '<input type="password" id="new2UserPassword" name="new2UserPassword" lay-verify="required|userPasswordeq" placeholder="确认新密码" autocomplete="off" class="layui-input" >'
						+ '</div>' + '</div>' + '<button style="display: none;" lay-filter="submitBtn" lay-submit="" id="summitBtn"/>' + '</form>'
			});

};
function submitForm() {
	$("button[lay-filter='submitBtn']").trigger('click');
}

layui.use('form', function() {
	var form = layui.form;
	// 自定义验证规则
	form.verify({
		new1UserPassword : [ /^$|(.+){6,16}$/, '长度必须在6到16之间' ],
		userPasswordeq : function(value, item) {
			if ($('#new1UserPassword').val() != $('#new2UserPassword').val()) {
				return '新密码两次输入不一致';
			}
		}

	});
	form.on('submit(submitBtn)', function(data) {
		$.ajax({
			type : "post",
			url : path + updateUserPwdUrl,
			data : data.field,
			dataType : "json",
			beforeSend : function() {
				layer.load(1, {
					shade : [ 0.1, '#fff' ]
				});
			},
			success : function(result) {
				layer.msg(result.msg);
				if (result.code == '0') {
					layer.close(htmlWin);
				}
			}
		});
		return false;
	});
	if(rpd == 1){
		showUpdateUserPwdWindow();
	}
});
