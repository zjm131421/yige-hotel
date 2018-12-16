$(function () {

    new Vue({
        el: '#app',
        data: {
            type: ''
        },
        methods: {
            initRoomType: function () {
                var html = "";
                $.ajax({
                    url: '/common/sysDict/list/roomType',
                    success: function (data) {
                        //加载数据
                        for (var i = 0; i < data.length; i++) {
                            html += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                        }
                      //  html+='<option value="' + "1"+ '">' + "test" + '</option>'
                        $("#type").append(html);
                        $("#type").chosen({
                            maxHeight: 200
                        });

                        $("#type").on('change', initRoomList());
                    }
                });
            }
        },
        mounted: function () {
            this.initRoomType();
        }
    });

    validateRule();
})

function initRoomList() {
    var type = $("#type").val();
    var html = "";
    $.ajax({
        chche: true,
        type: "POST",
        url: '/hotel/room/book/list',
        data: {
            type:type,
            bookDate:$("#checkOutDate").val()
        },
        async: false,
        success: function (data) {

            //加载数据
            $("#roomId").html('');
            var rooms = data.data.records;
            var room1 = "";

            for (var i = 0; i < rooms.length; i++) {
                if(rooms[i].status == undefined){
                    html += '<option data-price="'+rooms[i].price+'" value="' + rooms[i].roomId + '">' + rooms[i].name + '</option>'
                }
            }
            $("#roomId").html(html);
            $("#roomId").val(room1);
            $("#roomId").trigger("chosen:updated");

            $("#roomId").chosen({
                maxHeight: 200
            });
        }
    });
}

$.validator.setDefaults({
    submitHandler: function () {
        chage();
    }
})

function chage() {
    var orderId = $("#orderId").val();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/hotel/room/order/chage/"+orderId,
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
            roomId: {
                required: true
            }
        },
        message: {
            roomId: {
                required: icon + "请选择房间"
            }
        }
    })
}

function changePrice() {
    $("#price").val($("#roomId option:selected").data("price")/100);
}