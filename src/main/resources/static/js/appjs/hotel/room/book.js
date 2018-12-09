$(function () {
    validateRule();
})

$.validator.setDefaults({
    submitHandler: function () {
        book();
    }
});

function book() {
    var roomId = $("#roomId").val();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/hotel/room/book/"+roomId,
        data: $('#signupForm').serialize(),
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success:function (data) {
            if(data.code == 0){
                parent.layer.msg(data.msg)
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            }else{
                parent.layer.msg(data.msg);
            }
        }
    });
}

function validateRule() {

    var icon = "<i class='fa fa-times-circle'></i> ";

    $("#signupForm").validate({
        rules: {
            customerName: {
                required: true
            },
            customerMobile: {
                required: true
            },
            arrivalTime:{
                required: true
            }
        },
        message: {
            customerName: {
                required: icon + "请输入预订人"
            },
            customerMobile: {
                require: icon + "请输入预订手机号"
            },
            arrivalTime: {
                require: icon + "请选择预抵时间"
            }
        }
    })
}