$(function () {
    validateRule();
})

$.validator.setDefaults({
    submitHandler: function () {
        checkOut();
    }
});

function checkOut(){
    var orderId = $("#orderId").val();
    $("#netPrice").val($("#netPrice").val() * 100);
    $.ajax({
        cache: true,
        type: "POST",
        url: "/hotel/room/order/checkOut/"+orderId,
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
            netPrice: {
                required: true
            }
        },
        message: {
            netPrice: {
                required: icon + "请输入应收金额"
            }
        }
    })
}