$(function(){
	$('#num').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#pass').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	// 按钮是否可点击
	function checkBtn()
	{
		$(".log-btn").off('click');
        var inp = $.trim($('#num').val());
        var pass = $.trim($('#pass').val());
        if (inp != '' && pass != '') {
            $(".log-btn").removeClass("off");
            sendBtn();
        } else {
            $(".log-btn").addClass("off");
        }
	}

	function checkAccount(username){
		if (username == '') {
			$('.num-err').removeClass('hide').find("em").text('请输入账户');
			return false;
		} else {
			$('.num-err').addClass('hide');
			return true;
		}
	}

	function checkPass(pass){
		if (pass == '') {
			$('.pass-err').removeClass('hide').text('请输入密码');
			return false;
		} else {
			$('.pass-err').addClass('hide');
			return true;
		}
	}

	// 登录点击事件
	function sendBtn(){
        $(".log-btn").click(function(){
            // var type = 'phone';
            var inp = $.trim($('#num').val());
            var pass = $.trim($('#pass').val());
            if (checkAccount(inp) && checkPass(pass)) {
                var params = {};
                params.username = inp;
                params.password = pass;

                console.log(params);

                $.ajax({
                    type: "POST",
                    url: ctx+"login",
                    data: params,
                    success: function (r) {
                        if (r.code == 0) {
                            parent.location.href = '/index';
                        } else {
                            layer.msg(r.msg);
                        }
                    }
                });
            } else {
                return false;
            }
        });
	}

	// 登录的回车事件
	$(window).keydown(function(event) {
    	if (event.keyCode == 13) {
    		$('.log-btn').trigger('click');
    	}
    });


	$(".form-data").delegate(".send","click",function () {
		var phone = $.trim($('#num2').val());
		if (checkPhone(phone)) {
				$.ajax({
		            url: '/getcode',
		            type: 'post',
		            dataType: 'json',
		            async: true,
		            data: {phone:phone,type:"login"},
		            success:function(data){
		                if (data.code == '0') {
		                    
		                } else {
		                    
		                }
		            },
		            error:function(){
		                
		            }
		        });
	       	var oTime = $(".form-data .time"),
			oSend = $(".form-data .send"),
			num = parseInt(oTime.text()),
			oEm = $(".form-data .time em");
		    $(this).hide();
		    oTime.removeClass("hide");
		    var timer = setInterval(function () {
		   	var num2 = num-=1;
	            oEm.text(num2);
	            if(num2==0){
	                clearInterval(timer);
	                oSend.text("重新发送验证码");
				    oSend.show();
	                oEm.text("120");
	                oTime.addClass("hide");
	            }
	        },1000);
		}
    });



});