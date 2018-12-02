var prefix = "/hotel/room";
$(function () {
    load();
});

var load = function () {
    $('#exampleTable')
        .bootstrapTable({
            method: 'get',
            url: prefix + "/list",
            striped: true,
            dataType: "json",
            pagination: true,
            singleSelect: false,
            iconSize: 'outline',
            toolbar: '#exampleToolbar',
            pageSize: 10,
            pageNumber: 1,
            search: true,
            showColumns: true,
            sidePagination : "server",
            queryParamsType : "",
            // //设置为limit则会发送符合RESTFull格式的参数
            queryParams : function(params) {
                return {
                    //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    pageNumber : params.pageNumber,
                    pageSize : params.pageSize,
                    name : $('#searchName').val(),
                };
            },
            responseHandler : function(res){
                console.log(res);
                return {
                    "total": res.data.total,//总数
                    "rows": res.data.records   //数据
                };
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'name',
                    title: '房间名称'
                },
                {
                    field: 'typeName',
                    title: '房间类型'
                },
                {
                    field: 'bedNumber',
                    title: '床数'
                },
                {
                    field: 'airConditioned',
                    title: '空调',
                    align: 'center',
                    formatter: function (value,row,index) {
                        if(value == '0'){
                            return '<span class="label label-danger">无</span>';
                        }else if(value == '1') {
                            return '<span class="label label-danger">有</span>';
                        }
                    }
                },
                {
                    field: 'windowed',
                    title: '窗户',
                    align: 'center',
                    formatter: function (value,row,index) {
                        if(value == '0'){
                            return '<span class="label label-danger">无</span>';
                        }else if(value == '1') {
                            return '<span class="label label-danger">有</span>';
                        }
                    }
                },
                {
                    field: 'televioned',
                    title: '电视',
                    align: 'center',
                    formatter: function (value,row,index) {
                        if(value == '0'){
                            return '<span class="label label-danger">无</span>';
                        }else if(value == '1') {
                            return '<span class="label label-danger">有</span>';
                        }
                    }
                },
                {
                    field: 'hasToilet',
                    title: '厕所',
                    align: 'center',
                    formatter: function (value,row,index) {
                        if(value == '0'){
                            return '<span class="label label-danger">无</span>';
                        }else if(value == '1') {
                            return '<span class="label label-danger">有</span>';
                        }
                    }
                },
                {
                    field: 'price',
                    title: '价格',
                    align: 'center',
                    formatter: function (value,row,index) {
                        value = value / 100;
                        return value;
                    }
                },
                {
                    title : '操作',
                    field : 'id',
                    align : 'center',
                    formatter : function(value, row, index) {
                        var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                            + row.id
                            + '\')"><i class="fa fa-edit "></i></a> ';
                        var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                            + row.id
                            + '\')"><i class="fa fa-remove"></i></a> ';
                        return e + d ;
                    }
                }
            ]
        });
}

function reLoad() {
    var opt = {
        query : {
            name : $('#searchName').val()
        }
    }
    $('#exampleTable').bootstrapTable('refresh', opt);
}

function add(id){
    layer.open({
        type : 2,
        title : '添加房间',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add'
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix + "/remove",
            type : "post",
            data : {
                'id' : id
            },
            success : function(data) {
                if (data.code == 0) {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    })
}

function edit(id) {
    layer.open({
        type : 2,
        title : '房间修改',
        maxmin : true,
        shadeClose : false,
        area : [ '800px', '520px' ],
        content : prefix + '/edit/' + id
    });
}