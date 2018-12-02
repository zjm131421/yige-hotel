var prefix = "/hotel/room"
$(function () {
    validateRule();
    selectLoad();
})

function selectLoad() {
    var html = "";
    $.ajax({
        url : '/common/sysDict/list/roomType',
        success : function(data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                console.log(data[i])
                html += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
            }
            console.log(html)
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight : 200
            });
            //点击事件
            $('.chosen-select').on('change', function(e, params) {
                console.log(params.selected);
                var opt = {
                    query : {
                        type : params.selected,
                        name : $('#searchName').val()
                    }
                }
                $('#exampleTable').bootstrapTable('refresh', opt);
            });
        }
    });
}

$.validator.setDefaults({

    submitHandler : function() {
        submitAdd();
    }
});
$.validator.addMethod("typeValiy", function(value, element) {
    var val = value;
    if(val =='选择类别'){
        return true;
    }else{
        return false;
    }
}, "请选择类别");

function submitAdd() {
    var price = $('#price');
    price.val(price.val()*100);
    $.ajax({
        cache : true,
        type : "POST",
        url : prefix + "/save",
        data : $('#signupForm').serialize(),
        async : false,
        error : function(request) {
            layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("保存成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                layer.alert(data.msg)
            }
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
            type1: {
                require: icon + "请选择房间类别"
            }
        }
    })
}