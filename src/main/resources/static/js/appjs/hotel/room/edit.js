var prefix = "/sys/menu"
$(function () {
    validateRule();

})


function validateRule() {

    var icon = "<i class='fa fa-times-circle'></i> ";

    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            },
            type: {
                required: true
            }
        },
        message: {
            name: {
                required: icon + "请输入房间名称"
            },
            type1: {
                require: icon + "请选择房间类别"
            }
        }
    })
}