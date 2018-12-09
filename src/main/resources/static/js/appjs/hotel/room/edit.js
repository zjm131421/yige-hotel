var prefix = "/sys/menu"
$(function () {
    validateRule();
    selectLoad();
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/hotel/room/update",
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

function selectLoad() {
    var html = "";
    $.ajax({
        url: '/common/sysDict/list/roomType',
        success: function (data) {
            //加载数据
            var roomType = $('#type').attr("data-roomType");
            for (var i = 0; i < data.length; i++) {
                if (roomType === data[i].id) {
                    html += '<option selected value="' + data[i].id + '">' + data[i].name + '</option>'
                } else {
                    html += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                }
            }
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight: 200
            });
            //点击事件
            $('.chosen-select').on('change', function (e, params) {
                console.log(params.selected);
                var opt = {
                    query: {
                        type: params.selected,
                        name: $('#searchName').val()
                    }
                }
                $('#exampleTable').bootstrapTable('refresh', opt);
            });
        }
    });
}


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
            type: {
                require: icon + "请选择房间类别"
            }
        }
    })
}