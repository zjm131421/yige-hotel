$(function () {
    new Vue({
        el:'#app',
        data:{
            checkInDay:1,
        },
        methods:{
            initCheckOutDate:function () {
                var date;
                if(this.beforeDawn()){
                    date = moment().format("YYYY-MM-DD");
                }else{
                    date = moment().add(1,'day').format("YYYY-MM-DD");
                }

                $("#expectCheckOutDate").val(date);
            },
            beforeDawn:function () {
                var today = moment().format("YYYY-MM-DD");
                return moment().isBefore(today+' 06:00') && moment().isAfter(today + ' 00:00');
            }
        },
        watch:{
            checkInDay:function (val, oldVal) {
                var date;
                if(this.beforeDawn()){
                    date = moment().add(val-1,'day').format("YYYY-MM-DD")
                }else {
                    date = moment().add(val,'day').format("YYYY-MM-DD")
                }
                $("#expectCheckOutDate").val(date);
            }
        },
        mounted:function () {
            this.initCheckOutDate();
        }
    });

    validateRule();
})

$.validator.setDefaults({
    submitHandler: function () {
        open();
    }
});


function open() {
    var roomId = $("#roomId").val();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/hotel/room/order/add/"+roomId,
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
            customerNumberId: {
                required: true
            }
        },
        message: {
            customerName: {
                required: icon + "请输入预订人"
            },
            customerNumberId: {
                require: icon + "请输入预订手机号"
            }
        }
    })
}